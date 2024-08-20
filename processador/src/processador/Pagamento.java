package processador;

public class Pagamento {
    private double valorPago;
    private String data;
    private String tipoPagamento;

    public Pagamento(double valorPago, String data, String tipoPagamento) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Entrada Inválida.");
        }
        tipoPagamento = tipoPagamento.toUpperCase(); 
        if (!tipoPagamento.equals("BOLETO") && !tipoPagamento.equals("CARTAO") && !tipoPagamento.equals("TRANSFERENCIA")) {
            throw new IllegalArgumentException("Tipo de pagamento Inválido.");
        }
        if (tipoPagamento.equals("BOLETO") && (valorPago < 0.01 || valorPago > 5000.00)) {
            throw new IllegalArgumentException("Valor do boleto fora do limite permitido.");
        }

        this.valorPago = valorPago;
        this.data = data;
        this.tipoPagamento = tipoPagamento.toUpperCase();
    }

    public double getValorPago() {
        return valorPago;
    }

    public String getData() {
        return data;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }
    
}
