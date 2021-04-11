package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
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
        Assert.assertNotEquals("bola", "Bola");

        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));

        Usuario u1 = new Usuario("u1");
        Usuario u2 = new Usuario("u1");

        Assert.assertEquals(u1, u2);

        Assert.assertSame(u2, u2);

        Usuario u3 = null;
        Assert.assertNotEquals(u1,u3);
        Assert.assertNull(u3);
    }
}
