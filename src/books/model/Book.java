package books.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import books.exceptions.NoPropetiesException;
import books.model.interfaces.IBook;
import books.model.interfaces.IPropertiesUsed;
import books.model.interfaces.IShearchMatch;
import books.model.interfaces.ISubDivision;
import books.model.interfaces.IText;
import books.model.listener.TextAddedListener;
import books.model.rules.BookAbvRule;
import books.model.rules.BookNameRule;

@XStreamAlias("Book")
public class Book implements IBook {
	
	private static final Logger LOGGER = Logger.getLogger(Book.class);

	private String abv = null;
	
	private static final BookAbvRule BOOK_ABV_RULE = new BookAbvRule();

	private String name = null;
	
	private static final BookNameRule BOOK_NAME_RULE = new BookNameRule();

	private String folderPath = null;
	
	private String hierarchy = null;
	
	private int nbrOfText = -1;
	
	private boolean load = false;
	
	private boolean save = true;
	
	private List<ISubDivision> subDivisions = new ArrayList<ISubDivision>();
	
	private List<TextAddedListener> textAddedListeners = new ArrayList<TextAddedListener>();

	private boolean autoOpen = false;

	public Book(String fPtah){
		this.folderPath = fPtah;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IPropertiesUsed#load()
	 */
	@Override
	public Properties loadInfo() throws NoPropetiesException {
		if(this.folderPath==null){
			throw new NullPointerException();
		}
		if(!this.subDivisions.isEmpty()){
			return null;
		}
		String infoPath = this.folderPath+File.separator+PROPERTIES_FILE_NAME;
		File file = new File(infoPath);
		Properties pr = new Properties();
		
		try {
			pr.load(new FileReader(file));
			this.name = pr.getProperty(KEY_NAME).trim();
			this.abv = pr.getProperty(KEY_ABV).trim();
			this.hierarchy = pr.getProperty(KEY_HIERARCHY).trim();
			this.nbrOfText = Integer.parseInt(pr.getProperty(KEY_NBR_OF_TEXTS,"-1"));
			this.autoOpen = Boolean.parseBoolean(pr.getProperty(KEY_AUTO_OPEN, "false"));
			if(this.autoOpen){
				this.loadSubDivisions();
			}
			return pr;
		} catch (FileNotFoundException e) {
			throw new NoPropetiesException();
		} catch (IOException e) {
			throw new NoPropetiesException(e.getMessage());
		}

	}
	
	/**
	 * 
	 */
	public void loadSubDivisions(){
		File folder = new File(folderPath);
		if(!folder.exists()){
			throw new NullPointerException("le dossier n'éxist pas");
		}
		if(!folder.isDirectory()){
			throw new IllegalArgumentException("le fichier n'est pas un dossier");
		}
		if(!this.subDivisions.isEmpty()){
			LOGGER.warn("subDivisions already load");
			return;
		}
		File[] files = folder.listFiles();
		for(File file :files){
			ISubDivision div = new SubDivision(this,file.getAbsolutePath());
			try {
				div.loadInfo();
				this.subDivisions.add(div);
			} catch (NoPropetiesException e) {}
		}
		this.sortSubdivisions();
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IBook#getAbv()
	 */
	@Override
	public String getAbv() {
		return this.abv;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IBook#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ISubDivisonContainer#getSubDivisions()
	 */
	@Override
	public ISubDivision[] getSubDivisions() {
		return subDivisions.toArray(new ISubDivision[0]);
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ISubDivisonContainer#isSubDivisionEmpty()
	 */
	@Override
	public boolean isSubDivisionEmpty() {
		File folder = new File(this.folderPath);
		if(!folder.exists()){
			return true;
		}
		File[] files = folder.listFiles();
		for(File file :files){
			File[] files2 = file.listFiles();
			for(File file2 :files2){
				if(file2.getName().contains(IPropertiesUsed.PROPERTIES_FILE_NAME)){
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IShearable#shearch(java.lang.String)
	 */
	@Override
	public IShearchMatch[] shearch(String regex) {
		return shearch(regex, null);
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ILoadSaveObject#isSave()
	 */
	@Override
	public boolean isSave() {
		return this.save;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ILoadSaveObject#save()
	 */
	@Override
	public void save() {
		try {
			saveInfo();
			this.save = true;
		} catch (NoPropetiesException e) {
			LOGGER.error("les informations n'ont pas été enregistrés", e);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ILoadSaveObject#isLoad()
	 */
	@Override
	public boolean isLoad() {
		return this.load;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ILoadSaveObject#load()
	 */
	@Override
	public void load() throws IOException {
		if(!this.isLoad()){
			this.loadSubDivisions();
			boolean loadTest = true;
			for(ISubDivision div : this.subDivisions){
				div.load();
				if(!div.isLoad()){
					loadTest=false;
				}
			}
			this.load = loadTest;
		}
		if(!this.isLoad()){
			return;
		}
		int nbrTmp = this.nbrOfText;
		this.nbrOfText = calculNbrOfTexts();
		if(this.nbrOfText!= nbrTmp){
			this.save = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IShearable#shearch(java.lang.String, java.lang.String)
	 */
	@Override
	public IShearchMatch[] shearch(String regex, String translation) {
		List<IShearchMatch> listOut = new ArrayList<IShearchMatch>();
		ISubDivision[] tabDiv = getSubDivisions();
		for(ISubDivision division : tabDiv){
			listOut.addAll(Arrays.asList(division.shearch(regex,translation)));
		}
		return	listOut.toArray(new IShearchMatch[0]);
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ILoadSaveObject#getFilePath()
	 */
	@Override
	public String getFilePath() {
		return this.folderPath;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ILoadSaveObject#setFilePath(java.lang.String)
	 */
	@Override
	public void setFilePath(String path) {
		this.folderPath = path;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ISubDivisonContainer#getSubDivision(java.lang.String)
	 */
	@Override
	public ISubDivision getSubDivision(String name) {
		for(ISubDivision div : this.subDivisions){
			if(div.getName().equals(name)){
				return div;
			}
		}
		return null;
	}

	@Override
	public Properties saveInfo() throws NoPropetiesException {
		if(this.folderPath==null){
			throw new NullPointerException();
		}
		String infoPath = this.folderPath+File.separator+PROPERTIES_FILE_NAME;
		File file = new File(infoPath);
		Properties pr = new Properties();
		if(save){
			return pr;
		}
		try {
			pr.load(new FileReader(file));
			pr.setProperty(KEY_NAME, this.name);
			pr.setProperty(KEY_ABV, this.abv);
			pr.setProperty(KEY_HIERARCHY, this.hierarchy);
			pr.setProperty(KEY_NBR_OF_TEXTS, String.valueOf(this.nbrOfText));
			pr.setProperty(KEY_AUTO_OPEN, String.valueOf(this.autoOpen));
			pr.store(new FileWriter(file), null);
			return pr;
		} catch (FileNotFoundException e) {
			throw new NoPropetiesException();
		} catch (IOException e) {
			throw new NoPropetiesException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ISubDivisonContainer#sortSubdivisions()
	 */
	@Override
	public void sortSubdivisions() {
		boolean sortByName = true;
		for(ISubDivision div : this.subDivisions){
			if(div.getOrder()>0){
				sortByName = false;
				break;
			}
		}
		if(sortByName){
			Collections.sort(subDivisions,
					new SubDivisionNameComparator());
		}else{
			Collections.sort(subDivisions,
					new OrderObjectComparator());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ISubDivisonContainer#getNbrOfTexts()
	 */
	@Override
	public int getNbrOfTexts() {
		return this.nbrOfText;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IBook#setAbv(java.lang.String)
	 */
	@Override
	public String setAbv(String newAbv) {
		String error = BOOK_ABV_RULE.checkRule(newAbv);
		if(error!=null){
			return error;
		}
		this.abv = BOOK_ABV_RULE.modifyWithRule(newAbv);
		this.save = false;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IBook#setName(java.lang.String)
	 */
	@Override
	public String setName(String newName) {
		String error = BOOK_NAME_RULE.checkRule(newName);
		if(error!=null){
			return error;
		}
		this.name = BOOK_NAME_RULE.modifyWithRule(newName);
		this.save = false;
		return null;
	}

	@Override
	public boolean isAutoOpen() {
		return this.autoOpen;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IBook#calculNbrOfTexts()
	 */
	@Override
	public int calculNbrOfTexts() {
		int nbrOut = 0;
		ISubDivision[] divs = this.getSubDivisions();
		for(ISubDivision div : divs){
			nbrOut+=div.calculNbrOfTexts();
		}
		return nbrOut;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ILoadSaveObject#saveAll()
	 */
	@Override
	public void saveAll() throws IOException {
		this.save();
		ISubDivision[] divs = getSubDivisions();
		for(ISubDivision div : divs){
			div.saveAll();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IBook#addTextAddedListener(books.model.listener.TextAddedListener)
	 */
	@Override
	public void addTextAddedListener(TextAddedListener listener) {
		if(!this.textAddedListeners.contains(listener)){
			this.textAddedListeners.add(listener);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IBook#removeTextAddedListener(books.model.listener.TextAddedListener)
	 */
	@Override
	public void removeTextAddedListener(TextAddedListener listener) {
		this.textAddedListeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.IBook#getTextAddedListeners()
	 */
	@Override
	public TextAddedListener[] getTextAddedListeners() {
		return this.textAddedListeners.toArray(new TextAddedListener[0]);
	}
	
	protected void fireTextAdded(IText text){
		TextAddedListener[] tab = getTextAddedListeners();
		for(TextAddedListener listener : tab){
			listener.textAdded(text);
		}
	}

}
