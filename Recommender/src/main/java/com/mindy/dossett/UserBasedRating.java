package com.mindy.dossett;
/*
Look at similarity among users to calculate average ratings for all qualified movies
https://www.geeksforgeeks.org/user-based-collaborative-filtering/
 */

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.Collections;

public class UserBasedRating extends SimilarityRatingCal {

    public UserBasedRating(String id, int neighborSize, int minRater, Filter f) {
        super(id, neighborSize, minRater, f);
    }

    private double calUserSim(User user, User other) {
        double similarityScore = 0.0;
        double nomUser = 0.0;
        double nomOther = 0.0;
        int minNumCommon = 0;
        ArrayList<String> userMovies = user.getMoviesRated();
        double userAvg = user.getAvgRating();
        ArrayList<String> otherMovies = other.getMoviesRated();
        double otherAvg = other.getAvgRating();
        for (String mid1: userMovies) {
            for (String mid2 : otherMovies) {
                if (mid1.equals(mid2)) {
                    minNumCommon++;
                    double userScore = user.getRating(mid1) - userAvg;
                    double otherScore = other.getRating(mid2) - otherAvg;
                    similarityScore += (userScore) * (otherScore);
                    nomUser += Math.pow(userScore, 2);
                    nomOther += Math.pow(otherScore, 2);
                }
            }
        }
        if (minNumCommon >= 2 && similarityScore != 0.0 && nomUser != 0.0 && nomOther != 0.0) {
                return similarityScore / (Math.sqrt(nomUser * nomOther));
        }
        return -100.0;
        }


    private ArrayList<RatingLookUp> getUserSimilarity() {
        ArrayList<RatingLookUp> similarityScore = new ArrayList<RatingLookUp>();
        User user = UserDatabase.getUser(userId);
        for (User other: UserDatabase.getUsers()){
            String otherId = other.getUserId();
            if (!otherId.equals(userId)){
                double cosineScore = calUserSim(user,other);
                if (cosineScore != -100.0) {
                    similarityScore.add(new RatingLookUp(otherId, cosineScore));
                }
            }
        }
        Collections.sort(similarityScore, Collections.reverseOrder());
        return similarityScore;
    }

    @Override
    public ArrayList<RatingLookUp> getSimilarRatings() {
        ArrayList<RatingLookUp> similarityScore = getUserSimilarity();
        int numNeighors = Math.min(similarityScore.size(),similarityNum);
        ArrayList<RatingLookUp> similarityRatingList = new ArrayList<RatingLookUp>();
        ArrayList<Movie> movieList= MovieDatabase.filterBy(filter);
        Double userAvg = UserDatabase.getUser(userId).getAvgRating();
        for (Movie movie:movieList){
            String movieId = movie.getId();
            int counter = 0;
            double norm = 0.0;
            double total = 0.0;
            for (int i=0; i < numNeighors; i++){
                RatingLookUp userRating = similarityScore.get(i);
                Double cosineScore = userRating.getRatingValue();
                String otherId = userRating.getLookUpId();
                User userOther = UserDatabase.getUser(otherId);
                double otherAvg = userOther.getAvgRating();
                double rating = userOther.getRating(movieId);
                if (rating != -1 ){
                    counter++;
                    norm += Math.abs(cosineScore);
                    total += (rating-otherAvg)*cosineScore;
                }
            }
            if (counter >= minimalRater) {
                double predRating = userAvg+(total/norm);
                similarityRatingList.add(new RatingLookUp(movieId, predRating));
            }
        }
        Collections.sort(similarityRatingList, Collections.reverseOrder());
        return similarityRatingList;
    }
}
