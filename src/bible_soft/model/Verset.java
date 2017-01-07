package bible_soft.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bible_soft.recherche.CompteurDeMots;
import util.StringUtil;

public class Verset implements ILoadSaveOld{

	private Chapitre chapitre;

	private int numero;

	private char cmpNumero = '\0';

	private String text;

	private boolean isSave = false;

	private boolean isLoad = false;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Verset [numero=" + numero + ", text=" + text + "]";
	}

	/**
	 * 
	 */
	public Verset(Chapitre chapitre,int numero) {
		super();
		if(chapitre==null){
			throw new IllegalArgumentException("Le chapitre ne pas etre null.");
		}
		this.chapitre = chapitre;
		if(numero<0){
			throw new IllegalArgumentException("le numero d'un verset doit etre sup. ou égal à 1");
		}
		this.numero = numero;
		if(!this.chapitre.addVerset(this)){
			throw new IllegalArgumentException("le verset exist déjà dans le Chapitre: "+chapitre+".");
		}
	}
	public Verset(Chapitre chapitre,int numero,char cmp) {
		super();
		if(chapitre==null){
			throw new IllegalArgumentException("Le chapitre ne pas etre null.");
		}
		this.chapitre = chapitre;
		if(numero<0){
			throw new IllegalArgumentException("le numero d'un verset doit etre sup. ou égal à 1");
		}
		this.numero = numero;
		this.cmpNumero = cmp;
		if(!this.chapitre.addVerset(this)){
			throw new IllegalArgumentException("le verset exist déjà dans le Chapitre: "+chapitre+".");
		}
	}

	/**
	 * @return the chapitre
	 */
	public Chapitre getChapitre() {
		return chapitre;
	}

	public int getNumero(){
		return numero;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text.trim();
	}

	@Override
	public void load() {
		File file = new File(getFilePath());
		if(!file.exists()){
			return;
		}
		try {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			String line = buf.readLine();
			boolean first = true;
			text = "";
			while(line!=null){
				if(!first){
					text+="\n";
				}
				first = false;
				text +=line;
				line = buf.readLine();
			}
			text = getTranslationZoneText(text.trim(),"nouvelle traduction");
			buf.close();
			isLoad = true;
			isSave = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private String getTranslationZoneText(String text,String translation){
		String textStart = "<"+translation+">";
		String textEnd ="</"+translation+">"; 
		int indexStart = text.indexOf(textStart);
		int indexEnd = text.indexOf(textEnd);
		if(indexEnd<0 && indexStart<0){
			throw new IllegalArgumentException("les balises "+textStart+" n'ont pas été trouvées.");
		}else if(indexEnd>=0 && indexStart<0){
			throw new IllegalArgumentException("la balise de début "+textStart+" n'a pas été trouvée.");
		}else if(indexEnd<0 && indexStart>=0){
			throw new IllegalArgumentException("la balise de fin "+textEnd+" n'a pas été trouvée.");
		}else if((indexStart+textStart.length())>indexEnd){
			throw new IllegalArgumentException("les balises "+textStart+" ne sont pas dans le bonne ordre.");
		}
		String translations = text.substring(indexStart+textStart.length(), indexEnd);
		return translations.trim();
	}

	@Override
	public void save() {
		File file = new File(getFilePath());
		if(file.exists()){
			return;
		}
		else{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				isSave = false;
				return;
			}
		}
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(file));
			buf.write(text);
			buf.close();
			isSave = true;
		} catch (IOException e) {
			e.printStackTrace();
			isSave = false;
		}

	}

	@Override
	public boolean isLoad() {
		return isLoad;
	}

	@Override
	public boolean isSave() {
		return isSave;
	}

	public String getFilePath(){
		String add = "";
		if(cmpNumero!='\0'){
			add+=cmpNumero;
		}
		return getChapitre().getFolderPath()+File.separator+LivreColector.getNumberString(numero)+add;
	}

	public int compteMotRegex(String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		// Check all occurrences
		int nbr=0;
		while (matcher.find()) {
			//	        System.out.println("Start index: " + matcher.start());
			//	        System.out.println(" End index: " + matcher.end());
			System.out.println("\t"+getRefference()+"\"" + matcher.group()+"\"\n"+getText());
			nbr++;
		}
		return nbr;
	}

	public synchronized int compteMot(String mot){
		if(text==null){
			return 0;
		}
		int k = text.indexOf(mot);
		int nbr = 0;
		while(k>0){
			k = text.indexOf(mot,k+mot.length());
			nbr++;
		}
		if(nbr>0){
			System.out.println("\t"+getRefference()+": "+nbr+" occurrence de \""+mot+"\"");
			//			System.out.println(text);
		}

		//		System.out.flush();
		return nbr;
	}

	public String getRefference(){
		return this.getChapitre().getLivre().getAbv()+" "+this.getChapitre().getNumero()+", "+this.numero;
	}

	public String[] getMots(){
		String textTmp = StringUtil.sansAccent(text);
		return textTmp.split("\\W");
	}


}
