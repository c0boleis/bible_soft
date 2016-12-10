package bible_soft.model;

import java.io.File;

public class LivreColector {
	
	public static String pathFolderLivre = "/home/bata/bible";
	
	public static File folderLivre = new File(pathFolderLivre);
	
	public static String getNumberString(int k){
		if(k>999){
			return null;
		}
		if(k<0){
			return null;
		}
		String value = String.valueOf(k);
		while(value.length()<=3){
			value="0"+value;
		}
		return value;
	}

}
