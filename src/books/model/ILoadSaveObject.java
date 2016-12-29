package books.model;

import java.io.IOException;

public interface ILoadSaveObject {
	
	public boolean isSave();
	
	public void Save() throws IOException;
	
	public boolean isLoad();
	
	public void load() throws IOException;

}
