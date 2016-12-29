package ihm.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import books.model.IBook;
import books.model.IShearable;
import books.model.IShearchMatch;
import books.model.ISubDivision;
import ihm.Window;

public class BookNode extends DefaultMutableTreeNode implements IShearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 920517207020679907L;
	
	private IBook book;
	
	public BookNode( IBook b) {
		super();
		this.book = b;
//		this.setAllowsChildren(true);
//		init();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#isLeaf()
	 */
//	@Override
//	public boolean isLeaf() {
//		return !book.isEmpty();
//	}

	public final void init(){
		if(this.getChildCount()>0){
			return;
		}
		this.book.loadSubDivisions();
		ISubDivision[] tab = this.book.getSubDivisions();
		SubBookDivisionNode node =null;
		for(ISubDivision div : tab){
			node = new SubBookDivisionNode(div);

			Window.getTreeBooks().getPersoModel().insertNodeInto(node, this, this.getChildCount());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString(){
		return book.getName();
	}

	@Override
	public IShearchMatch[] shearch(String regex) {
		return this.book.shearch(regex);
	}

}
