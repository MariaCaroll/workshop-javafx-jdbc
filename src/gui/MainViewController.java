package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController  implements Initializable {
	
	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemAbout;
	
	// inico:  trata os elementos do menu em  scene buider
	@FXML
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuItemVendedorAction");	
	}
	
	@FXML
	public void onMenuItemVDepartamentoAction() {
		System.out.println("onMenuItemVDepartamentoAction");	
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("onMenuItemAboutAction");	
	}
	
	//fim:  trata os os elementos do menu em  scene buider//
	

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}

}
