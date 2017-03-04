/**
 * 
 */
package ihm.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C.B.
 *
 */
public abstract class ActionPersoImplement implements ActionPerso {

	private List<ActionListenerPerso> listeners = new ArrayList<>();

	/* (non-Javadoc)
	 * @see ihm.actions.ActionPerso#addActionListener(ihm.actions.ActionListenerPerso)
	 */
	@Override
	public void addActionListener(ActionListenerPerso listener) {
		if(!listeners.contains(listener)){
			listeners.add(listener);
		}

	}

	/* (non-Javadoc)
	 * @see ihm.actions.ActionPerso#removeActionListener(ihm.actions.ActionListenerPerso)
	 */
	@Override
	public void removeActionListener(ActionListenerPerso listener) {
		listeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see ihm.actions.ActionPerso#getActionListenerPersos()
	 */
	@Override
	public ActionListenerPerso[] getActionListenerPersos() {
		return listeners.toArray(new ActionListenerPerso[0]);
	}
	
	protected void fireActionStarted() {
		ActionListenerPerso[] tab = getActionListenerPersos();
		for(ActionListenerPerso listener : tab){
			listener.ActionStarted(this);
		}
	}
	
	protected void fireActionFinished() {
		ActionListenerPerso[] tab = getActionListenerPersos();
		for(ActionListenerPerso listener : tab){
			listener.ActionFinished(this);
		}
	}

}
