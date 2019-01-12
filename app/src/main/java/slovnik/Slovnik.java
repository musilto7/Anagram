package slovnik;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.example.anagram.MainActivity;

import android.content.res.AssetManager;
import android.util.Log;



import cteniSouboru.CteniSouboru;

public class Slovnik {

	private CteniSouboru c;
	private SlovaSeStejnymPoctemZnaku[] slovaSeStejnymPoctemZnaku;
	private HashSet<String> nazvySouboru;
	private final String  AAdata = "AAdata.sam", textovaPodoba = "text.txt", AAdataBin = "AAdata.bin", AAobsazeneSoubory = "AAobsazeneSoubory.sam";
	private HashSet<String> obsazeneSoubory = new HashSet<String>(); 
	private AssetManager asset;
	
	public Slovnik(AssetManager asset) throws IOException, ClassNotFoundException{
		this.asset = asset;
		//nazvySouboru = pridejNazvySouboru();	//do na
		
		new Prevodnik();
		
		
		{		
		slovaSeStejnymPoctemZnaku = new SlovaSeStejnymPoctemZnaku[20];
		
				
		//obsazeneSoubory = ctiSouborSdaty();
		
		
		//nazvySouboru = vymazZnazvuSouboruObsazeneSoubory(obsazeneSoubory, nazvySouboru);		
				
		
		
		c =  new CteniSouboru(AAdata, asset);		
		ctiAzapisuj();
		
		}
		
		
		
	}
	
	 private void ctiZeSouboru() throws StreamCorruptedException, IOException, ClassNotFoundException{
		File file = new File(MainActivity.getInternalDir(), AAdataBin);
		FileInputStream filis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(filis);
		slovaSeStejnymPoctemZnaku = (SlovaSeStejnymPoctemZnaku[]) ois.readObject();
	 }
	
	private void ctiAzapisuj(){
		int i = 0;
		while(true){
			try{
				String temp;
			 zapis(temp = (c.getSlovo()));
				
			 if(i% 100 == 0)
				 Log.i("Slovnik.ctiAzapisuj()", temp + " : " + i);
			 i++;
			}
			catch(NoSuchElementException nse){
				//vyjimka nastane, v pripade, ze je soubor docten, a zpracovan
				//nenosetruje se
				break;
			}
		}
	}
	private void zapis(String slovo){
		String[] slova = slovo.split(":");
		
		int pocetZnaku = slova[0].length();
		if(slovaSeStejnymPoctemZnaku.length < pocetZnaku){
			navysKapacitu(pocetZnaku, slova[0]);
		}
		if(slovaSeStejnymPoctemZnaku[pocetZnaku-1] == null){
			slovaSeStejnymPoctemZnaku[pocetZnaku-1] = new SlovaSeStejnymPoctemZnaku(pocetZnaku);		
		}
		slovaSeStejnymPoctemZnaku[pocetZnaku-1].pridejSlovo(slova[0], slova[1].split(","));
	}
	
	private void navysKapacitu(int znaky, String slovo){
		if(znaky >= 100)
		{throw new IllegalArgumentException("Dokument obsahuje prilis dlouhy String: " + slovo);}
		SlovaSeStejnymPoctemZnaku[] temp = new SlovaSeStejnymPoctemZnaku[znaky];
		
		for(int i = 0; i < slovaSeStejnymPoctemZnaku.length; i++){
			temp[i] = slovaSeStejnymPoctemZnaku[i];			
		}
		slovaSeStejnymPoctemZnaku = temp;
	}
	
	public void vypis(){
		for(int i = 0; i < slovaSeStejnymPoctemZnaku.length; i++){
			if(slovaSeStejnymPoctemZnaku[i] != null){
			System.out.print("{");
			slovaSeStejnymPoctemZnaku[i].vypis();
			System.out.println("}");
			}
		}
	}
	
	private void zapisDoSouboru() throws IOException{
			
		
		File file = new File(MainActivity.getInternalDir(), AAdataBin);
		
		file.createNewFile();
		FileOutputStream fous = new FileOutputStream(file);
		ObjectOutputStream oos2 = new ObjectOutputStream(fous);
		oos2.writeObject(slovaSeStejnymPoctemZnaku);
		oos2.close();		
	}
	
	private HashSet<String> ctiSouborSdaty() throws IOException, ClassNotFoundException{
		HashSet<String> navrat = new HashSet<String>();
		File file = new File(AAobsazeneSoubory);
		if(!file.exists()){
			return navrat;
		}		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		navrat = (HashSet<String>)ois.readObject();	
		ois.close();
		
		file = new File(AAdataBin);
		if(navrat.size() != 0 && !file.exists())
			navrat.clear();
		if(file.exists()){
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			slovaSeStejnymPoctemZnaku = (SlovaSeStejnymPoctemZnaku[])ois.readObject();
			ois.close();
		}
		
		
		return navrat;
	}
	
	private String[] pridejDoPoleStringu(String[] navrat, String hodnota){		
		String[] temp = new String[navrat.length + 1];
		for(int i = 0; i < navrat.length; i++){
			temp[i] = navrat[i];
		}
		temp[navrat.length] = hodnota;
		navrat = temp;
		
		return navrat;	
	}
	
	private HashSet<String> vymazZnazvuSouboruObsazeneSoubory(HashSet<String> obsazeneSoubory,HashSet<String> nazvySouboru){
		nazvySouboru.removeAll(obsazeneSoubory);
		return nazvySouboru;
	}
	
	private void nactiDataZeSouboru(Scanner scn){
		String key;
		ArrayList<StringBuilder> slova = new ArrayList<StringBuilder>();
		while(scn.hasNext()){
			String usek = scn.next();
			int index = usek.indexOf(':');
			key = usek.substring(0, index);
			ziskejSlova(slova, usek.substring(index +1, usek.length()));
			
			nacti(key, slova);
			
			slova.clear();			
		}		
	}
	
	private void nacti(String key, ArrayList<StringBuilder> slova){
		int pocetZnaku = key.length();
		if(slovaSeStejnymPoctemZnaku.length < pocetZnaku){
			navysKapacitu(pocetZnaku, slova.get(0).toString());
		}
		if(slovaSeStejnymPoctemZnaku[pocetZnaku-1] == null){
			slovaSeStejnymPoctemZnaku[pocetZnaku-1] = new SlovaSeStejnymPoctemZnaku(pocetZnaku);		
		}
		
		slovaSeStejnymPoctemZnaku[pocetZnaku -1].nacti(key, slova);
	}
	
	private void ziskejSlova(ArrayList<StringBuilder> slova, String usek){
		int index = 0;
		slova.add(new StringBuilder(""));
		for(int i =0; i < usek.length(); i++){
			if(usek.charAt(i) == ','){
				index++;
				slova.add(new StringBuilder(""));
				continue;
			}
			slova.get(index).append(usek.charAt(i));
		}		
	}
	private HashSet<String> pridejNazvySouboru(){
		File file = new File(new File("").getAbsolutePath());
		
		
		FilenameFilter filnf = new FilenameFilter() {			
			
			public boolean accept(File file, String name) {
				
				return name.endsWith(".txt");
			}
		};
		
		String[] vystup = file.list(filnf);
		
		HashSet<String> nazvy =  new HashSet<String>();
		
		nazvy.addAll(java.util.Arrays.asList(vystup));	
		
		return nazvy;
	}
	
	public String[][] vratAnagramy(String ...klice){
		String[][] navrat = new String[klice.length][];
		for(int i = 0; i < klice.length; i++){
			if(klice[i].length() > slovaSeStejnymPoctemZnaku.length)
				return null;
			navrat[i] = slovaSeStejnymPoctemZnaku[klice[i].length()-1].vratAnagramy(klice[i]);
			if(navrat.length == 0)
				return null;
		}
		
		return navrat;
		
	}
	
}
