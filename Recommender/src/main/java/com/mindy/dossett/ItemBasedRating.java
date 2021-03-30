package com.mindy.dossett;
/*
Look at cosine similarity among movies to calculate ratings
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
    public void getSimilarity() {
        User user = UserDatabase.getUser(userId);
        ArrayList<String> allMovies = user.getMoviesRated();
        for (String id1: allMovies){
            for (Movie other: MovieDatabase.filterBy(filter)) {
                String otherId = other.getId();
                if (!otherId.equals(id1)) {
                    double cosineScore = calCosineSim(null, null, id1, otherId);
                    if (cosineScore != -100.0) {
                        similarityList.add(new RatingLookUp(otherId, cosineScore));
                    }
                }
            }
        }
        Collections.sort(similarityList, Collections.reverseOrder());
    }

    @Override
    public ArrayList<RatingLookUp> getSimilarRatings() {
//        getSimilarity();
//        int numNeighors = Math.min(similarityList.size(),similarityNum);
//        ArrayList<RatingLookUp> similarityRatingList = new ArrayList<RatingLookUp>();
//        Double userAvg = UserDatabase.getUser(userId).getAvgRating();
//        for (RatingLookUp similarityScore:similarityList){
//            String movieId = similarityScore.getLookUpId();
//            int counter = 0;
//            double norm = 0.0;
//            double total = 0.0;
//            Double cosineScore = similarityScore.getRatingValue();
//                String otherId = userRating.getLookUpId();
//                User userOther = UserDatabase.getUser(otherId);
//                double otherAvg = userOther.getAvgRating();
//                double rating = userOther.getRating(movieId);
//                if (rating != -1 ){
//                    counter++;
//                    norm += Math.abs(cosineScore);
//                    total += (rating-otherAvg)*cosineScore;
//                }
//            }
//            if (counter >= minimalRater) {
//                double predRating = userAvg+(total/norm);
//                similarityRatingList.add(new RatingLookUp(movieId, predRating));
//            }
//        }
//        Collections.sort(similarityRatingList, Collections.reverseOrder());
//        return similarityRatingList;
        return null;
    }

}
