package agents;

import gui.ChatFrame;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class ChatAgent extends Agent {

	ChatFrame window;

	@Override
	protected void setup() {
		super.setup();
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Chat");
		sd.setName("Chat");
		dfd.addServices(sd);
		ReceiveBehaviour receiveBehaviour = new ReceiveBehaviour();
		this.addBehaviour(receiveBehaviour);
		window = new ChatFrame(receiveBehaviour);
	}
}
