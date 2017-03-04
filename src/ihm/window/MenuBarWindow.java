package ihm.window;

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
import books.model.Workspace;

public class MenuBarWindow extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6093430319447253057L;
	
	/*
	 * les menus
	 */
	private static JMenu menuFile;
	
	private static JMenu menuHelp;
	
	/*
	 * les menuItems
	 */
	
	private static JMenuItem menuItemLoadBook;
	
	private static JMenuItem menuItemAbout;
	
	private static JMenuItem menuItemClose;
	
	private static JMenuItem menuItemSave;
	
	private static JMenuItem menuItemSaveAll;
	
	public MenuBarWindow(){
		this.add(getMenuFile());
		this.add(getMenuHelp());
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
			menuFile.add(getMenuItemSaveAll());
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
					Window.save();
					
				}
			});
		}
		return menuItemSave;
	}
	
	/**
	 * @return the menuItemSaveAll
	 */
	private static JMenuItem getMenuItemSaveAll() {
		if(menuItemSaveAll==null){
			menuItemSaveAll = new JMenuItem();
			menuItemSaveAll.setText("Enregistrer Tout");
			menuItemSaveAll.setEnabled(true);
			menuItemSaveAll.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Window.saveAll();
					
				}
			});
		}
		return menuItemSaveAll;
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
					Window.close();
					
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
			menuItemLoadBook.setText("Charger un livre");
			menuItemLoadBook.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.setMultiSelectionEnabled(false);
					int rep = chooser.showOpenDialog(Window.get());
					if(rep  != JFileChooser.APPROVE_OPTION ){
						return;
					}
					File file = chooser.getSelectedFile();
					if(file == null){
						return;
					}
					Book livre = new Book(file.getPath());
					try {
						livre.loadInfo();
						Workspace.get().addBook(livre);
					} catch (NoPropetiesException e1) {
						JOptionPane.showMessageDialog(Window.get(), e1.getMessage(), "ERREUR", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			});
			
		}
		return menuItemLoadBook;
	}

	public void init(Object object) {
		getMenuItemSave().setEnabled(!Workspace.get().isSave());
		
	}

	/**
	 * @return the menuItemAbout
	 */
	private static JMenuItem getMenuItemAbout() {
		if(menuItemAbout == null){
			menuItemAbout = new JMenuItem();
			menuItemAbout.setText("A propos");
			menuItemAbout.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(Window.get(),
							"BibleSoft:\n"
							+ "version : "+Window.SOFT_VERSION);
					
				}
			});
		}
		return menuItemAbout;
	}

	/**
	 * @return the menuHelp
	 */
	private static JMenu getMenuHelp() {
		if(menuHelp == null){
			menuHelp = new JMenu();
			menuHelp.setText("Aide");
			menuHelp.add(getMenuItemAbout());
		}
		return menuHelp;
	}

}
