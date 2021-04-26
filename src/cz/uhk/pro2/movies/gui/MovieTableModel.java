package cz.uhk.pro2.movies.gui;

import cz.uhk.pro2.movies.model.Movie;
import cz.uhk.pro2.movies.services.GenreTranslator;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MovieTableModel extends AbstractTableModel {
    private List<Movie> movies;
    private GenreTranslator translator = new GenreTranslator();

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie m = movies.get(rowIndex);
        switch (columnIndex){
            case 0: return m.getYear();
            case 1: return m.getTitle();
            case 2:
               System.out.println(m.getPoster() != null);

                if (m.getPoster() != null && !m.getPoster().equals("") && !m.getPoster().equals("N/A")){

                try {
                    return new ImageIcon(new URL(m.getPoster()));
                } catch (MalformedURLException e) {
                   // System.out.println("picture didnt found");
                    return null;
                }}else {return null;}
            case 3:
                return translator.translateGenre(m.getGenre());
            default: return null;
        }
    }



    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: return String.class;
            case 1: return String.class;
            case 2: return Icon.class;
            default: return Object.class;
        }
    }
}

