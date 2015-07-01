package client.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.marketclient.MarketClient;
import marketlib.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class MenuController implements Initializable
{
	MarketClient mc = MarketClient.newMarketClient(); 
	ArrayList<Product> list = new ArrayList<Product>();

    @FXML
    private Label labelMsg;
	
	@FXML
	private TableColumn<?, ?> columnProvider;

    @FXML
    private TextField textId;

    @FXML
    private Pane paneMenuClient;

    @FXML
    private TableColumn<?, ?> columnValidity;

    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TextField textQtd;

    @FXML
    private TableColumn<?, ?> columnPrice;

    @FXML
    private TableColumn<?, ?> columnQuantity;

    @FXML
    private Button btRefresh;

    @FXML
    private Button btBuy;

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<?, ?> columnName;

	@Override    
	public void initialize(URL url, ResourceBundle bundle)
	{    
		this.refresh();
	}    
	    
    @FXML
    void onClickRefresh(ActionEvent event) 
    {
    	this.refresh();
    }

    @FXML
    void onClickBuy(ActionEvent event) 
    {
    	String buyFlag = mc.buy(textId.getText(), textQtd.getText());
    	
    	if(buyFlag.equals("success"))
    	{
    		labelMsg.setText("Compra efetuada com sucesso!");
    		labelMsg.setVisible(true);
    	}
    	else{
    		labelMsg.setText("Erro na compra");
    		labelMsg.setVisible(true);
    	}
    	this.refresh();
    }

    void refresh()
    {
    	try
    	{
    		mc.refreshProductsFile();
    		list = mc.registeredProductsList();
    	
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