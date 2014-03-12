package fact;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class InitFactBehaviour extends CyclicBehaviour{

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (message != null) {
			int fact = Integer.valueOf(message.getContent());
			System.out.println("INIT FACT " + fact);
			myAgent.addBehaviour(new HandleFactBehaviour(fact));
		} else {
			block();
		}
	}

}
