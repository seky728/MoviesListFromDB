package cz.uhk.pro2.movies.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @SerializedName("Title")
    private String title;
    @SerializedName("Poster")
    private String poster;
    @SerializedName("Year")
    private String year;
    @SerializedName("Type")
    private String type;
    @SerializedName("imdbID")
    private String imdbId;
    @SerializedName("Genre")
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
