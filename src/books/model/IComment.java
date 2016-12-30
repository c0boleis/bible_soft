package books.model;

public interface IComment {
	
	public IText[] getRefferencedTexts();
	
	public IText[] getCommentText();
	
	public String getCommentString();

}
