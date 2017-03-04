package ihm.actions;

import javax.swing.JOptionPane;

import books.model.Comment;
import books.model.Workspace;
import books.model.interfaces.IText;
import ihm.window.Window;

/**
 * 
 * @author bata
 *
 */
public class AddCommentAction extends ActionPersoImplement{
	
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
							"Le nom peut contenir seulement des lettres",
							"ERROR",
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
					"Le commentaire n'a pas été ajouter",
					"ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
