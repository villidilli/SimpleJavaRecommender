package com.mindy.dossett;

import java.util.ArrayList;

public class RepeatFilter implements Filter {
    private ArrayList<String> watched;

    public RepeatFilter(User user) {
        watched = user.getMoviesRated();
    }

    @Override
    public boolean satisfies(String movieId) {
        for (String movie : watched) {
            if (movie.equals(movieId)) {
                return false;
            }
        }

        return true;
    }
}