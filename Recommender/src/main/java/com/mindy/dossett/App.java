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

    public static void main(String[] args){
        UserBasedRating userR = new UserBasedRating("2",100,10,
                new RepeatFilter(UserDatabase.getUser("2")));
        System.out.println(userR.getSimilarRatings());
    }
}

