package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema.Show;

class ShowTest {
    
    private String data;
    private String artista;
    private double cache;
    private double despesasInfra;
    private boolean dataEspecial;
    private double porcentagemVIP;
    private Show show;
    
    @BeforeEach
    void setUp() {
        data = "31/07/2024";
        artista = "Bon Jovi";
        cache = 2000.0;
        despesasInfra = 1000.0;
        dataEspecial = false;
        porcentagemVIP = 0.2;
        show = new Show(data, artista, cache, despesasInfra, dataEspecial, porcentagemVIP);
    }

    @Test
    void testCriarShowValido() {
        try {
            new Show(data, artista, cache, despesasInfra, dataEspecial, porcentagemVIP);
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada inválida", e.getMessage());
        }
    }
    
    @Test
    void testCriarShowInvalidoPorcentagemMaxVipExcedida() {
        try {
            new Show(data, artista, cache, despesasInfra, dataEspecial, 0.31);
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem maxima de ingressos VIP excedida", e.getMessage());
        }
    }
    
    @Test
    void testCriarShowInvalidoPorcentagemMinVipNaoAtingida() {
        try {
            new Show(data, artista, cache, despesasInfra, dataEspecial, 0.19);
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem maxima de ingressos VIP excedida", e.getMessage());
        }
    }
    
    @Test
    void testGerarLote() {
        int numIngressos = 10;
        double desconto = 0.1;
        int precoBase = 10;
        int loteId = show.createLote(numIngressos, desconto, precoBase);
        assertNotNull(loteId);
    }
    
    @Test
    void testComprarLotePorId() {
        int numIngressos = 10;
        double desconto = 0.1;
        int precoBase = 10;
        int loteId = show.createLote(numIngressos, desconto, precoBase);
        
        double respostaEsperada = 7 * 10 * 0.9 + 2 * 20 * 0.9 + 1 * 5;
        double respostaRecebida = show.comprarLotePorId(loteId);
        assertEquals(respostaEsperada, respostaRecebida);
    }
    
    @Test
    void testGerarRelatorio() {
    	show = new Show(data, artista, 1000, 2000, true, porcentagemVIP);
    	
        int numIngressos = 500;
        double desconto = 0.15;
        int precoBase = 10;
        int loteId = show.createLote(numIngressos, desconto, precoBase);
        show.comprarLotePorId(loteId);
        
        String respostaEsperada = ""
                + "Relatorio do show:\n"
                + "Numero de ingressos normais vendidos: 350\n"
                + "Numero de ingressos VIPs vendidos: 100\n"
                + "Numero de ingressos meia entrada vendidos: 50\n"
                + "Receita liquida do show: 1625,00\n"
                + "Custos do show: 3300,00\n"
                + "Status financeiro do show: LUCRO";
        
        assertEquals(respostaEsperada, show.gerarRelatorio());
    }
}
