package environnement;

import jade.core.Agent;
import utils.Sudoku;

@SuppressWarnings("serial")
public class EnvironnementAgent extends Agent {

	public static Sudoku sudoku;

	public EnvironnementAgent() {
		sudoku = new Sudoku("examples/grille5.txt");
	}
}
