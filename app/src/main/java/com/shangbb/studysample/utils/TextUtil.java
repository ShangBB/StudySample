package com.shangbb.studysample.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    public static boolean isPhone(String content) {
        if (content == null) {
            return false;
        }
        String arr = "^[1][34578]\\d{9}$";
        Pattern pattern = Pattern.compile(arr);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static boolean isMobilePhone(String content) {
        if (content == null) {
            return false;
        }
        String arr = "^((\\+86)|(86))?[1][34578]\\d{9}$";
        Pattern pattern = Pattern.compile(arr);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static boolean isPhoneAccount(String content) {
        if (content == null) {
            return false;
        }
        String arr = "^((\\+86)|(86))?[1-9][34578]\\d{9}$";
        Pattern pattern = Pattern.compile(arr);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static boolean is400(String content) {
        if (content == null) {
            return false;
        }
        String arr = "^[4][0][0][0-9]+";
        Pattern pattern = Pattern.compile(arr);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static String formatUrl(String url) {
        return url.replaceAll("[\\|/]", "_");
    }

    public static boolean isPWEasy(String pw) {
        String format = "[\\da-zA-Z]{6,20}";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(pw);
        return matcher.matches();
    }

    public static boolean isEmpty(String content) {
        if (content == null || content.trim().equals("")) {
            return true;
        }

        return false;
    }

    public static boolean isInteger(String content) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static String formatTime(int time) {
        if (time >= 10) {
            return time + "";
        }

        return "0" + time;
    }

    public static String encodeUrl(String str, String charset)
            throws UnsupportedEncodingException {
        String zhPattern = "[\\u4e00-\\u9fa5]+";
        str = str.replaceAll(" ", "+");
        Pattern p = Pattern.compile(zhPattern);
        Matcher m = p.matcher(str);
        StringBuffer b = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
        }
        m.appendTail(b);
        return b.toString();
    }

}
