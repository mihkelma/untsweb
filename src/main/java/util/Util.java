package util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static String asString(InputStream is) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.encode(password);
    }

    public static String listToString(List<?> list) {
        String i;
        i = "{";
        for (Object obj : list) {
            i += obj;
            System.out.println(obj);
        }
        i += "}";
        return i;
    }

    public static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    public static java.sql.Date getDate(String string) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date tmpDate = new java.sql.Date(121111);
        try {
            tmpDate = new java.sql.Date(dateFormat.parse(string).getTime());
        } catch (Exception e) {
            tmpDate = getCurrentDate();
        }
        return tmpDate;
    }

    public static String formatDateDots(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static String formatDateNoDots(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        return dateFormat.format(date);
    }

    public static String printTwoDecimals(Double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }
}
