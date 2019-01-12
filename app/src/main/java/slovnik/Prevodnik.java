package slovnik;

import java.util.HashMap;

public class Prevodnik {
	private static HashMap<Character, Character> prevodnik; 
	
	
	public Prevodnik(){
		prevodnik = new HashMap<Character, Character>();
		
		for(char i = 'a'; i <= 'z'; i++){
			prevodnik.put(i, i);
		}
		
		prevodnik.put('ě','e');
		prevodnik.put('š','s');
		prevodnik.put('č','c');
		prevodnik.put('ř','r');
		prevodnik.put('ž','z');
		prevodnik.put('ý','y');
		prevodnik.put('á','a');
		prevodnik.put('í','i');
		prevodnik.put('é','e');
		prevodnik.put('ú','u');
		prevodnik.put('ü', 'u');
		prevodnik.put('ó','o');
		prevodnik.put('ů','u');		
		prevodnik.put('ť','t');
		prevodnik.put('ď','d');
		prevodnik.put('ň','n');
		prevodnik.put('ǎ', 'a');
	}
	
	public static char getZnakBezDiakritiky(char znak){
		return prevodnik.get(znak);
	}
}
