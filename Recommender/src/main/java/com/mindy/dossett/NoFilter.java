package com.mindy.dossett;

public class NoFilter implements Filter{
    @Override
    public boolean satisfies(String movieId) {
        return true;
    }
}
