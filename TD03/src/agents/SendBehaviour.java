package agents;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;

import model.ChatMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class SendBehaviour extends OneShotBehaviour {
	private final String message;
	private final ObjectMapper mapper = new ObjectMapper();

	public SendBehaviour(String message) {
		this.message = message;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ChatMessage chatMessage = new ChatMessage(myAgent.getName(), message);
		StringWriter stringWriter = new StringWriter();
		try {
			mapper.writeValue(stringWriter, chatMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String messageString = stringWriter.toString();
		if (messageString != null) {
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.setContent(messageString);
			for (DFAgentDescription agentDesc : ((ChatAgent) myAgent)
					.getReceivers()) {
				message.addReceiver(agentDesc.getName());
			}
			myAgent.send(message);
			System.out.println("Message sent");
		}
	}

}
