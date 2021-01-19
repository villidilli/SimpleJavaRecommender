package com.mindy.dossett;


public class GenreFilter implements Filter {
    private String myGenre;

    public GenreFilter(String genre) {
        myGenre = genre;
    }

    @Override
    public boolean satisfies(String movieId) {
        String[] genreList = MovieDatabase.getGenres(movieId).split(",");
        for (String genre : genreList) {
            if (genre.trim().equals(myGenre)) {
                return true;
            }
        }
        return false;
    }
}
