package ihm.tree.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import bible_soft.model.ILoadSaveOld;
import books.exceptions.NoPropetiesException;
import books.model.interfaces.IBook;
import books.model.interfaces.ILoadSaveObject;
import books.model.interfaces.IShearable;
import books.model.interfaces.IShearchMatch;
import books.model.interfaces.ISubDivision;
import ihm.Window;

public class BookNode extends DefaultMutableTreeNode 
implements IShearable, ILoadSaveObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 920517207020679907L;
	
	private IBook book;
	
	public BookNode( IBook b) {
		super();
		this.book = b;
//		this.setAllowsChildren(true);
		init(false);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#isLeaf()
	 */
//	@Override
//	public boolean isLeaf() {
//		return !book.isEmpty();
//	}
	
	public final void init(){
		init(true);
	}

	public final void init(boolean load){
		if(this.getChildCount()>0){
			return;
		}
		if(load){
			this.book.loadSubDivisions();
		}
		ISubDivision[] tab = this.book.getSubDivisions();
		SubBookDivisionNode node =null;
		for(ISubDivision div : tab){
			node = new SubBookDivisionNode(div);

			Window.getTreeBooks().getTreeBooksModel().insertNodeInto(node, this, this.getChildCount());
		}
	}
	
	public final void refresh(){
		int size = getChildCount();
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for(int index = 0; index<size;index++){
			TreeNode node = getChildAt(index);
			nodes.add(node);
		}
		for(TreeNode node : nodes){
			Window.getTreeBooks().getTreeBooksModel().removeNodeFromParent((MutableTreeNode) node);
		}
		Window.getTreeBooks().getTreeBooksModel().reload(this);
		init(false);
		Window.getTreeBooks().getTreeBooksModel().reload(this);
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

	@Override
	public IShearchMatch[] shearch(String regex, String translation) {
		return this.book.shearch(regex, translation);
	}

	@Override
	public void load() throws IOException {
		this.book.load();
		
	}

	@Override
	public void save() throws IOException {
		this.book.save();
		
	}

	@Override
	public boolean isLoad() {
		return this.book.isLoad();
	}

	@Override
	public boolean isSave() {
		return this.book.isSave();
	}

	@Override
	public String getFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilePath(String path) {
		// TODO Auto-generated method stub
		
	}

}
