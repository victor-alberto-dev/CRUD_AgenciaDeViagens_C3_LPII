import java.io.*;
import java.util.Scanner;

public class AgenciaDeViagemMain {
	static StringBuffer memoriaPacotesDeViagem = new StringBuffer();
	static StringBuffer memoriaClientes = new StringBuffer();
	static Scanner scan = new Scanner (System.in);

	public static void main(String[] args) {
		char opcao, resp = 'N';
		do {
			opcao = menu();
			switch(opcao) {
			case '1' :
				inserirDadosPacote();
				break;
			case '2':
				if (verificarMemoriaVazia(memoriaPacotesDeViagem)) {
					System.out.println("\nNenhum pacote foi cadastrado. Operação cancelada.");
				} else {
					inserirDadosCliente();					
				}
				break;
			case '3':
				alterarDados();					
				break;
			case '4':
				excluirDados();				
				break;
			case '5':
				pesquisarDadosPacote();				
				break;
			case '6':
				pesquisarDadosCliente();			
				break;
			case '7':
				if (verificarMemoriaVazia(memoriaClientes) || verificarMemoriaVazia(memoriaPacotesDeViagem)) {
					System.out.println("\nNenhum cliente foi cadastrado. Operação cancelada.");
				} else {	
					pesquisarPorPacote();
				}
				break;
			case '8':	
				mostrarTodosDados();
				break;
			case '9':
				System.out.print("Deseja realmente sair do programa? S/N: ");
				resp = Character.toUpperCase(scan.next().charAt(0));
				if (resp == 'S') {
					System.out.println("\nPrograma finalizado.");
					System.out.println("\nDesenvolvido por:\nVictor Alberto Dos Passos\n");	
				}
				break;
			default:
				System.out.println("\nOpção inválida. Tente novamente.");
			}
		} while (resp != 'S');
		System.exit(0);
	}
	
	public static char menu(){
		System.out.println("==================================================================================================================================================");
		System.out.print("Menu\n1. Inserir dados do pacote\n"+
				"2. Inserir dados do cliente\n"+
				"3. Alterar dados do cliente\n"+
				"4. Excluir dados do cliente\n"+
				"5. Pesquisar dados do pacote\n"+
				"6. Pesquisar dados do cliente\n"+
				"7. Pesquisar registros de clientes baseado em um pacote\n" +
				"8. Mostrar dados de pacotes e clientes cadastrados\n" +
				"9. Sair do programa\n");
				System.out.println("==================================================================================================================================================");
		System.out.print("Digite uma opção: ");
		return scan.next().charAt(0);
	} 
	
	public static void iniciarArquivo(StringBuffer memoria, String nomeArquivo){ 
		try{
			BufferedReader arquivoEntrada;
			arquivoEntrada = new BufferedReader (new FileReader(nomeArquivo));
			String linha = "";
			memoria.delete(0,memoria.length());
			do {
				linha = arquivoEntrada.readLine();
				if (linha != null) {
					memoria.append (linha + "\n");
				}
			} while (linha != null);
			arquivoEntrada.close();
		} catch (FileNotFoundException erro){
			System.out.println("\nArquivo não encontrado");
		} catch (Exception e){
			System.out.println("\nErro de Leitura!");
		}
	}
	
	public static void gravarDados(StringBuffer memoria, String nomeArquivo){
		try {
			BufferedWriter arquivoSaida;
			arquivoSaida = new BufferedWriter(new FileWriter (nomeArquivo));
			arquivoSaida.write(memoria.toString());
			arquivoSaida.flush();
			arquivoSaida.close();
		} catch (Exception e){
			System.out.println("\nErro de gravação!");
		}
	}
	
	public static boolean verificarMemoriaVazia(StringBuffer memoria) {
		if (memoria.length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void inserirDadosPacote() {
		int codigoPacote;
		String destinoPacote;
		double preco;
		try {
			System.out.print("Digite o código do pacote: ");
			codigoPacote = scan.nextInt();
			System.out.print("Digite o destino do pacote: ");
			destinoPacote = scan.next();
			System.out.print("Digite o preço do pacote: ");
			preco = scan.nextDouble();
			PacotesDeViagem reg = new PacotesDeViagem(codigoPacote, destinoPacote, preco);
			memoriaPacotesDeViagem.append(reg.toString());
			gravarDados(memoriaPacotesDeViagem, "PacotesDeViagem.txt");
			System.out.println("\nPacote cadastrado com sucesso!");
		} catch (Exception e) {
			System.out.println("\nErro de gravação");
		}
	}
	
	public static void inserirDadosCliente(){
		int codigoCliente, codigoPacote;
		String nomeCliente, dataEmbarque;
		if (verificarMemoriaVazia(memoriaPacotesDeViagem)) {
			System.out.println("\nNenhum pacote foi cadastrado. Operação cancelada.");
		} else {
			try {
				System.out.print("Digite o código do cliente: ");
				codigoCliente = scan.nextInt();
				System.out.print("Digite o nome do cliente: ");
				nomeCliente = scan.next();
				System.out.print("Digite a data de embarque do cliente (dd-mm-aaaa): ");
				dataEmbarque = scan.next();
				System.out.println("\n" + memoriaPacotesDeViagem);
				System.out.print("Digite um dos códigos acima que deseja associar ao cliente: ");
				codigoPacote = scan.nextInt();
				if (!verificarPacoteExistente(codigoPacote)) {
					System.out.println("\nErro: O código do pacote informado não existe. Inserção de dados cancelada.");
				} else {
					Clientes reg = new Clientes(codigoCliente, nomeCliente, dataEmbarque, codigoPacote);
					memoriaClientes.append(reg.toString());
					gravarDados(memoriaClientes, "Clientes.txt");
					System.out.println("\nCliente cadastrado com sucesso!");	
				}
			} catch (Exception e) {
				System.out.println("\nErro de gravação");
			}				
		}
		
	}
	
	public static boolean verificarPacoteExistente(int codigoPacote) {
		iniciarArquivo(memoriaPacotesDeViagem, "PacotesDeViagem.txt");
		if (memoriaPacotesDeViagem.length() != 0) {
			int inicio = 0;
			while (inicio != memoriaPacotesDeViagem.length()) {
				int ultimo = memoriaPacotesDeViagem.indexOf("\t", inicio);
				String temp = memoriaPacotesDeViagem.substring(inicio, ultimo);
				int codigoPacoteAtual = Integer.parseInt(temp);
				if (codigoPacote == codigoPacoteAtual) {
					return true;
				}
				inicio = memoriaPacotesDeViagem.indexOf("\n", ultimo) + 1;
			}
		}
		return false;
	}
	
	public static void alterarDados() {
		String codigoCliente, nomeCliente, dataEmbarque, codigoPacote;
		int inicio, fim, ultimo, primeiro;
		boolean achou=false;
		int procura;
		iniciarArquivo(memoriaClientes, "Clientes.txt"); 
		if (memoriaClientes.length() != 0) {
			System.out.println("\n" + memoriaClientes);
			System.out.print("\nDigite o código do cliente para alteração (Primeira coluna dos registros): ");
			procura = scan.nextInt();
			inicio = 0;  
			while ((inicio != memoriaClientes.length()) && (!achou)) {
				ultimo = memoriaClientes.indexOf ("\t", inicio);
				codigoCliente = memoriaClientes.substring(inicio, ultimo);
				primeiro = ultimo + 1;
				ultimo = memoriaClientes.indexOf ("\t", primeiro); 
				nomeCliente = memoriaClientes.substring(primeiro, ultimo);		
				primeiro = ultimo + 1;
				ultimo = memoriaClientes.indexOf ("\t", primeiro);
				dataEmbarque = memoriaClientes.substring(primeiro, ultimo);
				primeiro = ultimo + 1;
				fim = memoriaClientes.indexOf ("\n", primeiro);
				codigoPacote = memoriaClientes.substring(primeiro, fim);
				Clientes reg = new Clientes (Integer.parseInt(codigoCliente), nomeCliente, dataEmbarque, Integer.parseInt(codigoPacote));
				if (procura == reg.getCodigoCliente()){
					System.out.println("\nCódigo do cliente: " + reg.getCodigoCliente()+
							"  \nNome do cliente: " + reg.getNomeCliente()+
							"  \nData de embarque: " + reg.getDataEmbarque() + 
							"  \nPacote associado: " + reg.getCodigoPacote());
					System.out.print("\nEntre com a nova data de embarque: ");
					reg.setDataEmbarque(scan.next());
					memoriaClientes.replace(inicio, fim+1,reg.toString());
					gravarDados(memoriaClientes, "Clientes.txt");
					achou = true;
				}
				inicio = fim + 1; 
			}
			if (achou){
				System.out.println("\nAlteração realizada com sucesso.");
			}else{
				System.out.println("\nCódigo não encontrado. Alteração cancelada.");
			}
		} else {
			System.out.println("\nArquivo vazio. Operação cancelada.");
		}
	}
	
	public static void excluirDados() {
		String codigoCliente, nomeCliente, dataEmbarque, codigoPacote;
		int inicio, fim, ultimo, primeiro, procura;
		boolean achou = false;
		char resp;
		iniciarArquivo(memoriaClientes, "Clientes.txt"); 
		if (memoriaClientes.length() != 0) {
			System.out.println("\n" + memoriaClientes);
			System.out.print("\nDigite o código do cliente para exclusão (Primeira coluna dos registros): ");
			procura= scan.nextInt();
			inicio = 0;  
			while ((inicio != memoriaClientes.length()) && (!achou)) {
				ultimo = memoriaClientes.indexOf ("\t", inicio);
				codigoCliente = memoriaClientes.substring(inicio, ultimo);
				primeiro = ultimo + 1;
				ultimo = memoriaClientes.indexOf ("\t", primeiro); 
				nomeCliente = memoriaClientes.substring(primeiro, ultimo);		
				primeiro = ultimo + 1;
				ultimo = memoriaClientes.indexOf ("\t", primeiro);
				dataEmbarque = memoriaClientes.substring(primeiro, ultimo);
				primeiro = ultimo + 1;
				fim = memoriaClientes.indexOf ("\n", primeiro);
				codigoPacote = memoriaClientes.substring(primeiro, fim);
				Clientes reg = new Clientes (Integer.parseInt(codigoCliente), nomeCliente, dataEmbarque, Integer.parseInt(codigoPacote));
				if (procura == reg.getCodigoCliente()){
					System.out.print("\nCódigo do cliente: " + reg.getCodigoCliente()+
							"  \nNome do cliente: " + reg.getNomeCliente()+
							"  \nData de embarque: " + reg.getDataEmbarque() + 
							"  \nPacote associado: " + reg.getCodigoPacote() +
							"\nDeseja excluir o registro (S ou N)?\nOpção: ");
					resp = Character.toUpperCase(scan.next().charAt(0));
					if (resp == 'S'){
						memoriaClientes.delete (inicio, fim + 1);	
						System.out.println("\nRegistro excluído.");
						gravarDados(memoriaClientes, "Clientes.txt"); 
					} else{
						System.out.println("\nExclusão cancelada.");
					}
					achou = true;
				}
				inicio = fim + 1;  
			}
			if (!achou){
				System.out.println("\nCódigo não encontrado. Operação cancelada.");
			}
		} else {
			System.out.println("\nArquivo vazio. Operação cancelada.");
		}
	}
	
	public static void pesquisarDadosPacote() {
		String codigoPacote, destinoPacote, preco;
		int inicio, fim, ultimo, primeiro;
		boolean achou = false;
		int procura;
		iniciarArquivo(memoriaPacotesDeViagem, "PacotesDeViagem.txt"); 
		if (memoriaPacotesDeViagem.length() != 0) {   
			System.out.print("\nDigite o codigo do pacote para pesquisar: ");
			procura= scan.nextInt();
			inicio = 0;    
			while ((inicio != memoriaPacotesDeViagem.length()) && (!achou)) {
				ultimo = memoriaPacotesDeViagem.indexOf ("\t", inicio);
				codigoPacote = memoriaPacotesDeViagem.substring(inicio, ultimo);
				primeiro = ultimo + 1;
				ultimo = memoriaPacotesDeViagem.indexOf ("\t", primeiro);
				destinoPacote = memoriaPacotesDeViagem.substring(primeiro, ultimo);
				primeiro = ultimo + 1;
				fim = memoriaPacotesDeViagem.indexOf ("\n", primeiro);
				preco = memoriaPacotesDeViagem.substring(primeiro, fim);
				PacotesDeViagem reg = new PacotesDeViagem (Integer.parseInt(codigoPacote), destinoPacote, Double.parseDouble(preco));
				if (procura == reg.getCodigoPacote()){
					System.out.println("\nCódigo do pacote: " + reg.getCodigoPacote() +
							           "\nDestino do pacote: " + reg.getDestinoPacote() +
							           "\nValor do pacote: R$" + reg.getPreco());
					achou = true;
				}
				inicio = fim + 1;  
			}
			if (!achou){
				System.out.println("\nCódigo não encontrado.");
			}
		}else{
			System.out.println("\nArquivo vazio. Operação cancelada.");
		}
	}
	
	public static void pesquisarDadosCliente() {
		String codigoCliente, nomeCliente, dataEmbarque, codigoPacote;
		int inicio, fim, ultimo, primeiro;
		boolean achou = false;
		int procura;
		iniciarArquivo(memoriaClientes, "Clientes.txt");
		if (memoriaClientes.length() != 0) {
			System.out.print("Digite o código do cliente para pesquisar: ");
			procura = scan.nextInt();
			inicio = 0;
			while((inicio != memoriaClientes.length()) && (!achou)) {
				ultimo = memoriaClientes.indexOf("\t", inicio);
				codigoCliente = memoriaClientes.substring(inicio, ultimo);
				primeiro = ultimo + 1;
				ultimo = memoriaClientes.indexOf("\t", primeiro);
				nomeCliente = memoriaClientes.substring(primeiro, ultimo);
				primeiro = ultimo + 1;
				ultimo = memoriaClientes.indexOf ("\t", primeiro);
				dataEmbarque = memoriaClientes.substring(primeiro, ultimo);
				primeiro = ultimo + 1;
				fim = memoriaClientes.indexOf ("\n", primeiro);
				codigoPacote = memoriaClientes.substring(primeiro, fim);
				Clientes reg = new Clientes (Integer.parseInt(codigoCliente), nomeCliente, dataEmbarque, Integer.parseInt(codigoPacote));
				if (procura == reg.getCodigoCliente()) {
					System.out.println("\nCódigo do cliente: " + reg.getCodigoCliente()+
							"  \nNome do cliente: " + reg.getNomeCliente()+
							"  \nData de embarque: " + reg.getDataEmbarque() + 
							"  \nPacote associado: " + reg.getCodigoPacote());
					achou = true;
				}
				inicio = fim + 1;
			}
			if (!achou) {
				System.out.println("\nCódigo não encontrado.");
			}
			
		} else {
			System.out.println("\nArquivo vazio. Operação cancelada.");
		}
	}
	
	public static void pesquisarPorPacote() {
		iniciarArquivo(memoriaClientes, "Clientes.txt");
		System.out.println("\n" + memoriaPacotesDeViagem);
		System.out.print("Escolha e digite um dos códigos acima para exibir os clientes associados a ele: ");
		int codigoPacote = scan.nextInt();
		if (!verificarPacoteExistente(codigoPacote)) {
			System.out.println("\nPacote não encontrado. Pesquisa cancelada.");
		} else {
			int inicio = 0;
			boolean achou = false;
			while (inicio < memoriaClientes.length()) {
				int fim = memoriaClientes.indexOf("\n", inicio);
				String linha = memoriaClientes.substring(inicio, fim);
				String [] dados = linha.split("\t");
				int codigoPacoteCliente = Integer.parseInt(dados[3]);
				if (codigoPacote == codigoPacoteCliente) {
					System.out.print("\n" + dados[0] + "\t" + dados[1] 
							+ "\t" + dados[2] + "\t" + dados[3] + "\n");
					achou = true;
				}
				inicio = fim + 1;
			}
			if (!achou) {
				System.out.println("\nNenhum cliente está associado a este pacote.");
			}	
		}
	}
	
	public static void mostrarTodosDados() {
		if (verificarMemoriaVazia(memoriaPacotesDeViagem)) {
			System.out.println("\nNenhum pacote de viagem foi cadastrado.");
		} else {
			System.out.println("\nPacotes de Viagem: ");
			System.out.println("-----------------------------------------------------------");
			String [] pacotes = memoriaPacotesDeViagem.toString().split("\n");
			for (String pacote : pacotes) {
				String [] dadosPacote = pacote.split("\t");
				System.out.println("| Código Pacote: " + dadosPacote[0] + 
                        " | Destino: " + dadosPacote[1] + 
                        " | Preço: R$ " + dadosPacote[2] + " |");
				System.out.println("-----------------------------------------------------------");
			}
		}
		if (verificarMemoriaVazia(memoriaClientes)) {
			System.out.println("\nNenhum cliente foi cadastrado.");
		} else {
			System.out.println("\nClientes Cadastrados: ");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
			String [] clientes = memoriaClientes.toString().split("\n");
			for (String cliente : clientes) {
				String [] dadosCliente = cliente.split("\t");
				System.out.println("| Código do Cliente: " + dadosCliente[0] +
                        " | Nome do cliente: " + dadosCliente[1] +
                        " | Data de Embarque: " + dadosCliente[2] +
                        " | Código do Pacote associado: " + dadosCliente[3] + " |");
				System.out.println("-----------------------------------------------------------------------------------------------------------------------");
			}
		}
	}
}