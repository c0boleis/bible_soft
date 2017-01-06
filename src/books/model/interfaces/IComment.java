package books.model.interfaces;

public interface IComment extends ILoadSaveObject,IReadable,IEditable {
	
	public static final String KEY_REFFERENCED_TEXT = "refference_text";
	
	public static final String KEY_COMMENT_TEXT = "comment_text";
	
	public static final String KEY_COMMENT_STRING = "comment_string";
	
	public static final String ATTRIBUTE_REFFERENCED_TEXT = "refferenced_text";
	
	public static final String ATTRIBUTE_COMMENT_TEXT = "comment_text";
	
	public static final String ATTRIBUTE_COMMENT_STRING = "comment_string";
	
	public String getName();
	
	public IText[] getRefferencedTexts();
	
	public IText[] getCommentText();
	
	public String getCommentString();

}
