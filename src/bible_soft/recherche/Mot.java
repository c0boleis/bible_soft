package bible_soft.recherche;

public class Mot {

	public String mot;
	
	private  boolean isVerbe = false;
	
	public Mot(String text){
		mot = text;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mot == null) ? 0 : mot.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Mot)) {
			return false;
		}
		Mot other = (Mot) obj;
		if (mot == null) {
			if (other.mot != null) {
				return false;
			}
		}
		if(mot.equalsIgnoreCase(other.mot)){
			return true;
		}
		if(Math.abs(mot.length()-other.mot.length())!=1){
			return false;
		}
		/*
		 * check pluriel
		 */
		if(mot.length()<2 || other.mot.length()<2){
			return false;
		}
		String text ="";
		int size = 0;
		if(mot.length()>other.mot.length()){
			text = mot;
			size = other.mot.length();
		}else{
			text = other.mot;
			size = mot.length();
		}
		char c = text.charAt(text.length()-1);
		if(c == 's' || c == 'x'){
			String mot1 = mot.substring(0, size-1);
			String mot2 = other.mot.substring(0, size-1);
			return mot1.equalsIgnoreCase(mot2);
		}
		return false;
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mot [mot=" + mot + "]";
	}


	public int compareToIgnoreCase(Mot mot2) {
		return mot.compareToIgnoreCase(mot2.mot);
	}


	public boolean equalsIgnoreCase(Mot mt) {
		return this.equals(mt);
	}


	/**
	 * @return the isVerbe
	 */
	public boolean isVerbe() {
		return isVerbe;
	}


	/**
	 * @param isVerbe the isVerbe to set
	 */
	public void setVerbe(boolean isVerbe) {
		this.isVerbe = isVerbe;
	}
	
}
