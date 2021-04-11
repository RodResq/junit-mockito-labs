package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocacaoTest {

    @Test
    void deveValidarValores() {

        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 1, 5.5);

        //acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        //verificacao

        assertThat(locacao.getValor(), is(equalTo(5.5)));
        assertThat(locacao.getValor(), is(not(6.0)));
        assertEquals(locacao.getUsuario(), usuario);
        assertEquals(locacao.getFilme(), filme);
        assertEquals(locacao.getUsuario().getNome(), usuario.getNome());
        assertEquals(locacao.getFilme().getNome(), filme.getNome());
        assertEquals(locacao.getFilme().getEstoque(), filme.getEstoque());
        assertEquals(locacao.getFilme().getPrecoLocacao(), filme.getPrecoLocacao());

    }
}
