package mistoCinu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import slovnik.Slovnik;
import vstupDat.VstupDat;

import android.content.res.AssetManager;


import kombinace.Kombinace;

public class MistoCinu {
	private Slovnik slovnik;	
	private String[] vstupujiciData;
	private String vstupniSlova;
	private int pocetSlov, pocetPismen = 0;
	private ArrayList<Integer[]> deleni;	
	private HashMap<Vysledek, Boolean> vystup;
	
	
	public MistoCinu(AssetManager asset) throws IOException, ClassNotFoundException{	
			slovnik = new Slovnik(asset);			
	}
	
	public String[] getAnagrams(String vstupni_slovo){
		
			VstupDat  vstupDat = new VstupDat(vstupni_slovo);
			vstupujiciData = vstupDat.getData();			
			pocetSlov = vstupujiciData.length;
			if(pocetSlov == 0)
				return new String[0];
			
				
			pocetPismen = getPocetPismen(vstupujiciData);
			if(pocetPismen > 20){
				
				return new String[]{"Zadaný vstup je příliš dlouhý"};
			}
			
			vstupniSlova = getVstupniSlova(vstupujiciData);
			deleni = new Deleni(pocetSlov, pocetPismen).getDeleni();
			
			pracuj();
			return tisk();
			
	}
	
	private void pracuj(){
			vystup = new HashMap<Vysledek, Boolean>();
		String[][] temp;
		if((temp = slovnik.vratAnagramy(vstupniSlova)) != null){
			pridejDoHashMap(temp);
		}
		
		for(int i = 0; i < deleni.size(); i++){
			 Integer[] pole = deleni.get(i);
			
			 Kombinace komb = new Kombinace(pocetPismen, pole[0]);
				while (true){
					try{
						String prv = prevodNaString(komb.getKombinace());
						String dru = prevodNaString(komb.getZbytekPoKombinaci());
												
						temp = slovnik.vratAnagramy(prv,dru);
						
						if(temp != null){
							pridejDoHashMap(temp);
						}
						
					}
					catch(IndexOutOfBoundsException ex){
						break;
					}
				}			
		}		
		
	}
	
	private String[] tisk(){
		LinkedList<String> list = new LinkedList<String>();
		for(Iterator<Map.Entry<Vysledek, Boolean>> it = vystup.entrySet().iterator();it.hasNext();){
			list.add(it.next().getKey().toString());
		}
		String[] navrat = new String[list.size()];
		for(int i = 0; i < navrat.length; i++){
			navrat[i] = list.get(i);
		}
		return navrat;
	}
	
	private void pridejDoHashMap(String[][] temp){
		
		if(temp.length == 1){
			for(int i = 0; i < temp[0].length; i++){
				vystup.put(new Vysledek(temp[0][i]), true);				
			}
			return;
		}
		for(int i = 0; i < temp[0].length; i++){
			for(int k = 0; k < temp[1].length; k++){
				vystup.put(new Vysledek(temp[0][i] + " " + temp[1][k]), true);
			}
		}
	}
	
	private String prevodNaString(int[] indexovePole){
		StringBuilder stbVystup = new StringBuilder("");
		for(int i = 0; i < indexovePole.length; i++){
			stbVystup.append(vstupniSlova.charAt(indexovePole[i]));
		}
		return stbVystup.toString();
	}
	
	private int getPocetPismen(String[] pole){
		int citac = 0;
		for(int i = 0; i < pole.length; i++){
			citac += pole[i].length();
		}
		return citac;
	}
	private String getVstupniSlova(String[] vstupujiciData){
		StringBuilder temp = new StringBuilder("");
		for(int i = 0; i < vstupujiciData.length; i++){
			temp.append(vstupujiciData[i]);
		}
		return temp.toString();
	}
}
