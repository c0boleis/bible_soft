package dico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import bible_soft.model.Verset;
import bible_soft.recherche.CompteurDeMots;
import bible_soft.recherche.Mot;

public class Verbe extends Mot{

	private List<String> conjs = new ArrayList<String>();
	
	private static final String PATH_FOLDER = "/home/bata/verbes/";

	public Verbe(String text) {
		super(text);
	}
	
	public Verbe(File file) {
		super("");
		try {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			mot = buf.readLine();
			String line = buf.readLine();
			while(line!=null){
				conjs.add(line.trim());
				line = buf.readLine();
//				System.out.println(line);
			}
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isThisVerbe(String text){
		if( text.equalsIgnoreCase(this.mot)){
			return true;
		}
		return conjs.contains(text);
	}
	
	public void load(){
		File file = new File(PATH_FOLDER+mot.trim().toLowerCase());
		if(!file.exists()){
			throw new NullPointerException();
		}
		try {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			String line = buf.readLine();
			line = buf.readLine();
			while(line!=null){
				conjs.add(line.trim().toLowerCase());
				line = buf.readLine();
			}
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(){
		if(conjs.isEmpty()){
			return;
		}
		File file = new File(PATH_FOLDER+mot.trim().toLowerCase());
		if(file.exists()){
			return;
		}
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(file));
			buf.write(mot.trim().toLowerCase());
			for(String st : conjs){
				buf.write("\n");
				buf.write(st.trim().toLowerCase());
			}
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fetchFromInternet() throws IOException{

		
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec("rm /home/bata/tmp_html/*");
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String cmd = "wget -P /home/bata/tmp_html/ http://leconjugueur.lefigaro.fr/conjugaison/verbe/"
				+mot.toLowerCase()+".html";
//		System.out.println(cmd);
		Process proc1 = runtime.exec(cmd);
		try {
			proc1.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String rt ="";
		File file = new File("/home/bata/tmp_html/"+mot.toLowerCase()+".html");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while(line!=null){
			rt +=line;
			line = reader.readLine();
		}
		reader.close();
		ParseString(rt);
	}

	private void ParseString(String text){
		String baliseDeb = "class=\"tempsBloc\">";
		String baliseFin = "</div>";
		int indexDeb  = text.indexOf(baliseDeb);
		int indexFin = -1;
		while(indexDeb>0){
			indexDeb = text.indexOf(baliseFin,indexDeb);
			indexFin = text.indexOf(baliseFin,indexDeb+baliseFin.length());
			if(indexFin<0){
				break;
			}
			if(indexFin<=indexDeb){
				throw new IndexOutOfBoundsException("ParseString verbe indexFin<=indexDeb");
			}
			String conj = text.substring(indexDeb+baliseFin.length(), indexFin);
			searchVerbe(conj);
			indexDeb = text.indexOf(baliseDeb,indexFin+1);
		}
		
	}
	
	private void searchVerbe(String text){
//		String baliseDeb = "<b>";
//		String baliseFin = "</b>";
		String split = "<br";
		String[] tab = CompteurDeMots.sansAccent(text).split(split);
		for(String st : tab){
			int k  = st.indexOf('>');
			if(k>0){
				st = st.substring(k+1);
			}
//			 ç 	Alt+ 0231 	&#231; 	&ccedil; 	c cédille minuscule
//			 è 	Alt+ 0232 	&#232; 	&egrave; 	e accent grave minuscule
//			 é 	Alt+ 0233 	&#233; 	&eacute; 	e accent aigu minuscule
//			 ê 	Alt+ 0234 	&#234; 	&ecirc; 	e accent circonflexe minuscule
//			 ë 	Alt+ 0235 	&#235; 	&euml;
//			 à 	Alt+ 0224 	&#224; 	&agrave; 	a accent grave minuscule
//			 á 	Alt+ 0225 	&#225; 	&aacute; 	a accent aigu minuscule
//			 â 	Alt+ 0226 	&#226; 	&acirc; 	a accent circonflexe minuscule
//			 ã 	Alt+ 0227 	&#227; 	&atilde; 	a tilde minuscule
//			 ä 	Alt+ 0228 	&#228; 	&auml;
			String conj = st.replace("&acirc;", "a")
					.replace("&agrave;", "a")
					.replace("&aacute;", "a")
					.replace("&atilde;", "a")
					.replace("&auml;", "a")
					.replace("&eacute;", "e")
					.replace("&ccedil;", "c")
					.replace("&egrave;", "e")
					.replace("&ecirc;", "e")
					.replace("&euml;", "e")
					.replace("&eacute;", "e")
					.replace(";", "")
					.replace("<p>", "")
					.replace("</p>", "")
					.replace("<b>", "")
					.replace("</b>", "");
			
			String[] tab1 = conj.split("\\W");
			if(tab1.length<=0){
				continue;
			}
			conj = tab1[tab1.length-1].trim();
			if(conj.length()<=0){
				continue;
			}
			if(conjs.contains(conj)){
				continue;
			}
			conjs.add(conj);
		}
		
	}

}
