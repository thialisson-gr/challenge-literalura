package com.alura.literalura.service;

import java.util.List;

public interface IConverteDados {
    <T> List<T> obterDadosArray(String json, Class<T> classe);
    <T> T obterDados(String json, Class<T> classe);
}
