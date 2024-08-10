package processador;

import java.util.ArrayList;

public class Processador {

    Fatura fatura;

    public Processador(Fatura fatura) {
        this.fatura = fatura;
    }

    public void processarPagamentos(ArrayList<Boleto> boletos, ArrayList<Pagamento> pagamentos) {
        double totalPago = 0.0;

        // Processar boletos
        for (Boleto boleto : boletos) {
            double valorBoleto = boleto.getValor();
            if (boleto.getData().compareTo(fatura.getData()) > 0) {
                valorBoleto *= 1.10; // Adiciona 10% ao valor pago com atraso
            }
            totalPago += valorBoleto;
            Pagamento pagamento = new Pagamento(valorBoleto, boleto.getData(), "BOLETO");
            fatura.adicionarPagamento(pagamento);
        }

        // Processar outros pagamentos
        for (Pagamento pagamento : pagamentos) {
            String tipoPagamento = pagamento.getTipoPagamento();
            String dataPagamento = pagamento.getData();

            boolean incluirPagamento = false;
            if (tipoPagamento.equals("CARTAO_CREDITO")) {
                // Verificar se o pagamento com cartão foi feito pelo menos 15 dias antes da data da fatura
                if (diasEntreDatas(dataPagamento, fatura.getData()) >= 15) {
                    incluirPagamento = true;
                }
            } else if (tipoPagamento.equals("TRANSFERENCIA_BANCARIA")) {
                // Verificar se o pagamento por transferência foi feito na mesma data ou antes da data da fatura
                if (dataPagamento.compareTo(fatura.getData()) <= 0) {
                    incluirPagamento = true;
                }
            }

            if (incluirPagamento) {
                totalPago += pagamento.getValorPago();
                fatura.adicionarPagamento(pagamento);
            }
        }

        if (totalPago >= fatura.getValorTotal()) {
            fatura.setEstado("PAGA");
        } else {
            fatura.setEstado("PENDENTE");
        }
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
