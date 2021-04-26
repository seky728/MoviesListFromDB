package cz.uhk.pro2.movies.services;

import com.google.gson.Gson;
import cz.uhk.pro2.movies.model.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class OmdbMovieSearch implements MovieSearch {
    private String apiKey ;// =  "f34c8435";

    public OmdbMovieSearch(String apiKey){
        this.apiKey = apiKey;
    }

    @Override
    public List<Movie> searchMovies(String text) {
         String url = "http://omdbapi.com/?s="+ text +"&apikey="+ apiKey;

         try {
            URL u = new URL(url);
             InputStream is = u.openStream();
             Gson gson = new Gson();
             InputStreamReader isr = new InputStreamReader(is);
            OmdbApiSearchResult result = gson.fromJson(isr, OmdbApiSearchResult.class);
            List<Movie> movies = result.Search; // zde movies nemají nastavený žánr, protože vyhledávací dotaz jej nevrací
             for (Movie m : movies) {
                 //musíme udělat podrobný dotaz pro ImdbId, čímž získáme i žánr (genre)
                setMovieDetails(m);
             }
             return movies;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
         }

        return null;
    }
    private class OmdbApiSearchResult{
        private String Response;
        private String totalResults;
        private List<Movie> Search;
    }

    private void setMovieDetails(Movie m){
        String url = "http://omdbapi.com/?i="+ m.getImdbId() +"&apikey="+ apiKey;

        try {
            URL u = new URL(url);
            InputStream is = u.openStream();
            Gson gson = new Gson();
            InputStreamReader isr = new InputStreamReader(is);
            Movie m2 = gson.fromJson(isr, Movie.class);
            m.setGenre(m2.getGenre());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

