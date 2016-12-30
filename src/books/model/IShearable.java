package books.model;

public interface IShearable {
	
	public IShearchMatch[] shearch(String regex);
	
	public IShearchMatch[] shearch(String regex,String translation);

}
