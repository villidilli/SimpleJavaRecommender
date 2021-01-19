package com.mindy.dossett;
/*
This class contains all movie related information for all movies from movies.csv
 */

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MovieDatabase {
    private static HashMap<String, Movie> movieMap;
    private static final String movieDataPath = "src/main/resources/movies.csv";

    public MovieDatabase() {
    }

    private static void initialize() throws IOException {
        if (movieMap == null) {
            movieMap = new HashMap<String, Movie>();
            loadMovieData();
        }
    }


    private static ArrayList<Movie> ReadMovieData() throws IOException {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        CSVParser csvParser = new ReadCSVFile().getCSVParser(movieDataPath);
        for (CSVRecord row : csvParser) {
            String movieId = row.get("id");
            String title = row.get("title");
            String year = row.get("year");
            String genres = row.get("genre");
            String director = row.get("director");
            String country = row.get("country");
            String minutes = row.get("minutes");
            String poster = row.get("poster");
            Movie movie = new Movie(movieId, title, year, genres, director, country, poster, minutes);
            movieList.add(movie);
        }
        return movieList;
    }

    private static void loadMovieData() throws IOException {
        ArrayList<Movie> movieList = ReadMovieData();
        for (Movie movie : movieList) {
            movieMap.put(movie.getId(), movie);
        }
    }

    public static boolean containsId(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.containsKey(movieId);
    }

    public static int getYear(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.get(movieId).getYear();
    }

    public static String getGenres(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.get(movieId).getGenres();
    }

    public static String getTitle(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.get(movieId).getTitle();
    }

    public static Movie getMovie(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.get(movieId);
    }

    public static String getPoster(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.get(movieId).getPoster();
    }

    public static int getMinutes(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.get(movieId).getMinutes();
    }

    public static String getCountry(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.get(movieId).getCountry();
    }

    public static String getDirector(String movieId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieMap.get(movieId).getDirector();
    }

    public static int size() {
        return movieMap.size();
    }

    public static ArrayList<String> filterBy(Filter f) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<String>();
        for (String movieId : movieMap.keySet()) {
            if (f.satisfies(movieId)) {
                list.add(movieId);
            }
        }
        return list;
    }

}
