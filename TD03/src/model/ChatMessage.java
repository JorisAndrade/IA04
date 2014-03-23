package model;

public class ChatMessage {
	private String auteur;
	private String message;
	
	public ChatMessage(){
		auteur = null;
		message = null;
	}
	
	public ChatMessage(String auteur, String message){
		this.auteur = auteur;
		this.message = message;
	}
	
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
