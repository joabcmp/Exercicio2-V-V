package sistema;

public class Ingresso {
    private int id;
    private TipoIngresso tipo;
    private Status status;
    private double preco;

    public Ingresso(double precoBase, TipoIngresso tipo) {
        if (precoBase <= 0) {
            throw new IllegalArgumentException("Entrada inválida");
        }
        this.preco = precoBase;
        this.tipo = tipo;
        this.status = Status.NAO_VENDIDO;

        switch (tipo) {
            case VIP:
                this.preco *= 2;
                break;
            case MEIA_ENTRADA:
                this.preco /= 2;
                break;
            default:
                break;
        }
    }

    public double getPreco() {
        return this.preco;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        if (this.status == Status.VENDIDO) {
            throw new IllegalArgumentException("Ingresso já vendido");
        }
        this.status = status;
    }
}

