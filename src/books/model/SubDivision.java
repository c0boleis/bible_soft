package books.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import books.exceptions.NoPropetiesException;

public class SubDivision implements ISubDivision {

	private String abv = null;

	private String name = null;

	private String folderPath = null;

	private String hierarchy = null;
	
	private boolean load = false;
	
	private boolean save = true;
	
	private ISubDivision subDivision;
	
	private IBook book;

	private List<IText> texts = new ArrayList<IText>();

	private int order = -1;

	private List<ISubDivision> subDivisions = new ArrayList<ISubDivision>();

	public SubDivision(ISubDivision div,String fPtah){
		if(div==null){
			throw new NullPointerException(
					"la subdivision d'une subdivision ne peut pas etre null");
		}
		if(fPtah==null){
			throw new NullPointerException(
					"le dossier d'une subdivision ne peut pas etre null");
		}
		this.subDivision = div;
		this.folderPath = fPtah;
	}
	
	public SubDivision(IBook book,String fPtah){
		if(book==null){
			throw new NullPointerException(
					"le livre d'une subdivision ne peut pas etre null");
		}
		if(fPtah==null){
			throw new NullPointerException(
					"le dossier d'une subdivision ne peut pas etre null");
		}
		this.book = book;
		this.folderPath = fPtah;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IPropertiesUsed#load()
	 */
	@Override
	public void loadInfo() throws NoPropetiesException {
		if(this.folderPath==null){
			throw new NullPointerException();
		}
		String infoPath = this.folderPath+File.separator+PROPERTIES_FILE_NAME;
		File file = new File(infoPath);
		Properties pr = new Properties();

		try {
			pr.load(new FileReader(file));
			this.name = pr.getProperty(KEY_NAME);
			this.abv = pr.getProperty(KEY_ABV);
			this.hierarchy = pr.getProperty(KEY_HIERARCHY);
			this.order = Integer.parseInt(pr.getProperty(KEY_ORDER, "-1"));
		} catch (FileNotFoundException e) {
			throw new NoPropetiesException();
		} catch (IOException e) {
			throw new NoPropetiesException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new NoPropetiesException("L'ordre est male écrit dans les propriétés");
		}

	}

	@Override
	public void loadSubDivisions(){
		File folder = new File(folderPath);
		if(!folder.exists()){
			throw new NullPointerException("le dossier n'éxist pas");
		}
		if(!folder.isDirectory()){
			throw new IllegalArgumentException("le fichier n'est pas un dossier");
		}
		if(!isSubDivisionEmpty()){
			return;
		}
		File[] files = folder.listFiles();
		for(File file :files){
			if(file.isDirectory()){
				SubDivision sub = new SubDivision(this,file.getAbsolutePath());
				try {
					sub.loadInfo();
					subDivisions.add(sub);
				} catch (NoPropetiesException e) {}
			}
		}
		boolean sortByName = false;
		for(ISubDivision div : this.subDivisions){
			if(div.getOrder()<0){
				sortByName = true;
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

	@Override
	public String getAbv() {
		return this.abv;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ISubDivision[] getSubDivisions() {
		return subDivisions.toArray(new ISubDivision[0]);
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public IText[] getTexts() {
		return texts.toArray(new IText[0]);
	}

	@Override
	public boolean isSubDivisionEmpty() {
		return subDivisions.isEmpty();
	}

	@Override
	public void loadTexts() {
		File folder = new File(folderPath);
		if(!folder.exists()){
			throw new NullPointerException("le dossier n'éxist pas");
		}
		if(!folder.isDirectory()){
			throw new IllegalArgumentException("le fichier n'est pas un dossier");
		}
		if(!isTextEmpty()){
			return;
		}
		File[] files = folder.listFiles();
		for(File file :files){
			if(!file.isDirectory()){
				if(file.getName()
						.contains(IPropertiesUsed.PROPERTIES_FILE_NAME)){
					continue;
				}
				try {
					Text sub = new Text(this,file.getAbsolutePath());
					texts.add(sub);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		boolean sortByName = false;
		for(IText div : this.texts){
			if(div.getOrder()<0){
				sortByName = true;
				break;
			}
		}
		if(sortByName){
			Collections.sort(texts,
					new TextNameComparator());
		}else{
			Collections.sort(texts,
					new OrderObjectComparator());
		}
	}

	@Override
	public boolean isTextEmpty() {
		return texts.isEmpty();
	}

	@Override
	public String getHierarchy() {
		return this.hierarchy;
	}

	@Override
	public String read() {
		String text = "";
		boolean first = true;
		IText[] texts = this.getTexts();
		for(IText t : texts){
			if(!first){
				text+="\n";
			}
			first = false;
			text+=t.getName()+":\t"+t.getText();
		}
		return text;
	}

	@Override
	public IShearchMatch[] shearch(String regex) {
		List<IShearchMatch> listOut = new ArrayList<IShearchMatch>();
		ISubDivision[] tabDiv = getSubDivisions();
		for(ISubDivision division : tabDiv){
			listOut.addAll(Arrays.asList(division.shearch(regex)));
		}
		IText[] tabText = getTexts();
		for(IText text : tabText){
			listOut.addAll(Arrays.asList(text.shearch(regex)));
		}
		return	listOut.toArray(new IShearchMatch[0]);
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
		boolean loadTest = true;
		this.loadSubDivisions();
		for(ISubDivision div : this.subDivisions){
			div.load();
			if(!div.isLoad()){
				loadTest = false;
			}
		}
		this.loadTexts();
		for(IText text : this.texts){
			text.load();
			if(!text.isLoad()){
				loadTest = false;
			}
		}
		this.load = loadTest;
	}

	@Override
	public ISubDivision getSubdivions() {
		return this.subDivision;
	}

	@Override
	public IBook getBook() {
		if(this.subDivision!=null){
			return this.subDivision.getBook();
		}
		return this.book;
	}

}
