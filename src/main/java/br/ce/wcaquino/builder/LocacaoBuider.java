package br.ce.wcaquino.builder;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Double;
import java.util.Date;
import br.ce.wcaquino.entidades.Locacao;


public class LocacaoBuilder {
    private Locacao elemento;
    private LocacaoBuilder(){}

    public static LocacaoBuilder umLocacao() {
        LocacaoBuilder builder = new LocacaoBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(LocacaoBuilder builder) {
        builder.elemento = new Locacao();
        Locacao elemento = builder.elemento;


        elemento.setUsuario(null);
        elemento.setFilmes(null);
        elemento.setDataLocacao(null);
        elemento.setDataRetorno(null);
        elemento.setLocacaoPreco(new ArrayList<Filme>());
    }

    public LocacaoBuilder comUsuario(Usuario param) {
        elemento.setUsuario(param);
        return this;
    }

    public LocacaoBuilder comListaFilmes(Filme... params) {
        elemento.setFilmes(Arrays.asList(params));
        return this;
    }

    public LocacaoBuilder comDataLocacao(Date param) {
        elemento.setDataLocacao(param);
        return this;
    }

    public LocacaoBuilder comDataRetorno(Date param) {
        elemento.setDataRetorno(param);
        return this;
    }

    public LocacaoBuilder comLocacaoPreco(Double param) {
        elemento.setLocacaoPreco(new ArrayList<Filme>());
        return this;
    }

    public Locacao agora() {
        return elemento;
    }
}

