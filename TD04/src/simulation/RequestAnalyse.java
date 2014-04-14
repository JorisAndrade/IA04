package simulation;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import utils.CellsMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class RequestAnalyse extends CyclicBehaviour {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void action() {
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("1"));
		ACLMessage response = myAgent.receive(template);
		if (response != null) {
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);

			CellsMessage requestMessage;
			try {
				requestMessage = mapper.readValue(response.getContent(),
						CellsMessage.class);
				int rank = requestMessage.getRank();
				message.setContent(response.getContent());
				message.addReceiver(((SimulationAgent) myAgent).agents[rank]);
				myAgent.send(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			block();
		}
	}
}
