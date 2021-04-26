package cz.uhk.pro2.movies.services;

import cz.uhk.pro2.movies.model.Movie;

import java.util.List;

public interface MovieSearch {
    /**
     * Vyhleda filmy podle zadaneho text
     * @param text cast nazvu filmu
     * @return seznam nalezenych filmu
     */
    public List<Movie> searchMovies(String text);

}
