package books.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import books.exceptions.NoPropetiesException;

public class SubDivision implements ISubDivision {

	private String abv = null;

	private String name = null;

	private String folderPath = null;
	
	private String hierarchy = null;
	
	private List<IText> texts = new ArrayList<IText>();
	
	private List<ISubDivision> subDivisions = new ArrayList<ISubDivision>();

	public SubDivision(String fPtah){
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
		} catch (FileNotFoundException e) {
			throw new NoPropetiesException();
		} catch (IOException e) {
			throw new NoPropetiesException(e.getMessage());
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
		File[] files = folder.listFiles();
		for(File file :files){
			if(file.isDirectory()){
				SubDivision sub = new SubDivision(file.getAbsolutePath());
				try {
					sub.loadInfo();
					subDivisions.add(sub);
				} catch (NoPropetiesException e) {}
			}
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setOrder(int order) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTextEmpty() {
		// TODO Auto-generated method stub
		return texts.isEmpty();
	}

	@Override
	public String getHierarchy() {
		return this.hierarchy;
	}

}
