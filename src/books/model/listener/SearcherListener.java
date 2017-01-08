package books.model.listener;

import books.model.interfaces.IShearchMatch;

public interface SearcherListener {
	
	public void matchesFind(IShearchMatch match);
	
	public void searchEnd();

}
