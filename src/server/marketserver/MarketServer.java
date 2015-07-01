package server.marketserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import marketlib.*;


public class MarketServer extends DEFINE implements Runnable
{
	//Isolando construtor (Singleton)
	private MarketServer() {}
		
	private static MarketServer instance = null;
		
	//metodo "construtor":
	public static synchronized MarketServer newMarketServer() {
		if(instance == null) {
			instance = new MarketServer();
		}
		return instance;
	}
	
	//Fica aceitando conexoes e jogando em Threads
	public void initialize() throws Exception 
	{ 
		//Thread para aceitar conexoes
		Thread t = new Thread(instance);
		t.start();
	}
	
	public void run() {
		ServerSocket ss;
		try {
			ss = new ServerSocket(4040);
		
			while(true) {
				System.out.println("Waiting for client's connection...");
				Socket servSock = ss.accept();
				System.out.println("Client connected!\n");
				Thread t = new Thread(new ServerThread(servSock, instance));
				t.start();
				}
		}
			catch(Exception e){}
	}
	
private ArrayList<Costumer> registeredCostumersList() throws IOException{
		
		ArrayList<Costumer> list = new ArrayList<Costumer>();
		String[] str;
		BufferedReader buffRead = new BufferedReader(new FileReader("src/server/marketserver/Costumers.csv"));
		String line = buffRead.readLine(); 
		while(line != null){
				Costumer c = new Costumer();
				str = line.split(","); //Separando cada campo pelo delimitador
				c.setName(str[0]);
				c.setAddress(str[1]);
				c.setPhone(str[2]);
				c.setEmail(str[3]);
				c.setID(str[4]);
				c.setPassword(str[5]);
				list.add(c);
				line = buffRead.readLine(); //Lendo proximo registro
		}
		buffRead.close();
		return list;
	}
	
	
	
	//Le arquivo de produtos e retorna lista de produtos
	public ArrayList<Product> registeredProductsList() throws Exception{
		
		ArrayList<Product> list = new ArrayList<Product>();
		String[] str;
		BufferedReader buffRead = new BufferedReader(new FileReader("src/server/marketserver/Products.csv"));
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
	
	public synchronized String registerCostumer(Costumer c) {
		try {
			ArrayList<Costumer> costList = this.registeredCostumersList();
			
			
			//Verificando se ja existe usuario com esse ID cadastrado
			if(costList.stream().anyMatch(cost -> cost.getID().equals(c.getID()))) {
				System.out.println("Costumer already registered!");
				return DUPLICATED_COSTUMER;
			}
			
			//Se nao estiver cadastrado, cadastra-o
			else {
				BufferedWriter buffWrite = new BufferedWriter(new FileWriter("src/server/marketserver/Costumers.csv", true));
				buffWrite.append(c.getName() + "," + c.getAddress() + "," + c.getPhone() + "," + c.getEmail() + "," + c.getID() + "," + c.getPassword() + "\n");
				buffWrite.close();
				return SUCCESS;
			}
		}
		
		catch (IOException e) {
			System.out.println("Error while registering user (MarketServer)");
			return ERROR;
		}
		
	}
	
	//Faz login do cliente
		public String login(String ID, String password) throws Exception{
			//Carregando todos os usuarios cadastrados para um ArrayList
			ArrayList<Costumer> costList = this.registeredCostumersList();
			
			//Filtando por ID
			//verificando se o password combina com o parametro
			if( costList.stream().filter(cost -> cost.getID().equals(ID)).anyMatch(cost -> cost.getPassword().equals(password)) ) {
				return SUCCESS;
			}
			return ERROR;
		}
		
		//Verifica se o produto com o ID passado ja esta cadastrado
		public boolean isProductRegistered(String ID) throws Exception {
			ArrayList<Product> prodList = this.registeredProductsList();
			
			//Verificando se ja existe produto com esse ID cadastrado
			if(prodList.stream().anyMatch(prod -> prod.getID().equals(ID))) {
				return true;
			}
			return false;
		}
		
		public synchronized String writeProduct(String name, String ID, String p, String expDate, String provider, int quantity) throws Exception { //p = price
			
			if(quantity < 0)
				quantity = 0;
			
			if(this.isProductRegistered(ID)) {
				System.out.println("Produto ja registrado");
				return DUPLICATED_PRODUCT;
			}			
			else {
				try{
					float price = Float.parseFloat(p);
		
					//Verifica se price eh um numero
					if(!Float.isNaN(price)) {
						BufferedWriter buffWrite = new BufferedWriter(new FileWriter("src/server/marketserver/Products.csv", true));
						buffWrite.append(name + "," + ID + "," + price + "," + expDate + "," + provider + "," + quantity + "\n");
						buffWrite.close();
						//System.out.println("Produto registrado com sucesso");
						return SUCCESS;
					}
				}
				
				catch(NumberFormatException e) {
					System.out.println("Preco invalido");
					return INVALID_PRICE; //Valor de float/price errado
				}
				System.out.println("Erro ao registrar produto");
				return ERROR;
			}
		}
				
		//Dado um produto, escreve no arquivo de produtos registrados
		//Sobrecarga
		public synchronized String writeProduct(Product p) throws Exception {
			return this.writeProduct(p.getName(), p.getID(), Float.toString(p.getPrice()), p.getExpirationDate(), p.getProvider(), p.getQuantity());
		}
		
		public ArrayList<String> sendProductsFile() throws Exception{
			BufferedReader buffRead = new BufferedReader(new FileReader("src/server/marketserver/Products.csv"));
			String line = buffRead.readLine(); 
			ArrayList<String> strList = new ArrayList<String>();
			
			while(line != null){
				strList.add(line);
				line = buffRead.readLine();
			}
			
			buffRead.close();
			return strList;
		}
		
		//Adiciona quantidade de um certo produto
		public synchronized String addQuantity(String ID, int q) throws Exception {
			//Se o produto nao estiver registrado
			if(!this.isProductRegistered(ID)) {
				System.out.println("Produto nao consta");
				return INVALID_PRODUCT;
			}
			
			ArrayList<Product> prodList = this.registeredProductsList();
			
			if(prodList.stream().filter(p -> p.getID().equals(ID)).anyMatch(p -> p.getQuantity() >= -q)){
			
			List<Product> updtList = prodList.stream().peek(p -> {
												if(p.getID().equals(ID)) {
													p.setQuantity(p.getQuantity() + q);
												}
											}).collect(Collectors.toList());

			
			//Apagando arquivo
			FileWriter fileOut = new FileWriter("src/server/marketserver/Products.csv");
			fileOut.write("");
			fileOut.flush();
			fileOut.close();
			
			for(Product p : updtList) {
				this.writeProduct(p);
			}
			
			return SUCCESS;	
			}
			
			else{
				return INVALID_QUANTITY;
			}
		}
		
		public ArrayList<Product> outOfStock() throws Exception{
			ArrayList<Product> prodList = this.registeredProductsList();
			ArrayList<Product> outOfStockList = new ArrayList<Product>();
			
			for(Product p : prodList){
				if(p.getQuantity() == 0){
					outOfStockList.add(p);
				}
			}
			return outOfStockList;
			
		}
		
}
