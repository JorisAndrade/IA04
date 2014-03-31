package simulation;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class UpdateBehaviour extends CyclicBehaviour {

	@Override
	public void action() {
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("3"));
		ACLMessage response = myAgent.receive(template);
		if (response != null) {
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.setConversationId("2");
			message.setContent(response.getContent());
			message.addReceiver(new AID("AgentEnvironnement", AID.ISLOCALNAME));
			myAgent.send(message);
		} else {
			block();
		}
	}
}
