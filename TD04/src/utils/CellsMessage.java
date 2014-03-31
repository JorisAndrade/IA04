package utils;

public class CellsMessage {
	private Cellule[] cells;

	public CellsMessage() {
		cells = new Cellule[9];
	}

	public CellsMessage(Cellule[] c) {
		if (c.length != 9) {
			System.out.println("Nombre de cellules incorrects");
		} else {
			cells = c;
		}
	}

	public Cellule[] getCells() {
		return cells;
	}

	public void setCells(Cellule[] cells) {
		this.cells = cells;
	}
}
