package books.model.interfaces;

public interface IEditable {
	
	public String[] getEditableAttributes();
	
	public String isEditable(String key,String value);
	
	public boolean edit(String key,String value);

}
