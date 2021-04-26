package cz.uhk.pro2.movies.services;

import cz.uhk.pro2.movies.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryMyMoviesDao implements MyMoviesDao {
    private List<Movie> movies = new ArrayList<>(); // "Místo kam ukládáme data"
    private long idGenerator = 1;
    private int index = 0;


    @Override
    public void save(Movie movie) {
        if (movie.getId() == 0){
            //nový záznam
            movies.add(movie);
            movie.setId(idGenerator++);

        } else {
            //stávající záznam určený k aktualizaci
            for (Movie m2 : movies){
                if (m2.getId() == movie.getId()){
                    //nalezeno
                    movies.set(index,movie); // radeji udelame "replace" do kolekce
                }
                index ++;
            }
        }
    }


    @Override
    public List<Movie> findMovie(String s) {
        throw new UnsupportedOperationException("Not implemented yet, TODO");
    }

    @Override
    public void delete(Movie movie) {
        movies.remove(movie);
    }

    @Override
    public List<Movie> findAllMovies() {
      //  return Collections.unmodifiableList(movies);
        return List.copyOf(movies); // nemodifikovatelna kopie listu (=simulujeme chování DAO pracujícího s SQL serveru)
    }
}
