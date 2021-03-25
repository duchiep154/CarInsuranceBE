package com.c0920g1.c0920g1carinsurancebe.utils.regex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TimeToMilliseconds {
    public static long dateToMilliseconds(String date) {
            Date dateJava = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dateJava = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert dateJava != null;
            return dateJava.getTime();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String date = RegexToString.regexDate(input.nextLine());
        System.out.println(dateToMilliseconds(date));
    }
}
