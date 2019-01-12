package mistoCinu;

public class Vysledek implements Comparable<Vysledek>{

	private String hodnota;
	
	public Vysledek(String hod){
		hodnota = hod;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Vysledek) || obj == null)
			return false;
		Vysledek temp = (Vysledek) obj;
		if(temp.hodnota.contains(" ") && hodnota.contains(" ")){
			if(temp.hodnota.equals(hodnota))
				return true;
			int index1, index2;
			index1 = temp.hodnota.indexOf(" ");
			index2 = hodnota.indexOf(" ");
			if(temp.hodnota.substring(0, index1).equals(hodnota.substring(index2 + 1, hodnota.length())) && temp.hodnota.substring(index1 + 1, temp.hodnota.length()).equals(hodnota.substring(0, index2)))
				return true;
			return false;
		}
		else
			return temp.hodnota.equals(hodnota);
		
	}
	
	public int hashCode(){
		int navrat = 0;
		for(int i = 0; i < hodnota.length(); i++){
			navrat += hodnota.charAt(i);
		}
		return navrat;
	}

	
	public int compareTo(Vysledek o) {
		return hashCode() - o.hashCode();
		
	}
	public String getString(){
		return hodnota;
	}
	public String toString(){
		return hodnota;
	}
	
}
