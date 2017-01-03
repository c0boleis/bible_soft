/**
 * 
 */
package books.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import books.model.interfaces.IComment;
import books.model.interfaces.ILoadSaveObject;
import books.model.interfaces.IText;

/**
 * @author Corentin Boleis
 *
 */
public class Comment implements IComment,ILoadSaveObject {
	
	private String name;
	
	private List<IText> textsRefferenced;
	
	private List<IText> commentTexts;
	
	private String comment = null;
	
	private boolean save = false;
	
	private boolean load = true;
	
	private String filePath = null;
	
	public Comment(String name, IText[] ref,String comment) {
		textsRefferenced = new ArrayList<IText>();
		textsRefferenced.addAll(Arrays.asList(ref));
		
		commentTexts = new ArrayList<IText>();
		this.comment = comment;
		this.name = name;
	}
	
	public Comment(String name, IText[] ref,IText[] comment) {
		textsRefferenced = new ArrayList<IText>();
		textsRefferenced.addAll(Arrays.asList(ref));
		
		commentTexts = new ArrayList<IText>();
		commentTexts.addAll(Arrays.asList(comment));
		this.name = name;
	}

	public Comment(String filePath) {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see books.model.IComment#getRefferencedTexts()
	 */
	@Override
	public IText[] getRefferencedTexts() {
		return textsRefferenced.toArray(new IText[0]);
	}

	/* (non-Javadoc)
	 * @see books.model.IComment#getCommentText()
	 */
	@Override
	public IText[] getCommentText() {
		return commentTexts.toArray(new IText[0]);
	}

	/* (non-Javadoc)
	 * @see books.model.IComment#getCommentString()
	 */
	@Override
	public String getCommentString() {
		if(commentTexts.isEmpty()){
			return comment;
		}
		String com = "";
		for(IText text : commentTexts){
			if(com.length()>0){
				com+="\n";
			}
			com+=text.getText(text.getDefaultTranslation());
		}
		return com;
	}

	@Override
	public boolean isSave() {
		return this.save;
	}

	@Override
	public void Save() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLoad() {
		return this.load;
	}

	@Override
	public void load() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFilePath() {
		return this.filePath;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
