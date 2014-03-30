package utils;

import java.util.ArrayList;

public class Cellule {

	protected int mValeur;
	protected ArrayList<Integer> mValeursPossibles = new ArrayList<Integer>();
	protected int l;
	protected int c;

	public Cellule(int v, int l, int c) {
		mValeur = v;
		this.l = l;
		this.c = c;
		if (v == 0) {
			for (int i = 1; i < 10; ++i) {
				mValeursPossibles.add(i);
			}
		}
	}

	public synchronized void removeValsPossibles(ArrayList<Integer> vals) {
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

	public void setVal(int val) {
		mValeur = val;
		mValeursPossibles.clear();
	}

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
		return "Cellule [mValeur=" + mValeur + ", l=" + l + ", c=" + c + "]";
	}
}