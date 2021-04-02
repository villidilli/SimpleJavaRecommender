package com.mindy.dossett;

/*
This is the first Java class used to get initial User rating info
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class UserInfoInitializer {

    public ArrayList<String> getMovieForRating(){
        // only include more recent movies for young audiences
        ArrayList<Movie> allMovies = MovieDatabase.filterBy(new YearAfterFilter(1980));
        // Find the top rated movies
        ArrayList<RatingLookUp> allRatings = new ArrayList<RatingLookUp>();
        //Find the most watched movies
        ArrayList<RatingLookUp> allCounts = new ArrayList<RatingLookUp>();
        for (Movie movie: allMovies){
            double rating = movie.calAvgMovieRating();
            int cnt = movie.getViewCount();
            if (cnt != 0){
                String id = movie.getId();
                allRatings.add(new RatingLookUp(id, rating));
                allCounts.add(new RatingLookUp(id, cnt));
            }
        }
        Collections.sort(allRatings, Collections.reverseOrder());
        Collections.sort(allCounts, Collections.reverseOrder());
        ArrayList<String> top_100 = new ArrayList<String>();
        for (int i = 0; i< 100; i++){
            top_100.add(allRatings.get(i).getLookUpId());
            top_100.add(allCounts.get(i).getLookUpId());
        }
        // Random shuffle for top 30 movies
        Random rand = new Random();
        HashSet<String> chosenMovie = new HashSet<String>();
        while (chosenMovie.size() < 30){
            int randomInd = rand.nextInt(200);
//            System.out.println(MovieDatabase.getMovie(top_100.get(randomInd)).getTitle());
            chosenMovie.add(top_100.get(randomInd));
//            System.out.println(chosenMovie.size());
        }
        ArrayList<String> movieList = new ArrayList<String>(chosenMovie);
        return movieList;
    }
}
