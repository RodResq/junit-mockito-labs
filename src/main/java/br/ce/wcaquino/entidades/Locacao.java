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

	public Double getLocacaoPreco() {
		Double valorTotal = 0.0;

		for (Filme filme : filmes) {
			valorTotal += filme.getValor();
		}

		return valorTotal;
	}



	public List<Filme> getFilmes() {
		return filmes;
	}
}