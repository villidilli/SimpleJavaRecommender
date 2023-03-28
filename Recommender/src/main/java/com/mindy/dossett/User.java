package com.mindy.dossett;

import java.util.HashMap;
import java.util.ArrayList;
import static java.util.stream.Collectors.toCollection;

/*
This object represents a single User, a record from UserDatabase
 */

public class User {
    private String userId;
    private HashMap<String, RatingLookUp> infoMap; // хранит в себе К - айди фильма / V - его рейтинг

    public User(String aUserId){
        userId = aUserId;
        infoMap = new HashMap<String, RatingLookUp>();
    }

    public void addRating(String movieId,double rating){
        infoMap.put(movieId, new RatingLookUp(movieId, rating));
    }

    public boolean hasRating(String movieId){
        return infoMap.containsKey(movieId);
    }

    public String getUserId() {
        return userId;
    }

    public double getRating(String movieId){
        if (infoMap.containsKey(movieId)){
            return infoMap.get(movieId).getRatingValue();
        }
        return -1;
    }
    
    public double getAvgRating(){
        int num = numRatings();
        if (num > 0) {
            double total = 0.0;
            for (RatingLookUp rating : infoMap.values()) {
                total += rating.getRatingValue(); // возвращает значение поле рейтинга
            }
            return (double)(total /num);
        }
        return 0.0;
    }

    public int numRatings(){
        return infoMap.size();
    }

    public ArrayList<String> getMoviesRated(){
        return infoMap.keySet().stream().collect(toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", numOfRatings =" + infoMap.size() +
                '}';
    }
}
