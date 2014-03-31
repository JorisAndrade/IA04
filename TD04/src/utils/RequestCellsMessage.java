package utils;

public class RequestCellsMessage {
	private int rank;

	public RequestCellsMessage() {
		setRank(-1);
	}

	public RequestCellsMessage(int r) {
		rank = r;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
