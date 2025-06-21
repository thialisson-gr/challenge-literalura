package com.alura.literalura;

import java.util.Scanner;

public class Principal {
    Scanner leitura = new Scanner(System.in);
    private  ConsumoApi consumoApi = new ConsumoApi();
    private final String urlLivro = "https://gutendex.com/books/?search=";
    String json = "";
    private ConverteDados converteDados = new ConverteDados();

    public void buscarLivroDaApi(){
        System.out.println("Digite o título do livro:");
        String livro = leitura.nextLine().trim().replaceAll(" ", "%20");
        json = consumoApi.obterDados(urlLivro + livro);
        System.out.println(json);
        ListaDeLivros listaDeLivros = converteDados.obterDados(json, ListaDeLivros.class);

        // Verifica se há livros retornados
        if (listaDeLivros.getResults() != null && !listaDeLivros.getResults().isEmpty()) {
            Livro livro1 = listaDeLivros.getResults().get(0);
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


}
