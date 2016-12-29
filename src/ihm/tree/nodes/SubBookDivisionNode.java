package ihm.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import books.IReadable;
import books.model.IShearable;
import books.model.IShearchMatch;
import books.model.ISubDivision;
import books.model.IText;
import books.model.SubDivision;
import ihm.Window;

public class SubBookDivisionNode extends DefaultMutableTreeNode 
implements IReadable,IShearable{

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

}
