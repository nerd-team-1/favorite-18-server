package com.nerd.favorite18.core.api._common.utils;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConvertUtils {
    public static Integer stringToInteger(String score) {
        try {
            BigDecimal decimalScore = new BigDecimal(score);
            decimalScore = decimalScore.setScale(0, RoundingMode.DOWN);

            return decimalScore.intValue();
        } catch (NumberFormatException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "정수 소수점 변환 실패");
        }
    }

    public static Double stringToDouble(String similarity) {
        try {
            BigDecimal decimalSimilarity = new BigDecimal(similarity);
            decimalSimilarity = decimalSimilarity.setScale(2, RoundingMode.FLOOR);

            return decimalSimilarity.doubleValue();
        } catch (NumberFormatException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "실수 소수점 변환 실패");
        }
    }
}
