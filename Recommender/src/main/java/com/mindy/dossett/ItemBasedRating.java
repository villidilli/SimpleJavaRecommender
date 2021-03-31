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

    @Override
    public double calCosineSim(User user, User other, String id1, String id2) {
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
        if (minNumCommonUser >=minimalRater){
            return similarityScore/(Math.sqrt(nomMovie*nomOther));
        }
        return -100.0;
    }

    @Override
    public void getSimilarity() {

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
                    double cosineScore = calCosineSim(null, null, movieId, otherId);
                    if (cosineScore != -100.0) {
                        newList.add(new RatingLookUp(movieId, cosineScore));
                    }
                }
            }
            if (newList.size() >= similarityNum) {
                allCosineScores.put(otherId, newList);
//                System.out.println(otherId);
//                System.out.println(allCosineScores.get(otherId));
            }
        }
//        System.out.println("after for loop");
//        for (String id: allCosineScores.keySet()){
//            System.out.println(id);
//            System.out.println(allCosineScores.get(id));
//        }
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
//            System.out.println(avgR1);
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
                    System.out.println(cosine);
                    total += cosine* (userRating - avgR2);
                    norm += cosine;
                }
            }
            System.out.println(norm+" "+counter+" "+total);
            if (counter >= similarityNum && norm > 0.0) {
                double predRating = avgR1 + (total / norm);
                calRatings.add(new RatingLookUp(movie, predRating));
            }
        }
        // scale between 1 to 10 ((scale = (X-xmin)/(Xmax-Xmin))*10;
        Collections.sort(calRatings, Collections.reverseOrder());
        return calRatings;
    }

}
