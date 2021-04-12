package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocacaoException;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocacaoTest {

    private static int contador = 0;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private LocacaoService locacaoService;
    private List<Filme> filmes;

    @Before
    public void setup() {
        System.out.println("before");
        locacaoService = new LocacaoService();
        filmes = new ArrayList<Filme>();

    }

    @After
    public void after() {
        System.out.println("after");
        contador++;
        System.out.println(contador);
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("before class");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("after class");
    }

    @Test()
    public void deveValidarValores() throws Exception {

        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 2, 5.5);
        Filme filme2 = new Filme("Filme 2", 2, 6.0);

        filmes = new ArrayList<Filme>();
        filmes.add(filme1);
        filmes.add(filme2);

        //acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        //verificacao

        errorCollector.checkThat(filme1.getValor(), is(equalTo(5.5)));
        errorCollector.checkThat(filme2.getValor(), is(equalTo(6.0)));
        errorCollector.checkThat(filme1.getValor(), is(not(6.0)));
        errorCollector.checkThat(locacao.getUsuario(), is(equalTo(usuario)));
        errorCollector.checkThat(locacao.getUsuario().getNome(), is(equalTo(usuario.getNome())));
        errorCollector.checkThat(locacao.getFilmes().get(0).getNome(), is(equalTo(filme1.getNome())));
        errorCollector.checkThat(locacao.getFilmes().get(1).getNome(), is(equalTo(filme2.getNome())));
        errorCollector.checkThat(locacao.getFilmes().get(0).getEstoque(), is(equalTo(filme1.getEstoque())));
        errorCollector.checkThat(locacao.getFilmes().get(1).getEstoque(), is(equalTo(filme2.getEstoque())));
        errorCollector.checkThat(locacao.getFilmes().get(0).getValor(), is(equalTo(filme1.getValor())));
        errorCollector.checkThat(locacao.getFilmes().get(1).getValor(), is(equalTo(filme2.getValor())));

    }

    @Test
    public void verificaValorTotalLocalcao() throws FilmeSemEstoqueException, LocacaoException {
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 2, 5.0);
        Filme filme2 = new Filme("Filme 2", 2, 5.0);

        filmes = new ArrayList<Filme>();
        filmes.add(filme1);
        filmes.add(filme2);

        //acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        Assert.assertEquals(locacao.getLocacaoPreco(), 10.0, 0.01);
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarFilmeSemEstoque() throws FilmeSemEstoqueException, LocacaoException {

        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.5);
        filmes.add(filme);

        locacaoService.alugarFilme(usuario, filmes);
    }

    @Test()
    public void naoDeveAlugarFilmeSemEstoque2() throws LocacaoException{

        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.5);
        filmes.add(filme);

        try {
            //acao
            Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
            Mockito.when(locacaoService.alugarFilme(usuario, filmes)).thenReturn(new Locacao());
            Assert.fail();
        } catch (FilmeSemEstoqueException e) {
            assertThat(e.getMessage(), is(equalTo("Filme sem estoque!")));
        }
    }

    @Test
    public void deveLancarExcesaoFilmeSemEstoque() throws FilmeSemEstoqueException, LocacaoException {
//      cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.5);
        filmes.add(filme);
//      acao
        expectedException.expect(FilmeSemEstoqueException.class);
        expectedException.expectMessage("Filme sem estoque!");
//      verificacao
        locacaoService.alugarFilme(usuario, filmes);

    }

    @Test
    public void deveLancarExecaoSeUsuarioVazio() throws FilmeSemEstoqueException {
        Filme filme =  new Filme("Filme 1", 1, 5.5);
        filmes.add(filme);

        try {
            locacaoService.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocacaoException e) {
            assertThat(e.getMessage(), is(equalTo("Usuario nao pode ser nulo!")));
        }
    }

    @Test
    public void deveLancarExecessaoSeFilmeVazio() throws FilmeSemEstoqueException {
        Usuario usuario = new Usuario("Usuario 1");

        try {
            locacaoService.alugarFilme(usuario, null);
        }  catch (LocacaoException e) {
            assertThat(e.getMessage(), is(equalTo("Filme vazio")));
        }
    }

    @Test
    public void deveLancarExecessaoSeFilmeVazio2() throws FilmeSemEstoqueException, LocacaoException {
        Usuario usuario = new Usuario("Usuario 1");

        expectedException.expect(LocacaoException.class);
        expectedException.expectMessage("Filme vazio");

        locacaoService.alugarFilme(usuario, null);


    }
}
