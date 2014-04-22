package td5_bis;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class PropagateGeoAgent extends Agent {
	ObjectMapper mapper = new ObjectMapper();
	@Override
	protected void setup() {
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				MessageTemplate template = MessageTemplate
						.MatchPerformative(ACLMessage.REQUEST);
				ACLMessage msg = myAgent.receive(template);
				if (msg != null) {
					ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
					RequestMessage messageObject = new RequestMessage();
					messageObject.setRequest(4);
					messageObject.setParam(msg.getContent());
					StringWriter stringWriter = new StringWriter();
					try {
						mapper.writeValue(stringWriter, messageObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String messageString = stringWriter.toString();
					message.setContent(messageString);
					message.addReceiver(new AID("GeodataAgent", AID.ISLOCALNAME));
					myAgent.send(message);
				}
			}
		});
	}
}