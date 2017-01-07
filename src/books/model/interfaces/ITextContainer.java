package books.model.interfaces;

public interface ITextContainer extends IReadable{
	
	public IText[] getTexts();

	public void sortTexts();
	
	public void loadTexts();
	
	public boolean isTextEmpty();

}
