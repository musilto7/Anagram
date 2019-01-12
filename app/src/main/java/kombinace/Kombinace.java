package kombinace;

public class Kombinace {

	private int n, k;
	private int[] pole;
	
	public Kombinace(int n, int k){
		if(n < k)
			throw new IllegalArgumentException("n > k");
		this.n = n;
		this.k = k;
		pole = new int[k];
		for(int i = 0; i < pole.length; i++)
			pole[i] = i;
		pole[pole.length - 1] -= 1;		//inicializace
	}
	public int[] getKombinace()throws IndexOutOfBoundsException{		
		int index = pole.length -1;
		nextKombinace(index);
		return pole;
	}
	
	public int[] getZbytekPoKombinaci(){
		if (n == k)
			return new int[0];
		int index = 0;
		int[] navrat = new int[n- k];
		for(int i = 0; i < n; i++){
			if(!poleContains(i)){
				navrat[index] = i;
				index++;
			}
		}
		return navrat;
	}
	
	private boolean poleContains(int i){
		for(int k = 0; k < pole.length; k++){
			if(pole[k] == i){
				return true;
			}
		}
		return false;
	}
	
	private void nextKombinace(int index) throws IndexOutOfBoundsException{
		if(pole[index] < n-1 && k-index + pole[index] < n){
			pole[index]++;
			
			return;
		}
		vyrovnej(index);
		nextKombinace(index - 1);
	}
	
	private void vyrovnej(int index) throws IndexOutOfBoundsException{
		int hlavni = pole[index-1] + 2;
		for(int i = index; i < pole.length; i++){
			pole[i] = hlavni++;
		}
	}
}
