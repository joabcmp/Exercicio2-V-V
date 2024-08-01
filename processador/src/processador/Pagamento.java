package processador;
public class Pagamento {
    private double valorPago;
    private String data;
    private String tipoPagamento;

    public Pagamento(double valorPago, String data) {
    	
        this.valorPago = valorPago;
        this.data = data;
        this.tipoPagamento = "BOLETO";
       
    }

	public Object getValorPago() {
		return null;
	}

	public Object getDataPagamento() {
		return null;
	}

}