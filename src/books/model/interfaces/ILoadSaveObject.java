package books.model.interfaces;

import java.io.IOException;

public interface ILoadSaveObject {
	
	public boolean isSave();
	
	public void save() throws IOException;
	
	public void saveAll() throws IOException;
	
	public boolean isLoad();
	
	public void load() throws IOException;
	
	public String getFilePath();
	
	public void setFilePath(String path);

}