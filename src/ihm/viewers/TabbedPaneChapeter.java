/**
 * 
 */
package ihm.viewers;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author bata
 *
 */
public class TabbedPaneChapeter extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1112642137816667715L;
	
	private static JPanel paneEmpty;
	
	public TabbedPaneChapeter(){
		this.setMinimumSize(new Dimension(100, 150));
		this.add("Empty", getPaneEmpty());
		this.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
	}

	/**
	 * @return the paneEmpty
	 */
	private static JPanel getPaneEmpty() {
		if(paneEmpty == null){
			paneEmpty = new JPanel();
			paneEmpty.setPreferredSize(new Dimension(100, 150));
			paneEmpty.setName("tab_empty");
		}
		return paneEmpty;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTabbedPane#add(java.lang.String, java.awt.Component)
	 */
	@Override
	public Component add(String arg0, Component arg1) {
		this.remove(getPaneEmpty());
		Component cmp =  super.add(arg0, arg1);
		this.setSelectedIndex(this.getTabCount()-1);
		return cmp;
	}
}
