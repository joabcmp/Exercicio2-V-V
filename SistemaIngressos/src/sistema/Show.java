package sistema;

import java.util.ArrayList;
import java.util.List;

public class Show {
    private String data;
    private String artista;
    private double cache;
    private double despesasInfra;
    private boolean dataEspecial;
    private double porcentagemVIP;
    private List<Lote> lotes;

    public Show(String data, String artista, double cache, double despesasInfra, boolean dataEspecial, double porcentagemVIP) {
        if (porcentagemVIP < 0.2 || porcentagemVIP > 0.3) {
            throw new IllegalArgumentException("Porcentagem de ingressos VIP excedida");
        }
        this.data = data;
        this.artista = artista;
        this.cache = cache;
        this.despesasInfra = despesasInfra;
        this.dataEspecial = dataEspecial;
        this.porcentagemVIP = porcentagemVIP;
        this.lotes = new ArrayList<>();
    }

    public int createLote(int numIngressos, double desconto, double precoBase) {
        Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
        lotes.add(lote);
        return lote.getId();
    }

    public double comprarLotePorId(int id) {
        Lote lote = lotes.get(id);
        lote.compraIngressos();
        return lote.getReceita();
    }

    public String gerarRelatorio() {
        int numNormaisVendidos = 0;
        int numVipVendidos = 0;
        int numMeiaVendidos = 0;
        double receitaTotal = 0;

        for (Lote lote : lotes) {
            numNormaisVendidos += lote.GetNumIngressosNormalVendidos();
            numVipVendidos += lote.GetNumIngressosVipVendidos();
            numMeiaVendidos += lote.GetNumIngressosMeiaVendidos();
            receitaTotal += lote.getReceita();
        }

        double custoTotal = cache + despesasInfra;
        if (dataEspecial) {
            custoTotal += despesasInfra * 0.15;
        }

        double receitaLiquida = receitaTotal - custoTotal;
        String statusFinanceiro;
        if (receitaLiquida > 0) {
            statusFinanceiro = "LUCRO";
        } else if (receitaLiquida < 0) {
            statusFinanceiro = "PREJUÍZO";
        } else {
            statusFinanceiro = "ESTÁVEL";
        }

        return String.format(
            "Relatorio do show:\n" +
            "Numero de ingressos normais vendidos: %d\n" +
            "Numero de ingressos VIPs vendidos: %d\n" +
            "Numero de ingressos meia entrada vendidos: %d\n" +
            "Receita liquida do show: %.2f\n" +
            "Custos do show: %.2f\n" +
            "Status financeiro do show: %s",
            numNormaisVendidos, numVipVendidos, numMeiaVendidos, receitaLiquida, custoTotal, statusFinanceiro
        );
    }

	public double getPorcentagemVIP() {
		return porcentagemVIP;
	}

	public void setPorcentagemVIP(double porcentagemVIP) {
		this.porcentagemVIP = porcentagemVIP;
	}
}
