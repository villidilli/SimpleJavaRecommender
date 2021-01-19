package com.mindy.dossett;

public class DirectorsFilter implements Filter {
    private String myDirectors;

    public DirectorsFilter(String directors) {
        myDirectors = directors;
    }

    @Override
    public boolean satisfies(String movieId) {
        String[] directorArray = myDirectors.split(",");
        String movieDirectors = MovieDatabase.getDirector(movieId);
        for (String director : directorArray) {
            if (movieDirectors.indexOf(director.trim()) != -1) {
                return true;
            }
        }
        return false;
    }
}
