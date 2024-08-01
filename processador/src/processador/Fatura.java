package processador;

import java.util.ArrayList;
import java.util.List;

public class Fatura {
    
    private double valorTotal;
    private String nomeCliente;
	private String data;
	private List<Pagamento> pagamentos;
	private String estado;

    public Fatura(String data, double valorTotal, String nomeCliente) {
        this.data = data;
        this.valorTotal = valorTotal;
        this.nomeCliente = nomeCliente;
        this.estado = "NAO PAGO";
        this.pagamentos = new ArrayList<Pagamento>();
    }

	public Object getEstado() {
		return null;
	}

}
