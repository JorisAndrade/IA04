package utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CellsMessage {
	private List<Cellule> cells = new ArrayList<Cellule>();
	private int rank;

	public CellsMessage() {
		rank = -1;
	}
	@JsonCreator 
	public CellsMessage(@JsonProperty("cells") List<Cellule> c,@JsonProperty("rank") int r) {
		rank = r;
		if (c.size() != 9) {
			System.out.println("Nombre de cellules incorrects");
		} else {
			cells = c;
		}
	}

	public List<Cellule> getCells() {
		return cells;
	}

	public void setCells(List<Cellule> cells) {
		this.cells = cells;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int r) {
		rank = r;
	}
	
	@Override
	public String toString() {
		String result = "Rank: " + rank + "cellules : ";
		for(Cellule c : cells){
			result+= c.toString();
		}
		return result;
	}
}
