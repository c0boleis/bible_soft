package ihm.actions;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import books.model.Searcher;
import books.model.interfaces.IBook;
import books.model.interfaces.IShearable;
import books.model.interfaces.IShearchMatch;
import books.model.interfaces.ISubDivision;
import books.model.listener.SearcherListener;
import books.model.rules.BookNameRule;
import ihm.dialogue.SearhcPane;
import ihm.dialogue.TextPane;
import ihm.window.Window;

public class SetNameAction extends ActionPersoImplement{

	private static final Logger LOGGER = Logger.getLogger(SetNameAction.class);

	private IBook book = null;
	
	private ISubDivision subDivision = null;

	private static SearcherListener searcherListener;

	public SetNameAction(IBook read) {
		this.book = read;
	}
	
	public SetNameAction(ISubDivision read) {
		this.subDivision = read;
	}

	/*
	 * (non-Javadoc)
	 * @see ihm.actions.ActionPerso#doAction()
	 */
	@Override
	public void doAction() {
		String initName = null;
		if(this.book!=null){
			initName = this.book.getName();
		}else{
			initName = this.subDivision.getName();
		}
		TextPane textPane = new TextPane(new BookNameRule(),initName);
		String name = textPane.getSearchRegex();
		if(name==null){
			return;
		}
		if(this.book!=null){
			this.book.setName(name);
		}else{
			this.subDivision.setName(name);
		}
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
