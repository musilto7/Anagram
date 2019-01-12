package cteniSouboru;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import android.content.res.AssetManager;



public class CteniSouboru {	
	private Scanner scn;
	private HashSet povoleneZnaky;
	
	public CteniSouboru(String nazevSouboru, AssetManager asset) throws IOException{
		
		scn = new Scanner(asset.open(nazevSouboru));
		povoleneZnaky = new HashSet<Character>();
		inicializacePovolenychznaku();
	}
	
	public String getSlovo(){
		String slovo = scn.next();		
				
		return slovo;
	}
	
	private void inicializacePovolenychznaku(){
		String znaky = "ěščřžýáíéóúůťďň";
		
		for(int i = 'a'; i <= 'z'; i++){
			povoleneZnaky.add((char)(i));
		}
		
		for(int i = 0; i < znaky.length();i++){
			povoleneZnaky.add(znaky.charAt(i));
		}
				
	}
	
	private boolean jePovolenyZnak(char c){
		return povoleneZnaky.contains(c);
	}
	
	private String orezZleva(String slovo){
		int citac= 0;
		for(int i = 0; i < slovo.length(); i++){
			if(povoleneZnaky.contains(slovo.charAt(i))){
				break;
			}
			citac++;
		}
		if(slovo.length() > citac)
			return slovo.substring(citac, slovo.length());
		return "";
	}
	
	private String orezZprava(String slovo){
		int citac= 0;
		for(int i = slovo.length()-1; i >=0; i--){
			if(povoleneZnaky.contains(slovo.charAt(i))){
				break;
			}
			citac++;
		}
		if(slovo.length() > citac)
			return slovo.substring(0, slovo.length()-citac);
		return "";
	}
	
	private boolean obsahujeNepovolenyZnak(String slovo){
		for(int i =0; i < slovo.length(); i++){
			if(!jePovolenyZnak(slovo.charAt(i)))
				return true;			
		}
		return false;
	}
	
	
}
