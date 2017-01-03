package ihm.actions;

import java.awt.event.WindowStateListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import books.model.Comment;
import books.model.Workspace;
import books.model.interfaces.IComment;
import books.model.interfaces.IReadable;
import books.model.interfaces.IText;
import ihm.Window;
import ihm.reader.Reader;

/**
 * 
 * @author bata
 *
 */
public class AddCommentAction implements ActionPerso{
	
	private IText text;

	public AddCommentAction(IText read) {
		this.text = read;
	}

	/*
	 * (non-Javadoc)
	 * @see ihm.actions.ActionPerso#doAction()
	 */
	@Override
	public void doAction() {
		String name = null;
		while(name==null){
			name = JOptionPane.showInputDialog(Window.get(), "Entrez le nom du commentaire", "");
			if(name==null){
				return;
			}
			name = name.trim().toLowerCase();
			char[] tmp =  name.toCharArray();
			for(int index=0;index<tmp.length;index++){
				if(!Character.isAlphabetic(tmp[index])){
					JOptionPane.showMessageDialog(Window.get(),
							"ERROR",
							"Le nom peut contenir seulement des lettres",
							JOptionPane.ERROR_MESSAGE);
					name=null;
					break;
				}
			}
		}
		String text = JOptionPane.showInputDialog(Window.get(), "Entrez le text du commentaire", "");
		if(text==null){
			return;
		}
		Comment comment = new Comment(name, new IText[]{this.text}, text);
		if(!Workspace.get().addComment(comment)){
			JOptionPane.showMessageDialog(Window.get(),
					"ERROR",
					"Le commentaire n'a pas été ajouter",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
