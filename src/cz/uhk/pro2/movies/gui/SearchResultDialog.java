package cz.uhk.pro2.movies.gui;

import cz.uhk.pro2.movies.model.Movie;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class SearchResultDialog extends JDialog {
    public SearchResultDialog(List<Movie> movies) {
        setTitle("Výsledek vyhledávání");
        setModal(true); // nastavení na modální okno
        MovieTableModel model = new MovieTableModel(movies);
        JTable tbl = new JTable(model);

        add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel pnl = new JPanel();
        JButton btn = new JButton("Přidat do mých filmů");
        btn.setEnabled(false);
        pnl.add(btn);
        add(pnl, BorderLayout.SOUTH);
        tbl.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) { // reaguje na změnu
                System.out.println(e);
                if (!e.getValueIsAdjusting()){ //reaguje na stlačení tlačítka na označení, zmáčknutí -> adjust, uvolnění -> !adjust
                    System.out.println("!adjusted");// ptám se jestli je v tabulce něco označeno
                    if (tbl.getSelectedRow() < 0){
                        btn.setEnabled(false); // není, nastav tlačítko na disable
                    } else {
                        btn.setEnabled(true); // je, povol tlačítko
                    }
                } else {
                    System.out.println("adjusted");
                }
            }



        }



        );


        btn.addActionListener(e -> {
            selectedMovie = movies.get(tbl.getSelectedRow()); //zapamatujeme vybraný film
            setVisible(false); // zavření dialogu, není zničen, stále v paměti existuje
        });

        pack();
    }

    private Movie selectedMovie;

    public Movie getSelectedMovie(){

        return selectedMovie;
    }

}
