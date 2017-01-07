package ihm.consol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class Consol extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6718573012527704310L;
	
	private JPanel panelOptions;
	
	private JTextArea panelDisplay;
	
	private JScrollPane scrollDisplay;
	
	private JMenuItem menuItemCopy;
	
	private JMenuItem menuItemSelectAll;
	
	private JPopupMenu popupMenuConsol;
	
	/**
	 * @return the menuItemCopy
	 */
	private JMenuItem getMenuItemCopy() {
		if(menuItemCopy == null){
			menuItemCopy = new JMenuItem("Copier");
			menuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
			menuItemCopy.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Toolkit toolKit = Toolkit.getDefaultToolkit();
					Clipboard cb = toolKit.getSystemClipboard();
					cb.setContents(new StringSelection(getPanelDisplay().getSelectedText()), null);
					
				}
			});
		}
		return menuItemCopy;
	}

	/**
	 * @return the menuItemSelectAll
	 */
	private JMenuItem getMenuItemSelectAll() {
		if(menuItemSelectAll == null){
			menuItemSelectAll = new JMenuItem("Selectioner tout");
			menuItemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
			menuItemSelectAll.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					getPanelDisplay().setSelectionStart(0);
					getPanelDisplay().setSelectionEnd(getPanelDisplay().getText().length());
					
				}
			});
		}
		return menuItemSelectAll;
	}

	/**
	 * @return the popupMenuConsol
	 */
	private JPopupMenu getPopupMenuConsol() {
		if(popupMenuConsol == null){
			popupMenuConsol = new JPopupMenu();
			popupMenuConsol.add(getMenuItemSelectAll());
			popupMenuConsol.add(getMenuItemCopy());
		}
		return popupMenuConsol;
	}

	public Consol(){
		this.setLayout(new BorderLayout());
		this.add(getPanelOptions(), BorderLayout.NORTH);
		this.add(getScrollDisplay(), BorderLayout.CENTER);
		this.setBackground(Color.white);
	}

	/**
	 * @return the panelOptions
	 */
	private JPanel getPanelOptions() {
		if(panelOptions == null){
			panelOptions = new JPanel();
			panelOptions.setBackground(Color.GRAY.brighter());
		}
		return panelOptions;
	}

	/**
	 * @return the panelDisplay
	 */
	private JTextArea getPanelDisplay() {
		if(panelDisplay == null){
			panelDisplay = new JTextArea();
			panelDisplay.setBackground(Color.white);
			panelDisplay.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isRightMouseButton(e)){
						getPopupMenuConsol().show(e.getComponent(),
								e.getX(), 
								e.getY());
					}
					
				}
			});
		}
		return panelDisplay;
	}

	/**
	 * @return the scrollDisplay
	 */
	private JScrollPane getScrollDisplay() {
		if(scrollDisplay == null){
			scrollDisplay = new JScrollPane();
			scrollDisplay.setViewportView(getPanelDisplay());
		}
		return scrollDisplay;
	}
	
	public void println(String st){
		this.getPanelDisplay().setText(this.getPanelDisplay().getText()+st+"\n");
	}

}
