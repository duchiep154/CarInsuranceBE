package com.c0920g1.c0920g1carinsurancebe.utils.regex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class RegexToBoolean {
    public static boolean regexName(String name) {
        final String regex = "([A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪỬỮỰỲỴÝỶỸ]([a-zàáâãèéêìíòóôõùúăđĩũơưăạảấầẩẫậắằẳẵặẹẻẽềềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹ]+)[ ])+[A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪỬỮỰỲỴÝỶỸ]([a-zàáâãèéêìíòóôõùúăđĩũơưăạảấầẩẫậắằẳẵặẹẻẽềềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹ]*)";
        return Pattern.matches(regex, name);
    }

    public static boolean regexEmail(String email) {
        final String regex = "[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]+";
        return Pattern.matches(regex, email);
    }

    public static boolean regexNameService(String nameService) {
        final String regex = "[A-Z][a-z]+";
        return Pattern.matches(regex, nameService);
    }

    public static boolean regexArea(String area) {
        final String regex = "([1-9][0-9][0-9])|([3-9][0-9])|[.][0-9]+";
        return Pattern.matches(regex, area);
    }

    public static boolean regexDate(String date) {
        final String regex = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
        return Pattern.matches(regex, date);
    }

    public static boolean isCheckDateUp18Age(String date) {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateJava = null;
        try {
            dateJava = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert dateJava != null;
        long millisecondsDateJava = dateJava.getTime();
        long milliseconds = currentTime - millisecondsDateJava;
        long seconds = milliseconds / 1000;
        long rateSecondsToYear = 60 * 60 * 24 * 365;
        long age = seconds / rateSecondsToYear;
        return age >= 18;
    }

    public static boolean regexPayRent(String payRent) {
        final String regex = "[1-9][0-9]|[0-9]+";
        return Pattern.matches(regex, payRent);
    }

    public static boolean regexAmountPeopleRent(String AmountPeopleRent) {
        final String regex = "[0][1-9]|[1][0-9]|[2][0]";
        return Pattern.matches(regex, AmountPeopleRent);
    }

    public static boolean regexBonusService(String BonusService) {
        final String regex = "(massage)|(Massage)|(karaoke)|(Karaoke)|(food)|(Food)|(drink)|(Drink)|(car)|(Car)";
        return Pattern.matches(regex, BonusService);
    }

    public static boolean regexAmountFloor(String AmountFloor) {
        final String regex = "[0][1-9]|[1-9][0-9]+";
        return Pattern.matches(regex, AmountFloor);
    }

    public static boolean regexTypeRoom(String TypeRoom) {
        final String regex = "[A-Z][a-z]+";
        return Pattern.matches(regex, TypeRoom);
    }

    public static boolean regexPhoneNumber(String phoneNumber) {
        final String regex = "[0][0-9]+";
        return Pattern.matches(regex, phoneNumber);
    }

    public static boolean regexGender(String phoneNumber) {
        final String regex = "(Male)|(Female)|(male)|(female)";
        return Pattern.matches(regex, phoneNumber);
    }

    public static boolean regexInputString(String str) {
        final String regex = "[^ ][A-Za-z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+";
        return Pattern.matches(regex, str);
    }

    public static boolean regexInputInt(String intNumber) {
        final String regex = "[0-9]+";
        return Pattern.matches(regex, intNumber);
    }

    public static boolean regexInputDouble(String doubleNumber) {
        final String regex = "[0-9]+.[0-9]+";
        return Pattern.matches(regex, doubleNumber);
    }
}
