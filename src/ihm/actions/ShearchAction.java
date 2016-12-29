package ihm.actions;

import javax.swing.JOptionPane;

import books.model.IShearable;
import books.model.IShearchMatch;
import ihm.Window;

public class ShearchAction implements ActionPerso{
	
	private IShearable shearable;

	public ShearchAction(IShearable read) {
		this.shearable = read;
	}

	@Override
	public void doAction() {
		String regex = JOptionPane.showInputDialog(Window.get(), "Entrez le regex recherché:");
		if(regex==null){
			return;
		}
		//TODO check regex
		IShearchMatch[] tab = this.shearable.shearch(regex);
		//TODO Print result
		System.out.println(tab.length+" résultats trouvés");
	}

}
