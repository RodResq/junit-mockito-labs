package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocacaoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    private LocacaoService locacaoService;

    @Parameter()
    public List<Filme> filmes;

    @Parameter(value = 1)
    public Double valorLocacao;

    @Before
    public void setup() {
        locacaoService = new LocacaoService();
    }

    private static Filme filme1 = new Filme("filme 1", 1, 4.0);
    private static Filme filme2 = new Filme("filme 2", 1, 4.0);
    private static Filme filme3 = new Filme("filme 3", 1, 4.0);
    private static Filme filme4 = new Filme("filme 4", 1, 4.0);
    private static Filme filme5 = new Filme("filme 5", 1, 4.0);
    private static Filme filme6 = new Filme("filme 6", 1, 4.0);

    @Parameters(name = "Teste {index} = {0} - {1}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][] {
                {Arrays.asList(filme1, filme2, filme3), 11.0},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.0},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0}
        });
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

    @Test
    public void print() {
        System.out.println(valorLocacao);
    }
}
