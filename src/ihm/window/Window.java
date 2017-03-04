/**
 * 
 */
package ihm.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
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
import ihm.PanelJob;
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

	static final Logger LOGGER = Logger.getLogger(Window.class);

	private static Window INSTANCE = new Window();
	
	private static WindowProperties windowProperties;

	private static JSplitPane mainSplit;

	private static JSplitPane secondSplit;

	private static JScrollPane scrollPaneTree;

	private static TreeBooks treeBooks;

	private static TabbedPaneChapeter tabbedPaneChapeter;

	private static MenuBarWindow menuBarWindow;
	
	private static PanelJob panelJob;

	private static Consol consol;
	
	private static WindowListener windowListener;

	protected static final String SOFT_VERSION = "0.0.3";

	private static WorkspaceListener workspaceListener;

	private Window(){
		super();
		this.setTitle("Bible soft");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setJMenuBar(getMenuBarWindow());
		this.setLayout(new BorderLayout());
		this.add(getMainSplit(),BorderLayout.CENTER);
		this.add(getPanelJob(), BorderLayout.SOUTH);
		this.addWindowListener(getWindowListener());
	}

	public static void main(String[] args) {
		File file = new File("log4Jconfig.properties");
		if(!file.exists()){
			try {
				file.createNewFile();
				BufferedWriter buf = new BufferedWriter(new FileWriter(file));
				buf.write("log4j.rootLogger=debug, stdout, R\n"

						+"log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n"
						+"log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n"

						+"# Pattern to output the caller's file name and line number.\n"
						+"log4j.appender.stdout.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\n"

						+"log4j.appender.R=org.apache.log4j.RollingFileAppender\n"
						+"log4j.appender.R.File=bible_soft.log\n"

						+"log4j.appender.R.MaxFileSize=100KB\n"
						+"# Keep one backup file\n"
						+"log4j.appender.R.MaxBackupIndex=1\n"

						+"log4j.appender.R.layout=org.apache.log4j.PatternLayout\n"
						+"log4j.appender.R.layout.ConversionPattern=%p %t %c - %m%n");
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PropertyConfigurator.configure("log4Jconfig.properties");
		LOGGER.debug("Start programme");
		open();
	}

	public static Window get(){
		return INSTANCE;
	}

	public static void open(){
		try {
			getWindowProperties().loadProperties();
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
//		INSTANCE.pack();
		INSTANCE.setVisible(true);

		getSecondSplit().setDividerLocation(getWindowProperties().getSecondSplitLocation());
		getMainSplit().setDividerLocation(getWindowProperties().getMainSplitLocation());
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
	protected static JSplitPane getMainSplit() {
		if(mainSplit==null){
			mainSplit = new  JSplitPane();
			mainSplit.setLeftComponent(getScrollPaneTree());
			mainSplit.setRightComponent(getSecondSplit());
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
			getWindowProperties().saveProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void saveAll() {
		try {
			Workspace.get().saveAll();
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
	protected static JSplitPane getSecondSplit() {
		if(secondSplit == null){
			secondSplit = new JSplitPane();
			secondSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
			secondSplit.setLeftComponent(getTabbedPaneChapeter());
			secondSplit.setRightComponent(getConsol());
		}
		return secondSplit;
	}

	public static void close() {
		try {
			getWindowProperties().saveProperties();
		} catch (IOException e) {
			LOGGER.error("Les propriétés de la fenètre n'on pas été enregistré", e);
		}
		System.exit(0);
	}

	/**
	 * @return the panelJob
	 */
	public static PanelJob getPanelJob() {
		if(Window.panelJob==null){
			Window.panelJob = new PanelJob();
		}
		return Window.panelJob;
	}

	/**
	 * @return the windowProperties
	 */
	private static WindowProperties getWindowProperties() {
		if(windowProperties == null){
			windowProperties = new WindowProperties();
		}
		return windowProperties;
	}

	/**
	 * @return the windowListener
	 */
	private static WindowListener getWindowListener() {
		if(windowListener==null){
			windowListener = new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent arg0) {
				}
				
				@Override
				public void windowIconified(WindowEvent arg0) {
				}
				
				@Override
				public void windowDeiconified(WindowEvent arg0) {
				}
				
				@Override
				public void windowDeactivated(WindowEvent arg0) {
					
				}
				
				@Override
				public void windowClosing(WindowEvent arg0) {
					try {
						getWindowProperties().saveProperties();
					} catch (IOException e) {
						LOGGER.error("Les propriétés de la fenètre n'on pas été enregistré", e);
					}
					
				}
				
				@Override
				public void windowClosed(WindowEvent arg0) {
					try {
						getWindowProperties().saveProperties();
					} catch (IOException e) {
						LOGGER.error("Les propriétés de la fenètre n'on pas été enregistré", e);
					}
				}
				
				@Override
				public void windowActivated(WindowEvent arg0) {
					
				}
			};
		}
		return windowListener;
	}

}
