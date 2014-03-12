package models;

import java.util.List;

public class CalcQuery {
	public static final String MULTIPLICATION = "multiplication";
	private String action;
	private List<Integer> numbers;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<Integer> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
	
}
