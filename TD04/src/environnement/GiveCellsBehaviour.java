package environnement;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.StringWriter;

import utils.CellsMessage;
import utils.Cellule;
import utils.RequestCellsMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class GiveCellsBehaviour extends CyclicBehaviour{

	private ObjectMapper mapper = new ObjectMapper();

	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive(MessageTemplate
				.MatchPerformative(ACLMessage.REQUEST));

		System.out.println("W8ing for message");
		if (message != null) {
			System.out.println("Message Received");
			try {
				RequestCellsMessage requestMessage = mapper.readValue(message.getContent(), RequestCellsMessage.class);
				Cellule[] cellules = EnvironnementAgent.sudoku.getCellulesForRank(requestMessage.getRank());
				System.out.println("cellules récupérées" + cellules);
				CellsMessage cellsMessage = new CellsMessage(cellules);
				StringWriter stringWriter = new StringWriter();
				try {
					mapper.writeValue(stringWriter, cellsMessage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String messageString = stringWriter.toString();
				System.out.println("message envoyé: " + messageString);
				if (messageString != null) {
					ACLMessage response = new ACLMessage(ACLMessage.INFORM);
					response.setContent(messageString);
					response.addReceiver(new AID("AgentSimulation", AID.ISLOCALNAME));
					myAgent.send(response);
					System.out.println("Message sent");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			block();
		}
	}

}
