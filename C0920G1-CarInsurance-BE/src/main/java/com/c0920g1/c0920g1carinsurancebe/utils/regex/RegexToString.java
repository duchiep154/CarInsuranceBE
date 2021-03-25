package com.c0920g1.c0920g1carinsurancebe.utils.regex;
import com.c0920g1.c0920g1carinsurancebe.utils.check_input_output.ToUpCase;

import java.util.Scanner;

public class RegexToString {
    public static String regexPhoneNumber(String phoneNumber) {
        Scanner input = new Scanner(System.in);
        while (!RegexToBoolean.regexPhoneNumber(phoneNumber)) {
            if (!RegexToBoolean.regexPhoneNumber(phoneNumber)) {
                System.err.println("Do not true format phone number (0xxxxxxx...):");
            }
            phoneNumber = input.nextLine();
        }
        return phoneNumber;
    }

    public static String regexEmail(String email) {
        Scanner input = new Scanner(System.in);
        while (!RegexToBoolean.regexEmail(email)) {
            if (!RegexToBoolean.regexEmail(email)) {
                System.err.println("Do not true format email (abc@abc.abc):");
            }
            email = input.nextLine();
        }
        return email;
    }

    public static String regexDate(String date) {
        Scanner input = new Scanner(System.in);
        while (!RegexToBoolean.regexDate(date)) {
            if (!RegexToBoolean.regexDate(date)) {
                System.err.println("Do not true format day (dd/MM/yyyy):");
            }
            date = input.nextLine();
        }
        return date;
    }

    public static String regexGender(String gender) {
        Scanner input = new Scanner(System.in);
        while (!RegexToBoolean.regexGender(gender)) {
            if (!RegexToBoolean.regexGender(gender)) {
                System.err.println("Do not true format gender (Male/Female):");
            }
            gender = input.nextLine();
        }
        return ToUpCase.upCaseMore(gender);
    }
}
