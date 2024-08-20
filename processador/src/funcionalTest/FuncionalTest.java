package funcionalTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import processador.Fatura;
import processador.Processador;
import processador.Pagamento;

public class FuncionalTest {
	/** 
	 * AVL 
	 * Utiliza-se valores nos limites permitidos para assegurar que o sistema se comporte corretamente 
	 * quando se encontram nos limites de suas condições.
	 * 
	 * limites de criação de boletos 0.001 e 5000
	 * criação de testes para os valores fora do limite 0 e 5001
	 */
	
    @Test
    public void testCriarBoletosValidosLimSuperior() {
        // Testar boleto com valor no limite superior
        try {
            Pagamento b1 = new Pagamento(5000, "28/03/2024", "Boleto");
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para boleto válido.");
        }
    }
    
    @Test
    public void testCriarBoletosValidosLimInferior() {
        // Testar boleto com valor no limite inferior
        try {
            Pagamento b1 = new Pagamento(0.01, "28/03/2024", "Boleto");
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para boleto válido.");
        }
    }
    
    @Test
    public void testCriarBoletosInvalidosInferior() {
        // Testar boleto com valor abaixo do limite inferior
        try {
            Pagamento b1 = new Pagamento(0.001, "28/03/2024", "Boleto");
            fail("Deveria lançar exceção para valor de boleto abaixo do mínimo permitido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Valor do boleto fora do limite permitido.", e.getMessage());
        }
    }
    
    @Test
    public void testCriarBoletosInvalidosSuperior() {
        // Testar boleto com valor acima do limite superior
        try {
            Pagamento b1 = new Pagamento(5001, "28/03/2024", "Boleto");
            fail("Deveria lançar exceção para valor de boleto acima do maximo permitido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Valor do boleto fora do limite permitido.", e.getMessage());
        }
    }
    
    /**
     * Partições de Equivalência
     * A ideia do teste por PE é identificar uma suite de testes através do uso de um representante de cada partição
     * Classes de Equivalência:
	 * Data da conta é exatamente 15 dias antes da data da fatura (válido).
	 * Data da conta é mais de 15 dias antes da data da fatura (válido).
	 * Data da conta é menos de 15 dias antes da data da fatura (inválido).
     */
    
    @Test
    public void testPagamentoCartão15DiasAntes() {
    	//testar pagamento com cartão com mais de 15 dias antes da fatura
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(1500, "01/02/2023", "CARTAO");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    @Test
    public void testPagamentoCartão15Dias() {
    	//testar pagamento com cartão com exatamente 15 dias antes da fatura
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
    public void testPagamentoCartãoMenosDe15Dias() {
    	//testar pagamento com cartão com exatamente 15 dias antes da fatura
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(1500, "10/02/2023", "CARTAO");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }
    
    /**
     * Partições de Equivalência
     * Data de pagamento do boleto é igual ou anterior à data da conta (pago em dia, sem acréscimo).
     * Data de pagamento do boleto é posterior à data da conta (pago com atraso, acréscimo de 10%).
     */
    
    @Test
    public void testBoletoEmDia() {
        // Pagamento com boleto sem acrescimo
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(1500, "20/02/2023", "Boleto");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    @Test
    public void testBoletoAtrasado() {
        // Pagamento com boleto atrasado, gerando 10% no valor da fatura 
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(1500, "21/02/2023", "Boleto");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    /**
     * Tabela de Decisões
     * Explicação das Regras:
	 * Regra 1: BOLETO pago em dia, soma total >= fatura: Fatura marcada como PAGA.
	 * Regra 2: BOLETO pago com atraso, acréscimo de 10% aplicado, soma total >= fatura: Fatura marcada como PAGA.
	 * Regra 3: BOLETO pago com atraso, soma total insuficiente: Fatura marcada como PENDENTE.
	 * Regra 4: CARTAO_CREDITO pago em dia, soma total >= fatura: Fatura marcada como PAGA.
	 * Regra 5: CARTAO_CREDITO pago com atraso, aplica 10% de acréscimo, somaTotal >= fatura: Fatura marcada como PAGA.
	 * Regra 6: CARTAO_CREDITO pago com atraso, soma total insuficiente, não inclui na soma: Fatura marcada como PENDENTE.
	 * Regra 7: TRANSFERENCIA_BANCARIA realizada em dia, soma total >= fatura: Fatura marcada como PAGA.
 	 * Regra 8: TRANSFERENCIA_BANCARIA pago com atraso, soma total >= Fatura: Fatura marcada como PAGA.
	 * Regra 9: TRANSFERENCIA_BANCARIA realizada em dia, mas a soma total é insuficiente: Fatura marcada como PENDENTE.
     */
    
    @Test
    public void regra1() {
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(1500, "20/02/2023", "Boleto");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    @Test
    public void regra2() {
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(1650, "21/02/2023", "Boleto");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    @Test
    public void regra3() {
        // Pagamento com boleto atrasado, gerando 10% no valor da fatura 
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento b1 = new Pagamento(1500, "21/02/2023", "Boleto");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(b1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }
    
    @Test
    public void regra4() {
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(1500, "05/02/2023", "CARTAO");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    public void regra5() {
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(1650, "06/02/2023", "CARTAO");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    public void regra6() {
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag1 = new Pagamento(1500, "06/02/2023", "CARTAO");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag1);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }
    
    @Test
    public void regra7() {
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag2 = new Pagamento(1500, "20/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    @Test
    public void regra8() {
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag2 = new Pagamento(1650, "22/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PAGA", fatura.getEstado());
    }
    
    @Test
    public void regra9() {
        Fatura f = new Fatura("20/02/2023", 1500);
        Processador p = new Processador(f);
        Pagamento pag2 = new Pagamento(160, "19/02/2023", "TRANSFERENCIA");
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pag2);
        p.processarPagamentos(pagamentos);
        Fatura fatura = p.getFatura();
        assertEquals("PENDENTE", fatura.getEstado());
    }
}
