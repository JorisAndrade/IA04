package simulation;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class InitializationBehaviour extends Behaviour {

	protected int compteur = 0;

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate
				.MatchPerformative(ACLMessage.SUBSCRIBE));
		if (message != null) {
			((SimulationAgent) myAgent).agents[compteur] = message.getSender();
			compteur++;
		}
	}

	@Override
	public boolean done() {
		return compteur >= 27;
	}

}
