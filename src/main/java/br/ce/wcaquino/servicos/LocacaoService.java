package br.ce.wcaquino.servicos;

import br.ce.wcaquino.builder.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocacaoException;
import br.ce.wcaquino.utils.DataUtils;

import java.io.FileFilter;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

	private LocacaoDao dao;
	private SpcService cpcService;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocacaoException {

		System.out.println(filmes);
		if (filmes == null || filmes.isEmpty()) {
			throw new LocacaoException("Filme vazio");
		}
		if (usuario == null) {
			throw new LocacaoException("Usuario nao pode ser nulo!");
		}

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque!");
			}

		}

		if (cpcService.possuiNagativacao(usuario)) {
			throw new LocacaoException("Usuario Negativado");
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setLocacaoPreco(filmes);
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		dao.salvar(locacao);
		
		return locacao;
	}

	public void setDao(LocacaoDao dao) {
		this.dao = dao;
	}

	public void setSpcService(SpcService spc) {
		cpcService = spc;
	}

	public void prorrogarLocacao(Locacao locacao, int dias) {

		Locacao novaLocaocao = new Locacao();
		novaLocaocao.setUsuario(locacao.getUsuario());
		novaLocaocao.setFilmes(locacao.getFilmes());
		novaLocaocao.setDataLocacao(new Date());
		novaLocaocao.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
		novaLocaocao.setLocacaoPreco(calcularPrecoProrrogacaoLocacao(locacao.getFilmes(), dias));
		dao.salvar(novaLocaocao);

	}

	private List<Filme> calcularPrecoProrrogacaoLocacao(List<Filme> filmes, int dias) {

		for (Filme filme : filmes) {
			filme.setValor(filme.getValor() * dias);
		}
		return  filmes;
	}

}