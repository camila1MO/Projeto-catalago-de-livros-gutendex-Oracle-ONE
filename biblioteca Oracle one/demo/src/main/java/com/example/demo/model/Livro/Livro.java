package com.example.demo.model;

import com.example.demo.DadosLivro;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Evita duplicar o mesmo título se desejar
    private String titulo;

    private String idioma;
    private Double numeroDownloads;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade ajuda a salvar o autor junto se necessário
    @JoinColumn(name = "autor_id") // Define o nome da coluna no banco de dados
    private Autor autor;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.getTitulo();
        // Proteção contra lista de idiomas nula ou vazia
        if (dadosLivro.getIdiomas() != null && !dadosLivro.getIdiomas().isEmpty()) {
            this.idioma = dadosLivro.getIdiomas().get(0);
        } else {
            this.idioma = "Desconhecido";
        }
        this.numeroDownloads = dadosLivro.getDownloads();
    }

    // Getters e Setters necessários para o funcionamento do Repository e Console
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }
    public Double getNumeroDownloads() { return numeroDownloads; }
    public Autor getAutor() { return autor; }
    
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return String.format("Livro: %s | Autor: %s | Idioma: %s | Downloads: %.2f", 
            titulo, (autor != null ? autor.getNome() : "N/A"), idioma, numeroDownloads);
    }
}