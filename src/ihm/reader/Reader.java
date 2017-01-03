package ihm.reader;

import javax.swing.JTextArea;

import books.model.interfaces.IReadable;

public class Reader extends JTextArea{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4329895168805006962L;

	private IReadable readable;
	
	public Reader(IReadable div) {
		this.readable = div;
		load();
	}
	
	private void load(){
		this.setText(readable.read());
	}

}
