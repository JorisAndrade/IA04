package environnement;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;

import utils.CellsMessage;
import utils.Cellule;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class UpdateCellsBehaviour extends CyclicBehaviour {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void action() {
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("2"));
		ACLMessage message = myAgent.receive(template);
		if (message != null) {
			try {
				CellsMessage requestMessage = mapper.readValue(
						message.getContent(), CellsMessage.class);
				List<Cellule> updateCells = requestMessage.getCells();
				for (Cellule c : updateCells) {
					EnvironnementAgent.sudoku.setCellule(c);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			EnvironnementAgent.sudoku.afficherTabMore();
			System.out.println("\n\n");
		}
	}

}
