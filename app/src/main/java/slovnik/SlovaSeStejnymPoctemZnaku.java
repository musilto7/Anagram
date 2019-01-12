package slovnik;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import treeMap.ZiskejTreeMap;

public class SlovaSeStejnymPoctemZnaku implements Serializable{
	private int pocetZnaku;
	private HashMap<String,AnagramickaSlova> anagramickaSlova;
	
	public SlovaSeStejnymPoctemZnaku(int pocetZnaku){
		this.pocetZnaku = pocetZnaku;
		anagramickaSlova = new HashMap<String, AnagramickaSlova>();
	}
	public void pridejSlovo(String key, String[] slova){
		
		TreeMap<Character, Integer> vkladanaMapa = ZiskejTreeMap.getTreemap(key);
		AnagramickaSlova vkladaneSlovo = new AnagramickaSlova(vkladanaMapa, slova, key);
		anagramickaSlova.put(key, vkladaneSlovo);
	}
	
	
	
	private String getSlovoBezDiakritiky(String slovo){
		StringBuilder slovoBezDiakritiky = new StringBuilder("");
		for(int i = 0; i < slovo.length(); i++){
			slovoBezDiakritiky.append(Prevodnik.getZnakBezDiakritiky(slovo.charAt(i)));
		}
		return new String(slovoBezDiakritiky);
	}
	
	public void vypis(){
		 Iterator<Entry<String, AnagramickaSlova>> it = anagramickaSlova.entrySet().iterator();
		 while(it.hasNext()){
		 Entry<String, AnagramickaSlova> temp = it.next();
		 
		 System.out.print("[" + temp.getValue()+": ");
		 temp.getValue().vypis();
		 
			 
		 System.out.println("], ");
		 }
	}
	protected void zapisRadek(BufferedWriter bufw) throws IOException{
		Iterator<Entry<String, AnagramickaSlova>> it = anagramickaSlova.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, AnagramickaSlova> temp = it.next();
			bufw.write(temp.getValue() +":" + temp.getValue().toString3() + " ");
		}
	}
	
	protected void nacti(String key, ArrayList<StringBuilder> slova){
		TreeMap<Character, Integer> vkladanaMapa = ZiskejTreeMap.getTreemap(key);
		
		for(int i = 0; i < slova.size(); i++){
		AnagramickaSlova vkladaneSlovo = new AnagramickaSlova(vkladanaMapa, slova.get(i).toString(),key);
		if(!anagramickaSlova.containsValue(vkladaneSlovo)){
			//System.out.println("zalozeni");
			anagramickaSlova.put(vkladaneSlovo.toString(),vkladaneSlovo);
		}
		else{
			
		  AnagramickaSlova temp = anagramickaSlova.get(vkladaneSlovo.toString());
		  temp.pridejSlovo(slova.get(i).toString());
		  //System.out.println(temp);
		}
		}
	}
	
	protected String[] vratAnagramy(String pseudoKey){
		String slovoBezDiakritiky = getSlovoBezDiakritiky(pseudoKey);
		TreeMap<Character, Integer> vkladanaMapa = ZiskejTreeMap.getTreemap(slovoBezDiakritiky);
		AnagramickaSlova vkladaneSlovo = new AnagramickaSlova(vkladanaMapa, "");
				
		AnagramickaSlova temp = anagramickaSlova.get(vkladaneSlovo.toString());
		if(temp == null){
			return new String[0];
		}
		return temp.getSlova();
	}
}
