package com.mindy.dossett;

import java.util.ArrayList;
import java.util.Collections;

public class RecommenderRunner {
    public void printRecommendationFor(){
        UserInfoInitializer initializer = new UserInfoInitializer();
        initializer.UpdateNewUser();
        User newUser = UserDatabase.getUser("newUser");
        RepeatFilter filter = new RepeatFilter(newUser);
        ItemBasedRating itemSim = new ItemBasedRating("newUser",5,3, filter);
        ArrayList<RatingLookUp> itemRec = itemSim.getSimilarRatings();
        System.out.println(itemRec);
        UserBasedRating userSim = new UserBasedRating("newUser",50,5, filter);
        ArrayList<RatingLookUp> userRec = userSim.getSimilarRatings();
        System.out.println(userRec);
        ArrayList<RatingLookUp> Rec = new ArrayList<RatingLookUp>();
        ArrayList<String> repeatMovies = new ArrayList<String>();
        int itemSize = itemRec.size() >= 10 ? 10: itemRec.size();
        int userSize = userRec.size() >= 10 ? 10: userRec.size();
        // remove repeat ones
        if (itemSize == 0 && userSize == 0){
            System.out.println("Sorry, we don't have enough information to provide you with good recommendations at this time.");
        } else if (itemSize > 0 && userSize > 0){
            for (int i = 0; i < itemSize; i++){
                String movieId = itemRec.get(i).getLookUpId();
                for (int j = 0; j<userSize; j++){
                    String id2 = userRec.get(j).getLookUpId();
                    if (movieId.equals(id2)){
                        repeatMovies.add(movieId);
                        break;
                    }
                }
            }
            for (String id: repeatMovies){
                for (RatingLookUp rec: itemRec){
                    if (!rec.getLookUpId().equals(id)){
                        Rec.add(rec);
                    }
                }
            }
            for (RatingLookUp rec: userRec){
                Rec.add(rec);
            }
            Collections.sort(Rec, Collections.reverseOrder());
        } else if (itemSize > 0) {
            for (RatingLookUp rec: itemRec){
                Rec.add(rec);
            }
        } else {
            for (RatingLookUp rec: userRec){
                Rec.add(rec);
            }
        }
        int recItems = Rec.size() >=10 ? 10: Rec.size();
        if (recItems > 0) {
            System.out.println(recItems);
            for (int k = 0; k < recItems; k++) {
                String movieId = Rec.get(k).getLookUpId();
                String movieTitle = MovieDatabase.getTitle(movieId);
                System.out.println("We recommend movie <<" + movieTitle + ">> based upon your rating info");
            }
        }
    }
}
