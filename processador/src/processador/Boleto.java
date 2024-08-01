package processador;

public class Boleto {
    private String codigo;
    private String data;
    private double valor;

    public Boleto(String codigo, String  data, double valor) {
        if (codigo == null || codigo.isEmpty() || data == null || data.isEmpty() || valor <= 0 || valor > 6000) {
    		throw new IllegalArgumentException("Entrada Inválida.");
    	}
        this.codigo = codigo;
        this.data = data;
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public String  getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    public void setValorPago(double valorPago) {
        this.valor = valorPago;
    }

}