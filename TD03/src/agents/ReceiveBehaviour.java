package agents;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import javax.swing.DefaultListModel;

import model.ChatMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class ReceiveBehaviour extends CyclicBehaviour{
	private DefaultListModel<ChatMessage> listModel = new DefaultListModel<ChatMessage>();
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive(MessageTemplate
				.MatchPerformative(ACLMessage.INFORM));
		System.out.println("W8ing for message");
		if (message != null) {
			System.out.println("Message Received");
			try {
				ChatMessage chatMessage = mapper.readValue(message.getContent(), ChatMessage.class);
				listModel.addElement(chatMessage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			block();
		}
	}
	public DefaultListModel<ChatMessage> getListModel() {	
		return listModel;
	}
	

}
