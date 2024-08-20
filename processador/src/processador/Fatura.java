package processador;

import java.util.ArrayList;
import java.util.List;

public class Fatura {
    
    private double valorTotal;
    private String data;
    private String estado;
    private List<Pagamento> pagamentos;

    public Fatura(String data, double valorTotal) {
        if (data == null || data.isEmpty() || valorTotal <= 0) {
            throw new IllegalArgumentException("Entrada Inválida.");
        }
        this.data = data;
        this.valorTotal = valorTotal;
        this.estado = "PENDENTE";
        this.pagamentos = new ArrayList<>();
    }

    public String getData() {
        return data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void adicionarPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
    }

    public double calcularTotalPago() {
        double resposta = pagamentos.stream().mapToDouble(Pagamento::getValorPago).sum();
        atualizarEstado();
        return resposta;
    }

    public void atualizarEstado() {
        double totalPago = calcularTotalPago();
        if (totalPago >= valorTotal) {
            this.estado = "PAGA";
        } else {
            this.estado = "PENDENTE";
        }
    }
    
    public void aplicarAtraso() {
        this.valorTotal *= 1.10; // Aplica acréscimo de 10% por atraso
    }
}
