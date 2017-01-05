/**
 * 
 */
package books.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import books.model.interfaces.IComment;
import books.model.interfaces.ILoadSaveObject;
import books.model.interfaces.IText;

/**
 * @author Corentin Boleis
 *
 */
public class Comment implements IComment{
	
	private static Logger LOGGER = Logger.getLogger(Comment.class);
	
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
		this.filePath = filePath;
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
	public void save() throws IOException {
		File file = new File(getFilePath());
		BufferedWriter buf = new BufferedWriter(new FileWriter(file));

		buf.write("<"+IComment.KEY_REFFERENCED_TEXT+">\n");
		IText[] tmp = this.getRefferencedTexts();
		for(IText text : tmp){
			buf.write(text.getPath()+"\n");
		}
		buf.write("</"+IComment.KEY_REFFERENCED_TEXT+">\n");


		tmp = this.getCommentText();
		if(tmp.length>0){
			buf.write("<"+IComment.KEY_COMMENT_TEXT+">\n");
			for(IText text : tmp){
				buf.write(text.getPath()+"\n");
			}
			buf.write("</"+IComment.KEY_COMMENT_TEXT+">\n");
		}
		String comm = this.getCommentString();
		buf.write("<"+IComment.KEY_COMMENT_STRING+">\n");
		buf.write(comm+"\n");
		buf.write("</"+IComment.KEY_COMMENT_STRING+">\n");
		buf.close();
		this.save = true;
	}

	@Override
	public boolean isLoad() {
		return this.load;
	}

	@Override
	public void load() throws IOException {
		File file = new File(getFilePath());
		BufferedReader buf = new BufferedReader(new FileReader(file));
		String line = buf.readLine();
		String text = "";
		while(line!=null){
			text+=line+"\n";
			line = buf.readLine();
		}
		buf.close();
		this.textsRefferenced = loadRefferencedText(text);
		this.commentTexts = loadCommentText(text);
		this.comment = loadCommentString(text);
		this.name = file.getName();
		
	}
	
	private List<IText> loadRefferencedText(String text){
		List<IText> listOut = new ArrayList<IText>();
		String textDeb = "<"+IComment.KEY_REFFERENCED_TEXT+">";
		String textEnd = "</"+IComment.KEY_REFFERENCED_TEXT+">";
		int indexDeb = text.indexOf(textDeb);
		if(indexDeb<0){
			return listOut;
		}
		int indexEnd = text.indexOf(textEnd);
		if(indexEnd<0){
			return listOut;
		}
		indexDeb = indexDeb+textDeb.length();
		if(indexDeb>=indexEnd){
			return listOut;
		}
		String info = text.substring(indexDeb, indexEnd).trim();
		String[] tab = info.split("\n");
		for(String st : tab){
			String path = st.trim();
			if(path.length()==0){
				continue;
			}
			IText t = Workspace.getText(path);
			if(t==null){
				LOGGER.warn("L'IText["+st.trim()+"] n'a pas été trouvé.");
			}else{
				listOut.add(t);
			}
		}
		return listOut;
	}
	
	private List<IText> loadCommentText(String text){
		List<IText> listOut = new ArrayList<IText>();
		String textDeb = "<"+IComment.KEY_COMMENT_TEXT+">";
		String textEnd = "</"+IComment.KEY_COMMENT_TEXT+">";
		int indexDeb = text.indexOf(textDeb);
		if(indexDeb<0){
			return listOut;
		}
		int indexEnd = text.indexOf(textEnd);
		if(indexEnd<0){
			return listOut;
		}
		indexDeb = indexDeb+textDeb.length();
		if(indexDeb>=indexEnd){
			return listOut;
		}
		String info = text.substring(indexDeb, indexEnd);
		String[] tab = info.split("\n");
		for(String st : tab){
			if(st.trim().length()==0){
				continue;
			}
			IText t = Workspace.getText(st.trim());
			if(t==null){
				LOGGER.warn("L'IText["+st.trim()+"] n'a pas été trouvé.");
			}else{
				listOut.add(t);
			}
		}
		return listOut;
	}
	
	private String loadCommentString(String text){
		String textDeb = "<"+IComment.KEY_COMMENT_STRING+">";
		String textEnd = "</"+IComment.KEY_COMMENT_STRING+">";
		int indexDeb = text.indexOf(textDeb);
		if(indexDeb<0){
			return null;
		}
		int indexEnd = text.indexOf(textEnd);
		if(indexEnd<0){
			return null;
		}
		indexDeb = indexDeb+textDeb.length();
		if(indexDeb>=indexEnd){
			return null;
		}
		String info = text.substring(indexDeb, indexEnd).trim();
		if(info.length()==0){
			return null;
		}
		return info;
	}

	@Override
	public String getFilePath() {
		return this.filePath;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setFilePath(String path) {
		this.filePath = path;
	}

	@Override
	public String read() {
		return getCommentString();
	}

	@Override
	public String read(String translation) {
		return read();
	}

}
