package com.alura.literalura;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {

    private String title;
    private List<Autor> authors;
    private List<String> languages;
    private int download_count;

    @JsonProperty("authors")
    private void unpackAuthors(List<Autor> authors) {
        if (authors != null && !authors.isEmpty()) {
            this.authors = Collections.singletonList(authors.get(0)); // pega apenas o primeiro autor
        }
    }

    public Livro() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Autor> getAuthors() {
        return authors;
    }

    public void setAuthors(Autor authors) {
        this.authors = Collections.singletonList(authors);
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    @Override
    public String toString() {
        return  "---------Livro---------" + "\n" +
                "Título: " + title + "\n" +
                "Autor: " + authors + "\n" +
                "Idiomas: " + languages + "\n" +
                "Contagem de downloads: " + download_count;
    }
}
