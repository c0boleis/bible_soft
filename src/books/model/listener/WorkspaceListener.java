package books.model.listener;

import books.model.interfaces.IBook;
import books.model.interfaces.IComment;

/**
 * 
 * @author Corentin Boleis
 *
 */
public interface WorkspaceListener {
	
	public void bookAdd(IBook bookAdd);
	
	public void bookRemove(IBook bookRemove);
	
	public void commentAdd(IComment commentAdd);
	
	public void commentRemove(IComment commentRemove);

}
