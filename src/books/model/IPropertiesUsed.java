package books.model;

import books.exceptions.NoPropetiesException;

public interface IPropertiesUsed {
	
	public static final String PROPERTIES_FILE_NAME = "info.properties";
	
	public static final String KEY_NAME = "name";
	
	public static final String KEY_ABV = "abv";
	
	public static final String KEY_HIERARCHY = "hierarchy";
	
	public static final String KEY_ORDER = "order";
	
	public static final String KEY_AUTO_OPEN = "auto_open";
	
	public void loadInfo() throws NoPropetiesException;

}
