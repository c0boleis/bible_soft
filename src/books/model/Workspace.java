package books.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.apache.log4j.Logger;

import bible_soft.model.ILoadSave;
import books.exceptions.NoPropetiesException;
import books.model.interfaces.IBook;
import books.model.interfaces.IComment;
import books.model.interfaces.ISubDivision;
import books.model.interfaces.ISubDivisonContainer;
import books.model.interfaces.IText;
import books.model.listener.WorkspaceListener;
import ihm.Window;

public class Workspace implements ILoadSave{
	
	private static final Logger LOGGER = Logger.getLogger(Workspace.class);

	public static final String KEY_BOOKS = "book";

	public static final String KEY_COMMENT = "comment";

	public static final String KEY_SEPARATOR = "=";
	
	public static final String KEY_WORkSPACE = "{workspace}";
	/**
	 * define from the workspace folder
	 */
	public static final String DEFAULT_COMMENT_FOLDER_PATH = KEY_WORkSPACE+File.separator+"comment";

	private static String folderCommentPath = null;

	private static String folder_path;

	private static final String FILE_BOOK_NAME = "books.txt";

	private static List<IBook> books = new ArrayList<IBook>();

	private static List<IComment> comments = new ArrayList<IComment>();

	private static List<WorkspaceListener> listeners = new ArrayList<WorkspaceListener>();

	private static boolean save = false;

	private static Workspace INSTANCE = new Workspace();

	public static Workspace get(){
		return INSTANCE;
	}

	/**
	 * @return the folder_comment_path
	 */
	public static String getFolderCommentPath() {
		if(folderCommentPath == null){
			folderCommentPath = 
					DEFAULT_COMMENT_FOLDER_PATH
					.replace(KEY_WORkSPACE, get().getFolder_path())
					.replace(File.separator+File.separator, File.separator);
		}
		return folderCommentPath;
	}

	/**
	 * @param folder_comment_path the folder_comment_path to set
	 */
	public static void setFolder_comment_path(String folder_comment_path) {
		Workspace.folderCommentPath = folder_comment_path;
	}

	private boolean allowListener = true;

	private Workspace() {
	}

	public boolean addBook(IBook book){
		if(Workspace.books.contains(book)){
			return false;
		}
		Workspace.books.add(book);
		Workspace.save = false;
		this.fireBookAdd(book);
		this.fireSaveChange();
		return true;
	}

	public boolean addComment(IComment comment){
		if(Workspace.comments.contains(comment)){
			return false;
		}
		Workspace.comments.add(comment);
		Workspace.save = false;
		this.fireCommentAdd(comment);
		this.fireSaveChange();
		return true;
	}

	public void addWorkspaceListener(WorkspaceListener listener){
		if(!Workspace.listeners.contains(listener)){
			Workspace.listeners.add(listener);
		}
	}

	private void fireBookAdd(IBook book){
		WorkspaceListener[] listeners = Workspace.get().getWorkspaceListeners();
		for(WorkspaceListener listener : listeners){
			listener.bookAdd(book);
		}
	}

	private void fireBookRemove(IBook book){
		WorkspaceListener[] listeners = Workspace.get().getWorkspaceListeners();
		for(WorkspaceListener listener : listeners){
			listener.bookRemove(book);
		}
	}

	private void fireCommentAdd(IComment comment){
		WorkspaceListener[] listeners = Workspace.get().getWorkspaceListeners();
		for(WorkspaceListener listener : listeners){
			listener.commentAdd(comment);
		}
	}

	private void fireCommentRemove(IComment comment){
		WorkspaceListener[] listeners = Workspace.get().getWorkspaceListeners();
		for(WorkspaceListener listener : listeners){
			listener.commentRemove(comment);
		}
	}
	
	private void fireSaveChange(){
		WorkspaceListener[] listeners = Workspace.get().getWorkspaceListeners();
		for(WorkspaceListener listener : listeners){
			listener.saveChange(save);
		}
	}

	public IBook[] getBooks(){
		return Workspace.books.toArray(new IBook[0]); 
	}

	public IComment[] getComments(){
		return Workspace.comments.toArray(new IComment[0]);
	}

	/**
	 * @return the folder_path
	 */
	public String getFolder_path() {
		return folder_path;
	}

	public File getFolderComment(){
		File folderComment = new File(getFolderCommentPath());
		if(!folderComment.exists()){
			folderComment.mkdirs();
		}
		return folderComment;
	}

	public WorkspaceListener[] getWorkspaceListeners(){
		return Workspace.listeners.toArray(new WorkspaceListener[0]);
	}

	/**
	 * @return the allowListener
	 */
	public boolean isAllowListener() {
		return allowListener;
	}

	@Override
	public boolean isLoad() {
		return !Workspace.comments.isEmpty() || !Workspace.books.isEmpty();
	}

	@Override
	public boolean isSave() {
		return Workspace.save;
	}

	/*
	 * (non-Javadoc)
	 * @see bible_soft.model.ILoadSave#load()
	 */
	@Override
	public void load() throws IOException, NoPropetiesException{
		if(get().isLoad()){
			return;
		}
		File folder = new File(getFolder_path());
		if(!folder.exists()){
			folder.mkdirs();
		}
		File file = new File(getFolder_path()+File.separator+FILE_BOOK_NAME);
		if(!file.exists()){
			file.createNewFile();
		}

		BufferedReader buf = new BufferedReader(new FileReader(file));
		String line = buf.readLine();
		while(line!=null){
			String path = line.trim();
			if(path.length()>0){
				String[] info = path.split(KEY_SEPARATOR);
				if(info.length==2){
					if(info[0].trim().equals(KEY_BOOKS)){
						Book book = new Book(info[1].trim());
						book.loadInfo();
						books.add(book);
					}else if(info[0].trim().equals(KEY_COMMENT)){
						Comment newComment = new Comment(info[1].trim());
						try{
							newComment.load();
							if(!newComment.isLoad()){
								LOGGER.warn("le commentaire : "+info[1].trim()+" n'a pas été chargé.");
							}
							Workspace.comments.add(newComment);
						}catch(IOException e){
							LOGGER.error("le commentaire : "+info[1].trim()+" n'a pas été chargé.", e);
						}
					}
				}

			}
			line = buf.readLine();
		}
		buf.close();
		save = true;
		this.fireSaveChange();
	}

	public void removeWorkspaceListener(WorkspaceListener listener){
		Workspace.listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see bible_soft.model.ILoadSave#save()
	 */
	@Override
	public void save() throws IOException{
		/*
		 * save the books path
		 */
		BufferedWriter buf = new BufferedWriter(new FileWriter(new File(getFolder_path()+File.separator+FILE_BOOK_NAME)));
		for(IBook book : Workspace.books){
			buf.write(KEY_BOOKS+KEY_SEPARATOR+book.getFilePath()+"\n");
		}
		/*
		 * the get folder comment is use to create the folder
		 * if necessary
		 */
		getFolderComment();
		for(IComment com : Workspace.comments){
			if(com.getFilePath()==null){
				com.setFilePath(getFolderCommentPath()+File.separator+com.getName());
			}
			buf.write(KEY_COMMENT+KEY_SEPARATOR+com.getFilePath()+"\n");
			try{
				com.save();
				if(!com.isSave()){
					LOGGER.warn("Le commentaire["+com.getName()
					+"] : "+com.getFilePath()+" n'a pas été enregistré.");
				}
			}catch(IOException e){
				LOGGER.error("Le commentaire["+com.getName()
				+"] : "+com.getFilePath()+" n'a pas été enregistré.",e);
			}
			
		}
		buf.close();
		save = true;
		this.fireSaveChange();
	}

	/**
	 * @param allowListener the allowListener to set
	 */
	public void setAllowListener(boolean allowListener) {
		this.allowListener = allowListener;
	}

	/**
	 * @param folder_path the folder_path to set
	 */
	public void setFolder_path(String folder_path) {
		Workspace.folder_path = folder_path;
	}
	
	public static IText getText(String path){
		String[] info = path.split("/");
		ISubDivisonContainer div = getBook(info[0]);
		for(int index=1;index<info.length;index++){
			if(index==(info.length-1)){
				return ((ISubDivision) div).getText(info[index]);
			}
			else{
				div = div.getSubDivision(info[index]);
			}
		}
		return null;
	}
	
	public static IBook getBook(String name){
		for(IBook book : Workspace.books){
			if(book.getName().equals(name)){
				return book;
			}
		}
		return null;
	}

}
