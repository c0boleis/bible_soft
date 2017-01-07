package books.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import books.exceptions.NoPropetiesException;
import books.model.interfaces.IBook;
import books.model.interfaces.IPropertiesUsed;
import books.model.interfaces.IShearchMatch;
import books.model.interfaces.ISubDivision;
import books.model.interfaces.IText;
import books.model.listener.OrderedObjectListener;

public class SubDivision implements ISubDivision {
	
	private static final Logger LOGGER = Logger.getLogger(ISubDivision.class);

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
	
	private boolean autoOpen = false;
	
	private List<OrderedObjectListener> orderedObjectListeners = new ArrayList<OrderedObjectListener>();

	private boolean allowListener = false;
	
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
	public Properties loadInfo() throws NoPropetiesException {
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
			this.hierarchy = pr.getProperty(KEY_HIERARCHY,DEFAULT_HIERARCHY);
			this.order = Integer.parseInt(pr.getProperty(KEY_ORDER, "-1"));
			autoOpen = Boolean.parseBoolean(pr.getProperty(KEY_AUTO_OPEN, "false"));
			if(autoOpen){
				this.loadSubDivisions();
				this.loadTexts();
			}
			return pr;
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
		/*
		 * we sort the subDivision
		 */
		this.sortSubdivisions();
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
		int oldOrder = this.order;
		this.order = order;
		if(oldOrder!=this.order){
			if(this.getSubDivision()!=null){
				this.getSubDivision().sortSubdivisions();
			}else{
				this.getBook().sortSubdivisions();
			}
			
			save = false;
			this.save();
			this.fireOrderChange(this.order, oldOrder);
		}
		
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
		this.sortTexts();
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
		return read(null);
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
		try {
			saveInfo();
			this.save = true;
		} catch (NoPropetiesException e) {
			LOGGER.error("les informations n'ont pas été enregistrés", e);
		}
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
		LOGGER.info("["+name+"] load =>"+loadTest);
		this.load = loadTest;
	}

	@Override
	public ISubDivision getSubDivision() {
		return this.subDivision;
	}

	@Override
	public IBook getBook() {
		if(this.subDivision!=null){
			return this.subDivision.getBook();
		}
		return this.book;
	}

	@Override
	public String read(String trad) {
		String text = "";
		boolean first = true;
		IText[] texts = this.getTexts();
		for(IText t : texts){
			if(!first){
				text+="\n";
			}
			first = false;
			if(trad!=null){
				text+=t.getName()+":\t"+t.getText(trad);
			}else{
				text+=t.getName()+":\t"+t.getText(t.getDefaultTranslation());
			}
			
		}
		return text;
	}

	@Override
	public IShearchMatch[] shearch(String regex, String translation) {
		List<IShearchMatch> listOut = new ArrayList<IShearchMatch>();
		ISubDivision[] tabDiv = getSubDivisions();
		for(ISubDivision division : tabDiv){
			listOut.addAll(Arrays.asList(division.shearch(regex,translation)));
		}
		IText[] tabText = getTexts();
		for(IText text : tabText){
			listOut.addAll(Arrays.asList(text.shearch(regex,translation)));
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
		if(this.subDivisions.isEmpty()){
			try {
				load();
			} catch (IOException e) {
				LOGGER.error("get subDivision avec son nom", e);
			}
		}
		for(ISubDivision div : this.subDivisions){
			if(div.getName().equals(name)){
				return div;
			}
		}
		return null;
	}

	@Override
	public IText getText(String name) {
		for(IText te : this.texts){
			if(te.getName().equals(name)){
				return te;
			}
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return getPath();
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.interfaces.ISubDivision#getPath()
	 */
	@Override
	public String getPath() {
		String pt = this.getName();
		ISubDivision div = getSubDivision();
		while(div!=null){
			String name = div.getName();
			pt=name+"/"+pt;
			div = div.getSubDivision();
		}
		pt=getBook().getName()+"/"+pt;
		return pt;
	}

	@Override
	public Properties saveInfo() throws NoPropetiesException {
		if(this.folderPath==null){
			throw new NullPointerException();
		}
		String infoPath = this.folderPath+File.separator+PROPERTIES_FILE_NAME;
		File file = new File(infoPath);
		Properties pr = new Properties();
		try {
			pr.load(new FileReader(file));
			pr.setProperty(KEY_NAME,this.name);
			if(this.abv!=null){
				pr.setProperty(KEY_ABV,this.abv);
			}
			pr.setProperty(KEY_HIERARCHY,this.hierarchy);
			if(this.order>=0){
				pr.setProperty(KEY_ORDER, String.valueOf(this.order));
			}
			pr.setProperty(KEY_AUTO_OPEN, this.autoOpen?"true":"false");
			pr.store(new FileWriter(file), null);
			return pr;
		} catch (FileNotFoundException e) {
			throw new NoPropetiesException();
		} catch (IOException e) {
			throw new NoPropetiesException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new NoPropetiesException("L'ordre est male écrit dans les propriétés");
		}
	}

	@Override
	public OrderedObjectListener[] getOrderListeners() {
		return this.orderedObjectListeners.toArray(new OrderedObjectListener[0]);
	}

	@Override
	public void addOrderListener(OrderedObjectListener listener) {
		if(!this.orderedObjectListeners.contains(listener)){
			this.orderedObjectListeners.add(listener);
		}
		
	}

	@Override
	public void removeOrderListener(OrderedObjectListener listener) {
		this.orderedObjectListeners.remove(listener);
		
	}

	@Override
	public boolean allowListener() {
		return this.allowListener;
	}

	@Override
	public void setAllowListener(boolean allow) {
		this.allowListener = allow;
	}
	
	private void fireOrderChange(int newOrder,int oldOrder){
		if(!this.allowListener){
			return;
		}
		OrderedObjectListener[] tab = getOrderListeners();
		for(OrderedObjectListener listener : tab){
			listener.orderChange(newOrder, oldOrder);
		}
	}

	@Override
	public void sortTexts() {
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

}
