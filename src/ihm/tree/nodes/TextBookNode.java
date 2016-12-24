package ihm.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import books.model.IText;

public class TextBookNode extends DefaultMutableTreeNode {

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

}
