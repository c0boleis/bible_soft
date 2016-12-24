package ihm.tree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class TreeBooksModel extends DefaultTreeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6106919206146126194L;
	
	private TreeBooks tree;

	public TreeBooksModel(TreeBooks t,TreeNode root) {
		super(root);
		this.tree = t;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultTreeModel#reload(javax.swing.tree.TreeNode)
	 */
	@Override
	public void reload(TreeNode node) {
		// TODO Auto-generated method stub
		super.reload(node);
	}

	public TreeBooksModel(TreeBooks t,TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		this.tree = t;
	}

}
