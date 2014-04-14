package environnement;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.StaleProxyException;

@SuppressWarnings("serial")
public class DoneBehaviour extends CyclicBehaviour{

	@Override
	public void action() {
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("4"));
		ACLMessage message = myAgent.receive(template);
		
		if(message!=null){
			if(EnvironnementAgent.sudoku.isDone()){
				EnvironnementAgent.sudoku.afficherTab();
				try {
					myAgent.getContainerController().kill();
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
