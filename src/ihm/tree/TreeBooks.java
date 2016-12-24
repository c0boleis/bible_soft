package ihm.tree;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import books.model.IBook;
import ihm.tree.nodes.BookNode;

public class TreeBooks extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3845549423235655074L;

	private List<IBook> books = new ArrayList<IBook>();

	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

	private TreeBooksModel model;
	
	private TreeBooksListeners listeners;

	public TreeBooks(){
		super();
		this.setMinimumSize(new Dimension(50, 150));
		this.setPreferredSize(new Dimension(50, 150));
		this.setModel(getPersoModel());
		this.setRootVisible(true);
		this.addTreeSelectionListener(getListeners());
		this.addMouseListener(getListeners());
	}

	public void addBook(IBook book){
		BookNode node = new BookNode(book);
		root.add(node);
		((DefaultTreeModel) this.getModel()).reload(root);
		this.repaint();
	}

	public void removeBook(IBook book){
		//TODO
	}

	public TreeBooksModel getPersoModel() {
		if(model==null){
			model = new TreeBooksModel(this,root);
		}
		return model;
	}

	/**
	 * @return the listeners
	 */
	public TreeBooksListeners getListeners() {
		if(listeners == null){
			listeners = new TreeBooksListeners(this);
		}
		return listeners;
	}

}
