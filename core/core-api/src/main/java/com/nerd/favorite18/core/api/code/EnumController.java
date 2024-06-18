package com.nerd.favorite18.core.api.code;

import com.nerd.favorite18.core.api.support.error.CoreApiException;
import com.nerd.favorite18.core.api.support.error.ErrorType;
import com.nerd.favorite18.core.api.support.response.ApiResponse;
import com.nerd.favorite18.core.enums.common.EnumMapper;
import com.nerd.favorite18.core.enums.common.EnumMapperValue;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/enum")
@RequiredArgsConstructor
public class EnumController {

    private final EnumMapper enumMapper;

    @GetMapping
    public ApiResponse<Map<String, List<EnumMapperValue>>> fetchAllEnumMap() {
        return ApiResponse.success(enumMapper.getAll());
    }

    @GetMapping("/{key}")
    public ApiResponse<List<EnumMapperValue>> fetchEnumListByKey(@PathVariable String key) {
        if (!enumMapper.isContainsKey(key)) {
            throw new CoreApiException(ErrorType.BAD_REQUEST);
        }
        return ApiResponse.success(enumMapper.get(key));
    }
 }
