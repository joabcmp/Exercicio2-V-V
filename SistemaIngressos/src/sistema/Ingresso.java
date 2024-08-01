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
    
    public TipoIngresso getTipo() {
        return this.tipo;
    }

    public void setStatus(Status status) {
        if (this.status == Status.VENDIDO) {
            throw new IllegalArgumentException("Ingresso já vendido");
        }
        this.status = status;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(preco);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingresso other = (Ingresso) obj;
		if (Double.doubleToLongBits(preco) != Double.doubleToLongBits(other.preco))
			return false;
		if (status != other.status)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
}

