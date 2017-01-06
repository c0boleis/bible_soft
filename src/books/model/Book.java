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

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import books.exceptions.NoPropetiesException;
import books.model.interfaces.IBook;
import books.model.interfaces.IPropertiesUsed;
import books.model.interfaces.IShearchMatch;
import books.model.interfaces.ISubDivision;

@XStreamAlias("Book")
public class Book implements IBook {
	
	private static final Logger LOGGER = Logger.getLogger(Book.class);

	private String abv = null;

	private String name = null;

	private String folderPath = null;
	
	private String hierarchy = null;
	
	private boolean load = false;
	
	private boolean save = true;
	
	private List<ISubDivision> subDivisions = new ArrayList<ISubDivision>();

	public Book(String fPtah){
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
		if(!this.subDivisions.isEmpty()){
			return;
		}
		String infoPath = this.folderPath+File.separator+PROPERTIES_FILE_NAME;
		File file = new File(infoPath);
		Properties pr = new Properties();
		
		try {
			pr.load(new FileReader(file));
			this.name = pr.getProperty(KEY_NAME);
			this.abv = pr.getProperty(KEY_ABV);
			this.hierarchy = pr.getProperty(KEY_HIERARCHY);
			boolean autoOpen = Boolean.parseBoolean(pr.getProperty(KEY_AUTO_OPEN, "false"));
			if(autoOpen){
				this.loadSubDivisions();
			}
		} catch (FileNotFoundException e) {
			throw new NoPropetiesException();
		} catch (IOException e) {
			throw new NoPropetiesException(e.getMessage());
		}

	}
	
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

	@Override
	public IShearchMatch[] shearch(String regex) {
		return shearch(regex, null);
	}

	@Override
	public boolean isSave() {
		return this.save;
	}

	@Override
	public void save() {
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

	@Override
	public IShearchMatch[] shearch(String regex, String translation) {
		List<IShearchMatch> listOut = new ArrayList<IShearchMatch>();
		ISubDivision[] tabDiv = getSubDivisions();
		for(ISubDivision division : tabDiv){
			listOut.addAll(Arrays.asList(division.shearch(regex,translation)));
		}
		return	listOut.toArray(new IShearchMatch[0]);
	}

	@Override
	public String getFilePath() {
		return this.folderPath;
	}

	@Override
	public void setFilePath(String path) {
		this.folderPath = path;
	}

	@Override
	public ISubDivision getSubDivision(String name) {
		for(ISubDivision div : this.subDivisions){
			if(div.getName().equals(name)){
				return div;
			}
		}
		return null;
	}

}
