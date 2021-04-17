package br.ce.wcaquino.builder;

import br.ce.wcaquino.entidades.Locacao;
import buildermaster.BuilderMaster;

public class GeradorDeBuilders {

    public static void main(String[] args) {

        new BuilderMaster().gerarCodigoClasse(Locacao.class);
    }
}
