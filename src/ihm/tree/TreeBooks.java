package ihm.tree;

import java.awt.Dimension;

import javax.swing.JTree;

public class TreeBooks extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3845549423235655074L;

	private static TreeBooks INSTANCE = new TreeBooks();
	
	private TreeBooks(){
		this.setMinimumSize(new Dimension(50, 150));
	}
	
	public static TreeBooks get(){
		return INSTANCE;
	}
}
