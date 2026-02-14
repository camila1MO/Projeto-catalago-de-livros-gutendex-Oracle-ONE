package com.example.demo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosAutor {
    @JsonAlias("name") private String nome;
    @JsonAlias("birth_year") private Integer anoNascimento;
    @JsonAlias("death_year") private Integer anoFalecimento;

    public String getNome() { return nome; }
    public Integer getAnoNascimento() { return anoNascimento; }
    public Integer getAnoFalecimento() { return anoFalecimento; }
}
