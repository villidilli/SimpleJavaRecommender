package com.mindy.dossett;

import java.util.ArrayList;
import java.util.Collections;

public class RecommenderRunner {
    public void printRecommendationFor() {
        UserInfoInitializer initializer = new UserInfoInitializer();
        initializer.UpdateNewUser();
        User newUser = UserDatabase.getUser("newUser");
        RepeatFilter filter = new RepeatFilter(newUser);
        ItemBasedRating itemSim = new ItemBasedRating("newUser", 5, 3, filter);
        ArrayList<RatingLookUp> itemRec = itemSim.getSimilarRatings();
        System.out.println(itemRec);
        UserBasedRating userSim = new UserBasedRating("newUser", 50, 5, filter);
        ArrayList<RatingLookUp> userRec = userSim.getSimilarRatings();
        System.out.println(userRec);
        ArrayList<RatingLookUp> Rec = new ArrayList<RatingLookUp>();
        int itemSize = itemRec.size() >= 10 ? 10 : itemRec.size();
        int userSize = userRec.size() >= 10 ? 10 : userRec.size();
        ArrayList<RatingLookUp> itemList = new ArrayList<RatingLookUp>(itemRec.subList(0,itemSize));
        ArrayList<RatingLookUp> userList = new ArrayList<RatingLookUp> (userRec.subList(0, userSize));
        // remove repeat ones
        if (itemSize == 0 && userSize == 0) {
            System.out.println("Sorry, we don't have enough information to provide you with good recommendations at this time.");
        } else {
            if (itemSize > 0 && userSize > 0) {
                System.out.println("Before: "+itemList.size());
                for (int i=0; i < itemSize; i++) {
                    RatingLookUp r1 = itemList.get(i);
                    String id1 = r1.getLookUpId();
                    for (RatingLookUp r2: userList) {
                        String id2 = r2.getLookUpId();
                        if (id1.equals(id2)) {
                            System.out.println("removed!"+ id2 + " "+id1);
                            itemList.remove(r1);
                            break;
                        }
                    }
                }
                System.out.println("After: "+itemList.size());
                Rec.addAll(itemList);
                System.out.println(Rec.size());
                Rec.addAll(userList);
                System.out.println(Rec.size());
                Collections.sort(Rec, Collections.reverseOrder());
            } else if (itemSize > 0) {
                Rec = itemList;
            } else {
                Rec = userList;
            }
            int recItems = Rec.size() >= 10 ? 10 : Rec.size();
            for (int k = 0; k < recItems; k++) {
                String movieId = Rec.get(k).getLookUpId();
                String movieTitle = MovieDatabase.getTitle(movieId);
                System.out.println("We recommend movie <<" + movieTitle + ">> based upon your rating info");
            }
        }
    }
}