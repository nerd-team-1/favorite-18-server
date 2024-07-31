package com.nerd.favorite18.core.api.model.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api._common.utils.StringUtils;
import com.nerd.favorite18.core.api.model.converter.ModelConverter;
import com.nerd.favorite18.core.api.model.dto.request.ModelScoreRequest;
import com.nerd.favorite18.core.api.model.dto.response.ModelScoreListResponse;
import com.nerd.favorite18.core.api.model.dto.response.ModelScoreResponse;
import com.nerd.favorite18.core.api.model.dto.request.ModelUploadRequest;
import com.nerd.favorite18.core.api.model.dto.response.ModelUploadResponse;
import com.nerd.favorite18.core.api.model.model.ScoreResult;
import com.nerd.favorite18.core.api.model.service.ModelScoreService;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Business
public class ModelScoreBusiness {
    @Value("${model.container-id}")
    private String containerId;
    @Value("${model.recorded-path}")
    private String recordedPath;
    @Value("${model.result-path}")
    private String resultPath;

    private final ModelScoreService modelScoreService;
    private final ModelConverter modelConverter;

    private final ObjectMapper objectMapper;

    public List<ModelScoreListResponse> getMyScoreList(UserDto userDto) {

        return modelScoreService.getScoreList(userDto);
    }

    public ModelUploadResponse saveFile(UserDto userDto, MultipartFile file, ModelUploadRequest request) {
        String today = StringUtils.getTodayString();
        String filename = userDto.getId() + "_" + today + "_" + request.getSongId() + "_01.m4a";

        Path filePath = Paths.get(recordedPath, filename);
        try {
            filePath = StringUtils.getUniqueFilePath(filePath);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            log.info("녹음 파일 저장 완료: {}", filePath);
        } catch (IOException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "파일 저장에 실패하였습니다.");
        }

        String newPath = filePath.getFileName().toString();
        String recordedFilename = newPath.substring(0, newPath.lastIndexOf('.'));
        final Long modelScoreId = modelScoreService.saveRecord(userDto, request.getSongId(), recordedFilename);

        return ModelUploadResponse.of(modelScoreId, recordedFilename);
    }

    public ModelScoreResponse scoreModel(ModelScoreRequest request) {
        String recordedFilename = request.getRecordedFilename();
        String command = String.format("docker exec %s bash -c \"~/model/fav18_score %s %s\"",
                containerId, request.getOriginalFilename(), recordedFilename);

        File resultFile = new File(resultPath + "/" + recordedFilename + "/analysis_result.json");
        log.info("resultPath: {}", resultFile.getPath());

        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            log.info("점수 분석 모델 실행...");
            Process process = Runtime.getRuntime().exec(command);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line);
                }
                while ((line = errorReader.readLine()) != null) {
                    log.error(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new CoreApiException(ErrorType.DEFAULT_ERROR, "점수 분석 실행 중 오류가 발생하였습니다.");
            }

        } catch (IOException | InterruptedException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "점수 분석에 실패하였습니다.");
        }
        stopwatch.stop();
        long elapsedMillis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        log.info("점수 분석 작업 완료. 소요 시간: {} ms (약 {})", elapsedMillis, StringUtils.formatElapsedTime(elapsedMillis));

        ScoreResult scoreResult;
        try {
            scoreResult = objectMapper.readValue(resultFile, ScoreResult.class);
        } catch (IOException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "결과파일 작성에 실패하였습니다.");
        }

        modelScoreService.saveScore(request.getModelScoreId(), scoreResult, elapsedMillis);

        return modelConverter.toResponse(recordedFilename, scoreResult);
    }
}
