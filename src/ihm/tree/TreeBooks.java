package ihm.tree;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

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
	
	private TreeBooksListeners treeBooksListeners;
	
	private TreeBooksPopupMenu treeBooksPopupMenu;

	public TreeBooks(){
		super();
		this.setMinimumSize(new Dimension(50, 150));
		this.setModel(getPersoModel());
		this.setRootVisible(true);
		this.addTreeSelectionListener(getTreeBooksListeners());
		this.addMouseListener(getTreeBooksListeners());
		this.setComponentPopupMenu(getTreeBooksPopupMenu());
	}

	public void addBook(IBook book){
		if(books.contains(book)){
			return;
		}
		books.add(book);
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
	public TreeBooksListeners getTreeBooksListeners() {
		if(treeBooksListeners == null){
			treeBooksListeners = new TreeBooksListeners(this);
		}
		return treeBooksListeners;
	}

	public TreeNode getRootNode() {
		return root;
	}

	/**
	 * @return the treeBooksPopupMenu
	 */
	public TreeBooksPopupMenu getTreeBooksPopupMenu() {
		if(treeBooksPopupMenu == null){
			treeBooksPopupMenu = new TreeBooksPopupMenu();
		}
		return treeBooksPopupMenu;
	}

	/**
	 * @return the books
	 */
	public IBook[] getBooks() {
		return books.toArray(new IBook[0]);
	}
}
