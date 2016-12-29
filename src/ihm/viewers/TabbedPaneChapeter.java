/**
 * 
 */
package ihm.viewers;

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
}
