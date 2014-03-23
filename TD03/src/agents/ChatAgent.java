package agents;

import gui.ChatFrame;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class ChatAgent extends Agent {

	ChatFrame window;
	ReceiveBehaviour receiveBehaviour;
	@Override
	protected void setup() {
		super.setup();
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Chat");
		sd.setName("Chat");
		dfd.addServices(sd);
		receiveBehaviour = new ReceiveBehaviour();
		this.addBehaviour(receiveBehaviour);
		window = new ChatFrame(this);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	public DFAgentDescription[] getReceivers() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Chat");
		sd.setName("Chat");
		template.addServices(sd);
		try {
			return DFService.search(this, template);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public void sendMessage(String message){
		this.addBehaviour(new SendBehaviour(message));
	}

	public ReceiveBehaviour getReceiveBehaviour() {
		return receiveBehaviour;
	}
	
	
	
}
