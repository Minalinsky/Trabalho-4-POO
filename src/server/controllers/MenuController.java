package server.controllers;

import marketlib.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import server.marketserver.MarketServer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController implements Initializable
{
	MarketServer ms = MarketServer.newMarketServer(); 
	ArrayList<Product> list = new ArrayList<Product>();
	private ArrayList<Product> outOfStockList;
	
	  @FXML
	    private TableColumn<?, ?> columnProvider;

	    @FXML
	    private TableColumn<?, ?> columnValidity;

	    @FXML
	    private Button btNewProduct;

	    @FXML
	    private TableColumn<?, ?> columnID;

	    @FXML
	    private TableColumn<?, ?> columnPrice;

	    @FXML
	    private TableColumn<?, ?> columnQuantity;

	    @FXML
	    private Button btRefresh;

	    @FXML
	    private TableView<Product> tableProducts;

	    @FXML
	    private Pane paneMenuServer;

	    @FXML
	    private TableColumn<?, ?> columnName;
    
    @Override    
	public void initialize(URL url, ResourceBundle bundle)
	{    
    	this.Refresh();
	}

    @FXML
    void onClickNewProduct(ActionEvent event) throws Exception
    {
    	Stage primaryStage = new Stage();
    	Parent root = FXMLLoader.load(getClass().getResource("../views/RegisterProduct.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
    
    @FXML
    void onClickRefresh(ActionEvent event) 
    {
    	this.Refresh();
    	
    }
    
    void Refresh()
    {
    	try
		{
    		//Verifica se ha produtos fora de estoque
	        //Se houver, pergunta se quer rebastecer o estoque
	        outOfStockList = ms.outOfStock();
	        if(!outOfStockList.isEmpty()){
				Stage primaryStage = new Stage();
		    	Parent root = FXMLLoader.load(getClass().getResource("../views/Restock.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				primaryStage.toFront();
			}
	        
    		//Cria lista de produtos e imprime-os na tabela
			list = ms.registeredProductsList();
			
			columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
			columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
			columnValidity.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
			columnProvider.setCellValueFactory(new PropertyValueFactory<>("provider"));
			columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			
	        tableProducts.setItems(FXCollections.observableArrayList(list));
	        
		}
		catch(Exception e)
		{
			System.out.println("Error when showing products! ");
		}
    }
    
}