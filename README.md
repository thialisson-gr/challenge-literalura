
# 📚 Challenge Literalura

Projeto desenvolvido como parte de um desafio proposto pela Alura, com o objetivo de praticar consumo de APIs, manipulação de dados com Java e criação de menus interativos no terminal. O sistema permite buscar livros, cadastrar autores, listar obras por idioma, gênero e muito mais.

## 🚀 Funcionalidades

- Buscar livros por título via API.
- Listar livros cadastrados no sistema.
- Filtrar livros por idioma, ano de publicação ou autor.
- Cadastrar autores e associar obras.
- Menu interativo no terminal.

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Jackson** – para conversão de JSON
- **HttpClient** – para consumo de API
- **IntelliJ IDEA** – ambiente de desenvolvimento

## 🌐 API Utilizada

Os dados são consumidos da API pública do projeto [Gutendex](https://gutendex.com/), que disponibiliza livros do Projeto Gutenberg em formato JSON.

## 📁 Organização do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com.literalura/
│   │       ├── model/          # Classes de domínio: Livro, Autor, etc.
│   │       ├── service/        # Serviços de conversão, API e lógica de negócio
│   │       ├── menu/           # Menu interativo no terminal
│   │       └── Main.java       # Classe principal
│   └── resources/
└── test/                       # (futuros testes automatizados)
```

## 🛠️ Como executar

1. 📥 Clone o repositório
   ```bash
   git clone https://github.com/thialisson-gr/challenge-literalura.git
   cd challenge-literalura
   mvn spring-boot:run
   ```
2. ⚙️ Configure o banco de dados no `application.properties`
   ```properties
   spring.application.name=literalura
   spring.datasource.url=jdbc:postgresql://${DB_HOST}/literalura_db
   spring.datasource.username=${DB_USER}
   spring.datasource.password=${DB_PASSWORD}
   spring.datasource.driver-class-name=org.postgresql.Driver
   hibernate.dialect=org.hibernate.dialect.HSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   ```

## 💡 Aprendizados

Durante o desenvolvimento, foram praticados conceitos como:

- Consumo de APIs REST com Java. .
- Criação de menus interativos via terminal.
- Modelagem de dados com Programação Orientada a Objetos.
- Organização de código limpo e reutilizável.


## 🤝 Contribuição

Contribuições são bem-vindas! Sinta-se livre para abrir issues ou fazer um fork e enviar um pull request.

## 📄 Licença

Este projeto está sob a licença MIT.
