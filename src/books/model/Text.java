package books.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import books.model.shearch.TextMatch;

/**
 * 
 * @author Corentin Boleis
 *
 */
public class Text implements IText, IOrderedObject {
	
	private static final Logger LOGGER = Logger.getLogger(Text.class);
	
	private HashMap<String, String> texts = new HashMap<String,String>();
	
	private String name;
	
	private String filePath;
	
	private int order = -1;
	
	private boolean load = false;
	
	private boolean save = false;
	
	private ISubDivision subDivision;

	private String defaultTranslation = NAME_DEFAULT_TRANSLATION;

	public Text(ISubDivision div,String text,String name) {
		if(div==null){
			throw new NullPointerException(
					"la subdivition d'un text ne peut pas etre null");
		}
		this.subDivision = div;
		this.setText(text, defaultTranslation);
		this.name = name;
	}
	
	public Text(ISubDivision div,String filePath) throws IOException {
		if(div==null){
			throw new NullPointerException(
					"la subdivition d'un text ne peut pas etre null");
		}
		if(filePath==null){
			throw new NullPointerException(
					"le fichier d'un text ne peut pas etre null");
		}
		this.filePath = filePath;
		this.subDivision = div;
		this.load();
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IOrderedObject#getOrder()
	 */
	@Override
	public int getOrder() {
		return this.order;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IOrderedObject#setOrder(int)
	 */
	@Override
	public void setOrder(int order) {
		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IText#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IText#getText()
	 */
	@Override
	public String getText(String trad) {
		return this.texts.get(trad);
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IText#setText()
	 */
	@Override
	public void setText(String t,String trad) {
		this.texts.put(trad, t);//TODO vérifier
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	@Override
	public String read() {
		return this.getText(defaultTranslation);
	}
	
	@Override
	public String read(String trad) {
		return this.texts.get(trad);
	}

	@Override
	public IShearchMatch[] shearch(String regex) {
		return shearch(regex,this.defaultTranslation);
	}

	@Override
	public String getRefference() {
		// TODO Auto-generated method stub
		return "N/A";
	}

	@Override
	public boolean isSave() {
		return this.save;
	}

	@Override
	public void Save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLoad() {
		return this.load;
	}

	@Override
	public void load() throws IOException {
		if(this.isLoad()){
			return;
		}
		File file = new File(filePath);
		this.name = file.getName();
		BufferedReader buf = new BufferedReader(new FileReader(file));
		String line = buf.readLine();
		String text ="";
		while(line!=null){
			text+=line+"\n";
			line = buf.readLine();
		}
		buf.close();
		String[] translations = getZoneTranslations(text);
		if(translations.length<=0){
			return;
		}
		this.defaultTranslation = translations[0];
		String zoneText = getZoneText(text);
		if(zoneText==null){
			this.load = false;
			return;
		}
		for(String trans : translations){
			try{
				String textTranslation = getTranslationZoneText(zoneText, trans);
				this.texts.put(trans, textTranslation);
			}catch(IllegalArgumentException e){
				LOGGER.error(e.getMessage());
			}
		}
		this.load = true;
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
	
	private String getZoneText(String text){
		String textStart = "<"+KEY_TEXT+">";
		String textEnd ="</"+KEY_TEXT+">"; 
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
		translations =  translations.trim();
		if(translations.length()==0){
			return null;
		}
		return translations;
	}
	
	private String[] getZoneTranslations(String text){
		String textStart = "<"+KEY_TRANSLATIONS+">";
		String textEnd ="</"+KEY_TRANSLATIONS+">"; 
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
		String info[] = translations.split(TRANSLATIONS_SEPARATOR);
		List<String> trans = new ArrayList<String>();
		for(String t : info){
			String st =t.trim();
			if(st.length()>0){
				trans.add(st);
			}
		}
		return trans.toArray(new String[0]);
	}

	@Override
	public ISubDivision getSubDivision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IShearchMatch[] shearch(String regex, String translation) {
		List<IShearchMatch> listOut = new ArrayList<IShearchMatch>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(getText(translation));
		// Check all occurrences
		while (matcher.find()) {
			listOut.add(new TextMatch(matcher.group(), this,translation));
		}
		return	listOut.toArray(new IShearchMatch[0]);
	}

	@Override
	public String[] getTraductions() {
		return texts.keySet().toArray(new String[0]);
	}

	@Override
	public String getDefaultTranslation() {
		return this.defaultTranslation;
	}

}