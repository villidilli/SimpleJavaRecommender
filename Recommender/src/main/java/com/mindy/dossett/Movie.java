package com.mindy.dossett;

/*
This is plain old POJO to represent movie object,
one unit record from MovieDatabase
 */

public class Movie {
    private String id;
    private String title;
    private int year;
    private String genres;
    private String director;
    private String country;
    private String poster;
    private int minutes;

    public Movie (String anID, String aTitle, String aYear, String theGenres, String aDirector,
                  String aCountry, String aPoster, String theMinutes) {
        id = anID.trim();
        title = aTitle.trim();
        year = Integer.parseInt(aYear.trim());
        genres = theGenres;
        director = aDirector;
        country = aCountry;
        poster = aPoster;
        minutes = Integer.parseInt(theMinutes);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenres() {
        return genres;
    }

    public String getDirector() {
        return director;
    }

    public String getCountry() {
        return country;
    }

    public String getPoster() {
        return poster;
    }

    public int getMinutes() {
        return minutes;
    }

    public double calAvgMovieRating(){
        double avgRating = 0.0;
        double cnt = 0.0;
        for (User user: UserDatabase.getUsers()){
            if (user.hasRating(id)){
                avgRating += user.getRating(id);
                cnt ++;
            }
        }
        if (cnt >=1){
            return (avgRating/cnt);
        }
        return -1.0;
    }

    public int getViewCount(){
        int cnt = 0;
        for (User user: UserDatabase.getUsers()){
            if (user.hasRating(id)){
                cnt ++;
            }
        }
        return cnt;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genres='" + genres + '\'' +
                ", director='" + director + '\'' +
                ", country='" + country + '\'' +
                ", poster='" + poster + '\'' +
                ", minutes=" + minutes +
                '}';
    }
}
