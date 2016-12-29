package books.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import books.model.shearch.TextMatch;

/**
 * 
 * @author Corentin Boleis
 *
 */
public class Text implements IText, IOrderedObject {
	
	private String text;
	
	private String name;
	
	private String filePath;
	
	private int order = -1;
	
	private boolean load = false;
	
	private boolean save = false;
	
	private ISubDivision subDivision;


	public Text(ISubDivision div,String text,String name) {
		if(div==null){
			throw new NullPointerException(
					"la subdivition d'un text ne peut pas etre null");
		}
		this.subDivision = div;
		this.text = text;
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
	public String getText() {
		return this.text;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IText#setText()
	 */
	@Override
	public void setText(String t) {
		this.text = t;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	@Override
	public String read() {
		return this.text;
	}

	@Override
	public IShearchMatch[] shearch(String regex) {
		List<IShearchMatch> listOut = new ArrayList<IShearchMatch>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		// Check all occurrences
		while (matcher.find()) {
			listOut.add(new TextMatch(matcher.group(), this));
		}
		return	listOut.toArray(new IShearchMatch[0]);
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
		this.text ="";
		while(line!=null){
			this.text+=line.trim()+"\n";
			line = buf.readLine();
		}
		buf.close();
		this.load = true;
	}

	@Override
	public ISubDivision getSubDivision() {
		// TODO Auto-generated method stub
		return null;
	}

}
