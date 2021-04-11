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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocacaoTest {

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private LocacaoService locacaoService;

    @Before
    public void setup() {
        System.out.println("before");
        locacaoService = new LocacaoService();
    }

    @After
    public void after() {
        System.out.println("after");
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

    @Test(expected = FilmeSemEstoqueException.class)
    public void checaFilmeSemEstoque1() throws FilmeSemEstoqueException, LocacaoException {

        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.5);

        locacaoService.alugarFilme(usuario, filme);
    }

    @Test()
    public void checaFilmeSemEstoque2() throws LocacaoException{

        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.5);

        try {
            //acao
            Locacao locacao = locacaoService.alugarFilme(usuario, filme);
            Mockito.when(locacaoService.alugarFilme(usuario, filme)).thenReturn(new Locacao());
            Assert.fail();
        } catch (FilmeSemEstoqueException e) {
            assertThat(e.getMessage(), is(equalTo("Filme sem estoque!")));
        }
    }

    @Test
    public void checaFilmeSemEstoqueLancandoExcecao() throws FilmeSemEstoqueException, LocacaoException {
//      cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.5);
//      acao
        expectedException.expect(FilmeSemEstoqueException.class);
        expectedException.expectMessage("Filme sem estoque!");
//      verificacao
        locacaoService.alugarFilme(usuario, filme);

    }

    @Test
    public void checaSeUsarioEstaVazio() throws FilmeSemEstoqueException {
        Filme filme =  new Filme("Filme 1", 1, 5.5);

        try {
            locacaoService.alugarFilme(null, filme);
            Assert.fail();
        } catch (LocacaoException e) {
            assertThat(e.getMessage(), is(equalTo("Usuario nao pode ser nulo!")));
        }
    }

    @Test
    public void chacaSeFimeEstaVazio() throws FilmeSemEstoqueException {
        Usuario usuario = new Usuario("Usuario 1");

        try {
            locacaoService.alugarFilme(usuario, null);
        }  catch (LocacaoException e) {
            assertThat(e.getMessage(), is(equalTo("Filme vazio")));
        }
    }

    @Test
    public void chacaSeFimeEstaVazio2() throws FilmeSemEstoqueException, LocacaoException {
        Usuario usuario = new Usuario("Usuario 1");

        expectedException.expect(LocacaoException.class);
        expectedException.expectMessage("Filme vazio");

        locacaoService.alugarFilme(usuario, null);


    }
}
