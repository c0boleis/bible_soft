package ihm.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import books.model.Searcher;
import books.model.interfaces.IBook;
import books.model.interfaces.ILoadSaveObject;
import books.model.interfaces.IOrderedObject;
import books.model.interfaces.IReadable;
import books.model.interfaces.IShearable;
import books.model.interfaces.ISubDivision;
import ihm.actions.ActionListenerPerso;
import ihm.actions.ActionPerso;
import ihm.actions.AddCommentAction;
import ihm.actions.LoadObjectAction;
import ihm.actions.ReadAction;
import ihm.actions.SaveObjectAction;
import ihm.actions.SetNameAction;
import ihm.actions.SetOrderAction;
import ihm.actions.ShearchAction;
import ihm.tree.nodes.BookNode;
import ihm.tree.nodes.SubBookDivisionNode;
import ihm.tree.nodes.TextBookNode;
import ihm.window.Window;

public class TreeBooksPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3024111508441250165L;
	
	private static final Logger LOGGER = Logger.getLogger(TreeBooksPopupMenu.class);

	private JMenuItem menuItemRead;

	private JMenuItem menuItemShearch;

	private JMenuItem menuItemAddComment;

	private JMenuItem menuItemLoad;

	private JMenuItem menuItemSave;

	private JMenuItem menuItemSetOrder;
	
	private JMenuItem menuItemSetName;
	
	private ActionListenerPerso actionListenerPerso = new ActionListenerPerso() {
		
		@Override
		public void ActionStarted(ActionPerso actionPerso) {
			waitAction();
		}
		
		@Override
		public void ActionFinished(ActionPerso actionPerso) {
			runAfterAction();
			Window.getPanelJob().finish();
		}
	};
	
	private boolean waitActionFinished = false;

	public TreeBooksPopupMenu() {
		super();
		this.add(getMenuItemRead());
		this.add(getMenuItemLoad());
		this.add(getMenuItemSave());
		this.add(getMenuItemShearch());
		this.add(getMenuItemAddComment());
		this.add(getMenuItemSetName());
		this.add(getMenuItemSetOrder());
	}

	public void init(Object[] tab){
		if(this.waitActionFinished){
			getMenuItemRead().setEnabled(false);
			getMenuItemShearch().setEnabled(false);
			getMenuItemAddComment().setEnabled(false);
			getMenuItemLoad().setEnabled(false);
			getMenuItemSave().setEnabled(false);
			getMenuItemSetOrder().setEnabled(false);
			getMenuItemSetName().setEnabled(false);
			return;
		}
		LOGGER.debug("init treePopupMenu for several object");
		if(tab==null){
			return;
		}
		getMenuItemRead().setVisible(true);
		getMenuItemRead().setEnabled(true);

		getMenuItemShearch().setVisible(true);
		getMenuItemShearch().setEnabled(!Searcher.isInProcess());

		getMenuItemAddComment().setVisible(true);
		getMenuItemAddComment().setEnabled(true);

		getMenuItemLoad().setVisible(true);
		getMenuItemLoad().setEnabled(true);

		getMenuItemSave().setVisible(true);
		getMenuItemSave().setEnabled(true);

		getMenuItemSetOrder().setVisible(true);
		getMenuItemSetOrder().setEnabled(true);
		
		boolean needSave = false;
		for(Object obj : tab){
			if(!(obj instanceof IReadable)){
				getMenuItemRead().setVisible(false);
				getMenuItemRead().setEnabled(false);
			}
			if(!(obj instanceof IShearable)){
				getMenuItemShearch().setVisible(false);
				//we can't start tow search process at the same time.
				getMenuItemShearch().setEnabled(false);
			}
			if(!(obj instanceof TextBookNode)){
				getMenuItemAddComment().setVisible(false);
				getMenuItemAddComment().setEnabled(false);
			}
			if(!(obj instanceof ILoadSaveObject)){

				getMenuItemLoad().setVisible(false);
				getMenuItemLoad().setEnabled(false);

				getMenuItemSave().setVisible(false);//TODO
				getMenuItemSave().setEnabled(false);
			}else{
				if(!((ILoadSaveObject) obj).isSave()){
					needSave = true;
				}
			}
			if(!(obj instanceof IOrderedObject)){
				getMenuItemSetOrder().setVisible(false);
				getMenuItemSetOrder().setEnabled(false);
			}
		}
		if(getMenuItemSave().isEnabled()){
			getMenuItemSave().setEnabled(needSave);
		}

	}

	public void init(Object obj){
		if(this.waitActionFinished){
			getMenuItemRead().setEnabled(false);
			getMenuItemShearch().setEnabled(false);
			getMenuItemAddComment().setEnabled(false);
			getMenuItemLoad().setEnabled(false);
			getMenuItemSave().setEnabled(false);
			getMenuItemSetOrder().setEnabled(false);
			getMenuItemSetName().setEnabled(false);
			return;
		}
		LOGGER.debug("init treePopupMenu for one object");
		getMenuItemRead().setVisible(false);
		getMenuItemRead().setEnabled(false);

		getMenuItemShearch().setVisible(false);
		getMenuItemShearch().setEnabled(false);

		getMenuItemAddComment().setVisible(false);
		getMenuItemAddComment().setEnabled(false);

		getMenuItemLoad().setVisible(false);
		getMenuItemLoad().setEnabled(false);

		getMenuItemSave().setVisible(false);
		getMenuItemSave().setEnabled(false);

		getMenuItemSetOrder().setVisible(false);
		getMenuItemSetOrder().setEnabled(false);
		
		getMenuItemSetName().setVisible(false);
		getMenuItemSetName().setEnabled(false);

		if(obj==null){
			return;
		}
		if(obj instanceof IReadable){
			getMenuItemRead().setVisible(true);
			getMenuItemRead().setEnabled(true);
		}
		if(obj instanceof IShearable){
			getMenuItemShearch().setVisible(true);
			//we can't start tow search process at the same time.
			getMenuItemShearch().setEnabled(!Searcher.isInProcess());
		}
		if(obj instanceof TextBookNode){
			getMenuItemAddComment().setVisible(true);
			getMenuItemAddComment().setEnabled(true);
		}
		if(obj instanceof ILoadSaveObject){

			getMenuItemLoad().setVisible(true);
			getMenuItemLoad().setEnabled(true);

			getMenuItemSave().setVisible(true);
			getMenuItemSave().setEnabled(!((ILoadSaveObject) obj).isSave());
		}
		if(obj instanceof IOrderedObject){
			getMenuItemSetOrder().setVisible(true);
			getMenuItemSetOrder().setEnabled(true);
		}
		if(obj instanceof SubBookDivisionNode || obj instanceof BookNode){
			getMenuItemSetName().setVisible(true);
			getMenuItemSetName().setEnabled(true);
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
					action.addActionListener(actionListenerPerso);
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

	/**
	 * @return the menuItemLoad
	 */
	public JMenuItem getMenuItemLoad() {
		if(menuItemLoad==null){
			menuItemLoad = new JMenuItem("Charger");
			menuItemLoad.setVisible(false);
			menuItemLoad.setEnabled(false);
			menuItemLoad.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] tab = Window.getTreeBooks().getTreeBooksListeners().getSelection();
					if(tab.length<=0){
						return;
					}
					LoadObjectAction action = new LoadObjectAction(
							(ILoadSaveObject) tab[0]);
					action.addActionListener(actionListenerPerso);
					action.doAction();
				}
			});
		}
		return menuItemLoad;
	}

	/**
	 * @return the menuItemSetOrder
	 */
	private JMenuItem getMenuItemSetOrder() {
		if(menuItemSetOrder==null){
			menuItemSetOrder = new JMenuItem("Modifier l'ordre");
			menuItemSetOrder.setVisible(false);
			menuItemSetOrder.setEnabled(false);
			menuItemSetOrder.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] tab = Window.getTreeBooks().getTreeBooksListeners().getSelection();
					if(tab.length<=0){
						return;
					}
					SetOrderAction action = new SetOrderAction(
							(IOrderedObject) tab[0]);
					action.doAction();
				}
			});
		}
		return menuItemSetOrder;
	}

	/**
	 * @return the menuItemSave
	 */
	private JMenuItem getMenuItemSave() {
		if(menuItemSave==null){
			menuItemSave = new JMenuItem("Enregistrer");
			menuItemSave.setVisible(false);
			menuItemSave.setEnabled(false);
			menuItemSave.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] tab = Window.getTreeBooks().getTreeBooksListeners().getSelection();
					if(tab.length<=0){
						return;
					}
					SaveObjectAction action = new SaveObjectAction(
							(ILoadSaveObject) tab[0]);
					action.doAction();
				}
			});
		}
		return menuItemSave;
	}

	private JMenuItem getMenuItemSetName() {
		if(menuItemSetName == null){
			menuItemSetName = new JMenuItem();
			menuItemSetName.setText("Renomer");
			menuItemSetName.setVisible(false);
			menuItemSetName.setEnabled(false);
			menuItemSetName.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] tab = Window.getTreeBooks().getTreeBooksListeners().getSelection();
					if(tab.length<=0){
						return;
					}
					Object obj = tab[0];
					SetNameAction action = null;
					if(obj instanceof BookNode){
						action = new SetNameAction(((BookNode) obj).getBook());
					}else if(obj instanceof SubBookDivisionNode){
						action = new SetNameAction(((SubBookDivisionNode) obj).getSubDivision());
					}
					if(action !=null){
						action.doAction();
					}
				}
			});
		}
		return menuItemSetName;
	}

	private void waitAction(){
		this.waitActionFinished = true;
		init(null);
	}
	
	private void runAfterAction(){
		this.waitActionFinished = false;
	}
}
