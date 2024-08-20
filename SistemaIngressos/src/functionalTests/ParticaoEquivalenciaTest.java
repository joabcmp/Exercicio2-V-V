package functionalTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.Show;

class ParticaoEquivalenciaTest {

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
    void testLoteCriado_10_30() {
    	try {
        	show.setPorcentagemVIP(0.1);
            show.createLote(155, 0.3, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de ingressos VIP excedida", e.getMessage());
        }
    }
    
    @Test
    void testLoteCriado_10_10() {
    	try {
        	show.setPorcentagemVIP(0.1);
            show.createLote(155, 0.1, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de ingressos VIP excedida", e.getMessage());
        }
    }
    
    @Test
    void testLoteCriado_10_negativo() {
    	try {
        	show.setPorcentagemVIP(0.1);
            show.createLote(155, -0.1, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de ingressos VIP excedida", e.getMessage());
        }
    }
    
    @Test
    void testLoteCriado_25_30() {
    	try {
        	show.setPorcentagemVIP(0.25);
            show.createLote(155, 0.3, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de desconto limite excedida", e.getMessage());
        }
    }
    
    @Test
    void testLoteCriado_25_10() {
    	show.setPorcentagemVIP(0.25);
        int loteId = show.createLote(125, 0.1, 10);
        assertNotNull(loteId);
    }
    
    @Test
    void testLoteCriado_25_negativo() {
    	try {
        	show.setPorcentagemVIP(0.25);
            show.createLote(155, -0.1, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de desconto limite excedida", e.getMessage());
        }
    }

    @Test
    void testLoteCriado_50_30() {
    	try {
        	show.setPorcentagemVIP(0.5);
            show.createLote(155, 0.3, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de ingressos VIP excedida", e.getMessage());
        }
    }
    
    @Test
    void testLoteCriado_50_10() {
    	try {
        	show.setPorcentagemVIP(0.5);
            show.createLote(155, 0.1, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de ingressos VIP excedida", e.getMessage());
        }
    }
    
    @Test
    void testLoteCriado_50_negativo() {
    	try {
        	show.setPorcentagemVIP(0.5);
            show.createLote(155, -0.1, 10);
            fail("Esperado erro por exceder porcentagem VIP");
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de ingressos VIP excedida", e.getMessage());
        }
    }



}
