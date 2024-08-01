package processador;

import java.util.ArrayList;
import java.util.List;

public class Processador {
	
	Fatura fatura;
	
	
    public Processador(Fatura fatura) {
		this.fatura = fatura;
	}

	public void processarBoletos(ArrayList<Boleto> boletos) {
        double totalPago = 0.0;

       
        for (Boleto boleto : boletos) {
        	if (boleto.getData().compareTo(fatura.getData()) > 0) {
                totalPago += boleto.getValor() * 1.10; // Adiciona 10% ao valor pago com atraso
            } else {
                totalPago += boleto.getValor();
            }
        }
        if (totalPago >= fatura.getValorTotal()) {
            fatura.setEstado("PAGA");
        } else {
            fatura.setEstado("PENDENTE");
        }
    }
    
	public Fatura getFatura() {
		return fatura;
		
	}

    public void processarPagamentos(ArrayList<Pagamento> pagamentos) {
        double totalPago = 0.0;
        for (Pagamento pagamento : pagamentos) {
            totalPago += pagamento.getValorPago();
        }
        if (totalPago >= fatura.getValorTotal()) {
            fatura.setEstado("PAGA");
        } else {
            fatura.setEstado("PENDENTE");
        }
    }

}