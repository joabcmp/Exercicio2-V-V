package teste;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import processador.Pagamento;

class PagamentoTest {

    @Test
    public void testCriarPagamentoValido() {
        // Testar criação de pagamento válido
        try {
            Pagamento pag = new Pagamento(856, "28/03/2024");
            assertEquals(856, pag.getValorPago());
            assertEquals("28/03/2024", pag.getDataPagamento());
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para pagamento válido.");
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComValorNegativo() {
        // Testar pagamento com valor negativo
        try {
            Pagamento pag = new Pagamento(-100, "28/03/2024");
            fail("Deveria lançar exceção para valor de pagamento negativo.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComDataInvalida() {
        // Testar pagamento com data inválida
        try {
            Pagamento pag = new Pagamento(856, "");
            fail("Deveria lançar exceção para data inválida.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComDataFutura() {
        // Testar pagamento com data futura (não deveria ser aceito se não permitido pelo sistema)
        try {
            Pagamento pag = new Pagamento(856, "28/03/2025");
            fail("Deveria lançar exceção para data de pagamento futura.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarPagamentoInvalidoComDataNoPassado() {
        // Testar pagamento com data no passado
        try {
            Pagamento pag = new Pagamento(856, "01/01/2022");
            assertEquals(856, pag.getValorPago());
            assertEquals("01/01/2023", pag.getDataPagamento());
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para data no passado.");
        }
    }

}
