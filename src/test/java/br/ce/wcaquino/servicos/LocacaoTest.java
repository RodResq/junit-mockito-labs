package br.ce.wcaquino.servicos;

import br.ce.wcaquino.builder.LocacaoBuilder;
import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocacaoException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.*;

import static br.ce.wcaquino.builder.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builder.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class LocacaoTest {

    @InjectMocks
    private LocacaoService service;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    private List<Filme> filmes;

    private SpcService spcService;

    private LocacaoDao dao;

    @Before
    public void setup() {
        service = new LocacaoService();
        filmes = new ArrayList<Filme>();
        dao = Mockito.mock(LocacaoDao.class);
        service.setDao(dao);
        spcService = Mockito.mock(SpcService.class);
        service.setSpcService(spcService);

    }

    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    @Test()
    public void deveValidarValores() throws Exception {

        Usuario usuario = umUsuario().agora();
        Filme filme1 = umFilme().agora();
        Filme filme2 = umFilme().agora();

        filmes = new ArrayList<Filme>();
        filmes.add(filme1);
        filmes.add(filme2);

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao

        errorCollector.checkThat(filme1.getValor(), is(equalTo(4.0)));
        errorCollector.checkThat(filme2.getValor(), is(equalTo(4.0)));
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
        Usuario usuario = umUsuario().agora();
        Filme filme1 = umFilme().agora();
        Filme filme2 = umFilme().agora();

        filmes = new ArrayList<Filme>();
        filmes.add(filme1);
        filmes.add(filme2);

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);
        Assert.assertEquals(locacao.getLocacaoPreco(), 8.0, 0.01);
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarFilmeSemEstoque() throws FilmeSemEstoqueException, LocacaoException {

        Usuario usuario = umUsuario().agora();
        Filme filme = umFilmeSemEstoque().agora();
        filmes.add(filme);

        service.alugarFilme(usuario, filmes);
    }

    @Test()
    public void naoDeveAlugarFilmeSemEstoque2() throws LocacaoException{

        Usuario usuario = umUsuario().agora();
        Filme filme = umFilme().semEstoque().agora();
        filmes.add(filme);

        try {
            //acao
            Locacao locacao = service.alugarFilme(usuario, filmes);
            Mockito.when(service.alugarFilme(usuario, filmes)).thenReturn(new Locacao());
            Assert.fail();
        } catch (FilmeSemEstoqueException e) {
            assertThat(e.getMessage(), is(equalTo("Filme sem estoque!")));
        }
    }

    @Test
    public void deveLancarExcesaoFilmeSemEstoque() throws FilmeSemEstoqueException, LocacaoException {
//      cenario
        Usuario usuario = umUsuario().agora();
        Filme filme = new Filme("Filme 1", 0, 5.5);
        filmes.add(filme);
//      acao
        expectedException.expect(FilmeSemEstoqueException.class);
        expectedException.expectMessage("Filme sem estoque!");
//      verificacao
        service.alugarFilme(usuario, filmes);

    }

    @Test
    public void deveLancarExecaoSeUsuarioVazio() throws FilmeSemEstoqueException {
        Filme filme =  umFilme().agora();
        filmes.add(filme);

        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocacaoException e) {
            assertThat(e.getMessage(), is(equalTo("Usuario nao pode ser nulo!")));
        }
    }

    @Test
    public void deveLancarExecessaoSeFilmeVazio() throws FilmeSemEstoqueException {
        Usuario usuario = umUsuario().agora();

        try {
            service.alugarFilme(usuario, null);
        }  catch (LocacaoException e) {
            assertThat(e.getMessage(), is(equalTo("Filme vazio")));
        }
    }

    @Test
    public void deveLancarExecessaoSeFilmeVazio2() throws FilmeSemEstoqueException, LocacaoException {
        Usuario usuario = umUsuario().agora();

        expectedException.expect(LocacaoException.class);
        expectedException.expectMessage("Filme vazio");

        service.alugarFilme(usuario, null);


    }

    @Test
    public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocacaoException {
        // cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora(),
        umFilme().agora(), umFilme().agora());
        
        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verficacao
        Assert.assertEquals(11.0, resultado.getLocacaoPreco(), 0.01);
    }

    @Test
    public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocacaoException {
        // cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora()
        );

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);
        //4+4+3+2+1=14
        //verficacao
        Assert.assertEquals(14.0, resultado.getLocacaoPreco(), 0.01);
    }

    @Test
    public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocacaoException {
        // cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora()
                );

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);
        //4+4+3+2+1=14
        //verficacao
        Assert.assertEquals(14.0, resultado.getLocacaoPreco(), 0.01);
    }

    @Test
    public void deveLancarExcecaoParaUsuarioNegativado() throws FilmeSemEstoqueException, LocacaoException {
        //cenario
        Usuario usuario = umUsuario().agora();
        Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        Mockito.when(spcService.possuiNagativacao(usuario)).thenReturn(true);

        expectedException.expect(LocacaoException.class);
        expectedException.expectMessage("Usuario Negativado");
        //acao
        service.alugarFilme(usuario, filmes);

    }

    @Test
    @Ignore
    public void naoDeveDevolverFilmeNoDomigo() throws FilmeSemEstoqueException, LocacaoException {
        //Cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());
        // acao
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificacao
        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataLocacao(), Calendar.MONDAY);
        assertTrue(ehSegunda);

    }

    @Test
    public void deveProrrogarLocacao() {

        Locacao locacao = LocacaoBuilder.umLocacao().agora();

        service.prorrogarLocacao(locacao, 3);

        ArgumentCaptor<Locacao> argumentCaptor = ArgumentCaptor.forClass(Locacao.class);
        Mockito.verify(dao).salvar(argumentCaptor.capture());
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception{
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017));

        //acao
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(retorno.getDataRetorno(), caiNumaSegunda());
        PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();

    }

}
