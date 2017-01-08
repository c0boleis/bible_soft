package ihm.actions;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import books.model.Searcher;
import books.model.interfaces.IShearable;
import books.model.interfaces.IShearchMatch;
import books.model.listener.SearcherListener;
import ihm.SearhcPane;
import ihm.Window;

public class ShearchAction implements ActionPerso{

	private static final Logger LOGGER = Logger.getLogger(ShearchAction.class);

	private IShearable shearable;

	private static SearcherListener searcherListener;

	public ShearchAction(IShearable read) {
		this.shearable = read;
	}

	@Override
	public void doAction() {
		String regex = SearhcPane.getSearchRegex();
		if(regex==null){
			return;
		}
		Searcher.setListener(getSearcherListener());
		
		Window.getConsol().clear();
		Searcher.search(this.shearable, regex);
	}

	/**
	 * @return the searcherListener
	 */
	public static SearcherListener getSearcherListener() {
		if(searcherListener == null){
			searcherListener = new SearcherListener() {

				@Override
				public void matchesFind(IShearchMatch match) {
					Window.getConsol().clear();
					Window.getConsol().println(String.valueOf(Searcher.getNbrMatch()));
				}

				@Override
				public void searchEnd() {
					LOGGER.debug("La recherche est fini");
					Window.getConsol().clear();
					if(Searcher.isError()){
						JOptionPane.showMessageDialog(Window.get(),
								"Erreur de Thread dans la recherche",
								"ERREUR", JOptionPane.ERROR_MESSAGE);
					}else{
						IShearchMatch[] tab = Searcher.getSearchResult();
						String add = "s";
						if(tab.length==0){
							add = "";
						}
						Window.getConsol().println(tab.length+" résultat"+add+" trouvé"+add);
						for(IShearchMatch result : tab){
							Window.getConsol().println(result.toString());
						}
						LOGGER.debug(tab.length+" résultats trouvés");
					}
				}
			};
		}
		return searcherListener;
	}

}
