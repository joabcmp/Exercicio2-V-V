package processador;

import java.util.ArrayList;

public class Processador {

    Fatura fatura;

    public Processador(Fatura fatura) {
        this.fatura = fatura;
    }

    public void processarPagamentos(ArrayList<Pagamento> pagamentos) {
        for (Pagamento pagamento : pagamentos) {
            String tipoPagamento = pagamento.getTipoPagamento();
            String dataPagamento = pagamento.getData();

            if (tipoPagamento.equals("BOLETO") && dataPagamento.compareTo(fatura.getData()) > 0) {
                fatura.aplicarAtraso();
            } else if (tipoPagamento.equals("CARTAO_CREDITO") && diasEntreDatas(dataPagamento, fatura.getData()) < 15) {
            	fatura.aplicarAtraso();
            }

            fatura.adicionarPagamento(pagamento);
        }
        
        fatura.atualizarEstado();
    }

    private int diasEntreDatas(String dataInicio, String dataFim) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
                java.time.LocalDate.parse(dataInicio, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                java.time.LocalDate.parse(dataFim, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }

    public Fatura getFatura() {
        return fatura;
    }
}
