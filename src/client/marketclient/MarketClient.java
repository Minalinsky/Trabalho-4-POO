package client.marketclient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import marketlib.*;
public class MarketClient extends DEFINE {
	
	private static ObjectOutputStream objOut = null;
	private static BufferedReader lineIn = null; //Criado pois o readLine() do ObjectInputStream está como "deprecated"
	private static PrintStream lineOut = null;
	private static Socket sock = null;
	private static String serverResponse;
	

	//Isolando construtor (Singleton)
	private MarketClient() {}
				
	private static MarketClient instance = null;
				
	//metodo "construtor":
	public static synchronized MarketClient newMarketClient() {
		if(instance == null) {
			instance = new MarketClient();
		}
		return instance;
	}
	
	//conecta client ao servidor
	public String connect(String ip) { 
		if(ip.equals(""))
			ip = ".";
		
		//Realizando conexao:
		try{
			System.out.println("Trying to connect...");
			sock = new Socket(ip, 4040);
			objOut = new ObjectOutputStream(sock.getOutputStream());
			lineIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			lineOut = new PrintStream(sock.getOutputStream());
			System.out.println("Success!");
			return(SUCCESS);
		}
		
		catch(UnknownHostException e) {
			System.out.println("connect - Client - UnknwnHostException");
			return(ERROR);
			}
		
		catch(IOException e) {
			System.out.println("connect - Client - IOException");
			return(ERROR);
			}
		}
	
	@SuppressWarnings("finally")
	public String registerCostumer(String name, String address, String phone, String email, String ID, String password) throws Exception { //Registra novo comprador
		//Envia operacao desejada ao Server
		lineOut.println(OP_REGISTER_COSTUMER); 
		
		//Envia o registro do comprador
		Costumer c = new Costumer();
		c.setName(name);
		c.setAddress(address);
		c.setPhone(phone);
		c.setEmail(email);
		c.setID(ID);
		c.setPassword(password);
		
		try {
			//Enviando informacoes a serem cadastradas
			objOut.writeObject(c);	
		} 
		
		catch (IOException e) {System.out.println("registerCostumer - Client - IOException");}
		
		finally {
			//Recebendo resposta do servidor:
			serverResponse = lineIn.readLine();
			
			return serverResponse;
			
		}
	}	
	
	public String login(String ID, String password) throws IOException {
		//Envia operacao a ser realizada
		lineOut.println(OP_LOGIN);
		
		//Envia parametros para o servidor
		lineOut.println(ID);
		lineOut.println(password);
		
		//aguarda resposta do servidor (sucesso/erro) 
		serverResponse = lineIn.readLine();
		return serverResponse;
	}
	
public ArrayList<Product> registeredProductsList() throws Exception{
		
		ArrayList<Product> list = new ArrayList<Product>();
		String[] str;
		BufferedReader buffRead = new BufferedReader(new FileReader("src/client/marketclient/Products.csv"));
		String line = buffRead.readLine(); 
		
		while(line != null){
			Product p = new Product();
			str = line.split(","); //Separando cada campo pelo delimitador
			p.setName(str[0]);
			p.setID(str[1]);
			p.setPrice(Float.parseFloat(str[2]));
			p.setExpirationDate(str[3]);
			p.setProvider(str[4]);
			p.setQuantity(Integer.parseInt(str[5]));
			list.add(p);
			line = buffRead.readLine(); //Lendo proximo registro
		}
		buffRead.close();
		return list;
	}

public String refreshProductsFile(){
	//Envia operacao a ser realizada
	lineOut.println(OP_REFRESH_CLIENT);
	
	try{
		FileWriter fileOut = new FileWriter("src/client/marketclient/Products.csv");
		String line = lineIn.readLine();
		
		while(!line.equals(EOF)){
			fileOut.write(line + "\n");
			line = lineIn.readLine();
		}
		
		fileOut.close();
		return SUCCESS;
	}
	catch(IOException e){
		return ERROR;
	}
}

public String buy(String ID, String quantity)
{
	try {
		//Envia operacao a ser realizada
		lineOut.println(OP_BUY);
			
		lineOut.println(ID);
		lineOut.println(quantity);
		
		//aguarda resposta do servidor (sucesso/erro) 
		
		serverResponse = lineIn.readLine();
		return serverResponse;
	}
	
	catch (Exception e) {
		return ERROR;
	}
}

}
