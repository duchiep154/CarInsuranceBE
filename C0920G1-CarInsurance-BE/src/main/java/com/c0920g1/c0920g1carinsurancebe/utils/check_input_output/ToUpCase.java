package com.c0920g1.c0920g1carinsurancebe.utils.check_input_output;

public class ToUpCase {
    public static String upCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String upCaseMore(String str) {
        str = str.trim();
        String[] arrStr = str.split("\\s+");
        StringBuilder format = new StringBuilder();
        for (String a : arrStr) {
            String b = upCase(a) + " ";
            format.append(b);
        }
        return format.toString().trim();
    }
}
