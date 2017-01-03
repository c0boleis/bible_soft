package bible_soft.model;

import java.io.IOException;

import books.exceptions.NoPropetiesException;

public interface ILoadSave {
	
	public void load() throws IOException, NoPropetiesException;
	
	public void save() throws IOException;
	
	public boolean isLoad();
	
	public boolean isSave();

}
