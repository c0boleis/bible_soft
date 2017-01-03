package books.model.shearch;

import books.model.interfaces.IShearchMatch;
import books.model.interfaces.IText;

public class TextMatch implements IShearchMatch{
	
	private String wordMatch;
	
	private IText objectMatch;
	
	private String translationName;

	public TextMatch(String word,IText text,String translation) {
		this.wordMatch = word;
		this.objectMatch = text;
		this.translationName = translation;
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
		return this.objectMatch.getRefference()+"\"" +wordMatch+"\"\n"+this.objectMatch.getText(this.translationName);
	}

	/*
	 * (non-Javadoc)
	 * @see books.model.IShearchMatch#getMatchWord()
	 */
	@Override
	public String getMatchWord() {
		return this.wordMatch;
	}

	@Override
	public String getTranslationName() {
		return this.translationName;
	}

}
