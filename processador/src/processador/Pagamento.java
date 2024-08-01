package processador;
public class Pagamento {
    private double valorPago;
    private String data;
    private String tipoPagamento;

    // Construtor
    public Pagamento(double valorPago, String data) {
    	if(data.isEmpty()){
    		throw new IllegalArgumentException("Entrada Inválida.");
    	}
        this.valorPago = valorPago;
        this.data = data;
        this.tipoPagamento = "BOLETO";
       
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }


}