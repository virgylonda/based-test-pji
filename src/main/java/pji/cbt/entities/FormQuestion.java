package pji.cbt.entities;

import java.io.Serializable;
import java.util.List;

public class FormQuestion implements Serializable{
	
	private String choice;
	
	private Category category;
	
	private Question question;
	
	private List<Answer> answers;

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}