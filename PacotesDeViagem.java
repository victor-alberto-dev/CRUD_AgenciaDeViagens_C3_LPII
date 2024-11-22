public class PacotesDeViagem {
    private int codigoPacote;
	private String destinoPacote;
	private double preco;
	
	public PacotesDeViagem(int codigoPacote, String destinoPacote, double preco) {
		this.codigoPacote = codigoPacote;
		this.destinoPacote = destinoPacote;
		this.preco = preco;
	}
	
	public int getCodigoPacote() {
		return this.codigoPacote;
	}
	public void setCodigoPacote(int codigoPacote) {
		this.codigoPacote = codigoPacote;
	}
	public String getDestinoPacote() {
		return this.destinoPacote;
	}
	public void setDestinoPacote(String destinoPacote) {
		this.destinoPacote = destinoPacote;
	}
	
	public double getPreco() {
		return this.preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String toString() {
		return this.codigoPacote + "\t" + this.destinoPacote + "\t" + this.preco + "\n";
	}
    
}
