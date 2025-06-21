package com.alura.literalura;

import java.util.*;

public class Principal {
    Scanner leitura = new Scanner(System.in);
    private  ConsumoApi consumoApi = new ConsumoApi();
    private final String urlLivro = "https://gutendex.com/books/?search=";
    String json = "";
    private ConverteDados converteDados = new ConverteDados();
    private List<Livro> livrosRegistrados = new ArrayList<>();

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
                    buscarLivroDaApi();
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
                    System.out.println("Insira o idioma para realizar a busca:");
                    System.out.println("(es - espanhol, en - inglês, fr - francês, pt - português)");
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

    public void buscarLivroDaApi(){
        System.out.println("Digite o título do livro:");
        String livro = leitura.nextLine().trim().replaceAll(" ", "%20");
        json = consumoApi.obterDados(urlLivro + livro);
        System.out.println(json);
        ListaDeLivros listaDeLivros = converteDados.obterDados(json, ListaDeLivros.class);

        // Verifica se há livros retornados
        if (listaDeLivros.getResults() != null && !listaDeLivros.getResults().isEmpty()) {
            Livro livro1 = listaDeLivros.getResults().get(0);
            livrosRegistrados.add(livro1);
            System.out.println("---------Detalhes do Livro---------");
            System.out.println("Título: " + livro1.getTitle());

            for (Autor autor : livro1.getAuthors()) {
                System.out.println("Autor: " + autor.getName());
                System.out.println("Ano de nascimento: " + autor.getBirth_year());
                System.out.println("Ano da morte: " + autor.getDeath_year());
            }

            String idiomasFormatados = String.join(", ", livro1.getLanguages());
            System.out.println("Idiomas: " + idiomasFormatados);
            System.out.println("Contagem de downloads: " + livro1.getDownload_count());
            System.out.println("---------Fim dos Detalhes---------");
        } else {
            System.out.println("Livro não encontrado ou resposta inválida.");
        }
    }

    public void listarLivrosRegistrados() {
        if (livrosRegistrados == null || livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        System.out.println("Livros registrados:");
        for (Livro livro : livrosRegistrados) {
            System.out.println("Título: " + livro.getTitle());
            String idiomas = String.join(", ", livro.getLanguages());
            System.out.println("Idiomas: " + idiomas);
            System.out.println("Downloads: " + livro.getDownload_count());
            System.out.println("-----------------------------");
        }
    }

    public void listarAutoresRegistrados() {
        if (livrosRegistrados == null || livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        // Usando Set para evitar autores duplicados
        Set<String> autores = new HashSet<>();
        for (Livro livro : livrosRegistrados) {
            for (Autor autor : livro.getAuthors()) {
                autores.add(autor.getName());
            }
        }

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("Autores registrados:");
            for (String nomeAutor : autores) {
                System.out.println(nomeAutor);
            }
        }
    }

    public void listarAutoresVivosNoAno() {
        if (livrosRegistrados == null || livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        System.out.print("Informe o ano para buscar autores vivos: ");
        int ano;
        try {
            ano = Integer.parseInt(leitura.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido.");
            return;
        }

        Set<String> autoresVivos = new HashSet<>();
        for (Livro livro : livrosRegistrados) {
            for (Autor autor : livro.getAuthors()) {
                Integer nascimento = autor.getBirth_year();
                Integer morte = autor.getDeath_year();
                if (nascimento != null && (morte == null || ano <= morte) && ano >= nascimento) {
                    autoresVivos.add(autor.getName());
                }
            }
        }

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo nesse ano.");
        } else {
            System.out.println("Autores vivos em " + ano + ":");
            for (String nome : autoresVivos) {
                System.out.println(nome);
            }
        }
    }



    public void listarLivrosPorIdioma(String idioma) {
        if (livrosRegistrados == null || livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        boolean encontrou = false;
        System.out.println("Livros no idioma '" + idioma + "':");
        for (Livro livro : livrosRegistrados) {
            if (livro.getLanguages() != null && livro.getLanguages().contains(idioma)) {
                System.out.println("Título: " + livro.getTitle());
                String idiomas = String.join(", ", livro.getLanguages());
                System.out.println("Idiomas: " + idiomas);
                System.out.println("Downloads: " + livro.getDownload_count());
                System.out.println("-----------------------------");
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum livro encontrado nesse idioma.");
        }
    }


}
