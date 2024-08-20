package functionalTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import sistema.Show;

class AvlTest {

    private Show show;

    @BeforeEach
    void setUp() {
        String data = "31/07/2024";
        String artista = "Bon Jovi";
        double cache = 2000.0;
        double despesasInfra = 1000.0;
        boolean dataEspecial = false;
        double porcentagemVIP = 0.2;
        show = new Show(data, artista, cache, despesasInfra, dataEspecial, porcentagemVIP);
    }

    @Test
    void testLoteCriado_20_10() {
    	show.setPorcentagemVIP(0.2);
        int loteId = show.createLote(100, 0.1, 10);
        assertNotNull(loteId);
    }

    @Test
    void testLoteCriado_21_10() {
    	show.setPorcentagemVIP(0.21);
        int loteId = show.createLote(105, 0.1, 10);
        assertNotNull(loteId);
    }

    @Test
    void testLoteCriado_25_10() {
    	show.setPorcentagemVIP(0.25);
        int loteId = show.createLote(125, 0.1, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_29_10() {
    	show.setPorcentagemVIP(0.29);
        int loteId = show.createLote(150, 0.1, 10);
        assertNotNull(loteId);
    }

    @Test
    void testLoteCriado_30_10() {
    	show.setPorcentagemVIP(0.3);
        int loteId = show.createLote(150, 0.1, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_19_10() {
    	try {
        	show.setPorcentagemVIP(0.19);
            show.createLote(155, 0.1, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de ingressos VIP excedida", e.getMessage());
        }
    }

    @Test
    void testErro_31_10() {
        try {
        	show.setPorcentagemVIP(0.31);
            show.createLote(155, 0.1, 10);
            fail("Esperado erro por estar abaixo do minimo da porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de ingressos VIP excedida", e.getMessage());
        }
    }

    @Test
    void testLoteCriado_25_0() {
    	show.setPorcentagemVIP(0.25);
        int loteId = show.createLote(150, 0, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_25_1() {
    	show.setPorcentagemVIP(0.25);
        int loteId = show.createLote(150, 0.01, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_25_24() {
    	show.setPorcentagemVIP(0.25);
        int loteId = show.createLote(150, 0.24, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_25_25() {
    	show.setPorcentagemVIP(0.25);
        int loteId = show.createLote(150, 0.25, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testErro_25_negativo() {
        try {
        	show.setPorcentagemVIP(0.25);
            show.createLote(155, -0.01, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de desconto limite excedida", e.getMessage());
        }
    }
    
    @Test
    void testErro_25_26() {
        try {
        	show.setPorcentagemVIP(0.25);
            show.createLote(155, 0.26, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de desconto limite excedida", e.getMessage());
        }
    }
    
    @Test
    void testLoteCriado_20_0() {
    	show.setPorcentagemVIP(0.2);
        int loteId = show.createLote(150, 0, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_20_1() {
    	show.setPorcentagemVIP(0.2);
        int loteId = show.createLote(150, 0.01, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_21_0() {
    	show.setPorcentagemVIP(0.21);
        int loteId = show.createLote(150, 0, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_21_1() {
    	show.setPorcentagemVIP(0.21);
        int loteId = show.createLote(150, 0.01, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_20_25() {
    	show.setPorcentagemVIP(0.2);
        int loteId = show.createLote(150, 0.25, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_21_25() {
    	show.setPorcentagemVIP(0.21);
        int loteId = show.createLote(150, 0.25, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_20_24() {
    	show.setPorcentagemVIP(0.2);
        int loteId = show.createLote(150, 0.24, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_21_24() {
    	show.setPorcentagemVIP(0.21);
        int loteId = show.createLote(150, 0.24, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_30_0() {
    	show.setPorcentagemVIP(0.3);
        int loteId = show.createLote(150, 0, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_29_0() {
    	show.setPorcentagemVIP(0.29);
        int loteId = show.createLote(150, 0, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_30_1() {
    	show.setPorcentagemVIP(0.3);
        int loteId = show.createLote(150, 0.01, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_29_1() {
    	show.setPorcentagemVIP(0.29);
        int loteId = show.createLote(150, 0.01, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_30_25() {
    	show.setPorcentagemVIP(0.3);
        int loteId = show.createLote(150, 0.25, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_29_25() {
    	show.setPorcentagemVIP(0.29);
        int loteId = show.createLote(150, 0.25, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_30_24() {
    	show.setPorcentagemVIP(0.3);
        int loteId = show.createLote(150, 0.24, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_29_24() {
    	show.setPorcentagemVIP(0.29);
        int loteId = show.createLote(150, 0.24, 10);
        assertNotNull(loteId);
    }
   
}