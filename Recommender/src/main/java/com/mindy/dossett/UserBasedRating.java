package com.mindy.dossett;
/*
Look at similarity among users to calculate average ratings for all qualified movies
 */

import java.util.ArrayList;
import java.util.Collections;

public class UserBasedRating extends SimilarityRatingCal {

    public UserBasedRating(String id, int neighborSize, int minRater, Filter f) {
        super(id, neighborSize, minRater, f);
    }

    @Override
    public double calCosineSim(User user, User other, String id1, String id2) {
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
        if (minNumCommon >=2) {
            return similarityScore / (Math.sqrt(nomUser * nomOther));
        }
        return -100.0;
        }

    @Override
    public void getSimilarity() {
        User user = UserDatabase.getUser(userId);
        for (User other: UserDatabase.getUsers()){
            String otherId = other.getUserId();
            if (!otherId.equals(userId)){
                double cosineScore = calCosineSim(user,other, null, null);
                if (cosineScore != -100.0) {
                    similarityList.add(new RatingLookUp(otherId, cosineScore));
                }
            }
        }
        Collections.sort(similarityList, Collections.reverseOrder());
    }

    @Override
    public ArrayList<RatingLookUp> getSimilarRatings() {
        getSimilarity();
        int numNeighors = Math.min(similarityList.size(),similarityNum);
        ArrayList<RatingLookUp> similarityRatingList = new ArrayList<RatingLookUp>();
        ArrayList<Movie> movieList= MovieDatabase.filterBy(filter);
        Double userAvg = UserDatabase.getUser(userId).getAvgRating();
        for (Movie movie:movieList){
            String movieId = movie.getId();
            int counter = 0;
            double norm = 0.0;
            double total = 0.0;
            for (int i=0; i < numNeighors; i++){
                RatingLookUp userRating = similarityList.get(i);
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
