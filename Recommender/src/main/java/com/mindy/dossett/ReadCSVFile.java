package com.mindy.dossett;
/*
a class to read in CSV files
 */

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadCSVFile {
    public CSVParser getCSVParser(String dataPath)throws IOException{
        Path p = Paths.get(dataPath);
        Reader reader = Files.newBufferedReader(p);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        return csvParser;
    }
}