package models;

public class CalcResult {
	public static final String OK = "ok";
	public static final String ERROR = "error";
	private int result;
	private String comment;
	public CalcResult(){
		result = 0;
		comment = null;
	}
	public CalcResult(int result, String comment){
		this.result = result;
		this.comment = comment;
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
