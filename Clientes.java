public class Clientes {
    private int codigoCliente;
	private String nomeCliente;
	private String dataEmbarque;
	private int codigoPacote;
	

	public Clientes(int codigoCliente, String nomeCliente, String dataEmbarque, int codigoPacote) {
		this.codigoCliente = codigoCliente;
		this.nomeCliente = nomeCliente;
		this.dataEmbarque = dataEmbarque;
		this.codigoPacote = codigoPacote;
	}
	
	public int getCodigoCliente() {
		return this.codigoCliente;
	}
	
	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	
	public String getNomeCliente() {
		return this.nomeCliente;
	}
	
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	public String getDataEmbarque() {
		return this.dataEmbarque;
	}
	
	public void setDataEmbarque(String dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}
	
	public int getCodigoPacote() {
		return codigoPacote;
	}
	
	public void setCodigoPacote(int codigoPacote) {
		this.codigoPacote = codigoPacote;
	}
	
	public String toString() {
		return this.codigoCliente + "\t" + this.nomeCliente + "\t" + this.dataEmbarque + "\t" + this.codigoPacote + "\n"; 
	}
}
