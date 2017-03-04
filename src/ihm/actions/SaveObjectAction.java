package ihm.actions;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import books.model.interfaces.ILoadSaveObject;
import ihm.tree.nodes.BookNode;
import ihm.tree.nodes.SubBookDivisionNode;
import ihm.window.Window;

public class SaveObjectAction extends ActionPersoImplement{
	
	private static final Logger LOGGER = Logger.getLogger(SaveObjectAction.class);
	
	private ILoadSaveObject saveObject;

	public SaveObjectAction(ILoadSaveObject read) {
		this.saveObject = read;
	}

	@Override
	public void doAction() {
		try {
			this.saveObject.save();
			LOGGER.debug(this.saveObject.toString()+" a été enregistré.");
			if(this.saveObject instanceof BookNode){
				((BookNode) this.saveObject).refresh();
			}else if(this.saveObject instanceof SubBookDivisionNode){
				((SubBookDivisionNode) this.saveObject).refresh();
			}
		} catch (IOException e) {
			LOGGER.error("l\'objet n'a pas été enregistré.", e);
			JOptionPane.showMessageDialog(Window.get(),
					"l\'objet n'a pas été enregistré.",
					"ERREUR",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
