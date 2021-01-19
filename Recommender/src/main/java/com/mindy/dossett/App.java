package com.mindy.dossett;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException {
        Path p = Paths.get("src/main/resources/ratings.csv");
        Reader reader = Files.newBufferedReader(p);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        for (CSVRecord row : csvParser) {
            String raterId = row.get(0);
            String movieId = row.get(1);
            double rating = Double.parseDouble(row.get(2));
            int epochTime = Integer.parseInt(row.get(3));
            User user = new User(raterId);
            user.addRating(movieId, rating, epochTime);
            System.out.println(user);

        }
//        long unixSeconds = Instant.now().getEpochSecond()*1000L;
//        System.out.println(unixSeconds);
//        System.out.println(System.currentTimeMillis());
//        Long now = new Date().getTime();
//        System.out.println(now);
//        System.out.println((1610296318-1381620027)/3600/24/365);
//        Date date = new java.util.Date(unixSeconds);
//        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//        sdf.setTimeZone(java.util.TimeZone.getDefault());
//        String formattedDate = sdf.format(date);
//        System.out.println(formattedDate);

    }
}

