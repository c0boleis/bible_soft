package ihm.tree;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import ihm.tree.nodes.BookNode;
import ihm.tree.nodes.SubBookDivisionNode;

public class TreeBooksListeners implements MouseListener,TreeSelectionListener{

	private TreeBooks tree;

	private Object[] selection = new Object[0];

	public TreeBooksListeners(TreeBooks t) {
		this.tree = t;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()<2){
			return;
		}
		if(selection.length<1){
			return;
		}
		if(selection[0] instanceof BookNode){
			((BookNode) selection[0]).init();
			System.out.println("BookNode load: "
					+((BookNode) selection[0]).getChildCount());
			tree.getPersoModel().reload((BookNode) selection[0]);
//			tree.setModel(new TreeBooksModel(tree,tree.getRootNode()));
			//			tree.getPersoModel().reload((TreeNode) tree.getModel().getRoot());

		}else if(selection[0] instanceof SubBookDivisionNode){
			((SubBookDivisionNode) selection[0]).init();
			tree.getPersoModel().reload(((SubBookDivisionNode) selection[0]));
			tree.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath[] paths = e.getPaths();
		List<Object> sel = new ArrayList<Object>();
		for(TreePath path : paths){
			sel.add(path.getLastPathComponent());
		}
		selection = sel.toArray(new Object[0]);
	}

}
