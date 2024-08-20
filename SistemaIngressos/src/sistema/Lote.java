package sistema;

import java.util.ArrayList;
import java.util.List;

public class Lote {
	private static int ultimoId = 0;
    private int id;
    private List<Ingresso> ingressos;
    private double desconto;

    public Lote(int numIngressos, double desconto, double porcentagemVIP, double precoBase) {
        if (numIngressos <= 0) {
            throw new IllegalArgumentException("Numero de ingressos vazio");
        }
        if (porcentagemVIP < 0.2 || porcentagemVIP > 0.3) {
            throw new IllegalArgumentException("Porcentagem de ingressos VIP excedida");
        }
        if (desconto < 0 || desconto > 0.25) {
            throw new IllegalArgumentException("Porcentagem de desconto limite excedida");
        }
        
        this.id = ++ultimoId;
        this.ingressos = new ArrayList<>();
        this.desconto = desconto;

        int numVIP = (int)Math.ceil(numIngressos * porcentagemVIP);
        int numMeia = (int)Math.ceil(numIngressos * 0.1);
        int numNormal = numIngressos - numVIP - numMeia;
        
        for (int i = 0; i < numVIP; i++) {
            ingressos.add(new Ingresso(precoBase, TipoIngresso.VIP));
        }
        for (int i = 0; i < numMeia; i++) {
            ingressos.add(new Ingresso(precoBase, TipoIngresso.MEIA_ENTRADA));
        }
        for (int i = 0; i < numNormal; i++) {
            ingressos.add(new Ingresso(precoBase, TipoIngresso.NORMAL));
        }
    }

    public List<Ingresso> getIngressos() {
        return this.ingressos;
    }
    
    public double comprIngressoByID(int id) {
    	double saida = -1;
    	for (Ingresso ingresso : ingressos) {
        	if (ingresso.getId() == id) {
        		ingresso.setStatus(Status.VENDIDO);
        		
        		if (ingresso.getTipo() == TipoIngresso.MEIA_ENTRADA) 
        			saida = ingresso.getPreco();
                else 
                	saida = ingresso.getPreco() * (1 - this.desconto);
        		break;
    		}
        }
    	return saida;
    }

    public void compraIngressos() {
        for (Ingresso ingresso : ingressos) {
        	if (ingresso.getStatus() != Status.VENDIDO) 
        		ingresso.setStatus(Status.VENDIDO);
        }
    }

    public int GetNumIngressosNormalVendidos() {
        int count = 0;
        for (Ingresso i : ingressos) {
            if (i.getStatus() == Status.VENDIDO && i.getTipo() == TipoIngresso.NORMAL) {
                count++;
            }
        }
        return count;
    }

    public int GetNumIngressosVipVendidos() {
        int count = 0;
        for (Ingresso i : ingressos) {
            if (i.getStatus() == Status.VENDIDO && i.getTipo() == TipoIngresso.VIP) {
                count++;
            }
        }
        return count;
    }

    public int GetNumIngressosMeiaVendidos() {
        int count = 0;
        for (Ingresso i : ingressos) {
            if (i.getStatus() == Status.VENDIDO && i.getTipo() == TipoIngresso.MEIA_ENTRADA) {
                count++;
            }
        }
        return count;
    }

    public double getReceita() {
    	double receita = 0;
        for (Ingresso ingresso : ingressos) {
            if (ingresso.getStatus() == Status.VENDIDO) {
                if (ingresso.getTipo() == TipoIngresso.MEIA_ENTRADA) receita += ingresso.getPreco();
                else receita += ingresso.getPreco() * (1 - this.desconto);
            }
        }
        return receita;

    }

	public int getId() {
		return id;
	}
}
