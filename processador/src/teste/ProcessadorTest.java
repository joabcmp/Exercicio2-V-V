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
        p.processarBoletos(boletos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

    @Test
    public void testProcessarBoletosCasoAtrasado() {
        // Fatura: 2.000,00 (20/02/2023)
        // Boletos: 500,00 pago em dia + 850,00 pago com 10% de acréscimo por atraso
        Fatura f = new Fatura("20/02/2023", 2000, "Joab Cesar M");
        Processador p = new Processador(f);
        Boleto b1 = new Boleto("111", "20/02/2023", 500);
        Boleto b2 = new Boleto("121", "21/02/2023", 850); // 10% de acréscimo
        ArrayList<Boleto> boletos = new ArrayList<>();
        boletos.add(b1);
        boletos.add(b2);
        p.processarBoletos(boletos);
        Fatura fatura = p.getFatura();
        assertEquals("NAO PAGO", fatura.getEstado());
    }

    @Test
    public void testProcessarPagamentosPorCartaoCredito() {
        // Fatura: 1.500,00 (20/02/2023)
        // Contas: 700,00 (05/02/2023 - válido) e 800,00 (17/02/2023 - válido)
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "05/02/2023");
        Pagamento pag2 = new Pagamento(800, "17/02/2023");
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
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "06/02/2023"); // Fora do período permitido
        Pagamento pag2 = new Pagamento(800, "17/02/2023");
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
        // Contas: 700,00 (pago por transferência) e 800,00 (pago por transferência)
        Fatura f = new Fatura("20/02/2023", 1500, "Joab Cesar M");
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(700, "20/02/2023");
        Pagamento pag2 = new Pagamento(800, "20/02/2023");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }

}
