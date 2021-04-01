package com.mindy.dossett;
/*
POJO for rating object.
 */

public class RatingLookUp implements Comparable<RatingLookUp>{
    private String id;  // either user or movieId
    private double ratingValue;


    public RatingLookUp(String aId, double aValue){
        id = aId;
        ratingValue = aValue;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public String getLookUpId() {
        return id;
    }

    @Override
    public String toString() {
        return "MovieRating{" +
                "LookUpId='" + id + '\'' +
                ", ratingValue=" + ratingValue +
                '}';
    }

    @Override
    public int compareTo(RatingLookUp o) {
        double delta = ratingValue - o.ratingValue;
        if (delta > 0 ) return 1;
        if (delta < 0) return -1;
        return 0;
    }
}
