package books.model;

import java.util.ArrayList;
import java.util.List;

import books.model.interfaces.IShearable;
import books.model.interfaces.IShearchMatch;
import books.model.listener.SearcherListener;

public class Searcher {
	
	private static boolean inProcess = false;
	
	private static boolean error = true;
	
	private static List<IShearchMatch> shearchMatchs = new ArrayList<IShearchMatch>();
	
	private static SearcherListener listener;
	
	public static void search(IShearable shearable, String regex){
		if(isInProcess()){
			throw new IllegalAccessError("Une recherche est déjà en cours");
		}
		inProcess = true;
		shearchMatchs.clear();
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				IShearchMatch[] tab = shearable.shearch(regex);
				if(shearchMatchs.size()==tab.length){
					error = false;
				}else{
					error = true;
					shearchMatchs.clear();
				}
				inProcess = false;
				if(listener!=null){
					listener.searchEnd();
				}
			}
		});
		th.start();
	}
	
	public static IShearchMatch[] getSearchResult(){
		if(isInProcess()){
			throw new IllegalAccessError("Une recherche est en cours, il faut attendre la fin de la recherche pour récupérer les résultats");
		}
		return shearchMatchs.toArray(new IShearchMatch[0]);
	}
	
	public synchronized static void addIShearchMatch(IShearchMatch add){
		Searcher.shearchMatchs.add(add);
		if(listener!=null){
			listener.matchesFind(add);
		}
	}
	
	public static int getNbrMatch(){
		return Searcher.shearchMatchs.size();
	}

	/**
	 * @return the inProcess
	 */
	public static boolean isInProcess() {
		return inProcess;
	}

	/**
	 * @return the error
	 */
	public static boolean isError() {
		return error;
	}

	/**
	 * @return the listener
	 */
	public static SearcherListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public static void setListener(SearcherListener listener) {
		Searcher.listener = listener;
	}
}
