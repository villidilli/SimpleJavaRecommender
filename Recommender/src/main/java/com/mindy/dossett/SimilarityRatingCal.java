package com.mindy.dossett;

import java.util.ArrayList;

public abstract class SimilarityRatingCal {
    public String userId;
    public int similarityNum;
    public int minimalRater;
    public Filter filter;

    public SimilarityRatingCal(String id, int neighborSize, int minRater, Filter f) {
        userId = id;
        similarityNum = neighborSize;
        minimalRater = minRater;
        filter = f;
    }

    public abstract ArrayList<RatingLookUp> getSimilarRatings();
}



