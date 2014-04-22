package analyse;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.CellsMessage;
import utils.Cellule;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class ResolutionBehaviour extends CyclicBehaviour {

	private ObjectMapper mapper;
	private List<Cellule> cells;
	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate
				.MatchPerformative(ACLMessage.REQUEST));
		if (message != null) {
			mapper = new ObjectMapper();
			CellsMessage requestMessage;
			try {
				requestMessage = mapper.readValue(message.getContent(),
						CellsMessage.class);
				cells = requestMessage.getCells();
				cleanRank();
				//Regle 1
				for (Cellule c : cells){
					if (c.getVal() == 0 && c.numberOfPossible() == 1){
						try {
							c.setValWithLastValPossible();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				//Regle 2
				for (int i=1; i<10; ++i){
					Cellule cc = null;
					for (Cellule c : cells){
						if (c.getVal() == i){
							cc = null;
							break;
						}
						else if (c.getVal() == 0 && c.valIsPossible(i)) {
							if (cc == null){
								cc = c;
							}
							else {
								cc = null;
								break;
							}
						}
					}
					if (cc != null){
						cc.setVal(i);
					}
				}
				
				//Regle 3
				for (Cellule cell : cells) {
					// on récupère les cellules à 2 elements
					if (cell.getVal() == 0 && cell.numberOfPossible() == 2){
						Cellule cc = null;
						int b = cell.getBinaryPossibles();
						// on en cherche une avec les même caleurs
						for (Cellule c : cells){
							if (cell != c && c.getBinaryPossibles() == b) {
									cc = c;
							}
						}
						//on retire ces valeurs aux autres cellules 
						if (cc != null){
							ArrayList<Integer> valsToRemove = new ArrayList<Integer>(cell.getmValeursPossibles());
							for (Cellule c : cells){
								if (c != cell && c != cc){
									c.removeValsPossibles(valsToRemove);
								}
							}
						}
					}
				}
				cleanRank();
				
				
				StringWriter stringWriter = new StringWriter();
				try {
					mapper.writeValue(stringWriter, requestMessage);
				} catch (Exception e) {
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
	
	private void cleanRank(){
		ArrayList<Integer> vals = new ArrayList<Integer>();
		for (Cellule c : cells){
			if (c.getVal() != 0){
				vals.add(c.getVal());
			}
		}
		for (Cellule c : cells){
			c.removeValsPossibles(vals);
		}
	}
	
}
