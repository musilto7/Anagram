package slovnik;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class AnagramickaSlova implements Comparable<AnagramickaSlova>, Serializable{

	private TreeMap<Character, Integer> pismenaPocty;
	private String[] slova;
	private String vnejsiReprezentace;
	
	public AnagramickaSlova(TreeMap<Character,Integer> vzor, String slovo){
		konstruktor(slovo, vzor);
		vnejsiReprezentace = toString2();
	}
	
	public AnagramickaSlova(TreeMap<Character,Integer> vzor, String[] slovo, String Key){
		slova = slovo;
		pismenaPocty = vzor;
		vnejsiReprezentace = Key;
	}

	
	public AnagramickaSlova(TreeMap<Character, Integer> vzor, String slovo, String key){
		konstruktor(slovo, vzor);
		vnejsiReprezentace = key;
	}
	
	private void konstruktor(String slovo, TreeMap<Character,Integer> vzor){
		slova = new String[1];
		slova[0] = slovo;		
		pismenaPocty = vzor;
	}
	
	
	protected void pridejSlovo(String slovo){//nejspis se tato metoda smaze, nebo zmeni
		if(!obsahujeSlovo(slova, slovo)){
			pridejMisto();
			slova[slova.length-1] = slovo;
		}
	}
	
	private boolean obsahujeSlovo(String[] slova, String slovo){
		for(int i =0; i < slova.length; i++){
			if (slova[i].equals(slovo))
				return true;
		}
		return false;
	}
	
	private void pridejMisto(){
		String[] temp = new String[slova.length + 1];
		for(int i = 0; i < slova.length; i++){
			temp[i] = slova[i];
		}
		slova = temp;
	}
	
	public int compareTo(AnagramickaSlova o) {
		if(pismenaPocty.equals(o.pismenaPocty)){
			return 0;
		}
			String aktualni = toString();
			String cizi = o.toString();
		for(int i = 0; i < aktualni.length(); i++){
			if(aktualni.charAt(i) > cizi.charAt(i))
				return 1;
			if(aktualni.charAt(i) < cizi.charAt(i))
				return -1;
		}
		return 0;
				
	}
	
	public boolean equals(Object o){
		if(!(o instanceof AnagramickaSlova)){
			return false;
		}
		AnagramickaSlova temp = (AnagramickaSlova) o;
		
		if(!pismenaPocty.equals(temp.pismenaPocty))
			return false;		
		return true;
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	protected String toString3(){
		StringBuilder stb = new StringBuilder("");
		for(int i = 0; i < slova.length; i++){
			stb.append(slova[i]);
			if(i != slova.length -1)
				stb.append(",");
		}
		return new String(stb);
	}
	
	private String toString2(){
		StringBuilder navrat = new StringBuilder("");
		for(Iterator<Entry<Character, Integer>> it = pismenaPocty.entrySet().iterator(); it.hasNext();){
			Entry<Character, Integer> polozka = it.next();
			for(int i = 0; i < polozka.getValue();i++){
				navrat.append(polozka.getKey());
			}			
		}
		return new String(navrat);
	}
	
	public String toString(){
		return vnejsiReprezentace;
	}
	
	public void vypis(){
		for(int i = 0; i < slova.length; i++){
			System.out.print(slova[i]+", ");
		}
	}
	protected String[] getSlova(){
		String[] temp = new String[slova.length];
		for(int i = 0; i < slova.length; i++){
			temp[i] = new String(slova[i]);
		}
		return temp;
	}
}

