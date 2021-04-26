package cz.uhk.pro2.movies.services;

import cz.uhk.pro2.movies.model.Movie;

import java.util.List;

public interface MyMoviesDao {
    /**
     * Vloží novy nebo aktualizuje stavajici zaznam do uloziste napr. DB
     * @param movie zaznam
     */
    void save(Movie movie);

    /**
     * najde a vrátí záznamy z uložiště podle vyhledávání
     * @param s
     * @return
     */
    List<Movie> findMovie(String s);

    void delete(Movie movie);

    List<Movie> findAllMovies();
}
