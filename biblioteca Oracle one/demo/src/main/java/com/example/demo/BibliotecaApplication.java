package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        exibeMenu();
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    --------------------------------
                    1 - Buscar Livro pelo Título (Web)
                    2 - Listar Autores Vivos em determinado ano
                    3 - Quantidade de livros por idioma
                    0 - Sair
                    --------------------------------
                    """);
            try {
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1 -> buscarLivroWeb();
                    case 2 -> listarAutoresVivos();
                    case 3 -> exibirEstatisticasPorIdioma();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número inteiro.");
                leitura.nextLine();
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para buscar:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados("https://gutendex.com/books/?search=" + nomeLivro.replace(" ", "%20"));
        
        RespostaDados dados = conversor.obterDados(json, RespostaDados.class);

        if (dados.getLivros() != null && !dados.getLivros().isEmpty()) {
            DadosLivro dadosLivro = dados.getLivros().get(0); 
            Livro livro = new Livro(dadosLivro);
            
            // Busca autor ou salva novo usando Optional (certifique-se que o Repo tenha este método)
            Autor autor = autorRepository.findByNomeContainingIgnoreCase(livro.getAutor().getNome())
                            .orElseGet(() -> autorRepository.save(livro.getAutor()));
            
            livro.setAutor(autor);
            livroRepository.save(livro);
            
            System.out.println("Livro salvo com sucesso: " + livro.getTitulo());
        } else {
            System.out.println("Livro não encontrado na API.");
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano:");
        try {
            var ano = leitura.nextInt();
            leitura.nextLine();
            List<Autor> vivos = autorRepository.buscarAutoresVivosNoAno(ano);
            if (vivos.isEmpty()) {
                System.out.println("Nenhum autor encontrado.");
            } else {
                vivos.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Ano inválido.");
            leitura.nextLine();
        }
    }

    private void exibirEstatisticasPorIdioma() {
        System.out.println("Digite o idioma (ex: pt, en, fr):");
        var idioma = leitura.nextLine();
        Long total = livroRepository.countByIdioma(idioma);
        System.out.println("Total de livros em " + idioma + ": " + total);
    }
}