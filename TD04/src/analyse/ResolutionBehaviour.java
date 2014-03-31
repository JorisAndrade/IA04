package analyse;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import utils.CellsMessage;
import utils.Cellule;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class ResolutionBehaviour extends CyclicBehaviour {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate
				.MatchPerformative(ACLMessage.REQUEST));
		if (message != null) {
			CellsMessage requestMessage;
			try {
				requestMessage = mapper.readValue(message.getContent(),
						CellsMessage.class);
				Cellule[] analyseCells = requestMessage.getCells();
				// Regle 1
				for (int i = 0; i < 9; i++) {
					Cellule cellule = analyseCells[i];
					if (cellule.numberOfPossible() == 1) {
						cellule.setValWithLastValPossible();
					}
				}
				// Regle 2
				for (int i = 1; i <= 10; i++) {
					int count = 0;
					Cellule present = null;
					for (int j = 0; j < 9; j++) {
						if (analyseCells[j].valIsPossible(j)) {
							count++;
							present = analyseCells[j];
						}
					}
					if (count == 1) {
						present.setVal(i);
					}
				}
				// Regle 3
				// pour chaque cellule
				for (int i = 1; i < 9; i++) {
					// si on a uniquement 2 chiffres
					if (analyseCells[i].numberOfPossible() == 2) {
						// on cherche les 2 memes chiffres ailleurs
						for (int j = i + 1; j < 9; j++) {
							if (analyseCells[j].numberOfPossible() == 2
									&& analyseCells[j].getBinaryPossibles() == analyseCells[i]
											.getBinaryPossibles()) {
								// et on les retire des autres cellules
								for (int k = 0; k < 9; k++) {
									List<Integer> removeList = new ArrayList<Integer>();
									removeList.add(i);
									removeList.add(j);
									if (k != j && k != i) {
										analyseCells[k]
												.removeValsPossibles(removeList);
									}
								}
							}
						}
					}
				}
				StringWriter stringWriter = new StringWriter();
				try {
					mapper.writeValue(stringWriter, requestMessage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String messageString = stringWriter.toString();
				if (messageString != null) {
					ACLMessage response = new ACLMessage(ACLMessage.INFORM);
					response.setContent(messageString);
					response.setConversationId("3");
					response.addReceiver(new AID("AgentSimulation",
							AID.ISLOCALNAME));
					myAgent.send(response);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
