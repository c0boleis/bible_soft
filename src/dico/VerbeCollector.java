package dico;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bible_soft.recherche.Mot;

public class VerbeCollector {

	private static List<Verbe> verbes = new ArrayList<Verbe>();

	private static final File folderVerbe = new File("/home/bata/verbes");

	public VerbeCollector() {
		// TODO Auto-generated constructor stub
	}

	public static void loadVerbe(){
		File[] tmp = folderVerbe.listFiles();
		//		int size = tmp.length;
		//		int count = 0;
		int index = 0;
		for(File file : tmp){
			File[] files = file.listFiles();
			System.out.println("folder : "+file.getName()+" nbr fichiers: "+files.length);
			for(File file1 : files){
				index++;
				Verbe verbe = new Verbe(file1);
				verbes.add(verbe);
			}
		}
		System.out.println(verbes.size()+" verbes chargés");
	}

	public static String searchVerbe(Mot mot){
		for(Verbe verbe : verbes){
			if(verbe.equals(mot)){
				if(verbe.isThisVerbe(mot.mot)){
					return verbe.mot;
				}
			}
		}
		return null;
	}

	public static String searchVerbe(String mot){
		String text = mot.trim();
		for(Verbe verbe : verbes){
			if(verbe.isThisVerbe(text)){
				return verbe.mot;
			}
		}
		return null;
	}

}