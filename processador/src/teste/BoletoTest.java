package teste;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import processador.Pagamento;

class BoletoTest {

    @Test
    public void testCriarBoletosValidos() {
        // Testar boleto com valor no limite superior
        try {
            Pagamento b1 = new Pagamento(5000, "28/03/2024", "Boleto");
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para boleto válido.");
        }
    }

    @Test
    public void testCriarBoletosInvalidosComValorForaDoLimiteInferior() {
        try {
            Pagamento b1 = new Pagamento(0, "28/03/2024", "Boleto");
            fail("Deveria lançar exceção para valor de boleto abaixo do mínimo permitido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Valor do boleto fora do limite permitido.", e.getMessage());
        }
    }

    @Test
    public void testCriarBoletosInvalidosComValorForaDoLimiteSuperior() {
        try {
            Pagamento b1 = new Pagamento(5001 , "28/03/2024", "Boleto");
            fail("Deveria lançar exceção para valor de boleto acima do máximo permitido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Valor do boleto fora do limite permitido.", e.getMessage());
        }
    }
}
