package com.mindy.dossett;
/*
Look at cosine similarity among movies to calculate ratings
https://towardsdatascience.com/comprehensive-guide-on-item-based-recommendation-systems-d67e40e2b75d
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ItemBasedRating extends SimilarityRatingCal {


    public ItemBasedRating(String id, int neighborSize, int minRater, Filter f) {
        super(id, neighborSize, minRater, f);
    }

    public double calItemSim(String id1, String id2) {
        double similarityScore = 0.0;
        double nomMovie = 0.0;
        double nomOther = 0.0;
        int minNumCommonUser = 0;
        ArrayList<User> userList = UserDatabase.getUsers();
        for (User rater: userList){
            if (rater.hasRating(id1) && rater.hasRating((id2))){
                minNumCommonUser ++;
                double userAvg = rater.getAvgRating();
                double m1Score = rater.getRating(id1) - userAvg;
                double m2Score = rater.getRating(id2) - userAvg;
                similarityScore += m1Score*m2Score;
                nomMovie += Math.pow(m1Score,2);
                nomOther += Math.pow(m2Score,2);
            }
        }
        if (minNumCommonUser >= minimalRater){
            return similarityScore/(Math.sqrt(nomMovie*nomOther));
        }
        return -100.0;
    }

    public HashMap<String, ArrayList<RatingLookUp>> getSimilarityScores() {
        HashMap<String, ArrayList<RatingLookUp>> allCosineScores = new HashMap<String, ArrayList<RatingLookUp>>();
        // movie i
        ArrayList<Movie> allMovies = MovieDatabase.filterBy(filter);
        User user = UserDatabase.getUser(userId);
        // movie j
        ArrayList<String> allRatedMovies = user.getMoviesRated();
        for (Movie other : allMovies) {
            String otherId = other.getId();
            ArrayList<RatingLookUp> newList = new ArrayList<RatingLookUp>();
            for (String movieId : allRatedMovies) {
                if (!otherId.equals(movieId)) {
                    double cosineScore = calItemSim(movieId, otherId);
                    if (cosineScore != -100.0) {
                        newList.add(new RatingLookUp(movieId, cosineScore));
                    }
                }
            }
            if (newList.size() >= similarityNum) {
                allCosineScores.put(otherId, newList);
            }
        }
        return allCosineScores;
    }

    @Override
    public ArrayList<RatingLookUp> getSimilarRatings() {
        ArrayList<RatingLookUp> calRatings = new ArrayList<RatingLookUp>();
        User user = UserDatabase.getUser(userId);
        Double userAvg = user.getAvgRating();
        HashMap<String, ArrayList<RatingLookUp>> allScores = getSimilarityScores();
        for (String movie: allScores.keySet()){
            ArrayList<RatingLookUp> allSims = allScores.get(movie);
            Double avgR1 = MovieDatabase.getMovie(movie).calAvgMovieRating();
            double norm = 0.0;
            double total = 0.0;
            int counter = 0;
            for (RatingLookUp rate:allSims ){
                String ratedId = rate.getLookUpId();
                Double avgR2 = MovieDatabase.getMovie(ratedId).calAvgMovieRating();
                Double userRating = user.getRating(ratedId);
                if (avgR2 != -1.0) {
                    counter++;
                    Double cosine = rate.getRatingValue();
                    total += cosine * (userRating - avgR2);
                    norm += Math.abs(cosine);
//                    if (movie.equals("1446192")){
//                        System.out.println(cosine+" "+userRating+" "+ avgR2);
//                        System.out.println(total+" "+norm);
//                    }
                }
            }
            if (counter >= similarityNum) {
                double predRating = avgR1 + (total / norm);
                calRatings.add(new RatingLookUp(movie, predRating));
            }
        }
        Collections.sort(calRatings, Collections.reverseOrder());
        return calRatings;
    }
}
