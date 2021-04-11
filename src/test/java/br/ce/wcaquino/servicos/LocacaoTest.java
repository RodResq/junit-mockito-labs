package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocacaoTest {

    @Test
    void deveValidarValores() {

        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 1, 5.5);

        //acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        //verificacao
        Assertions.assertEquals(locacao.getUsuario(), usuario);
        Assertions.assertEquals(locacao.getFilme(), filme);
        Assertions.assertEquals(locacao.getUsuario().getNome(), usuario.getNome());
        Assertions.assertEquals(locacao.getFilme().getNome(), filme.getNome());
        Assertions.assertEquals(locacao.getFilme().getEstoque(), filme.getEstoque());
        Assertions.assertEquals(locacao.getFilme().getPrecoLocacao(), filme.getPrecoLocacao());

    }
}
