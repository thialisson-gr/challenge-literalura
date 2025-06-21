package com.alura.literalura;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaDeLivros {
    private List<Livro> results;

    public List<Livro> getResults() {
        return results;
    }

    public void setResults(List<Livro> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "---------Livro---------" + results + "\n";
    }
}
