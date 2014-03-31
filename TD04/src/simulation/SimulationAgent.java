package simulation;

import jade.core.AID;
import jade.core.Agent;

@SuppressWarnings("serial")
public class SimulationAgent extends Agent {
	public AID[] agents = new AID[27];

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new InitializationBehaviour());
		addBehaviour(new RequestCells(this, 10000));
		addBehaviour(new RequestAnalyse());
		addBehaviour(new UpdateBehaviour());
	}
}
