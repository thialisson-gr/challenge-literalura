package com.alura.literalura;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
    private String name;
    private int birth_year;
    private int death_year;

    public Autor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year;
    }

    @Override
    public String toString() {
        return  name + '\n' +
                "Ano de nascimento" + birth_year + '\n' +
                "Ano da morte: " + death_year;
    }
}
