package com.nerd.favorite18.core.api.code;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.enums.common.EnumMapper;
import com.nerd.favorite18.core.enums.common.EnumMapperValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "0.1 [공통코드, Enum]", description = "Enum 타입, 공통코드 정보")
@Slf4j
@RestController
@RequestMapping("/enum")
@RequiredArgsConstructor
public class EnumController {

    private final EnumMapper enumMapper;

    @Operation(summary = "전체 Enum 정보 조회", description = "전체 Enum 정보를 조회한다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "데이터 조회 성공")
    @GetMapping
    public ApiResponse<Map<String, List<EnumMapperValue>>> fetchAllEnumMap() {
        return ApiResponse.success(enumMapper.getAll());
    }

    @Operation(summary = "Key를 가지고 있는 Enum 조회", description = "Enum의 클래스 명을 가지고 있는지 확인하고, 해당 Enum 정보를 조회한다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "데이터 조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "동일한 명칭의 TypeClass가 존재하지 않음")
    })
    @GetMapping("/{key}")
    public ApiResponse<List<EnumMapperValue>> fetchEnumListByKey(@Parameter(name = "TypeName") @PathVariable("key") String key) {
        if (!enumMapper.isContainsKey(key)) {
            throw new CoreApiException(ErrorType.BAD_REQUEST);
        }
        return ApiResponse.success(enumMapper.get(key));
    }
 }
