package com.alura.literalura.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthors(List<Autor> authors) {
        this.authors = authors;
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
