/**
 * 
 */
package ihm;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import books.exceptions.NoPropetiesException;
import books.model.Workspace;
import books.model.interfaces.IBook;
import ihm.tree.TreeBooks;
import ihm.viewers.TabbedPaneChapeter;
import lancement.Main;

/**
 * @author bata
 *
 */
public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9203348546932805145L;

	static final Logger LOGGER = Logger.getLogger(Main.class);

	private static Window INSTANCE = new Window();

	private static JSplitPane mainSplit;

	private static JScrollPane scrollPaneTree;

	private static TreeBooks treeBooks;

	private static TabbedPaneChapeter tabbedPaneChapeter;

	private static MenuBarWindow menuBarWindow;

	private static final File FILE_PROPERTIES = new File("setting.properties");

	private static final String KEY_WORKSPACE_PROPERTIES = "workspace";
	
	private static boolean save = true;

	private Window(){
		super();
		this.setTitle("Bible soft");
		this.setMinimumSize(new Dimension(300, 200));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setJMenuBar(getMenuBarWindow());
		this.setContentPane(getMainSplit());
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4Jconfig.properties");
		LOGGER.info("Start programme");
		open();
	}

	public static Window get(){
		return INSTANCE;
	}

	public static void open(){
		String defaultWorkspace = "default";
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(FILE_PROPERTIES));
			String workspaceFolder = properties.getProperty(
					KEY_WORKSPACE_PROPERTIES,defaultWorkspace);
			if(!workspaceFolder.equals(defaultWorkspace)){
				Workspace.get().setFolder_path(workspaceFolder);
			}
			Workspace.get().load();
			getTreeBooks().initData();
			conectListeners();
			save = true;
			getMenuBarWindow().init(null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoPropetiesException e) {
			e.printStackTrace();
		}
		INSTANCE.setVisible(true);
	}
	
	private static void conectListeners(){
		getTreeBooks().conectListeners();
	}

	/**
	 * @return the scrollPaneTree
	 */
	private static JScrollPane getScrollPaneTree() {
		if(scrollPaneTree==null){
			scrollPaneTree = new JScrollPane();
			scrollPaneTree.setViewportView(getTreeBooks());
		}
		return scrollPaneTree;
	}

	/**
	 * @return the mainSplit
	 */
	private static JSplitPane getMainSplit() {
		if(mainSplit==null){
			mainSplit = new  JSplitPane();
			mainSplit.setLeftComponent(getScrollPaneTree());
			mainSplit.setRightComponent(getTabbedPaneChapeter());
			mainSplit.setDividerLocation(150);
		}
		return mainSplit;
	}

	/**
	 * @return the treeBooks
	 */
	public static TreeBooks getTreeBooks() {
		if(treeBooks==null){
			treeBooks = new TreeBooks();
		}
		return treeBooks;
	}

	/**
	 * @return the tabbedPaneChapeter
	 */
	public static TabbedPaneChapeter getTabbedPaneChapeter() {
		if(tabbedPaneChapeter==null){
			tabbedPaneChapeter = new TabbedPaneChapeter();
		}
		return tabbedPaneChapeter;
	}

	public static void save() {
		try {
			Workspace.get().save();
			save = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the save
	 */
	public static boolean isSave() {
		return save;
	}

	public static void needSave(){
		save = false;
		getMenuBarWindow().init(null);
	}

	public static MenuBarWindow getMenuBarWindow(){
		if(menuBarWindow == null){
			menuBarWindow = new MenuBarWindow();
		}
		return menuBarWindow;
	}

}
