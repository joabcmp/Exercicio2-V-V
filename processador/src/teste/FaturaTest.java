package teste;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import processador.Fatura;

class FaturaTest {

    @Test
    public void testCriarFaturaEmDia() {
        // Testar fatura com dados válidos
        try {
            Fatura f = new Fatura("28/03/2024", 14500, "Joab Cesar M");
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para fatura válida.");
        }
    }

    @Test
    public void testCriarFaturaInvalidaComNomeInvalido() {
        try {
            Fatura f = new Fatura("28/03/2024", 14500, "");
            fail("Deveria lançar exceção para nome de fatura inválido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarFaturaInvalidaComDataInvalida() {
        try {
            Fatura f = new Fatura("", 14500, "Joab Cesar M");
            fail("Deveria lançar exceção para data inválida.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarFaturaInvalidaComValorInvalido() {
        try {
            Fatura f = new Fatura("28/03/2024", -100, "Joab Cesar M");
            fail("Deveria lançar exceção para valor de fatura inválido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarFaturaComValorZero() {
        try {
            Fatura f = new Fatura("28/03/2024", 0, "Joab Cesar M");
            fail("Deveria lançar exceção para valor de fatura igual a zero.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarFaturaComValorPositivoValido() {
        // Testar fatura com valor positivo válido
        try {
            Fatura f = new Fatura("28/03/2024", 1000, "Joab Cesar M");
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para valor de fatura positivo válido.");
        }
    }
}
