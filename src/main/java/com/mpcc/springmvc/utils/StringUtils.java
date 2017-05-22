/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.utils;

/**
 *
 * @author Huy_Nam
 */
public class StringUtils {

    public static String getEndString(String input) {
        return input.split("-")[1].substring(0, 6);
        //replaceAll("\\D", "");
    }

    public static String getExtensionFromChannel(String channel) {
        return channel.split("-")[0].replace("SIP/", "");
    }

    public static String getExtTransfer(String input) {
        return input.split("@")[0].split("\\/")[1];
    }

    public static boolean isNull(String input) {
        if (input == null || input.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static String prefixSip(String input) {
        StringBuilder sb = new StringBuilder("SIP/");
        return sb.append(input).toString();
    }

    public static boolean isSameChannel(String channel1, String channel2) {
        String channel11 = channel1.split("-")[1].substring(0, 6);
        String channel21 = channel2.split("-")[1].substring(0, 6);
        return channel11.equals(channel21);
    }

    public static boolean checkContainStr(String input, String strComp) {
        return !isNull(input) && input.contains(strComp);
    }

    public static int convertToInt(String input) {
        try {
            return Integer.valueOf(input.trim());
        } catch (Exception e) {
            return 0;
        }
    }
    public static long convertToLong(String input) {
        try {
            return Long.valueOf(input.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
