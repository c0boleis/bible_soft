package books.model.interfaces;

public interface IComment {
	
	public String KEY_REFFERENCED_TEXT = "refference_text";
	
	public String KEY_COMMENT_TEXT = "comment_text";
	
	public String KEY_COMMENT_STRING = "comment_string";
	
	public String getName();
	
	public IText[] getRefferencedTexts();
	
	public IText[] getCommentText();
	
	public String getCommentString();

}
