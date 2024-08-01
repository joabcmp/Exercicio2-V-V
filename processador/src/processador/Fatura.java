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
        if (data == null || data.isEmpty() || valorTotal <= 0 || nomeCliente == null || nomeCliente.isEmpty()) {
    		throw new IllegalArgumentException("Entrada Inválida.");
    	}
        this.data = data;
        this.valorTotal = valorTotal;
        this.nomeCliente = nomeCliente;
        this.estado = "PENDENTE";
        this.pagamentos = new ArrayList<Pagamento>();
    }

    public String getData() {
        return data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
    
    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }
	
    public void setEstado(String estado) {
        this.estado = estado;
    }

	public String getEstado() {
		return this.estado;
	}

}