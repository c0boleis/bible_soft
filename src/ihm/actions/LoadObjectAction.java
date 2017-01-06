package ihm.actions;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.tree.MutableTreeNode;

import org.apache.log4j.Logger;

import books.model.interfaces.ILoadSaveObject;
import ihm.Window;
import ihm.tree.nodes.BookNode;
import ihm.tree.nodes.SubBookDivisionNode;

public class LoadObjectAction implements ActionPerso{
	
	private static final Logger LOGGER = Logger.getLogger(LoadObjectAction.class);
	
	private ILoadSaveObject readable;

	public LoadObjectAction(ILoadSaveObject read) {
		this.readable = read;
	}

	@Override
	public void doAction() {
		try {
			this.readable.load();
			LOGGER.info(this.readable.toString()+" a été chargé.");
			if(this.readable instanceof BookNode){
				((BookNode) this.readable).refresh();
			}else if(this.readable instanceof SubBookDivisionNode){
				((SubBookDivisionNode) this.readable).refresh();
			}
		} catch (IOException e) {
			LOGGER.error("l\'objet n'a pas été chargé.", e);
			JOptionPane.showMessageDialog(Window.get(),
					"l\'objet n'a pas été chargé.",
					"ERREUR",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
