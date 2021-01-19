package com.mindy.dossett;


public class YearAfterFilter implements Filter {
    private int myYear;

    public YearAfterFilter(int year) {
        myYear = year;
    }

    @Override
    public boolean satisfies(String movieId) {
        return MovieDatabase.getYear(movieId) >= myYear;
    }
}
