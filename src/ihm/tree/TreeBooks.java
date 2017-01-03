package ihm.tree;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import books.model.Workspace;
import books.model.interfaces.IBook;
import books.model.interfaces.IComment;
import books.model.listener.WorkspaceListener;
import ihm.tree.nodes.BookNode;

public class TreeBooks extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3845549423235655074L;

	private List<IBook> books = new ArrayList<IBook>();

	private DefaultMutableTreeNode root;
	
	private DefaultMutableTreeNode nodeBook;
	
	private DefaultMutableTreeNode nodeComment;

	private TreeBooksModel model;
	
	private TreeBooksListeners treeBooksListeners;
	
	private TreeBooksPopupMenu treeBooksPopupMenu;
	
	private WorkspaceListener workspaceListener;

	public TreeBooks(){
		super();
		this.setMinimumSize(new Dimension(50, 150));
		this.setModel(getTreeBooksModel());
		this.setRootVisible(false);
		this.addTreeSelectionListener(getTreeBooksListeners());
		this.addMouseListener(getTreeBooksListeners());
		this.setComponentPopupMenu(getTreeBooksPopupMenu());
	}

	private void addBook(IBook book){
		if(books.contains(book)){
			return;
		}
		books.add(book);
		BookNode node = new BookNode(book);
		((DefaultTreeModel) this.getModel()).insertNodeInto(node, getNodeBook(), getNodeBook().getChildCount());
		((DefaultTreeModel) this.getModel()).reload(root);
		this.repaint();
	}

	public void removeBook(IBook book){
		//TODO
	}

	public TreeBooksModel getTreeBooksModel() {
		if(model==null){
			model = new TreeBooksModel(this,getNodeRoot());
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

	/**
	 * @return the workspaceListener
	 */
	private WorkspaceListener getWorkspaceListener() {
		if(workspaceListener == null){
			workspaceListener = new WorkspaceListener() {
				
				@Override
				public void commentRemove(IComment commentRemove) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void commentAdd(IComment commentAdd) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void bookRemove(IBook bookRemove) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void bookAdd(IBook bookAdd) {
					addBook(bookAdd);
					
				}
			};
		}
		return workspaceListener;
	}

	/**
	 * @return the nodeBook
	 */
	private DefaultMutableTreeNode getNodeBook() {
		if(nodeBook == null){
			nodeBook = new DefaultMutableTreeNode("Livres");
		}
		return nodeBook;
	}

	/**
	 * @return the nodeComment
	 */
	private DefaultMutableTreeNode getNodeComment() {
		if(nodeComment == null){
			nodeComment = new DefaultMutableTreeNode("Commentaires");
		}
		return nodeComment;
	}
	
	private DefaultMutableTreeNode getNodeRoot() {
		if(root == null){
			root = new DefaultMutableTreeNode("root");
			root.add(getNodeBook());
			root.add(getNodeComment());
		}
		return root;
	}

	public void initData() {
		IBook[] tabBook = Workspace.get().getBooks();
		for(IBook book : tabBook){
			addBook(book);
		}
		
	}

	public void conectListeners() {
		Workspace.get().addWorkspaceListener(getWorkspaceListener());
		
	}
}
