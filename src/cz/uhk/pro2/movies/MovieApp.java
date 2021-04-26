package cz.uhk.pro2.movies;

import cz.uhk.pro2.movies.gui.MovieTableModel;
import cz.uhk.pro2.movies.gui.SearchResultDialog;
import cz.uhk.pro2.movies.model.Movie;
import cz.uhk.pro2.movies.services.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovieApp extends JFrame {
    MovieSearch movieSearchService = new OmdbMovieSearch("f34c8435");
    MyMoviesDao dao = new DbMyMoviesDao();
    JTable jTable;
    MovieTableModel movieTableModel;// = new MovieTableModel(dao.findAllMovies());
    JTextField txtSearch = new JTextField(10);


    public MovieApp() throws HeadlessException{

        try{
            movieTableModel = new MovieTableModel(dao.findAllMovies());
            jTable = new JTable(movieTableModel);
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
            System.exit(1);
        }


        setTitle("Moje filmy");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);

        JTextField jTextField = new JTextField(10);

        JPanel jPanel = new JPanel();
        JButton jButton = new JButton("Hledat na OMDB");
        jButton.addActionListener((e)->showSearchDialog(jTextField.getText()));

        JButton update = new JButton("Update movie");
        update.addActionListener((e) -> update());

        JToolBar jToolBar = new JToolBar();
        add(jToolBar, BorderLayout.NORTH);
        JButton btnRemove = new JButton("Smazat film");
        jToolBar.add(btnRemove);
        btnRemove.addActionListener((e)->removeMovie());
        jToolBar.add(txtSearch);
        JButton btnSearchInDb = new JButton("Hledat v DB");
        btnSearchInDb.addActionListener(e -> searchInDb());
        jToolBar.add(btnSearchInDb);

        add(new JScrollPane(jTable), BorderLayout.CENTER);
        jPanel.add(jTextField);
        jPanel.add(jButton);
        jPanel.add(update);

        add(jPanel, BorderLayout.SOUTH);


        pack();
    }

    private void searchInDb(){
        String s = txtSearch.getText();

        movieTableModel.setMovies(dao.findMovie(s));
        movieTableModel.fireTableDataChanged();

    }

    private void update(){
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow >= 0){
            Movie m = movieTableModel.getMovies().get(selectedRow);
         /*
            MovieEditDialog d = new MovieEditDialog(m);
            d.setVisible(true);
            dao.save(m);

          */
            refreshTable();

        }
    }



    private void removeMovie(){
        if (!jTable.getSelectionModel().isSelectionEmpty()){
            int selectedRow = jTable.getSelectedRow();
           // Movie m = dao.findAllMovies().get(selectedRow); // riskantní při práci s DB, více uživatelů, můžou mi měnit data pod rukou
            Movie m = movieTableModel.getMovies().get(selectedRow);

            dao.delete(m);
            refreshTable();
        } else{
            JOptionPane.showMessageDialog(this, "Vyberte nejdrive film ke smazani");
        }


    }

    private void showSearchDialog(String searchText){
        //najdi filmy
        List<Movie> movies = movieSearchService.searchMovies(searchText);
        //zobrazit v JDialogu
        SearchResultDialog dialog = new SearchResultDialog(movies);
        dialog.setVisible(true); // zde se kod zastaví dokud se modální dialog nezavře
        Movie m = dialog.getSelectedMovie();
        if (m != null){
            //byl nejaky film vybran

            dao.save(m);
            refreshTable();
        }

    }


    private void refreshTable(){
        //TODO vytáhnout z DB nová data
        movieTableModel.setMovies(dao.findAllMovies());
        movieTableModel.fireTableDataChanged();
    }


    public static void main(String[] args) {
      /*  MovieSearch movieSearch = new OmdbMovieSearch("f34c8435");
        List<Movie> movies = movieSearch.searchMovies("Batman");
        int i = 1;
        for (Movie m : movies) {
            System.out.println(i +": "+ m.getTitle());

        i++;
        }
 */




 SwingUtilities.invokeLater(new Runnable() {
     @Override
     public void run() {
         MovieApp a = new MovieApp();
         a.setVisible(true);
     }
 });

    }
}
