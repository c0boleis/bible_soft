package bible_soft.recherche;

public class CompteMot {
	
	private int nbr = 1;
	
	private Mot mot;

	private boolean pluriel = false;
	
	private boolean verbe = false;
	
	public CompteMot(String st){
		mot = new Mot(st);
	}
	
	public int getCount(){
		return nbr;
	}
	
	public Mot getMot(){
		return mot;
	}
	
	public void increment(){
		nbr++;
	}

	public void setPluriel(boolean b) {
		pluriel = b;
	}
	
	public boolean isPluriel(){
		return pluriel;
	}

	/**
	 * @return the verbe
	 */
	public boolean isVerbe() {
		return verbe;
	}

	/**
	 * @param verbe the verbe to set
	 */
	public void setVerbe(boolean verbe) {
		this.verbe = verbe;
	}
	
	public String toString(){
		String p = "";
		if(isPluriel()&&isVerbe()){
			p="[p,v]";
		}
		if(isPluriel()&& !isVerbe()){
			p="[p]";
		}
		if(!isPluriel()&& isVerbe()){
			p="[v]";
		}
		return mot.mot+" ("+nbr+")"+p;
	}

}
