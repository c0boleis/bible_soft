package books.model.shearch;

import books.model.IShearchMatch;
import books.model.IText;

public class TextMatch implements IShearchMatch{
	
	private String wordMatch;
	
	private IText objectMatch;

	public TextMatch(String word,IText text) {
		this.wordMatch = word;
		this.objectMatch = text;
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IShearchMatch#getObjectMatch()
	 */
	@Override
	public IText getObjectMatch() {
		return objectMatch;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.objectMatch.getRefference()+"\"" +wordMatch+"\"\n"+this.objectMatch.getText();
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IShearchMatch#getMatchWord()
	 */
	@Override
	public String getMatchWord() {
		return this.wordMatch;
	}

}
