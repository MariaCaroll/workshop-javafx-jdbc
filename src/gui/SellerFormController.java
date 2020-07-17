package gui;


import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {
	
	private Seller entity;
	
	private SellerService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<DataChangeListener>();

	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Label labelErrorBaseSalary;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	
	public void setDepartmetService(SellerService service) {
		this.service = service;
	}
	
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void subscribeDataChangeLiestener(DataChangeListener listener) {
		
		dataChangeListeners.add(listener);
	}
	
	@FXML
	private void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity esta vazia");
		}
		if(service == null) {
			throw new IllegalStateException("Service esta vazia");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifieDataChangeListeners();
			Utils.currentStage(event).close();
		} 
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error ao Salvar", null, e.getMessage(), AlertType.ERROR);
			
		}
		
	}
	
	private void notifieDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners ) {
			listener.ondataChanged();
		}
		
	}

	private Seller getFormData() {
		
		Seller obj = new Seller();
		
		ValidationException ex = new ValidationException("Erro de Valida��o");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			ex.addError("name", "O campo n�o pode ser vazio!!");
		} else {
			obj.setName(txtNome.getText());
		} if (ex.getErrors().size() > 0) {
			throw ex;
		}
		return obj;
	}

	@FXML
	private void onBtCancelAction(ActionEvent ev) {
		Utils.currentStage(ev).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 70);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entidade Vazia");
		} else {
			txtId.setText(String.valueOf(entity.getId()));
			txtNome.setText(entity.getName());
			txtEmail.setText(entity.getEmail());
			Locale.setDefault(Locale.US);
			txtBaseSalary.setText(String.format( "%.2f" ,entity.getBaseSalary()));
			if (entity.getBirthDate() != null) {
				dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
			}
			
		}
	
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")) {
			labelErrorNome.setText(errors.get("name"));
		}
	}

}
