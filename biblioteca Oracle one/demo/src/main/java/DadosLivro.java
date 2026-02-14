package com.example.demo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosLivro {
    @JsonAlias("title") private String titulo;
    @JsonAlias("download_count") private Double downloads;
    @JsonAlias("languages") private List<String> idiomas;
    @JsonAlias("authors") private List<DadosAutor> autores;

    public String getTitulo() { return titulo; }
    public Double getDownloads() { return downloads; }
    public List<String> getIdiomas() { return idiomas; }
    public List<DadosAutor> getAutores() { return autores; }
}