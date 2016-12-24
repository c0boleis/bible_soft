package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import books.exceptions.NoPropetiesException;
import books.model.Book;
import ihm.tree.TreeBookCellRenderer;
import ihm.tree.TreeBooks;

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
	
	private static JMenuItem menuItemLoadBook;
	
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
			menuFile.add(getMenuItemLoadBook());
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

	/**
	 * @return the menuItemLoadBook
	 */
	private static JMenuItem getMenuItemLoadBook() {
		if(menuItemLoadBook==null){
			menuItemLoadBook = new JMenuItem();
			menuItemLoadBook.setText("Load book");
			menuItemLoadBook.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.setMultiSelectionEnabled(false);
					chooser.showOpenDialog(Window.get());
					
					File file = chooser.getSelectedFile();
					if(file == null){
						return;
					}
					Book livre = new Book(file.getPath());
					try {
						livre.loadInfo();
						Window.getTreeBooks().addBook(livre);
					} catch (NoPropetiesException e1) {
						JOptionPane.showMessageDialog(Window.get(), e1.getMessage(), "ERREUR", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					
				}
			});
			
		}
		return menuItemLoadBook;
	}

}
