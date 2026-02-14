package com.example.demo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaDados {
    @JsonAlias("results") 
    private List<DadosLivro> livros;

    public List<DadosLivro> getLivros() { return livros; }
    public void setLivros(List<DadosLivro> livros) { this.livros = livros; }
}