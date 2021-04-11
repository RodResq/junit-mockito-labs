package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AssetsTest {

    @Test
    void testes() {
        Assert.assertTrue(true);
        Assert.assertFalse(false);
        Assert.assertEquals(1,1);
        Assert.assertEquals(0.51233, 0.512, 0.001);
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer j = 5;

        Assert.assertEquals(Integer.valueOf(i), j);
        Assert.assertEquals(i, j.intValue());

        Assert.assertEquals("bola", "bola");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));

    }
}
