package teste;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import processador.Fatura;

class FaturaTest {

    @Test
    public void testCriarFaturaEmDia() {
        // Testar fatura com dados válidos
        try {
            Fatura f = new Fatura("28/03/2024", 14500);
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para fatura válida.");
        }
    }

    @Test
    public void testCriarFaturaInvalidaComDataInvalida() {
        try {
            Fatura f = new Fatura("", 14500);
            fail("Deveria lançar exceção para data inválida.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarFaturaInvalidaComValorInvalido() {
        try {
            Fatura f = new Fatura("28/03/2024", -100);
            fail("Deveria lançar exceção para valor de fatura inválido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

}
