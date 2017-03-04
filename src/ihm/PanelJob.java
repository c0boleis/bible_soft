package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PanelJob extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JProgressBar progressBar;
	
	private JLabel labelProgess;
	
	public PanelJob(){
		this.setLayout(new BorderLayout());
		this.add(getProgressBar(), BorderLayout.CENTER);
		this.add(getLabelProgess(),BorderLayout.WEST);
	}
	
	/**
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		if(progressBar == null){
			progressBar = new JProgressBar();
		}
		return progressBar;
	}

	public void work() {
		getProgressBar().setValue(getProgressBar().getValue()+1);
		
	}

	public void initValueToLoad(int valueToLoad) {
		getProgressBar().setMinimum(0);
		getProgressBar().setMaximum(valueToLoad);
		getProgressBar().setValue(0);
		getLabelProgess().setText("En cours");
		
	}
	
	public void finish(){
		getLabelProgess().setText("Terminé");
	}

	/**
	 * @return the labelProgess
	 */
	public JLabel getLabelProgess() {
		if(labelProgess == null){
			labelProgess = new JLabel("");
			labelProgess.setPreferredSize(new Dimension(100, 10));
		}
		return labelProgess;
	}

}
