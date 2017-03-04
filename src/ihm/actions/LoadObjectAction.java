package ihm.actions;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.tree.MutableTreeNode;

import org.apache.log4j.Logger;

import books.model.interfaces.IBook;
import books.model.interfaces.ILoadSaveObject;
import books.model.interfaces.ISubDivision;
import books.model.interfaces.IText;
import books.model.listener.TextAddedListener;
import ihm.tree.nodes.BookNode;
import ihm.tree.nodes.SubBookDivisionNode;
import ihm.window.Window;

public class LoadObjectAction extends ActionPersoImplement{
	
	private static final Logger LOGGER = Logger.getLogger(LoadObjectAction.class);
	
	private ILoadSaveObject loadSaveObject;
	
	private TextAddedListener textAddedListener;
	
	private int valueToLoad = -1;

	public LoadObjectAction(ILoadSaveObject read) {
		this.loadSaveObject = read;
		if(this.loadSaveObject instanceof BookNode){
			valueToLoad = ((BookNode) this.loadSaveObject).getBook().getNbrOfTexts();
			valueToLoad -=((BookNode) this.loadSaveObject).getBook().calculNbrOfTexts();
		}else if(this.loadSaveObject instanceof SubBookDivisionNode){
			valueToLoad = ((SubBookDivisionNode) this.loadSaveObject).getSubDivision().getNbrOfTexts();
			valueToLoad -= ((SubBookDivisionNode) this.loadSaveObject).getSubDivision().calculNbrOfTexts();
		}
	}

	@Override
	public void doAction() {
		this.fireActionStarted();
		if(this.valueToLoad>0){
			doActionThread();
			return;
		}
		try {
			this.loadSaveObject.load();
			LOGGER.debug(this.loadSaveObject.toString()+" a été chargé.");
			if(this.loadSaveObject instanceof BookNode){
				((BookNode) this.loadSaveObject).refresh();
			}else if(this.loadSaveObject instanceof SubBookDivisionNode){
				((SubBookDivisionNode) this.loadSaveObject).refresh();
			}
		} catch (IOException e) {
			LOGGER.error("l\'objet n'a pas été chargé.", e);
			JOptionPane.showMessageDialog(Window.get(),
					"l\'objet n'a pas été chargé.",
					"ERREUR",
					JOptionPane.ERROR_MESSAGE);
		}
		this.fireActionFinished();
		
	}
	
	private void doActionThread(){
		Window.getPanelJob().initValueToLoad(this.valueToLoad);
		if(this.loadSaveObject instanceof BookNode){
			((BookNode) this.loadSaveObject).getBook().addTextAddedListener(getTextAddedListener());
		}else if(this.loadSaveObject instanceof SubBookDivisionNode){
			((SubBookDivisionNode) this.loadSaveObject).getSubDivision().addTextAddedListener(getTextAddedListener());
			
		}
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					loadSaveObject.load();
					LOGGER.debug(loadSaveObject.toString()+" a été chargé.");
					if(loadSaveObject instanceof BookNode){
						((BookNode) loadSaveObject).refresh();
					}else if(loadSaveObject instanceof SubBookDivisionNode){
						((SubBookDivisionNode) loadSaveObject).refresh();
					}
				} catch (IOException e) {
					LOGGER.error("l\'objet n'a pas été chargé.", e);
//					JOptionPane.showMessageDialog(Window.get(),
//							"l\'objet n'a pas été chargé.",
//							"ERREUR",
//							JOptionPane.ERROR_MESSAGE);
				}
				fireActionFinished();
			}
		});
		th.start();
	}

	/**
	 * @return the textAddedListener
	 */
	public TextAddedListener getTextAddedListener() {
		if(textAddedListener==null){
			textAddedListener = new TextAddedListener() {
				
				@Override
				public void textAdded(IText text) {
					Window.getPanelJob().work();
					
				}
			};
		}
		return textAddedListener;
	}

}
