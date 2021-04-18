package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraTest {

    private Calculadora calculadora;

    @Mock
    private Calculadora calculadoraMock;

    @Spy
    private Calculadora calculadoraSpy;



    @Before
    public void setup() {
        calculadora = new Calculadora();
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void comparandoMockComSpy() {

        Mockito.when(calculadoraMock.somar(1, 2)).thenReturn(8);
//        Mockito.when(calculadoraSpy.somar(1,2)).thenReturn(8);
        Mockito.doReturn(8).when(calculadoraSpy).somar(1,2);
        Mockito.doNothing().when(calculadoraSpy).imprime();

        System.out.println("Mock: " + calculadoraMock.somar(1, 2));
        System.out.println("Mock: " + calculadoraSpy.somar(1, 5));

        System.out.println("Mock");
        calculadoraMock.imprime();

        System.out.println("Spy");
        calculadoraSpy.imprime();
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
