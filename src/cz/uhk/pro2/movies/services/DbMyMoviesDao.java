package cz.uhk.pro2.movies.services;


import cz.uhk.pro2.movies.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbMyMoviesDao implements MyMoviesDao {

    /**
     * otevře spojení s DB
     * používáme http://hsqldb.org/ , dovoluje spuštění app v testovacím režimu
     * @return
     */
    private Connection openConnectio() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection con = DriverManager.getConnection("jdbc:hsqldb:file:db.hsqldb");

        PreparedStatement s = con.prepareStatement("CREATE  table IF NOT EXISTS movies (id INTEGER IDENTITY PRIMARY KEY, title VARCHAR(100), poster VARCHAR(250), type VARCHAR(100), year VARCHAR(25), imdbId VARCHAR(100), genre VARCHAR(250))"); // sql select
        s.execute(); // spuštění sql příkazu
      //  PreparedStatement i = con.prepareStatement("INSERT INTO movies(title) VALUES ('Můj batman')");
      //  i.execute();
        return con;
    }




    @Override
    public void save(Movie movie) {
        try(Connection con = openConnectio()){
        if (movie.getId() == 0){

              PreparedStatement s = con.prepareStatement("INSERT INTO movies(title, poster, type, year, imdbID, genre) VALUES (?,?,?,?,?,?)");
              s.setString(1, movie.getTitle());
              s.setString(2, movie.getPoster());
              s.setString(3, movie.getType());
              s.setString(4, movie.getYear());
              s.setString(5, movie.getImdbId());
              s.setString(6, movie.getGenre());

              s.executeUpdate();



        } else {

            PreparedStatement s = con.prepareStatement("UPDATE movies set title = ? , poster = ?, type = ? , year = ?, imdbID = ?, genre = ? WHERE id = ?");
            s.setString(1, movie.getTitle());
            s.setString(2, movie.getPoster());
            s.setString(3, movie.getType());
            s.setString(4, movie.getYear());
            s.setString(5, movie.getImdbId());
            s.setString(6, movie.getGenre());
            s.setLong(7, movie.getId());


            s.executeUpdate();


        }

        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Chyba při insertu nového záznamu");
        }
    }


    @Override
    public void delete(Movie movie) {
        try (Connection con = openConnectio()){

            PreparedStatement s = con.prepareStatement("DELETE from movies WHERE id = ? ");
            s.setLong(1, movie.getId());
            int rs = s.executeUpdate();

            System.out.println(rs);


        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Chyba komunikace s databází, při delete", e);
        }

    }


    @Override
    public List<Movie> findAllMovies() {
        try (Connection con = openConnectio()) {

            PreparedStatement s = con.prepareStatement("select id, title, poster, type, year, imdbId, genre from movies");
            return executeQueryAndGetMovies(s);

        } catch (SQLException | ClassNotFoundException e) {// tady zavře spojení
            e.printStackTrace();
            throw new RuntimeException("Chyba kounikace s databází", e);
        }
    }

    @Override
    public List<Movie> findMovie(String text) {
        try (Connection con = openConnectio()) {

            PreparedStatement s = con.prepareStatement("select id, title, poster, type, year, imdbId, genre from movies where title like ?");
            s.setString(1, "%"+text+"%");
            return executeQueryAndGetMovies(s);

        } catch (SQLException | ClassNotFoundException e) {// tady zavře spojení
            e.printStackTrace();
            throw new RuntimeException("Chyba kounikace s databází", e);
        }
    }

    private List<Movie> executeQueryAndGetMovies(PreparedStatement s) throws SQLException {
        ResultSet rs = s.executeQuery(); // spusť dotaz který něco vrátí

        List<Movie> result = new ArrayList<>();
        while (rs.next()) {
            Movie movie = new Movie();
            movie.setId(rs.getInt("id"));
            movie.setImdbId(rs.getString("imdbId"));
            movie.setPoster(rs.getString("poster"));
            movie.setTitle(rs.getString("title"));
            movie.setType(rs.getString("type"));
            movie.setYear(rs.getString("year"));
            movie.setGenre(rs.getString("genre"));

            result.add(movie);
        }

        return result;
    }
}
