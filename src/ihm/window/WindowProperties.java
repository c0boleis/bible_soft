package ihm.window;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import books.model.Workspace;

public class WindowProperties {

	static final Logger LOGGER = Logger.getLogger(WindowProperties.class);
	
	private String DEFAULT_WORKSPACE = "default";

	private final String KEY_WORKSPACE_PROPERTIES = "workspace";
	
	private final String KEY_MAIN_SPLIT_LOCATION = "main_split_location";
	
	private int mainSplitLocation = -1;
	
	private final String KEY_SECOND_SPLIT_LOCATION = "second_split_location";
	
	private int SecondSplitLocation = -1;

	private File FILE_PROPERTIES;
	
	
	protected WindowProperties(){
	}
	
	protected void loadProperties() throws IOException{
		Properties properties = new Properties();
		properties.load(new FileReader(getFileProperties()));
		loadWorkspace(properties);
		loadDividerPostion(properties);
	}
	
	protected void saveProperties() throws IOException{
		Properties properties = new Properties();
		properties.load(new FileReader(getFileProperties()));
		saveDividerPostion(properties);
		properties.store(new FileWriter(getFileProperties()), null);
		LOGGER.debug("Propriétés de la fenètre enregistrés");
	}
	
	private void loadDividerPostion(Properties properties){
		try{
			this.mainSplitLocation = Integer.parseInt(properties.getProperty(KEY_MAIN_SPLIT_LOCATION, "-1"));
		}catch(NumberFormatException e){
			LOGGER.warn("la position du main split n'a pas put etre chargé",e);
			this.mainSplitLocation = -1;
		}
		try{
			this.SecondSplitLocation = Integer.parseInt(properties.getProperty(KEY_SECOND_SPLIT_LOCATION, "-1"));
		}catch(NumberFormatException e){
			LOGGER.warn("la position du second split n'a pas put etre chargé",e);
			this.SecondSplitLocation = -1;
		}
		
	}
	
	private void saveDividerPostion(Properties properties){
		this.SecondSplitLocation = Window.getSecondSplit().getDividerLocation();
		this.mainSplitLocation = Window.getMainSplit().getDividerLocation();
		properties.setProperty(KEY_MAIN_SPLIT_LOCATION, String.valueOf(this.mainSplitLocation));
		properties.setProperty(KEY_SECOND_SPLIT_LOCATION, String.valueOf(this.SecondSplitLocation));
	}
	
	private void loadWorkspace(Properties properties) throws IOException{
		String workspaceFolder = properties.getProperty(
				KEY_WORKSPACE_PROPERTIES,DEFAULT_WORKSPACE);
		File folder = new File(workspaceFolder);
		if(!folder.exists()){
			File file = null;
			while(file==null){
				JFileChooser choose = new JFileChooser();
				choose.setMultiSelectionEnabled(false);
				choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				choose.showOpenDialog(Window.get());
				file = choose.getSelectedFile();
				if(file==null){
					int rep = JOptionPane.showConfirmDialog(
							Window.get(),
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
		if(!workspaceFolder.equals(DEFAULT_WORKSPACE)){
			Workspace.get().setFolder_path(workspaceFolder);
		}
	}

	/**
	 * @return
	 */
	private File getFileProperties(){
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

	/**
	 * @return the mainSplitLocation
	 */
	protected int getMainSplitLocation() {
		return mainSplitLocation;
	}

	/**
	 * @return the secondSplitLocation
	 */
	protected int getSecondSplitLocation() {
		return SecondSplitLocation;
	}

}
