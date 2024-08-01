package com.nerd.favorite18.core.api._common.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StringUtils {
    public static String getTodayString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        return dateFormat.format(new Date());
    }

    public static Path getUniqueFilePath(Path filePath) {
        int COUNT_START = 1;

        String filename = filePath.getFileName().toString();
        String name = filename.substring(0, filename.lastIndexOf('_'));
        String extension = filename.substring(filename.lastIndexOf('.'));

        while (Files.exists(filePath)) {
            COUNT_START++;
            String newFilename = name + "_" + String.format("%02d", COUNT_START) + extension;
            filePath = filePath.resolveSibling(newFilename);
        }
        return filePath;
    }

    public static String formatElapsedTime(long elapsedMillis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis);

        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        seconds = seconds % 60;

        return String.format("%02d분 %02d초", minutes, seconds);
    }
}
