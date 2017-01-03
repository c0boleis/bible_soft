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

import bible_soft.model.ILoadSave;
import books.exceptions.NoPropetiesException;
import books.model.interfaces.IBook;
import books.model.interfaces.IComment;
import books.model.interfaces.IText;
import books.model.listener.WorkspaceListener;
import ihm.Window;

public class Workspace implements ILoadSave{

	public static final String KEY_BOOKS = "book";

	public static final String KEY_COMMENT = "comment";

	public static final String KEY_SEPARATOR = "=";

	/**
	 * define from the workspace folder
	 */
	public static final String DEFAULT_COMMENT_FOLDER_PATH = "comment";

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
			folderCommentPath = DEFAULT_COMMENT_FOLDER_PATH;
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
		this.fireBookAdd(book);
		return true;
	}

	public boolean addComment(IComment comment){
		if(Workspace.comments.contains(comment)){
			return false;
		}
		Workspace.comments.add(comment);
		this.fireCommentAdd(comment);
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
						Comment com = new Comment(info[1].trim());

						comments.add(com);
					}
				}

			}
			line = buf.readLine();
		}
		buf.close();
		/*
		 * load comment
		 */
		File folderComment = new File(getFolderCommentPath());
		File[] filesComment = folderComment.listFiles();
		for(File com : filesComment){
			IComment newComment = loadComment(com);
			
			
		}
		save = true;
	}
	
	private static IComment loadComment(File file){
		//TODO
		return null;
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
		IBook[] books = Window.getTreeBooks().getBooks();
		for(IBook book : books){
			buf.write(book.getFilePath()+"\n");
		}
		buf.close();
		/*
		 * save the comment
		 */
		for(IComment comment : comments){
			saveComment(comment);
		}
		save = true;
	}

	private static void saveComment(IComment comment) throws IOException{
		String path = getFolderCommentPath()+File.separator+comment.getName();
		File file = new File(path);
		BufferedWriter buf = new BufferedWriter(new FileWriter(file));

		buf.write("<"+IComment.KEY_REFFERENCED_TEXT+">\n");
		IText[] tmp = comment.getRefferencedTexts();
		for(IText text : tmp){
			buf.write(text.getPath()+"\n");
		}
		buf.write("</"+IComment.KEY_REFFERENCED_TEXT+">\n");


		tmp = comment.getCommentText();
		if(tmp.length>0){
			buf.write("<"+IComment.KEY_COMMENT_TEXT+">\n");
			for(IText text : tmp){
				buf.write(text.getPath()+"\n");
			}
			buf.write("</"+IComment.KEY_COMMENT_TEXT+">\n");
		}
		String comm = comment.getCommentString();
		buf.write("<"+IComment.KEY_COMMENT_STRING+">\n");
		buf.write(comm+"\n");
		buf.write("</"+IComment.KEY_COMMENT_STRING+">\n");

		buf.close();
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

}
