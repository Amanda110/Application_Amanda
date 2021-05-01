package application;

public class Evaluation {
	
	private String sujet;
	private String eval;
	private Double note;
	private String date;

	//constructeur vide
	public Evaluation()
	{
		this(null);
	}
	
	
	//constructeur avec 2 paramètres
		public Evaluation(String eval)
		{
			this.date = "";
			this.eval = eval;
			this.sujet = "";
			this.note = 0.0;
		}
	
	//Setters et getters
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

		public Double getNote() {
			return note;
		}

		public void setNote(Double note) {
			this.note = note;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

}