package ihm.dialogue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import books.model.interfaces.IRule;
import books.model.rules.DefaultRule;
import ihm.window.Window;

public class TextPane {

	private JTextField textField;
	
	private JLabel labelErreur;
	
	private IRule<String> rule;
	
	private String defaultValue = null;
	
	public TextPane(IRule<String> rule){
		if(rule!=null){
			this.rule = rule;
		}else{
			this.rule = new DefaultRule();
		}
	}
	public TextPane(IRule<String> rule,String defaultVal){
		this(rule);
		this.defaultValue = defaultVal;
	}


	private JPanel getPanelSearch(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getLabelErreur(), BorderLayout.WEST);
		panel.add(getTextField(),BorderLayout.CENTER);
		
		return panel;
	}

	public String getSearchRegex(){
		int rep = JOptionPane.showConfirmDialog(Window.get(),
				  getPanelSearch(),
                  "Recherche",
                  JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.PLAIN_MESSAGE);
		if(rep!=JOptionPane.OK_OPTION){
			return null;
		}
		String text = getTextField().getText();
		String error = rule.checkRule(text);
		if(error!=null){
			JOptionPane.showMessageDialog(Window.get(), 
					error,
					"ERREUR",
					JOptionPane.ERROR_MESSAGE);
			return getSearchRegex();
		}
		return rule.modifyWithRule(text);
	}
	/**
	 * @return the textField
	 */
	private JTextField getTextField() {
		if(textField == null){
			textField = new JTextField();
			if(this.defaultValue!=null){
				textField.setText(defaultValue);
			}
			textField.addCaretListener(new CaretListener() {
				
				@Override
				public void caretUpdate(CaretEvent arg0) {
					checkError();
				}
			});
			
		}
		return textField;
	}

	/**
	 * @return the labelErreur
	 */
	public JLabel getLabelErreur() {
		if(labelErreur == null){
			labelErreur = new JLabel("");
		}
		return labelErreur;
	}
	
	private void checkError(){
		String error = rule.checkRule(getTextField().getText());
		if(error!=null){
			getLabelErreur().setText("ERREUR");
			getLabelErreur().setForeground(Color.RED);
		}else{
			getLabelErreur().setText("OK");
			getLabelErreur().setForeground(Color.GREEN);
		}
		
	}
}
