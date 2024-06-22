package com.nerd.favorite18.core.api._common.utils;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import org.springframework.util.ObjectUtils;

public class FavoriteStringUtils {
    public static String removeWhitespace(String str) {
        if (ObjectUtils.isEmpty(str)) {
            throw new CoreApiException(ErrorType.NULL_POINT);
        }
        return str.replaceAll(" " , "").trim();
    }
}
