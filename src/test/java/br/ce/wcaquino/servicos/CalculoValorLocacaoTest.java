package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocacaoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class CalculoValorLocacaoTest {

    private LocacaoService locacaoService;

    private List<Filme> filmes;

    private Double valorLocacao;

    @Before
    public void setup() {
        locacaoService = new LocacaoService();
    }

    @Test
    public void deveCalcularValorLocacaoCOnsiderandoDescontos() throws FilmeSemEstoqueException, LocacaoException {
        // cenario
        Usuario usuario = new Usuario("Usuario 1");
        //acao
        Locacao resultado = locacaoService.alugarFilme(usuario, filmes);
        //verficacao
        Assert.assertThat(resultado.getLocacaoPreco(), is(valorLocacao));
    }
}
