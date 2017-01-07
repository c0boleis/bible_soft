/**
 * 
 */
package ihm;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import books.exceptions.NoPropetiesException;
import books.model.Workspace;
import books.model.interfaces.IBook;
import books.model.interfaces.IComment;
import books.model.listener.WorkspaceListener;
import ihm.consol.Consol;
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
	
	private static JSplitPane secondSplit;

	private static JScrollPane scrollPaneTree;

	private static TreeBooks treeBooks;

	private static TabbedPaneChapeter tabbedPaneChapeter;

	private static MenuBarWindow menuBarWindow;
	
	private static Consol consol;

	private static File FILE_PROPERTIES;

	private static final String KEY_WORKSPACE_PROPERTIES = "workspace";
	
	private static WorkspaceListener workspaceListener;
	
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
			properties.load(new FileReader(getFileProperties()));
			String workspaceFolder = properties.getProperty(
					KEY_WORKSPACE_PROPERTIES,defaultWorkspace);
			File folder = new File(workspaceFolder);
			if(!folder.exists()){
				File file = null;
				while(file==null){
					JFileChooser choose = new JFileChooser();
					choose.setMultiSelectionEnabled(false);
					choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					choose.showOpenDialog(INSTANCE);
					file = choose.getSelectedFile();
					if(file==null){
						int rep = JOptionPane.showConfirmDialog(
								INSTANCE,
								"le fichier est null voulez-vous quiter le logiciel?", 
								"Workspace", JOptionPane.INFORMATION_MESSAGE);
						if(rep==JOptionPane.OK_OPTION){
							System.exit(0);
						}
					}
				}
				workspaceFolder = file.getPath();
				properties.setProperty(KEY_WORKSPACE_PROPERTIES,
						workspaceFolder);
				properties.store(new FileWriter(getFileProperties()), null);
			}
			if(!workspaceFolder.equals(defaultWorkspace)){
				Workspace.get().setFolder_path(workspaceFolder);
			}
			Workspace.get().load();
			getTreeBooks().initData();
			conectListeners();
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
		Workspace.get().addWorkspaceListener(getWorkspaceListener());
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
			mainSplit.setRightComponent(getSecondSplit());
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
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	public static void needSave(){
//		save = false;
//		getMenuBarWindow().init(null);
//	}

	public static MenuBarWindow getMenuBarWindow(){
		if(menuBarWindow == null){
			menuBarWindow = new MenuBarWindow();
		}
		return menuBarWindow;
	}

	/**
	 * @return the workspaceListener
	 */
	private static WorkspaceListener getWorkspaceListener() {
		if(workspaceListener == null){
			workspaceListener = new WorkspaceListener() {
				
				@Override
				public void saveChange(boolean newValue) {
					getMenuBarWindow().init(null);
				}
				
				@Override
				public void commentRemove(IComment commentRemove) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void commentAdd(IComment commentAdd) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void bookRemove(IBook bookRemove) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void bookAdd(IBook bookAdd) {
					// TODO Auto-generated method stub
					
				}
			};
		}
		return workspaceListener;
	}

	/**
	 * @return the consol
	 */
	public static Consol getConsol() {
		if(consol == null){
			consol = new Consol();
		}
		return consol;
	}

	/**
	 * @return the secondSplit
	 */
	private static JSplitPane getSecondSplit() {
		if(secondSplit == null){
			secondSplit = new JSplitPane();
			secondSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
			secondSplit.setLeftComponent(getTabbedPaneChapeter());
			secondSplit.setRightComponent(getConsol());
		}
		return secondSplit;
	}
	
	private static File getFileProperties(){
		if(FILE_PROPERTIES==null){
			FILE_PROPERTIES = new File("setting.properties");
			if(!FILE_PROPERTIES.exists()){
				try {
					FILE_PROPERTIES.createNewFile();
				} catch (IOException e) {
					LOGGER.error("le fichier de propriété n'a pas été créé", e);
				}
			}
		}
		return FILE_PROPERTIES;
	}

}
