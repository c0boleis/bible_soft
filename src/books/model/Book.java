package books.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import books.exceptions.NoPropetiesException;

public class Book implements IBook {

	private String abv = null;

	private String name = null;

	private String folderPath = null;
	
	private String[] hierarchy = null;
	
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
			String ha = pr.getProperty(KEY_HIERARCHY);
			String[] tmp = ha.split(";");
			List<String> listTmp = new ArrayList<String>();
			for(String st : tmp){
				String mot = st.trim();
				if(mot.length()>0){
					listTmp.add(mot);
				}
			}
			this.hierarchy = listTmp.toArray(new String[0]);
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
		File[] files = folder.listFiles();
		for(File file :files){
			ISubDivision div = new SubDivision(file.getAbsolutePath());
			try {
				div.loadInfo();
				this.subDivisions.add(div);
			} catch (NoPropetiesException e) {}
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

}
