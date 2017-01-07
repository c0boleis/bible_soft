package ihm.actions;

import books.model.interfaces.IShearable;
import books.model.interfaces.IShearchMatch;
import ihm.SearhcPane;
import ihm.Window;

public class ShearchAction implements ActionPerso{
	
	private IShearable shearable;

	public ShearchAction(IShearable read) {
		this.shearable = read;
	}

	@Override
	public void doAction() {
		String regex = SearhcPane.getSearchRegex();
		if(regex==null){
			return;
		}
		IShearchMatch[] tab = this.shearable.shearch(regex);
		for(IShearchMatch result : tab){
			Window.getConsol().println(result.toString());
		}
		System.out.println(tab.length+" résultats trouvés");
	}

}
