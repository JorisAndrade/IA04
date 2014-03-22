package gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.ChatMessage;

@SuppressWarnings("serial")
public class ListCell extends JLabel implements ListCellRenderer<ChatMessage>{
	
	
	
	@Override
	// list, objet, index, isSelected, hasFocus
	public Component getListCellRendererComponent(
			JList<? extends ChatMessage> arg0, ChatMessage arg1, int arg2,
			boolean arg3, boolean arg4) {
		
		this.setText(arg1.getMessage());
		return this;
	}
}