package vstupDat;


import java.util.ArrayList;
import java.util.Scanner;

public class VstupDat {

	private String[] data;
	
	public VstupDat(String vstup){	      //pozdeji je mozno dodelat grafickou podobu, pro toho, kdo by se nespokojil s terminalem
		
		ArrayList<String> ar = new ArrayList<String>();
		Scanner scn2 = new Scanner(vstup);
		
		while(scn2.hasNext())
			ar.add(scn2.next().toLowerCase());
		
		data = new String[ar.size()];
		ar.toArray(data);
		
	}
	public String[] getData(){
		return data;
	}
}
