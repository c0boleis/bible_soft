package ihm.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import books.model.interfaces.IReadable;
import books.model.interfaces.IShearable;
import books.model.interfaces.IText;
import ihm.Window;
import ihm.actions.AddCommentAction;
import ihm.actions.ReadAction;
import ihm.actions.ShearchAction;
import ihm.tree.nodes.TextBookNode;

public class TreeBooksPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3024111508441250165L;

	private JMenuItem menuItemRead;
	
	private JMenuItem menuItemShearch;
	
	private JMenuItem menuItemAddComment;
	
	public TreeBooksPopupMenu() {
		super();
		this.add(getMenuItemRead());
		this.add(getMenuItemShearch());
		this.add(getMenuItemAddComment());
	}
	
	public void init(Object obj){
		getMenuItemRead().setVisible(false);
		getMenuItemRead().setEnabled(false);
		
		getMenuItemShearch().setVisible(false);
		getMenuItemShearch().setEnabled(false);
		
		getMenuItemAddComment().setVisible(false);
		getMenuItemAddComment().setEnabled(false);
		
		if(obj==null){
			return;
		}
		if(obj instanceof IReadable){
			getMenuItemRead().setVisible(true);
			getMenuItemRead().setEnabled(true);
		}
		if(obj instanceof IShearable){
			getMenuItemShearch().setVisible(true);
			getMenuItemShearch().setEnabled(true);
		}
		if(obj instanceof TextBookNode){
			getMenuItemAddComment().setVisible(true);
			getMenuItemAddComment().setEnabled(true);
		}
	}

	/**
	 * @return the menuItemRead
	 */
	private JMenuItem getMenuItemRead() {
		if(menuItemRead==null){
			menuItemRead = new JMenuItem("Lire");
			menuItemRead.setVisible(false);
			menuItemRead.setEnabled(false);
			menuItemRead.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] tab = Window.getTreeBooks().getTreeBooksListeners().getSelection();
					if(tab.length<=0){
						return;
					}
					ReadAction action = new ReadAction((IReadable) tab[0]);
					action.doAction();
					
				}
			});
		}
		return menuItemRead;
	}

	/**
	 * @return the menuItemShearch
	 */
	private JMenuItem getMenuItemShearch() {
		if(menuItemShearch==null){
			menuItemShearch = new JMenuItem("Rechercher");
			menuItemShearch.setVisible(false);
			menuItemShearch.setEnabled(false);
			menuItemShearch.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] tab = Window.getTreeBooks().getTreeBooksListeners().getSelection();
					if(tab.length<=0){
						return;
					}
					ShearchAction action = new ShearchAction((IShearable) tab[0]);
					action.doAction();
					
				}
			});
		}
		return menuItemShearch;
	}

	/**
	 * @return the menuItemAddComment
	 */
	private JMenuItem getMenuItemAddComment() {
		if(menuItemAddComment==null){
			menuItemAddComment = new JMenuItem("Ajouter un commentaire");
			menuItemAddComment.setVisible(false);
			menuItemAddComment.setEnabled(false);
			menuItemAddComment.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] tab = Window.getTreeBooks().getTreeBooksListeners().getSelection();
					if(tab.length<=0){
						return;
					}
					AddCommentAction action = new AddCommentAction(
							((TextBookNode) tab[0]).getIText());
					action.doAction();
				}
			});
		}
		return menuItemAddComment;
	}

}
