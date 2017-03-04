package books.model.interfaces;

import books.model.listener.TextAddedListener;

public interface IBook extends IPropertiesUsed,
ISubDivisonContainer, IShearable,ILoadSaveObject{
	
	public String getAbv();
	
	public String getName();
	
	public String setAbv(String newAbv);
	
	public String setName(String newName);
	
	public int calculNbrOfTexts();
	
	public void addTextAddedListener(TextAddedListener listener);
	
	public void removeTextAddedListener(TextAddedListener listener);
	
	public TextAddedListener[] getTextAddedListeners();
	
}
