package td5_bis;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import org.json.simple.JSONObject;

@SuppressWarnings("serial")
public class PropagateSparql extends Agent {

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
					JSONObject obj = new JSONObject();
					obj.put("request", 4);
					obj.put("param", msg.getContent());
					message.setContent(obj.toString());
					message.addReceiver(new AID("KBAgent", AID.ISLOCALNAME));
					myAgent.send(message);
				}
			}
		});
	}
}
