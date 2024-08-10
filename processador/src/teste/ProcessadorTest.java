package teste;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import processador.Boleto;
import processador.Fatura;
import processador.Processador;
import processador.Pagamento;

class ProcessadorTest {

    @Test
    public void testProcessarBoletosCasoIdeal() {
        // Fatura: 1.500,00 (20/02/2023)
        // Boletos: 500,00 + 400,00 + 600,00 (todos pagos em dia)
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 500);
        Boleto b2 = new Boleto("121", "20/02/2023", 400);
        Boleto b3 = new Boleto("131", "20/02/2023", 600);
        ArrayList<Boleto> boletos = new ArrayList<>();
        boletos.add(b1);
        boletos.add(b2);
        boletos.add(b3);
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarBoletosComAtraso() {
        // Fatura: 2.000,00 (20/02/2023)
        // Boletos: 500,00 pago em dia + 850,00 pago com 10% de acréscimo por atraso
        Fatura f = new Fatura("20/02/2023", 2000, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 500);
        Boleto b2 = new Boleto("121", "21/02/2023", 850); // 10% de acréscimo
        ArrayList<Boleto> boletos = new ArrayList<>();
        boletos.add(b1);
        boletos.add(b2);
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarBoletosIncompleto() {
        // Fatura: 2.000,00 (20/02/2023)
        // Boletos: 500,00 pago em dia + 700,00 pago em dia (total: 1200,00)
        Fatura f = new Fatura("20/02/2023", 2000, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 500);
        Boleto b2 = new Boleto("121", "20/02/2023", 700);
        ArrayList<Boleto> boletos = new ArrayList<>();
        boletos.add(b1);
        boletos.add(b2);
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosPorCartaoCredito() {
        // Fatura: 1.500,00 (20/02/2023)
        // Pagamentos: 700,00 por cartão de crédito e 800,00 por transferência
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "05/02/2023", "CARTAO_CREDITO");
        Pagamento pag2 = new Pagamento(800, "17/02/2023", "TRANSFERENCIA_BANCARIA");
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosComCartaoCreditoInvalido() {
        // Fatura: 1.500,00 (20/02/2023)
        // Pagamento com cartão de crédito feito fora do período permitido
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "06/02/2023", "CARTAO_CREDITO"); // Fora do período permitido
        Pagamento pag2 = new Pagamento(800, "17/02/2023", "TRANSFERENCIA_BANCARIA");
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosPorTransferenciaBancaria() {
        // Fatura: 1.500,00 (20/02/2023)
        // Pagamentos: 700,00 + 800,00 (ambos pagos por transferência)
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "20/02/2023", "TRANSFERENCIA_BANCARIA");
        Pagamento pag2 = new Pagamento(800, "20/02/2023", "TRANSFERENCIA_BANCARIA");
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosSemNenhumPagamento() {
        // Fatura: 1.500,00 (20/02/2023)
        // Sem pagamentos
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosComMisturaDeBoletosEOutrosPagamentos() {
        // Fatura: 2.000,00 (20/02/2023)
        // Boletos: 500,00 (pago com atraso, +10%) e Pagamentos: 1.300,00 (em dia)
        Fatura f = new Fatura("20/02/2023", 2000, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "21/02/2023", 500); // +10% de acréscimo
        Pagamento pag1 = new Pagamento(1300, "20/02/2023", "TRANSFERENCIA_BANCARIA");
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        boletos.add(b1);
        pagamentos.add(pag1);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }
        @Test
    public void testProcessarPagamentosMistosComDatasVariadas() {
        // Fatura: 2.000,00 (20/02/2023)
        // Boletos: 500,00 (pago em dia), 700,00 (pago com atraso +10%)
        // Pagamentos: 800,00 (CARTAO_CREDITO - válido)
        Fatura f = new Fatura("20/02/2023", 2000, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 500);
        Boleto b2 = new Boleto("121", "21/02/2023", 700); // +10% de acréscimo
        Pagamento pag1 = new Pagamento(800, "05/02/2023", "CARTAO_CREDITO");
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        boletos.add(b1);
        boletos.add(b2);
        pagamentos.add(pag1);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentoCartaoCreditoExatamente15DiasAntes() {
        // Fatura: 1.500,00 (20/02/2023)
        // Pagamento: 1.500,00 (CARTAO_CREDITO - exatamente 15 dias antes)
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(1500, "05/02/2023", "CARTAO_CREDITO");
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarVariosBoletosComEAtraso() {
        // Fatura: 3.000,00 (20/02/2023)
        // Boletos: 1000,00 (em dia), 1000,00 (em dia), 1000,00 (com atraso +10%)
        Fatura f = new Fatura("20/02/2023", 3000, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 1000);
        Boleto b2 = new Boleto("121", "20/02/2023", 1000);
        Boleto b3 = new Boleto("131", "21/02/2023", 1000); // +10% de acréscimo
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        boletos.add(b1);
        boletos.add(b2);
        boletos.add(b3);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentoParcialComTiposDiferentes() {
        // Fatura: 2.500,00 (20/02/2023)
        // Boletos: 1000,00 (pago em dia)
        // Pagamentos: 800,00 (CARTAO_CREDITO - válido), 500,00 (TRANSFERENCIA_BANCARIA)
        Fatura f = new Fatura("20/02/2023", 2500, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 1000);
        Pagamento pag1 = new Pagamento(800, "05/02/2023", "CARTAO_CREDITO");
        Pagamento pag2 = new Pagamento(500, "20/02/2023", "TRANSFERENCIA_BANCARIA");
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        boletos.add(b1);
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }

    @Test
    public void testProcessarBoletosNosValoresLimite() {
        // Fatura: 5.000,01 (20/02/2023)
        // Boletos: 0,01 (pago em dia), 5000,00 (pago em dia)
        Fatura f = new Fatura("20/02/2023", 5000.01, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 0.01);
        Boleto b2 = new Boleto("121", "20/02/2023", 5000.00);
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        boletos.add(b1);
        boletos.add(b2);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarVariosTiposComValorExato() {
        // Fatura: 2.500,00 (20/02/2023)
        // Boletos: 1000,00 (pago em dia)
        // Pagamentos: 500,00 (CARTAO_CREDITO - válido), 1000,00 (TRANSFERENCIA_BANCARIA)
        Fatura f = new Fatura("20/02/2023", 2500, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 1000);
        Pagamento pag1 = new Pagamento(500, "05/02/2023", "CARTAO_CREDITO");
        Pagamento pag2 = new Pagamento(1000, "20/02/2023", "TRANSFERENCIA_BANCARIA");
        ArrayList<Boleto> boletos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        boletos.add(b1);
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(boletos, pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

}
