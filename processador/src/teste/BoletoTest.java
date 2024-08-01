package teste;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import processador.Boleto;

class BoletoTest {

    @Test
    public void testCriarBoletosValidos() {
        // Testar boleto com valor no limite superior
        try {
            Boleto b1 = new Boleto("111", "28/03/2024", 5000);
        } catch (IllegalArgumentException e) {
            fail("Não deveria lançar exceção para boleto válido.");
        }
    }

    @Test
    public void testCriarBoletosInvalidosComCodigoInvalido() {
        try {
            Boleto b1 = new Boleto("", "28/03/2024", 6000);
            fail("Deveria lançar exceção para código de boleto inválido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarBoletosInvalidosComDataInvalida() {
        try {
            Boleto b1 = new Boleto("112", "", 6000);
            fail("Deveria lançar exceção para data inválida.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarBoletosInvalidosComValorForaDoLimiteInferior() {
        try {
            Boleto b1 = new Boleto("113", "28/03/2024", 0);
            fail("Deveria lançar exceção para valor de boleto abaixo do mínimo permitido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarBoletosInvalidosComValorForaDoLimiteSuperior() {
        try {
            Boleto b1 = new Boleto("114", "28/03/2024", 6001);
            fail("Deveria lançar exceção para valor de boleto acima do máximo permitido.");
        } catch (IllegalArgumentException e) {
            assertEquals("Entrada Inválida.", e.getMessage());
        }
    }

    @Test
    public void testCriarBoletoComPagamentoAtrasado() {
        // Simula um boleto pago com atraso
        Boleto b1 = new Boleto("115", "28/03/2024", 4000);
        double valorPagoComAtraso = b1.getValor() * 1.10; // Valor acrescido de 10% de juros
        assertEquals(4400, valorPagoComAtraso, 0.01, "O valor pago com atraso deve ser acrescido de 10%.");
    }
}
