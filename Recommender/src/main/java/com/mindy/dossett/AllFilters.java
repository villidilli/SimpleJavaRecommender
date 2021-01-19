package com.mindy.dossett;

import java.util.ArrayList;

public class AllFilters implements Filter {
    ArrayList<Filter> filters;

    public AllFilters() {
        filters = new ArrayList<Filter>();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(String movieId) {
        for (Filter f : filters) {
            if (!f.satisfies(movieId)) {
                return false;
            }
        }

        return true;
    }

}