package lancement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import books.model.interfaces.IPropertiesUsed;
import books.model.interfaces.IText;

public class TransformationFichierText {

	public static final String TRAD_NAME = "nouvelle traduction";
	
	private static final boolean done = true;

	public static void main(String[] args) {
		if(done){
			return;
		}
		File folder = new File("/home/bata/livres");
		doAction(folder);
	}

	public static final void doAction(File fi){
		if(!fi.exists()){
			return;
		}
		if(fi.getName().contains(".git")){
			return;
		}
		if(fi.isDirectory()){
			File[] files = fi.listFiles();
			for(File file : files){
				doAction(file);
			}
		}else{
			if(!fi.getName().contains(IPropertiesUsed.PROPERTIES_FILE_NAME)){
				transform(fi);
			}
		}
	}
	public static final void transform(File file){
		System.out.println(file.getPath());
		String text = read(file);
		if(text!=null){
			write(file, text);
		}
	}

	public static final String read(File file){
		try {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			String line = buf.readLine();
			String text = "";
			while(line!=null){
				text+=line.trim()+"\n";
				line = buf.readLine();
			}
			buf.close();
			return text;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final void write(File file,String text){
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(file));
			buf.write("<"+IText.KEY_TRANSLATIONS+">"
					+TRAD_NAME
					+"</"+IText.KEY_TRANSLATIONS+">\n");
			
			buf.write("<"+IText.KEY_TEXT+">"
					+ "<"+TRAD_NAME+">"
					+text
					+"</"+TRAD_NAME+">"
					+"</"+IText.KEY_TEXT+">");
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
