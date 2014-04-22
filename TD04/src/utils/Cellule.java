package utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "binaryPossibles" })
public class Cellule {

	private int mValeur;
	private List<Integer> mValeursPossibles;
	private int l;
	private int c;

	public int getmValeur() {
		return mValeur;
	}

	public void setmValeur(int mValeur) {
		this.mValeur = mValeur;
	}

	public List<Integer> getmValeursPossibles() {
		return mValeursPossibles;
	}

	public void setmValeursPossibles(List<Integer> mValeursPossibles) {
		for(int i = 0; i<mValeursPossibles.size(); i++){
			this.mValeursPossibles.add(mValeursPossibles.get(i).intValue());
		}
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public Cellule() {
		mValeursPossibles = new ArrayList<Integer>();
	}
	@JsonCreator
	public Cellule(@JsonProperty("mValeur") int v,@JsonProperty("l") int l ,@JsonProperty("c") int c,@JsonProperty("mValeursPossibles") int possibles[]){
		this.mValeursPossibles = new ArrayList<Integer>();
		this.mValeur = v;
		this.c = c;
		this.l = l;
		for(int i = 0; i< possibles.length; i++){
			this.mValeursPossibles.add(possibles[i]);
		}
	}
	public Cellule(int v, int l, int c) {
		mValeursPossibles = new ArrayList<Integer>();
		mValeur = v;
		this.l = l;
		this.c = c;
		if (v == 0) {
			for (int i = 1; i < 10; ++i) {
				mValeursPossibles.add(i);
			}
		}
	}

	public synchronized void removeValsPossibles(List<Integer> vals) {
		mValeursPossibles.removeAll(vals);

	}

	public synchronized boolean valIsPossible(int val) {
		return mValeursPossibles.contains(val);
	}

	public synchronized int getBinaryPossibles() {
		int a = 0;
		for (int i : mValeursPossibles) {
			a += 1 << i;
		}
		return a;
	}

	public int numberOfPossible() {
		return mValeursPossibles.size();
	}
	@JsonIgnore
	public void setVal(int val) {
		mValeur = val;
		mValeursPossibles.clear();
	}
	@JsonIgnore
	public int getVal() {
		return mValeur;
	}
	
	public void setValWithLastValPossible() throws Exception {
		if (mValeursPossibles.size() != 1) {
			throw new Exception("mValeursPossibles.size() == "
					+ mValeursPossibles.size());
		}
		setVal(mValeursPossibles.get(0));
	}

	@Override
	public String toString() {
		String result =  " V" + mValeur + " p=";
		for(Integer i : mValeursPossibles){
			result+=i;
		}
		return result;
	}
	
	public void merge(Cellule cellule){
		if(this.mValeur == 0){
			if(cellule.numberOfPossible()==0){
				this.setVal(cellule.getVal());
			} else {
				List<Integer> notPossible = new ArrayList<Integer>();
				for(int i = 1; i<=9; i++){
					notPossible.add(i);
				}
				notPossible.removeAll(cellule.mValeursPossibles);
				this.removeValsPossibles(notPossible);
			}
		}
	}
}