package bible_soft.recherche;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import dico.VerbeCollector;

public class CompteurDeMots {
	
	private List<CompteMot> mots = new ArrayList<CompteMot>();
	
	public void addMots(String mot){
		mot = mot.trim();
		if(mot.length()<=2){
			return;
		}
		String tmp = VerbeCollector.searchVerbe(mot);
		tmp = tmp.trim();
		if(tmp!=null){
			mot = tmp;
		}
		CompteMot compteMot = getMot(mot);
		if(compteMot!=null){
			if(compteMot.getMot().mot.length()!=mot.length()){
				compteMot.setPluriel(true);
			}
			compteMot.increment();
			return;
		}
		compteMot= new CompteMot(mot);
		if(tmp!=null){
			compteMot.setVerbe(true);
		}
		mots.add(compteMot);
	}
	
	public CompteMot getMot(String mtTmp){
		Mot mt = new Mot(mtTmp);
		for(CompteMot compteMot : mots){
			if(compteMot.getMot().equalsIgnoreCase(mt)){
				return compteMot;
			}
		}
		return null;
	}
	
	public void sort(){
		Collections.sort(mots, new Comparator<CompteMot>() {

			@Override
			public int compare(CompteMot o1, CompteMot o2) {
//				int cmp =  Integer.compare(o2.getCount(), o1.getCount());
//				if(cmp!=0){
//					return cmp;
//				}
				return o1.getMot().compareToIgnoreCase(o2.getMot());
			}
		});
	}
	
	public void print(int size){
		if(size<0){
			return;
		}
		if(size>=mots.size()){
			size=mots.size()-1;
		}
		for(int index = 0;index<size;index++){
			CompteMot m = mots.get(index);
			System.out.println(m);
		}
	}

	public int size() {
		return mots.size();
	}

	public static String sansAccent(String s) 
	{

		String strTemp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(strTemp).replaceAll("");
	}

}
