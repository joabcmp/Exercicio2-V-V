package functionalTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.Show;

class TabelaDecisaoTest {

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
    void testCompraIngresso_NORMAL_DESCONTO() {
    	double saidaEsperada = 90;
    	show.createLote(100, 0.1, 100);
    	double saida = show.comprIngressoNORMAL();
        assertEquals(saidaEsperada, saida);
    }
    
    @Test
    void testCompraIngresso_NORMAL_SEM_DESCONTO() {
    	double saidaEsperada = 100;
    	show.createLote(100, 0.0, 100);
    	double saida = show.comprIngressoNORMAL();
        assertEquals(saidaEsperada, saida);
    }
    
    @Test
    void testCompraIngresso_VIP_DESCONTO() {
    	double saidaEsperada = 180;
    	show.createLote(100, 0.1, 100);
    	double saida = show.comprIngressoVIP();
        assertEquals(saidaEsperada, saida);
    }
    
    @Test
    void testCompraIngresso_VIP_SEM_DESCONTO() {
    	double saidaEsperada = 200;
    	show.createLote(100, 0.0, 100);
    	double saida = show.comprIngressoVIP();
        assertEquals(saidaEsperada, saida);
    }
    
    @Test
    void testCompraIngresso_MEIA_DESCONTO() {
    	double saidaEsperada = 50;
    	show.createLote(100, 0.1, 100);
    	double saida = show.comprIngressoMEIA();
        assertEquals(saidaEsperada, saida);
    }
    
    @Test
    void testCompraIngresso_MEIA_SEM_DESCONTO() {
    	double saidaEsperada = 50;
    	show.createLote(100, 0.0, 100);
    	double saida = show.comprIngressoMEIA();
        assertEquals(saidaEsperada, saida);
    }
}
