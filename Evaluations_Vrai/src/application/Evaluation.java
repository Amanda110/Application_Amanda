package application;

public class Evaluation
{
	private String sujet;
	private String eval;
	private String somForm;
	private Double note;
	
	
	//constructeur vide
	public Evaluation()
	{
		this(null);
	}
	
	//constructeur avec 2 paramètres
	public Evaluation(String eval)
	{
		this.eval = eval;
		this.somForm = "";
		this.sujet = "";
		this.note = 0.0;
	}

	//Getters et setters
	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getEval() {
		return eval;
	}

	public void setEval(String eval) {
		this.eval = eval;
	}

	public String getSomForm() {
		return somForm;
	}

	public void setSomForm(String somForm) {
		this.somForm = somForm;
	}

	public Double getNote() {
		return note;
	}

	public void setNote(Double note) {
		this.note = note;
	}
	
	

}
