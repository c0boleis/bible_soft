/**
 * 
 */
package ihm.actions;

/**
 * @author bata
 *
 */
public interface ActionPerso {
	
	public void doAction();
	
	public void addActionListener(ActionListenerPerso listener);
	
	public void removeActionListener(ActionListenerPerso listener);
	
	public ActionListenerPerso[] getActionListenerPersos();

}
