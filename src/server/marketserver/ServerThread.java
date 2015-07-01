package server.marketserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import marketlib.Costumer;

public class ServerThread implements Runnable
{

	private static final String OP_REGISTER_COSTUMER = "register costumer";
	private static final String OP_LOGIN = "login";
	private static final String OP_REFRESH_CLIENT = "refresh client";
	private static final String EOF = "eof";
	
	
	private Socket servSock = null;
	private ObjectOutputStream objOut = null;
	private ObjectInputStream objIn = null;
	private BufferedReader lineIn = null; //Criado pois o readLine() do ObjectInputStream está como "deprecated"
	private PrintStream lineOut = null;
	private MarketServer marketServer;
	
	public ServerThread(Socket servSock, MarketServer marketServer) throws IOException {
		this.servSock = servSock;
		this.marketServer = marketServer;
		objOut = new ObjectOutputStream(servSock.getOutputStream());
		objIn = new ObjectInputStream(servSock.getInputStream());
		lineIn = new BufferedReader(new InputStreamReader(servSock.getInputStream()));
		lineOut = new PrintStream(servSock.getOutputStream()); 
	}
	
	public void run() {
		String op = ""; //operacao a ser realizada
		while(!op.equals("exit")) {
			try{
				op = lineIn.readLine(); //lendo operacao a ser realizada
				switch(op) {
					case OP_REGISTER_COSTUMER:
						Costumer c = (Costumer) objIn.readObject(); //le Costumer vindo do socket (client)
						System.out.println(c.getID());
						lineOut.println(marketServer.registerCostumer(c)); //registra Costumer e envia resposta para client
						break;
						
					case OP_LOGIN:
						String ID = lineIn.readLine(); //le ID vindo de client
						String password = lineIn.readLine(); //le password vindo de client
						lineOut.println(marketServer.login(ID, password)); //envia resposta para clint (sucesso/erro)
						break;
						
					case OP_REFRESH_CLIENT:
						ArrayList<String> str = marketServer.sendProductsFile();
						for(String s : str){
							lineOut.println(s);
						}
						lineOut.println(EOF);
						break;
						
					default: 
						break;
				}
			}
			
			catch(Exception e) {
				System.out.println("Error in ServerThread!");
				op = "exit"; //encerra thread
			}
		}
	}
}
