package ihm.tree.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import books.model.interfaces.ILoadSaveObject;
import books.model.interfaces.IOrderedObject;
import books.model.interfaces.IReadable;
import books.model.interfaces.IShearable;
import books.model.interfaces.IShearchMatch;
import books.model.interfaces.ISubDivision;
import books.model.interfaces.ISubDivisonContainer;
import books.model.interfaces.IText;
import books.model.listener.OrderedObjectListener;
import ihm.Window;

public class SubBookDivisionNode extends DefaultMutableTreeNode 
implements IReadable,IShearable,ILoadSaveObject,IOrderedObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3266881184618907401L;

	private ISubDivision subDivision;

	private DefaultMutableTreeNode nodeSubDivision;

	private DefaultMutableTreeNode nodeText;
	
	private OrderedObjectListener orderedObjectListener;

	public SubBookDivisionNode(ISubDivision div){
		super();
		this.subDivision = div;
		this.subDivision.addOrderListener(getOrderedObjectListener());
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
			if(!this.subDivision.getHierarchy().equals(ISubDivisonContainer.DEFAULT_HIERARCHY)){
				Window.getTreeBooks().getTreeBooksModel().insertNodeInto(getNodeSubDivision(), this, 0);
			}
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
		int size = getNodeSubDivision().getChildCount();
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for(int index = 0; index<size;index++){
			TreeNode node = getNodeSubDivision().getChildAt(index);
			nodes.add(node);
		}
		for(TreeNode node : nodes){
			Window.getTreeBooks().getTreeBooksModel().removeNodeFromParent((MutableTreeNode) node);
		}
		
		size = getChildCount();
		nodes.clear();
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
			if(subDivision.getHierarchy().equals(ISubDivisonContainer.DEFAULT_HIERARCHY)){
				nodeSubDivision = this;
				
			}else{
				nodeSubDivision = new DefaultMutableTreeNode(this.subDivision.getHierarchy());
			}
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
		this.subDivision.load();
		
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

	@Override
	public int getOrder() {
		return this.subDivision.getOrder();
	}

	@Override
	public void setOrder(int order) {
		this.subDivision.setOrder(order);
		
	}

	/**
	 * @return the orderedObjectListener
	 */
	private OrderedObjectListener getOrderedObjectListener() {
		if(orderedObjectListener == null){
			orderedObjectListener = new OrderedObjectListener() {
				
				@Override
				public void orderChange(int newOrder, int oldOrder) {
					Object obj = getParent();
					while(obj!=null){
						if(obj instanceof BookNode){
							((BookNode) obj).refresh();
							return;
						}else if(obj instanceof SubBookDivisionNode){
							((SubBookDivisionNode) obj).refresh();
							return;
						}else if(obj instanceof TreeNode){
							obj = ((TreeNode) obj).getParent();
						}else{
							obj = null;
						}
					}
					
				}
			};
		}
		return orderedObjectListener;
	}

}
