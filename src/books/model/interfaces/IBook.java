package books.model.interfaces;

public interface IBook extends IPropertiesUsed,
ISubDivisonContainer, IShearable,ILoadSaveObject{
	
	public String getAbv();
	
	public String getName();
	
}
