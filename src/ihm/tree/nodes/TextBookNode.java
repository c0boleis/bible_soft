package ihm.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import books.IReadable;
import books.model.IShearable;
import books.model.IShearchMatch;
import books.model.IText;

public class TextBookNode extends DefaultMutableTreeNode 
implements IReadable,IShearable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8285219244193209931L;
	
	private IText text;
	
	public TextBookNode(IText t){
		super();
		this.text = t;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString(){
		return this.text.getName();
	}

	@Override
	public String read() {
		return this.text.read();
	}

	@Override
	public IShearchMatch[] shearch(String regex) {
		return this.text.shearch(regex);
	}

	@Override
	public IShearchMatch[] shearch(String regex, String translation) {
		return this.text.shearch(regex, translation);
	}

	@Override
	public String read(String translation) {
		return this.text.read(translation);
	}

}
