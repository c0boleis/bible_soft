package ihm.actions;


import javax.swing.JOptionPane;

import books.model.interfaces.IOrderedObject;
import ihm.window.Window;

public class SetOrderAction extends ActionPersoImplement{

	private IOrderedObject readable;

	public SetOrderAction(IOrderedObject read) {
		this.readable = read;
	}

	@Override
	public void doAction() {
		String rep = JOptionPane.showInputDialog(Window.get(), "entrez l'ordre: ", this.readable.getOrder());
		if(rep==null){
			return;
		}
		try{
			int order= Integer.parseInt(rep);
			if(order>=0){
				this.readable.setOrder(order);
			}
			return;
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(Window.get(), 
					"L'ordre doit etre un nombre",
					"ERREUR", JOptionPane.ERROR_MESSAGE);
			doAction();
			return;
		}
	}

}
