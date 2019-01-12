package treeMap;

import java.util.TreeMap;

public class ZiskejTreeMap {

	
	public static TreeMap<Character, Integer> getTreemap(String slovo){
		 TreeMap<Character, Integer> temp = new TreeMap<Character, Integer>();
		 for(int i = 0; i < slovo.length(); i++){
			 if(temp.containsKey(slovo.charAt(i))){
				 int val = temp.get(slovo.charAt(i));
				 temp.put(slovo.charAt(i), val+1);
			 }
			 else{
				 temp.put(slovo.charAt(i), 1);
			 }
		 }
		 return temp;
	}
}
