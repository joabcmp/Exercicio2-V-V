package teste;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import processador.Fatura;
import processador.Processador;
import processador.Pagamento;

class ProcessadorTest {

    @Test
    public void testProcessarBoletosCasoIdeal() {
        // Fatura: 1.500,00 (20/02/2023)
        // Boletos: 500,00 + 400,00 + 600,00 (todos pagos em dia)
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(500, "20/02/2023", "Boleto");
        Pagamento b2 = new Pagamento(400, "20/02/2023", "Boleto");
        Pagamento b3 = new Pagamento(600, "20/02/2023", "Boleto");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        pagamentos.add(b2);
        pagamentos.add(b3);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarBoletosComAtraso() {
        // Fatura: 1350,00 (20/02/2023)
        // Boletos: 500,00 pago em dia + 850,00 pago com atraso, 10% de acréscimo(fatura = 1485,00) 
        Fatura f = new Fatura("20/02/2023", 1350);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(500, "20/02/2023", "Boleto");
        Pagamento b2 = new Pagamento(850, "21/02/2023", "Boleto"); // 10% de acréscimo
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        pagamentos.add(b2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarBoletosIncompleto() {
        // Fatura: 2.000,00 (20/02/2023)
        // Boletos: 500,00 pago em dia + 700,00 pago em dia (total: 1200,00)
        Fatura f = new Fatura("20/02/2023", 2000);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(500, "20/02/2023", "Boleto");
        Pagamento b2 = new Pagamento(700, "20/02/2023", "Boleto");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        pagamentos.add(b2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosPorCartaoCredito() {
        // Fatura: 1.500,00 (20/02/2023)
        // Pagamentos: 700,00 por cartão de crédito e 800,00 por transferência
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "05/02/2023", "CARTAO");
        Pagamento pag2 = new Pagamento(800, "17/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosComCartaoCreditoInvalido() {
        // Fatura: 1.500,00 (20/02/2023)
        // Pagamento com cartão de crédito feito fora do período permitido
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "06/02/2023", "CARTAO"); // Fora do período permitido
        Pagamento pag2 = new Pagamento(800, "17/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosPorTransferenciaBancaria() {
        // Fatura: 1.500,00 (20/02/2023)
        // Pagamentos: 700,00 + 800,00 (ambos pagos por transferência)
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "20/02/2023", "TRANSFERENCIA");
        Pagamento pag2 = new Pagamento(800, "20/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosSemNenhumPagamento() {
        // Fatura: 1.500,00 (20/02/2023)
        // Sem pagamentos
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosComMisturaDeBoletosEOutrosPagamentos() {
        // Fatura: 2.000,00 (20/02/2023)
        // Boletos: 500,00 (pago com atraso, +10%(fatura 2200) e Pagamentos: 1.700,00 (em dia)
        Fatura f = new Fatura("20/02/2023", 2000);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(500, "21/02/2023", "Boleto");
        Pagamento pag1 = new Pagamento(1700, "20/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        pagamentos.add(pag1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentoCartaoCreditoExatamente15DiasAntes() {
        // Fatura: 1.500,00 (20/02/2023)
        // Pagamento: 1.500,00 (CARTAO_CREDITO - exatamente 15 dias antes)
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(1500, "05/02/2023", "CARTAO");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarVariosBoletosComEAtraso() {
        // Fatura: 3.000,00 (20/02/2023)
        // Boletos: 1000,00 (em dia), 1000,00 (em dia), 1000,00 (com atraso +10%)
        Fatura f = new Fatura("20/02/2023", 3000);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(1000, "20/02/2023", "BOLETO");
        Pagamento b2 = new Pagamento(1000, "19/02/2023", "BOLETO");
        Pagamento b3 = new Pagamento(1000, "21/02/2023", "BOLETO"); // +10% de acréscimo
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        pagamentos.add(b2);
        pagamentos.add(b3);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentoParcialComTiposDiferentes() {
        // Fatura: 2.500,00 (20/02/2023)
        // Boletos: 1000,00 (pago em dia)
        // Pagamentos: 800,00 (CARTAO_CREDITO - válido), 500,00 (TRANSFERENCIA_BANCARIA)
        Fatura f = new Fatura("20/02/2023", 2500);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(1000, "20/02/2023", "BOLETO");
        Pagamento pag1 = new Pagamento(800, "05/02/2023", "CARTAO");
        Pagamento pag2 = new Pagamento(500, "20/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarVariosTiposComValorExato() {
        // Fatura: 2.500,00 (20/02/2023)
        // Boletos: 1000,00 (pago em dia)
        // Pagamentos: 500,00 (CARTAO_CREDITO - válido), 1000,00 (TRANSFERENCIA_BANCARIA)
        Fatura f = new Fatura("20/02/2023", 2500);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(1000, "20/02/2023", "BOLETO");
        Pagamento pag1 = new Pagamento(1000, "04/02/2023", "CARTAO");
        Pagamento pag2 = new Pagamento(500, "20/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

}
