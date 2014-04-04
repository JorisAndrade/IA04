package environnement;

import jade.core.Agent;
import utils.Sudoku;

@SuppressWarnings("serial")
public class EnvironnementAgent extends Agent {

	public static Sudoku sudoku;

	public EnvironnementAgent() {
		super.setup();
		sudoku = new Sudoku("examples/grille3.txt");
		this.addBehaviour(new DoneBehaviour());
		this.addBehaviour(new GiveCellsBehaviour());
		this.addBehaviour(new UpdateCellsBehaviour());
	}
}
