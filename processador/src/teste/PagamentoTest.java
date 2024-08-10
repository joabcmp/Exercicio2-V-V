package teste;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import processador.Pagamento;

class PagamentoTest {

    @Test
    public void testCriarPagamentoValidoBoleto() {
        // Testar criação de pagamento válido com boleto
        try {
            Pagamento pag = new Pagamento(500, "28/03/2024", "BOLETO");
            assertEquals(500, pag.getValorPago());
            assertEquals("28/03/2024", pag.getData());
            assertEquals("BOLETO", pag.getTipoPagamento());
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para pagamento válido com boleto.");
        }
    }

    @Test
    public void testCriarPagamentoValidoCartaoCredito() {
        // Testar criação de pagamento válido com cartão de crédito
        try {
            Pagamento pag = new Pagamento(1200, "28/03/2024", "CARTAO_CREDITO");
            assertEquals(1200, pag.getValorPago());
            assertEquals("28/03/2024", pag.getData());
            assertEquals("CARTAO_CREDITO", pag.getTipoPagamento());
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para pagamento válido com cartão de crédito.");
        }
    }

    @Test
    public void testCriarPagamentoValidoTransferenciaBancaria() {
        // Testar criação de pagamento válido com transferência bancária
        try {
            Pagamento pag = new Pagamento(700, "28/03/2024", "TRANSFERENCIA_BANCARIA");
            assertEquals(700, pag.getValorPago());
            assertEquals("28/03/2024", pag.getData());
            assertEquals("TRANSFERENCIA_BANCARIA", pag.getTipoPagamento());
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para pagamento válido com transferência bancária.");
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComValorNegativo() {
        // Testar pagamento com valor negativo
        try {
            Pagamento pag = new Pagamento(-100, "28/03/2024", "BOLETO");
            fail("Deveria lançar exceção para valor de pagamento negativo.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComDataInvalida() {
        // Testar pagamento com data inválida
        try {
            Pagamento pag = new Pagamento(856, "", "CARTAO_CREDITO");
            fail("Deveria lançar exceção para data inválida.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComValorForaDoLimiteSuperiorBoleto() {
        // Testar pagamento com valor superior ao permitido para boleto
        try {
            Pagamento pag = new Pagamento(6001, "28/03/2024", "BOLETO");
            fail("Deveria lançar exceção para valor de boleto acima do máximo permitido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComValorForaDoLimiteInferiorBoleto() {
        // Testar pagamento com valor inferior ao permitido para boleto
        try {
            Pagamento pag = new Pagamento(0, "28/03/2024", "BOLETO");
            fail("Deveria lançar exceção para valor de boleto abaixo do mínimo permitido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComTipoInvalido() {
        // Testar pagamento com tipo inválido
        try {
            Pagamento pag = new Pagamento(856, "28/03/2024", "CHEQUE");
            fail("Deveria lançar exceção para tipo de pagamento inválido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }
}
