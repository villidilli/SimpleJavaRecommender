package com.mindy.dossett;
/*
POJO for movie rating object.
 */

public class MovieRating implements Comparable<MovieRating>{
    private String movieId;
    private double ratingValue;
    private long ratedTime;

    public MovieRating(String aId, double aValue, long aTime){
        movieId = aId;
        ratingValue = aValue;
        ratedTime = aTime;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public long getRatedTime() {
        return ratedTime;
    }

    public String getMovieId() {
        return movieId;
    }

    @Override
    public String toString() {
        return "MovieRating{" +
                "movieId='" + movieId + '\'' +
                ", ratingValue=" + ratingValue +
                ", ratedTime=" + ratedTime +
                '}';
    }

    @Override
    public int compareTo(MovieRating o) {
        if (ratingValue < o.ratingValue) return -1;
        if (ratingValue > o.ratingValue) return 1;
        return 0;
    }
}
