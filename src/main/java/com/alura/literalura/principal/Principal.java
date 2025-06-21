package com.alura.literalura.principal;

import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.models.Autor;
import com.alura.literalura.models.ListaDeLivros;
import com.alura.literalura.models.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {
    @Autowired
    private LivroRepository livroRepository;
    Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String urlLivro = "https://gutendex.com/books/?search=";
    String json = "";
    private ConverteDados converteDados = new ConverteDados();


    @Autowired
    public Principal(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\nMenu:");
            System.out.println("1- Buscar livro pelo título");
            System.out.println("2- Listar livros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos em um determinado ano");
            System.out.println("5- Listar livros em um determinado idioma");
            System.out.println("0- Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(leitura.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosNoAno();
                    break;
                case 5:
                    System.out.println("Insira a abreviação do idioma para realizar a busca:");
                    System.out.println("es - espanhol");
                    System.out.println("en - inglês");
                    System.out.println("fr - francês");
                    System.out.println("pt - português");
                    String idioma = leitura.nextLine().trim();
                     listarLivrosPorIdioma(idioma);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public void buscarLivroPorTitulo() {
        System.out.println("Digite o título do livro:");
        String titulo = leitura.nextLine().trim();

        // Busca no banco de dados (ignora maiúsculas/minúsculas)
        Optional<Livro> livroOpt = livroRepository.findByTitleIgnoreCase(titulo);

        Livro livro;
        if (livroOpt.isPresent()) {
            livro = livroOpt.get();
            System.out.println("## Livro encontrado no banco de dados:");
        } else {
            // Busca na API
            String tituloUrl = titulo.replaceAll(" ", "%20");
            String json = consumoApi.obterDados(urlLivro + tituloUrl);
            ListaDeLivros listaDeLivros = converteDados.obterDados(json, ListaDeLivros.class);

            if (listaDeLivros.getResults() != null && !listaDeLivros.getResults().isEmpty()) {
                livro = listaDeLivros.getResults().get(0);
                livro.setId(null);
                if (livro.getAuthors() != null) {
                    for (Autor autor : livro.getAuthors()) {
                        autor.setId(null);
                    }
                }
                livroRepository.save(livro);
                System.out.println("## Livro encontrado na API e salvo no banco:");
            } else {
                System.out.println("## Livro não encontrado ou resposta inválida.");
                return;
            }
        }

        // Exibe detalhes do livro
        System.out.println("----Detalhes do Livro----");
        System.out.println("Título: " + livro.getTitle());
        for (Autor autor : livro.getAuthors()) {
            System.out.println("Autor: " + autor.getName());
        }
        String idiomasFormatados = String.join(", ", livro.getLanguages());
        System.out.println("Idiomas: " + idiomasFormatados);
        System.out.println("Contagem de downloads: " + livro.getDownload_count());
        System.out.println("--------------------------");
    }

    public void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros == null || livros.isEmpty()) {
            System.out.println("## Nenhum livro registrado.");
            return;
        }

        System.out.println("------Livros registrados:------");
        for (Livro livro : livros) {
            System.out.println("Título: " + livro.getTitle());
            if (livro.getAuthors() != null && !livro.getAuthors().isEmpty()) {
                System.out.print("Autor(es): ");
                List<String> nomesAutores = new ArrayList<>();
                for (Autor autor : livro.getAuthors()) {
                    nomesAutores.add(autor.getName());
                }
                System.out.println(String.join(", ", nomesAutores));
            } else {
                System.out.println("## Autor(es): Não informado");
            }
            String idiomas = String.join(", ", livro.getLanguages());
            System.out.println("Idiomas: " + idiomas);
            System.out.println("Downloads: " + livro.getDownload_count());
            System.out.println("-----------------------------");
        }
    }

    public void listarAutoresRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros == null || livros.isEmpty()) {
            System.out.println("## Nenhum livro registrado.");
            return;
        }

        // Mapeia autores pelo nome, associando dados e lista de livros
        Map<String, Autor> dadosAutores = new HashMap<>();
        Map<String, List<String>> livrosPorAutor = new HashMap<>();

        for (Livro livro : livros) {
            if (livro.getAuthors() != null) {
                for (Autor autor : livro.getAuthors()) {
                    dadosAutores.putIfAbsent(autor.getName(), autor);
                    livrosPorAutor.computeIfAbsent(autor.getName(), k -> new ArrayList<>()).add(livro.getTitle());
                }
            }
        }

        if (dadosAutores.isEmpty()) {
            System.out.println("## Nenhum autor registrado.");
        } else {
            System.out.println("----Autores registrados:----");
            for (String nomeAutor : dadosAutores.keySet()) {
                Autor autor = dadosAutores.get(nomeAutor);
                System.out.println("-----------------------------");
                System.out.println("Nome: " + autor.getName());
                System.out.println("Ano de nascimento: " + (autor.getBirth_year() != null ? autor.getBirth_year() : "Desconhecido"));
                System.out.println("Ano de morte: " + (autor.getDeath_year() != null ? autor.getDeath_year() : "Desconhecido"));
                System.out.println("Livros: " + String.join(", ", livrosPorAutor.get(nomeAutor)));
                System.out.println("------------------------");
            }
        }
    }

    public void listarAutoresVivosNoAno() {
        List<Livro> livros = livroRepository.findAll();
        if (livros == null || livros.isEmpty()) {
            System.out.println("## Nenhum livro registrado.");
            return;
        }

        System.out.print("Informe o ano para buscar autores vivos: ");
        int ano;
        try {
            ano = Integer.parseInt(leitura.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("## Ano inválido.");
            return;
        }

        Map<String, Autor> dadosAutores = new HashMap<>();
        Map<String, List<String>> livrosPorAutor = new HashMap<>();

        for (Livro livro : livros) {
            if (livro.getAuthors() != null) {
                for (Autor autor : livro.getAuthors()) {
                    Integer nascimento = autor.getBirth_year();
                    Integer morte = autor.getDeath_year();
                    if (nascimento != null && (morte == null || ano <= morte) && ano >= nascimento) {
                        dadosAutores.putIfAbsent(autor.getName(), autor);
                        livrosPorAutor.computeIfAbsent(autor.getName(), k -> new ArrayList<>()).add(livro.getTitle());
                    }
                }
            }
        }

        if (dadosAutores.isEmpty()) {
            System.out.println("## Nenhum autor encontrado vivo nesse ano.");
        } else {
            System.out.println("--Autores vivos em " + ano + ":--");
            for (String nomeAutor : dadosAutores.keySet()) {
                Autor autor = dadosAutores.get(nomeAutor);
                System.out.println("-----------------------------");
                System.out.println("Nome: " + autor.getName());
                System.out.println("Ano de nascimento: " + (autor.getBirth_year() != null ? autor.getBirth_year() : "Desconhecido"));
                System.out.println("Ano de morte: " + (autor.getDeath_year() != null ? autor.getDeath_year() : "Desconhecido"));
                System.out.println("Livros: " + String.join(", ", livrosPorAutor.get(nomeAutor)));
                System.out.println("-----------------------------");
            }
        }
    }



    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findAll();
        if (livros == null || livros.isEmpty()) {
            System.out.println("## Nenhum livro registrado.");
            return;
        }

        boolean encontrou = false;
        System.out.println("--Livros no idioma '" + idioma + "':--");
        for (Livro livro : livros) {
            if (livro.getLanguages() != null && livro.getLanguages().contains(idioma)) {
                System.out.println("-----------------------------");
                System.out.println("Título: " + livro.getTitle());
                String idiomas = String.join(", ", livro.getLanguages());
                System.out.println("Idiomas: " + idiomas);
                System.out.println("Downloads: " + livro.getDownload_count());
                System.out.println("-----------------------------");
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("## Nenhum livro encontrado nesse idioma no banco de dados.");
        }
    }


}
