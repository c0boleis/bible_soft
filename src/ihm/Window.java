/**
 * 
 */
package ihm;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import ihm.tree.TreeBookCellRenderer;
import ihm.tree.TreeBooks;
import ihm.viewers.TabbedPaneChapeter;

/**
 * @author bata
 *
 */
public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9203348546932805145L;
	
	private static Window INSTANCE = new Window();
	
	private static JSplitPane mainSplit;
	
	private static JScrollPane scrollPaneTree;
	
	private static TreeBooks treeBooks;

	private Window(){
		super();
		this.setTitle("Bible soft");
		this.setMinimumSize(new Dimension(300, 200));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setJMenuBar(MenuBarWindow.get());
		this.setContentPane(getMainSplit());
	}
	
	public static void main(String[] args) {
		open();
	}
	
	public static Window get(){
		return INSTANCE;
	}
	
	public static void open(){
		INSTANCE.setVisible(true);
	}

	/**
	 * @return the scrollPaneTree
	 */
	private static JScrollPane getScrollPaneTree() {
		if(scrollPaneTree==null){
			scrollPaneTree = new JScrollPane();
			scrollPaneTree.setViewportView(getTreeBooks());
		}
		return scrollPaneTree;
	}
	
	/**
	 * @return the mainSplit
	 */
	private static JSplitPane getMainSplit() {
		if(mainSplit==null){
			mainSplit = new  JSplitPane();
			mainSplit.setLeftComponent(getScrollPaneTree());
			mainSplit.setRightComponent(TabbedPaneChapeter.get());
			mainSplit.setDividerLocation(150);
		}
		return mainSplit;
	}

	/**
	 * @return the treeBooks
	 */
	public static TreeBooks getTreeBooks() {
		if(treeBooks==null){
			treeBooks = new TreeBooks();
		}
		return treeBooks;
	}

}
