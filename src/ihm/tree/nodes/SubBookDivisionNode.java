package ihm.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import books.model.ISubDivision;
import books.model.IText;
import ihm.Window;

public class SubBookDivisionNode extends DefaultMutableTreeNode {
	
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
//		init();
	}
	
	public final void init(){
		if(this.getChildCount()>0){
			return;
		}
		this.subDivision.loadSubDivisions();
		ISubDivision[] tab = this.subDivision.getSubDivisions();
		if(tab.length>0){
			this.add(getNodeSubDivision());
			for(ISubDivision div : tab){
				SubBookDivisionNode node = new SubBookDivisionNode(div);
				Window.getTreeBooks().getPersoModel().insertNodeInto(node, getNodeSubDivision(), 0);
			}
		}
		
		IText[] tab1 = this.subDivision.getTexts();
		if(tab1.length>0){
			this.add(getNodeText());
			for(IText text : tab1){
				getNodeText().add(new TextBookNode(text));
			}
		}
	}
	/**
	 * @return the nodeText
	 */
	private DefaultMutableTreeNode getNodeText() {
		if(nodeSubDivision == null){
			nodeSubDivision = new DefaultMutableTreeNode("Verset");//TODO properties
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
			nodeSubDivision = new DefaultMutableTreeNode("Chapter");//TODO properties
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

}
