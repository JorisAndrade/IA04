package fact;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.Random;

@SuppressWarnings("serial")
public class Fact extends Agent {
	public AID getReceiver() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Operations");
		sd.setName("Multiplication");
		template.addServices(sd);
		DFAgentDescription[] result;
		try {
			result = DFService.search(this, template);
			if (result.length > 0) {
				Random rand = new Random();
				return result[rand.nextInt(result.length)].getName();
			}
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new InitFactBehaviour());
	}
}
