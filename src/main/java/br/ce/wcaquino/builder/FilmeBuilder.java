package br.ce.wcaquino.builder;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder {

    private Filme filme;

    private FilmeBuilder() {}

    public static FilmeBuilder umFilme() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(2);
        builder.filme.setNome("Filme 1");
        builder.filme.setValor(4.0);
        return builder;
    }

    public Filme agora() {
        return filme;
    }
}
