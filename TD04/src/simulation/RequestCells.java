package simulation;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;

import utils.RequestCellsMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class RequestCells extends TickerBehaviour {

	private final ObjectMapper mapper = new ObjectMapper();

	public RequestCells(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		AID[] agents = ((SimulationAgent) myAgent).agents;
		for (int i = 0; i < agents.length; i++) {
			RequestCellsMessage requestMessage = new RequestCellsMessage(i);
			StringWriter stringWriter = new StringWriter();
			try {
				mapper.writeValue(stringWriter, requestMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String messageString = stringWriter.toString();
			if (messageString != null) {
				ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
				message.setContent(messageString);
				message.setConversationId("1");
				message.addReceiver(new AID("AgentEnvironnement",
						AID.ISLOCALNAME));
				myAgent.send(message);

			}
		}
	}
}
