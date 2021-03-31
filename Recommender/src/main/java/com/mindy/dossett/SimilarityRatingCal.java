package com.mindy.dossett;

import java.util.ArrayList;

public abstract class SimilarityRatingCal {
    public ArrayList<RatingLookUp> similarityList;
    public String userId;
    public int similarityNum;
    public int minimalRater;
    public Filter filter;

    public SimilarityRatingCal(String id, int neighborSize, int minRater, Filter f) {
        similarityList = new ArrayList<>();
        userId = id;
        similarityNum = neighborSize;
        minimalRater = minRater;
        filter = f;
    }

    public abstract double calCosineSim(User user, User other, String id1, String id2);

    public abstract void getSimilarity();

    public abstract ArrayList<RatingLookUp> getSimilarRatings();
}



