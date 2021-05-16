package application;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "evaluation")
public class EvaluationListWrapper 
{
	private List <Evaluation> evaluations;
	@XmlElement(name = "evaluation")
	public List <Evaluation> getEvaluations()
	{
		return evaluations;
	}
	public void setEvaluations(List<Evaluation> evaluations)
	{
		this.evaluations = evaluations;
	}
}

