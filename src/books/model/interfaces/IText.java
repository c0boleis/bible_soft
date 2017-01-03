package books.model.interfaces;

/**
 * 
 * @author Corentin Boleis
 * @version 0.1
 * @since 0.1
 * 
 * The smallest division, this one contains the text
 *
 */
public interface IText extends IOrderedObject,IReadable,
IShearable,ILoadSaveObject{
	
	public static final String KEY_TRANSLATIONS = "translations";
	
	public static final String TRANSLATIONS_SEPARATOR = ";";
	
	public static final String KEY_TEXT = "text";
	
	public static final String NAME_DEFAULT_TRANSLATION = "default";

	public String getName();
	
	public String[] getTraductions();
	
	public String getDefaultTranslation();
	
	public String getText(String trad);
	
	public void setText(String t,String trad);

	public String getRefference();
	
	public String getPath();
	
	public ISubDivision getSubDivision();
	
	public IBook getBook();

}
