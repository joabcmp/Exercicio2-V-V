package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sistema.Ingresso;
import sistema.Lote;
import sistema.Status;
import sistema.TipoIngresso;

class LoteTest {

    @Test
    void testCriarLoteValido() {
        try {
            int numIngressos = 10;
            double porcentagemVIP = 0.3;
            double desconto = 0.25;
            int precoBase = 10;

            Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada inválida", e.getMessage());
        }
    }

    @Test
    void testCriarLoteInvalidoNumeroIngressosVazio() {
        try {
            int numIngressos = 0;
            double porcentagemVIP = 0.3;
            double desconto = 0.25;
            int precoBase = 10;

            Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        } catch (IllegalArgumentException e) {
            assertEquals("Numero de ingressos vazio", e.getMessage());
        }
    }

    @Test
    void testCriarLoteInvalidoPorcentagemMaxVipExcedida() {
        try {
            int numIngressos = 10;
            double porcentagemVIP = 0.31;
            double desconto = 0.1;
            int precoBase = 10;

            Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem maxima de ingressos VIP excedida", e.getMessage());
        }
    }

    @Test
    void testCriarLoteInvalidoPorcentagemMinVipNaoAtingida() {
        try {
            int numIngressos = 10;
            double porcentagemVIP = 0.19;
            double desconto = 0.1;
            int precoBase = 10;

            Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem maxima de ingressos VIP excedida", e.getMessage());
        }
    }

    @Test
    void testCriarLoteInvalidoDescontoLimiteExcedido() {
        try {
            int numIngressos = 10;
            double porcentagemVIP = 0.2;
            double desconto = 0.26;
            int precoBase = 10;

            Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        } catch (IllegalArgumentException e) {
            assertEquals("Porcentagem de desconto limite excedida", e.getMessage());
        }
    }

    @Test
    void testGetIngressos() {
        List<Ingresso> respostaEsperada = new ArrayList<>();
        int precoBase = 10;
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.MEIA_ENTRADA));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.VIP));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.VIP));

        int numIngressos = 10;
        double porcentagemVIP = 0.2;
        double desconto = 0.25;
        int precoBaseLote = 10;

        Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBaseLote);
        assertEquals(respostaEsperada.size(), lote.getIngressos().size());
        assertTrue(lote.getIngressos().containsAll(respostaEsperada));
    }

    @Test
    void testComprarIngressos() {
        List<Ingresso> respostaEsperada = new ArrayList<>();
        int precoBase = 10;
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.MEIA_ENTRADA));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.VIP));
        respostaEsperada.add(new Ingresso(precoBase, TipoIngresso.VIP));

        for (Ingresso ingresso : respostaEsperada) {
            ingresso.setStatus(Status.VENDIDO);
        }

        int numIngressos = 10;
        double porcentagemVIP = 0.2;
        double desconto = 0.25;
        int precoBaseLote = 10;

        Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBaseLote);
        lote.compraIngressos();
        assertEquals(respostaEsperada.size(), lote.getIngressos().size());
        assertTrue(lote.getIngressos().containsAll(respostaEsperada));
    }

    @Test
    void testNumIngressosVendidosIncializaZero() {
        int respostaEsperada = 0;
        int numIngressos = 10;
        double porcentagemVIP = 0.2;
        double desconto = 0.1;
        int precoBase = 10;

        Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        int numIngressosVendidos = lote.GetNumIngressosNormalVendidos() +
                lote.GetNumIngressosVipVendidos() +
                lote.GetNumIngressosMeiaVendidos();
        assertEquals(respostaEsperada, numIngressosVendidos);
    }

    @Test
    void testGetNumIngressosNormalVendidos() {
        int respostaEsperada = 7;
        int numIngressos = 10;
        double porcentagemVIP = 0.2;
        double desconto = 0.1;
        int precoBase = 10;

        Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        lote.compraIngressos();
        assertEquals(respostaEsperada, lote.GetNumIngressosNormalVendidos());
    }

    @Test
    void testGetNumIngressosVipVendidos() {
        int respostaEsperada = 2;
        int numIngressos = 10;
        double porcentagemVIP = 0.2;
        double desconto = 0.1;
        int precoBase = 10;

        Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        lote.compraIngressos();
        assertEquals(respostaEsperada, lote.GetNumIngressosVipVendidos());
    }

    @Test
    void testGetNumIngressosMeialVendidos() {
        int respostaEsperada = 1;
        int numIngressos = 10;
        double porcentagemVIP = 0.2;
        double desconto = 0.1;
        int precoBase = 10;

        Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        lote.compraIngressos();
        assertEquals(respostaEsperada, lote.GetNumIngressosMeiaVendidos());
    }

    @Test
    void testGetReceita() {
        double respostaEsperada = 7 * 10 * 0.9 + 2 * 20 * 0.9 + 1 * 5;
        int numIngressos = 10;
        double porcentagemVIP = 0.2;
        double desconto = 0.1;
        int precoBase = 10;

        Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        lote.compraIngressos();
        assertEquals(respostaEsperada, lote.getReceita());
    }
}
