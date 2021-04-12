package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {

    private Calculadora calculadora;

    @Before
    public void setup() {
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomarDoisNumeros() {
//        cenario
        int a = 5;
        int b = 3;
//        acao
        int resultado  = calculadora.somar(a,b);

//        verificacao
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisNumeros() {
//        cenario
        int a = 5;
        int b = 3;
//        acao
        int resultado  = calculadora.subtrair(a,b);

//        verificacao
        Assert.assertEquals(2, resultado);
    }

    @Test
    public void deveDividirDoisNumeros() throws NaoPodeDividirPorZeroException {
//        cenario
        int a = 6;
        int b = 3;
//        acao
        int resultado  = 0;

        resultado = calculadora.dividir(a,b);

//        verificacao
        Assert.assertEquals(2, resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExceptionAoDividirPorZero() throws NaoPodeDividirPorZeroException {
//        cenario
        int a = 10;
        int b = 0;
//        acao
        int resultado  = calculadora.dividir(a,b);

//        verificacao
    }
}
