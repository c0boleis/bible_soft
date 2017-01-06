package ihm.tree.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import bible_soft.model.ILoadSaveOld;
import books.model.SubDivision;
import books.model.interfaces.ILoadSaveObject;
import books.model.interfaces.IReadable;
import books.model.interfaces.IShearable;
import books.model.interfaces.IShearchMatch;
import books.model.interfaces.ISubDivision;
import books.model.interfaces.IText;
import ihm.Window;

public class SubBookDivisionNode extends DefaultMutableTreeNode 
implements IReadable,IShearable,ILoadSaveObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3266881184618907401L;

	private ISubDivision subDivision;

	private DefaultMutableTreeNode nodeSubDivision;

	private DefaultMutableTreeNode nodeText;

	public SubBookDivisionNode(ISubDivision div){
		super();
		this.subDivision = div;
		init(false);
	}
	
	public final void init(){
		init(true);
	}

	public final void init(boolean load){
		if(this.getChildCount()>0){
			return;
		}
		if(load){
			this.subDivision.loadSubDivisions();
		}
		
		ISubDivision[] tab = this.subDivision.getSubDivisions();
		if(tab.length>0){
			Window.getTreeBooks().getTreeBooksModel().insertNodeInto(getNodeSubDivision(), this, 0);
			for(ISubDivision div : tab){
				SubBookDivisionNode node = new SubBookDivisionNode(div);
				Window.getTreeBooks().getTreeBooksModel().insertNodeInto(node, getNodeSubDivision(), getNodeSubDivision().getChildCount());
			}
		}
		this.subDivision.loadTexts();
		IText[] tab1 = this.subDivision.getTexts();
		if(tab1.length>0){
			Window.getTreeBooks().getTreeBooksModel().insertNodeInto(getNodeText(), this, this.getChildCount());
			for(IText text : tab1){
				Window.getTreeBooks().getTreeBooksModel().insertNodeInto(new TextBookNode(text), getNodeText(),  getNodeText().getChildCount());
			}
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
	
	/**
	 * @return the nodeText
	 */
	private DefaultMutableTreeNode getNodeText() {
		if(nodeText == null){
			nodeText = new DefaultMutableTreeNode(subDivision.getHierarchy());//TODO properties
		}
		return nodeText;
	}

	/**
	 * @return the subDivision
	 */
	public ISubDivision getSubDivision() {
		return subDivision;
	}

	/**
	 * @return the nodeSubDivision
	 */
	private DefaultMutableTreeNode getNodeSubDivision() {
		if(nodeSubDivision == null){
			nodeSubDivision = new DefaultMutableTreeNode(this.subDivision.getHierarchy());//TODO properties
		}
		return nodeSubDivision;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString(){
		return this.subDivision.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see books.IReadable#read()
	 */
	@Override
	public String read() {
		return this.subDivision.read();
	}

	@Override
	public IShearchMatch[] shearch(String regex) {
		return this.subDivision.shearch(regex);
	}

	@Override
	public IShearchMatch[] shearch(String regex, String translation) {
		return this.subDivision.shearch(regex, translation);
	}

	@Override
	public String read(String translation) {
		return this.subDivision.read(translation);
	}

	@Override
	public boolean isSave() {
		return this.subDivision.isSave();
	}

	@Override
	public void save() throws IOException {
		this.subDivision.save();
		
	}

	@Override
	public boolean isLoad() {
		return this.subDivision.isLoad();
	}

	@Override
	public void load() throws IOException {
		this.subDivision.save();
		
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
