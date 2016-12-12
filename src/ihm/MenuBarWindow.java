package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarWindow extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6093430319447253057L;
	
	private static MenuBarWindow INSTANCE = new MenuBarWindow();
	
	/*
	 * les menus
	 */
	private static JMenu menuFile;
	
	/*
	 * les menuItems
	 */
	
	private static JMenuItem menuItemClose;
	
	private static JMenuItem menuItemSave;
	
	private MenuBarWindow(){
		this.add(getMenuFile());
	}
	
	public static MenuBarWindow get(){
		return INSTANCE;
	}

	/**
	 * @return the menuFile
	 */
	private static JMenu getMenuFile() {
		if(menuFile==null){
			menuFile = new JMenu();
			menuFile.setText("Fichier");
			menuFile.add(getMenuItemSave());
			menuFile.addSeparator();
			menuFile.add(getMenuItemClose());
		}
		return menuFile;
	}

	/**
	 * @return the menuItemSave
	 */
	private static JMenuItem getMenuItemSave() {
		if(menuItemSave==null){
			menuItemSave = new JMenuItem();
			menuItemSave.setText("Enregistrer");
			menuItemSave.setEnabled(false);
			menuItemSave.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return menuItemSave;
	}
	
	/**
	 * @return the menuItemClose
	 */
	private static JMenuItem getMenuItemClose() {
		if(menuItemClose==null){
			menuItemClose = new JMenuItem();
			menuItemClose.setText("Fermer");
			menuItemClose.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return menuItemClose;
	}

}
