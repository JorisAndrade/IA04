package utils;

public class CellsMessage {
	private Cellule[] cells;
	private int rank;

	public CellsMessage() {
		cells = new Cellule[9];
		rank = -1;
	}

	public CellsMessage(Cellule[] c, int r) {
		rank = r;
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

	public int getRank() {
		return rank;
	}

	public void setRank(int r) {
		rank = r;
	}
}
