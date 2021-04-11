package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocacaoTest {

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Test()
    public void deveValidarValores() throws RuntimeException {

        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 2, 5.5);

        //acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        //verificacao

        errorCollector.checkThat(locacao.getValor(), is(equalTo(5.5)));
        errorCollector.checkThat(locacao.getValor(), is(not(6.0)));
        errorCollector.checkThat(locacao.getUsuario(), is(equalTo(usuario)));
        errorCollector.checkThat(locacao.getFilme(), is(equalTo(filme)));
        errorCollector.checkThat(locacao.getUsuario().getNome(), is(equalTo(usuario.getNome())));
        errorCollector.checkThat(locacao.getFilme().getNome(), is(equalTo(filme.getNome())));
        errorCollector.checkThat(locacao.getFilme().getEstoque(), is(equalTo(filme.getEstoque())));
        errorCollector.checkThat(locacao.getFilme().getPrecoLocacao(), is(equalTo(filme.getPrecoLocacao())));

    }

    @Test()
    public void checaFilmeSemEstoque() {

        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.5);

        try {
            //acao
            Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is(equalTo("Filme sem estoque!")));
        }
    }
}
