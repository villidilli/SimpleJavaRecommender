package com.mindy.dossett;
/*
Look at cosine similarity among movies to calculate ratings
https://towardsdatascience.com/comprehensive-guide-on-item-based-recommendation-systems-d67e40e2b75d
 */

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
        if (minNumCommonUser >=2){
            return similarityScore/(Math.sqrt(nomMovie*nomOther));
        }
        return -100.0;
    }

    @Override
    public void getSimilarity(String movieId) {
        // reset for each movieId similarity list
        similarityList.clear();
        ArrayList<Movie> allMovies = MovieDatabase.filterBy(filter);
        for (Movie other: allMovies) {
            String otherId = other.getId();
            if (!otherId.equals(movieId)) {
                double cosineScore = calCosineSim(null, null, movieId, otherId);
                if (cosineScore != -100.0) {
                    similarityList.add(new RatingLookUp(otherId, cosineScore));
                }
            }
        }
        Collections.sort(similarityList, Collections.reverseOrder());
    }

    @Override
    public ArrayList<RatingLookUp> getSimilarRatings() {
        User user = UserDatabase.getUser(userId);
        ArrayList<String> allRatedMovies = user.getMoviesRated();
        Double userAvg = UserDatabase.getUser(userId).getAvgRating();
        HashMap<String, ArrayList<Double>> similarityRatingMovieMap = new HashMap<String, ArrayList<Double>>();
        ArrayList<RatingLookUp> avgSimilarityRatingList = new ArrayList<RatingLookUp>();
        // need to go through all movies in this func not getSimiliarty Function... for each movie, need to go through rated items by this user of interest
        for (String id1: allRatedMovies){
            getSimilarity(id1);
            double curRating = user.getRating(id1);
            int counter = 0;
            double norm = 0.0;
            double total = 0.0;
            for (RatingLookUp similarityScore:similarityList) {
                String movieId = similarityScore.getLookUpId();
                double avgMovieScore = MovieDatabase.getMovie(movieId).calAvgMovieRating();
                if (avgMovieScore != -1.0) {
                    counter++;
                    double cosineScore = similarityScore.getRatingValue();
                    total += cosineScore * (curRating - avgMovieScore);
                    norm += cosineScore;
                }
                if (counter >= similarityNum) {
                    double predRating = userAvg + (total / norm);
                    if (similarityRatingMovieMap.containsKey(movieId)) {
                        similarityRatingMovieMap.get(movieId).add(predRating);
                    } else {
                        ArrayList<Double> ratings = new ArrayList<Double>();
                        ratings.add(predRating);
                        similarityRatingMovieMap.put(movieId, ratings);
                    }
                }

            }
        }
        for (String movieId: similarityRatingMovieMap.keySet()){
            ArrayList<Double> allScore = similarityRatingMovieMap.get(movieId);
            double total = 0.0;
            for (double score: allScore){
                total += score;
            }
            avgSimilarityRatingList.add(new RatingLookUp(movieId, total/allScore.size()));
        }
        Collections.sort(avgSimilarityRatingList, Collections.reverseOrder());
        return avgSimilarityRatingList;
    }

}
