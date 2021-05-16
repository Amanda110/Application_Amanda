/*
* @Author : Amanda Tishler
* @Date : 7 mai 2021
* @Nom du programme : Gestionnaire d'évaluations
* @Description : Programme permettant de gérer une liste d'évaluations avec la note obtenu et calculer la moyenne.
* Les données peuvent être sauvegardées dans un fichier XML.		
*/

package application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class EvaluationsController implements Initializable {

    @FXML
    private TextField txtMoyenne;
    
    @FXML
    private TableView<Evaluation> evaluationTable;

    @FXML
    private TextField txtNote;

    @FXML
    private TableColumn<Evaluation, String> evalColumn;

    @FXML
    private ComboBox<String> cboSujet;

    @FXML
    private TextField txtEval;

    @FXML
    private Button btnEffacer;

    @FXML
    private TableColumn<Evaluation, Double> noteColumn;

    @FXML
    private ComboBox<String> cboSF;

    @FXML
    private Button btnRecommencer;

    @FXML
    private Button btnModifier;

    @FXML
    private TableColumn<Evaluation, String> SFColumn;

    @FXML
    private Button btnAjouter;

    @FXML
    private TableColumn<Evaluation, String> sujetColumn;

 // liste pour les sujets - cette liste permattra de donner les valeurs du comboBox
 	private ObservableList<String> list = (ObservableList<String>) FXCollections.observableArrayList("Informatique", "Physique", "Chimie", "Maths", "Art", "Englais", "Français", "Géographie");

 	private ObservableList<String> list2 = (ObservableList<String>) FXCollections.observableArrayList("Sommative", "Formative");
 	//placer les evaluations dans un observable list
 	public ObservableList<Evaluation> evaluationData = FXCollections.observableArrayList();

 	//Créer une méthode pour accéder à la liste des évaluations

 	public ObservableList<Evaluation> getevaluationData()
 	{
 		return evaluationData;
 	}
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		cboSujet.setItems(list);
		cboSF.setItems(list2);
		//attribuer les valeurs aux colonnes du tableView
		sujetColumn.setCellValueFactory(new PropertyValueFactory<>("sujet"));
		evalColumn.setCellValueFactory(new PropertyValueFactory<>("eval"));
		noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
		SFColumn.setCellValueFactory(new PropertyValueFactory<>("somForm"));
		evaluationTable.setItems(evaluationData);

		btnModifier.setDisable(true);
		btnEffacer.setDisable(true);
		btnRecommencer.setDisable(true);

		showEvaluations(null);
		//Mettre à jour l'affichage d'un évaluation sélectionné
		evaluationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> showEvaluations(newValue));	

	}	
	
	@FXML
	public void txtMoyenne()
	{
		txtMoyenneAffiche(evaluationData);
	}
		
	//Afficher la moyenne
	public void txtMoyenneAffiche(List<Evaluation> evaluations)
	{
		Double moy = 0.0;
		
		for(Evaluation e:evaluations)
		{
			double note = e.getNote();
			moy += note;
		}
		double s = evaluations.size();
		moy = moy / s;
		txtMoyenne.setText(String.format("%.2f",moy));
		if(txtMoyenne.getText().equals("NaN"))txtMoyenne.setText("N/A");
	}
	
	@FXML
	public void verifNum() //méthode pour trouver des input non numériques
	{
		txtNote.textProperty().addListener((observable,oldValue,newValue)->
		{
			if(!newValue.matches("^[0-9](\\.[0-9]+)?$"))
			{
				txtNote.setText(newValue.replaceAll("[^\\d*\\.]", ""));
			}
		});
	}
	
	//Ajouter un évaluation
		@FXML
		void ajouter()
		{
			if(noEmptyInput())
			{
				Evaluation tmp = new Evaluation();

				tmp = new Evaluation();
				tmp.setEval(txtEval.getText());
				tmp.setSomForm(cboSF.getValue());
				tmp.setNote(Double.parseDouble(txtNote.getText()));
				tmp.setSujet(cboSujet.getValue());
					evaluationData.add(tmp);
					clearFields();
				txtMoyenneAffiche(evaluationData);
			
			}
		}

		//effacer le contenu des champs
		@FXML
		void clearFields()
		{
			cboSujet.setValue(null);
			txtEval.setText("");
			txtNote.setText("");
			cboSF.setValue(null);
			
		}

		//afficher les évaluations
		public void showEvaluations(Evaluation evaluation)
		{
			if(evaluation !=null)
			{
				cboSujet.setValue(evaluation.getSujet());
				txtEval.setText(evaluation.getEval());
				cboSF.setValue(evaluation.getSomForm());
				txtNote.setText(Double.toString(evaluation.getNote()));
				btnModifier.setDisable(false);
				btnEffacer.setDisable(false);
				btnRecommencer.setDisable(false);
				
			}
			else
			{
				clearFields();
			}
		}
	    
	    //Mise à jour d'un évaluation
		@FXML
		public void updateEvaluation()
		{
			if(noEmptyInput())
			{
				Evaluation evaluation = evaluationTable.getSelectionModel().getSelectedItem();

				evaluation.setEval(txtEval.getText());
				evaluation.setSomForm(cboSF.getValue());
				evaluation.setNote(Double.parseDouble(txtNote.getText()));
				evaluation.setSujet(cboSujet.getValue());
				evaluationTable.refresh();
				txtMoyenneAffiche(evaluationData);
			
			}
		}
		
		//effacer un évaluation
		@FXML
		public void deleteEvaluation()
		{
			int selectedIndex = evaluationTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Effacer");
				alert.setContentText("Confirmer la supression !!");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK)
					evaluationTable.getItems().remove(selectedIndex);
			}
			txtMoyenneAffiche(evaluationData);
		}
		
		// vérifier champs vides
		private boolean noEmptyInput()
		{
			String errorMessage="";
			if(txtEval.getText().trim().equals(""))
			{
				errorMessage += "Le champ evaluation ne doit pas être vide! \n";
			}
			if(cboSF.getValue() == null)
			{
				errorMessage += "Le champ Somative/Formative ne doit pas être vide ! \n";
			}
			if(txtNote.getText().trim().equals(""))
			{
				errorMessage += "Le champ note ne doit pas être vide ! \n";
			}
			if(cboSujet.getValue() == null)
			{
				errorMessage += "Le champ sujet ne doit pas être vide ! \n";
			}

			if(errorMessage.length()==0)
			{
				return true;
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Champs manquants");
				alert.setHeaderText("Compléter les champs manquants");
				alert.setContentText(errorMessage);
				alert.showAndWait();
				return false;
			}
		}

  //Sauvegarde de données
		
		   
		//Recupérer le chemin (path) des fichiers si ca existe
		public File getEvaluationFilePath()
	    {
	    	Preferences prefs = Preferences.userNodeForPackage(Main.class);
	    	String filePath = prefs.get("filePath", null);
	    	
	    	if (filePath != null)
	    	{
	    		return new File(filePath);
	    	}
	    		else
	    	{
	    		return null;
	    	}
	    	   	
	    }
	    
	    //Attribuer un chemin de fichiers
		public void setEvaluationFilePath(File file)
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			if (file != null)
			{
				prefs.put("filePath", file.getPath());
			}
			else
			{
				prefs.remove("filePath");
			}
		}
	    
		//Prendre les données de type XML et les convertir en données de type javaFx
		public void loadEvaluationDataFromFile(File file)
		{
			try {
				JAXBContext context = JAXBContext.newInstance(EvaluationListWrapper.class);
				Unmarshaller um = context.createUnmarshaller();
				
				EvaluationListWrapper wrapper = (EvaluationListWrapper) um.unmarshal(file);
				evaluationData.clear();
				evaluationData.addAll(wrapper.getEvaluations());
				setEvaluationFilePath(file);
				// Donner le titre du fichier chargé
				Stage pStage = (Stage) evaluationTable.getScene().getWindow();
				pStage.setTitle(file.getName());
				
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("les données n'ont pas été trouvées");
				alert.setContentText("les données de pouvaient pas être trouveees dans le fichier : \n" + file.getPath());
				alert.showAndWait();
			}
		}
	    
	    //Prendre les données de type JavaFx et les convertir en type XML
		
		public void saveEvaluationDataToFile(File file)
		{
			try {
				JAXBContext context = JAXBContext.newInstance(EvaluationListWrapper.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				EvaluationListWrapper wrapper = new EvaluationListWrapper();
				wrapper.setEvaluations(evaluationData);
				
				m.marshal(wrapper,  file);
				
				//Sauvegarde dans le registre
				setEvaluationFilePath(file);
				// Donner le titre du fichier sauvegardé
				Stage pStage = (Stage) evaluationTable.getScene().getWindow();
				pStage.setTitle(file.getName());
				
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Données non sauvegardées");
				alert.setContentText("les données de pouvaient pas être sauvegardées dans le fichier : \n" + file.getPath());
				alert.showAndWait();
			}
			
		}
	    
		//Commencer un nouveau
		@FXML
		private void handleNew()
		{
			getevaluationData().clear();
			setEvaluationFilePath(null);
		}
		
		//Le FileChooser permet à l'usager de choisir le fichier à ouvrir.
		
		@FXML
		private void handleOpen() 
		{
			FileChooser fileChooser = new FileChooser();
			
			//Permettre un filtre sur l'extension du fichier à chercher
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
			fileChooser.getExtensionFilters().add(extFilter);
			
			//montrer le dialogue
			File file = fileChooser.showOpenDialog(null);
			
			if (file != null)
			{
				loadEvaluationDataFromFile(file);
			}
		}
	    
	     /* 
	      * 	Sauvegarder le fichier correspondant à l'évaluation actif
	      * 	S'il n'y a pas de fichier, le menu sauvegarder sous va s'afficher
	      */
		
		@FXML
		private void handleSave()
		{
			File evaluationFile = getEvaluationFilePath();
			if (evaluationFile != null)
			{
				saveEvaluationDataToFile(evaluationFile);
			}
			else
			{
				handleSaveAs();
			}
		}
		
		
		//Ouvrir le FileChooser pour chemin
		
		@FXML
		private void handleSaveAs()
		{
			FileChooser fileChooser = new FileChooser();
			
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
			fileChooser.getExtensionFilters().add(extFilter);
			
			//sauvegarde
			File file = fileChooser.showSaveDialog(null);
			
			if (file != null)
			{
				//Vérification de l'extension
				if (!file.getPath().endsWith(".xml"))
				{
					file = new File(file.getPath() + ".xml");
				}
				saveEvaluationDataToFile(file);
			}
			
		}
		
		
}
