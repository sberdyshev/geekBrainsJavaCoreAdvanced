package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils;

/**
 * @author SBerdyshev
 */
public class Message {
    public static String getDataFromLine(final String line, final String startTag, final String endTag) {
        final int positionOfStartCommand = line.indexOf(startTag);
        final int positionOfEndCommand = line.indexOf(endTag);
        final int positionOfDataStart = positionOfStartCommand + startTag.length() + 1;
        final int positionOfDataEnd = positionOfEndCommand - 1;
        final boolean isCommandPositionsDifferencePositive = (positionOfEndCommand > positionOfStartCommand);
        final boolean isDataStartEndPositionsDifferencePositive = (positionOfDataEnd > positionOfDataStart);
        String data = null;
        if (isCommandPositionsDifferencePositive && isDataStartEndPositionsDifferencePositive) {
            data = line.substring(positionOfDataStart, positionOfDataEnd);
        }
        return data;
    }
}
