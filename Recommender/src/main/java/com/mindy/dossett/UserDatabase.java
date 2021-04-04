package com.mindy.dossett;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserDatabase {
    private static HashMap<String, User> userMap;
    private static final String userDataPath = "src/main/resources/ratings.csv";

    public UserDatabase(){}

    public static void initialize() throws IOException {
        if (userMap == null){
            userMap = new HashMap<String, User>();
            loadUserData();
        }
    }

    private static void loadUserData() throws IOException {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        CSVParser csvParser = new ReadCSVFile().getCSVParser(userDataPath);
        for (CSVRecord row: csvParser){
            String userId = row.get("rater_id");
            String movieId = row.get("movie_id");
            Double rating = Double.parseDouble(row.get("rating"));
            int time = Integer.parseInt(row.get("time"));
            if (!userMap.containsKey(userId)){
                userMap.put(userId, new User(userId));
            }
            userMap.get(userId).addRating(movieId, rating);
        }
    }

    public static void updateUserInfo(User user){
        String userId = user.getUserId();
        userMap.put(userId, user);
    }



    public static User getUser(String userId) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userMap.get(userId);
    }

    public static ArrayList<User> getUsers() {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<User> userList = new ArrayList<User>(userMap.values());

        return userList;
    }

    public static int size() {
        return userMap.size();
    }
}
