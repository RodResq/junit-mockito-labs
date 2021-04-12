package br.ce.wcaquino.entidades;

import java.util.Date;
import java.util.List;

public class Locacao {

	private Usuario usuario;
	private List<Filme> filmes;
	private Date dataLocacao;
	private Date dataRetorno;
	private Double locacaoPreco;


	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDataLocacao() {
		return dataLocacao;
	}
	public void setDataLocacao(Date dataLocacao) {
		this.dataLocacao = dataLocacao;
	}
	public Date getDataRetorno() {
		return dataRetorno;
	}
	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}
	public void setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
	}

	public void setLocacaoPreco(List<Filme> filmes) {

		Double valorTotal = 0.0;

		for (int i = 0; i < filmes.size(); i++) {

			Filme filme = filmes.get(i);
			Double valorFilme = filme.getValor();

			if ( i == 2) {
				valorFilme = valorFilme * 0.75;
			}
			if (i == 3) {
				valorFilme = valorFilme * 0.5;
			}

			valorTotal += valorFilme;
		}

		this.locacaoPreco = valorTotal;

	}


	public Double getLocacaoPreco() {
		return locacaoPreco;
	}



	public List<Filme> getFilmes() {
		return filmes;
	}
}