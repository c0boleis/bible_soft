package ihm.workspace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import books.exceptions.NoPropetiesException;
import books.model.Book;
import books.model.IBook;
import ihm.Window;

public class Workspace {
	
	private String folder_path;
	
	private static final String FILE_BOOK_NAME = "books.txt";
	
	public Workspace(String fol) {
		this.setFolder_path(folder_path);
	}
	
	public Workspace() {
	}
	
	public void load() throws IOException, NoPropetiesException{
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
				Book book = new Book(path);
				book.loadInfo();
				Window.getTreeBooks().addBook(book);
			}
			line = buf.readLine();
		}
		buf.close();
	}
	
	public void save() throws IOException{
		BufferedWriter buf = new BufferedWriter(new FileWriter(new File(getFolder_path()+File.separator+FILE_BOOK_NAME)));
		IBook[] books = Window.getTreeBooks().getBooks();
		for(IBook book : books){
			buf.write(book.getFolderName()+"\n");
		}
		buf.close();
	}

	/**
	 * @return the folder_path
	 */
	public String getFolder_path() {
		return folder_path;
	}

	/**
	 * @param folder_path the folder_path to set
	 */
	public void setFolder_path(String folder_path) {
		this.folder_path = folder_path;
	}

}
