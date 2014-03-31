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
public class GiveCellsBehaviour extends CyclicBehaviour {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void action() {
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("1"));
		ACLMessage message = myAgent.receive(template);

		if (message != null) {
			try {
				RequestCellsMessage requestMessage = mapper.readValue(
						message.getContent(), RequestCellsMessage.class);
				Cellule[] cellules = EnvironnementAgent.sudoku
						.getCellulesForRank(requestMessage.getRank());
				System.out.println("cellules recuperees" + cellules);
				CellsMessage cellsMessage = new CellsMessage(cellules,
						requestMessage.getRank());
				StringWriter stringWriter = new StringWriter();
				try {
					mapper.writeValue(stringWriter, cellsMessage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String messageString = stringWriter.toString();
				System.out.println("message envoye: " + messageString);
				if (messageString != null) {
					ACLMessage response = new ACLMessage(ACLMessage.INFORM);
					response.setContent(messageString);
					response.setConversationId("1");
					response.addReceiver(new AID("AgentSimulation",
							AID.ISLOCALNAME));
					myAgent.send(response);
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
