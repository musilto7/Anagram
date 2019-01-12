package mistoCinu;

import java.util.ArrayList;

public class Deleni {

	private ArrayList<Integer[]> deleni; 
	
	public Deleni(int pocetSlov, int pocetPismen){
		if(pocetSlov < 1 || pocetPismen < 1)
			throw new IllegalArgumentException();
		
		deleni = new ArrayList<Integer[]>();		
				
			int prv, dru;
			Integer[] pole = new Integer[2];
			pole[0] = prv = pocetPismen /2 + pocetPismen%2;
			pole[1] = dru = pocetPismen/2;
			deleni.add(pole);
			
			for(int i = pole[1]; i > 3; i--){
				pole = new Integer[2];
				pole[0] = ++prv;
				pole[1] = --dru;
				deleni.add(pole);
			}
				
	}
	
	public static String toString2(ArrayList<Integer[]> deleni){
		StringBuilder stb = new StringBuilder("");
		for(int i = 0; i < deleni.size(); i++){
			Integer[] temp = deleni.get(i);
			stb.append("[");
			for(int k = 0; k < temp.length; k++)
				stb.append(temp[k]+", ");
			stb.append("] ");
		}
		return stb.toString();
	}
	
	public ArrayList<Integer[]> getDeleni(){		
		return deleni;
	}
}
