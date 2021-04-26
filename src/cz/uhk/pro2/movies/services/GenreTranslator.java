package cz.uhk.pro2.movies.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GenreTranslator {

    Map<String, String> dictionary = new HashMap<>();


    public GenreTranslator(){
        dictionary.put("Action","Akční");
        //TODO DU -> přidat pár překladů
        dictionary.put("Comedy", "Komedie");
        dictionary.put("Thriller","Detektivka");
        dictionary.put("Adventure", "Dobrodružný");
    }

    /**
     * Přeloží filomové žánry ve tvaru [zanr1, zanr2 ...] z AJ do CZ
     * @return prelozeny seznam žánrů
     * @param genres seznam žánrů ve tvaru [zanr1, zanr2 ...]
     */
    public String translateGenre2(String genres){
        if (genres == null) return null;
        String[] parts = genres.split(", ");
        ArrayList<String> translated = new ArrayList<>();
        for (String part:parts){
            String translatedS = dictionary.get(part);
           if(translatedS == null){
               // pojem nenalezen ve slovníku
               translated.add(part); //necháme to původní
           } else {
               // teď máme v translatedS prelozeny pojem
               translated.add(translatedS);
           }
        }
        return String.join(", ", translated);
    }


    public String translateGenre(String genres){
        if (genres == null) return null;
        return Arrays.stream(genres.split(", ")).map(e -> dictionary.getOrDefault(e, e)).collect(Collectors.joining(", "));
    }

}
