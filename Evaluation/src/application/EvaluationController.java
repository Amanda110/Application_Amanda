package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EvaluationController implements Initializable{

    @FXML
    private TextField txtNote;

    @FXML
    private Button btnClear;

    @FXML
    private ComboBox<String> cboSujet;

    @FXML
    private TextField txtEval;
    
    @FXML
    private TextField txtDate;

    @FXML
    private Button btnEffacer;

    @FXML
    private TableColumn<Evaluation, Double> noteColumn;

    @FXML
    private TableColumn<Evaluation, String> evaluationColumn;

    @FXML
    private Button btnModifier;

    @FXML
    private TableColumn<Evaluation, String> dateColumn;

    @FXML
    private TableView<Evaluation> evaluationsTable;

    @FXML
    private Button btnAjouter;

    @FXML
    private TableColumn<Evaluation, String> sujetColumn;

 // liste pour les sujets - cette liste permattra de donner les valeurs du comboBox
 	private ObservableList<String> list = (ObservableList<String>) FXCollections.observableArrayList("Informatique", "Physique", "Chimie", "Maths", "Englais", "Art", "Français", "Géographie");

 	//placer les evaluations dans un observable list
 	public ObservableList<Evaluation> evaluationData = FXCollections.observableArrayList();

 	//Créer une méthode pour accéder à la liste des évaluations

 	public ObservableList<Evaluation> getevaluationData()
 	{
 		return evaluationData;
 	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cboSujet.setItems(list);
		//attribuer les valeurs aux colonnes du tableView
		evaluationColumn.setCellValueFactory(new PropertyValueFactory<>("evaluation"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		sujetColumn.setCellValueFactory(new PropertyValueFactory<>("sujet"));
		noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
		evaluationsTable.setItems(evaluationData);
		
		showEvaluations(null);
		
	}
	
	//Ajouter un étudiant
	@FXML
	void ajouter()
	{
		Evaluation tmp = new Evaluation();

		tmp = new Evaluation();
		tmp.setEval(txtEval.getText());
		tmp.setDate(txtDate.getText());
		tmp.setNote(Double.parseDouble(txtNote.getText()));
		tmp.setSujet(cboSujet.getValue());
			evaluationData.add(tmp);
			clearFields();
				
	}
	
	//effacer le contenu des champs
		@FXML
		void clearFields()
		{
			cboSujet.setValue(null);
			txtEval.setText("");
			txtNote.setText("");
			txtDate.setText("");
		}
		
	//afficher les étudiants
		public void showEvaluations(Evaluation evaluation)
		{
			if(evaluation !=null)
			{
				cboSujet.setValue(evaluation.getSujet());
				txtEval.setText(evaluation.getEval());
				txtDate.setText(evaluation.getDate());
				txtNote.setText(Double.toString(evaluation.getNote()));
				
			}
			else
			{
				clearFields();
			}
		}
}