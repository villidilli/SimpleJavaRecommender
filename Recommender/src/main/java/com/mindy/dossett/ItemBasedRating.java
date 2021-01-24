package com.mindy.dossett;
/*
Look at cosine similarity among movies to calculate ratings
 */

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemBasedRating extends SimilarityRatingCal {


    public ItemBasedRating(String id, int neighborSize, int minRater, Filter f) {
        super(id, neighborSize, minRater, f);
    }

    @Override
    public double calCosineSim(User user, User other) {
        return 0;
    }

    @Override
    public void getSimilarity() {

    }

    @Override
    public ArrayList<RatingLookUp> getSimilarRatings() {
        return null;
    }

}
