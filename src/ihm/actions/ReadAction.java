package ihm.actions;

import javax.swing.JScrollPane;

import books.IReadable;
import ihm.Window;
import ihm.reader.Reader;

public class ReadAction implements ActionPerso{
	
	private IReadable readable;

	public ReadAction(IReadable read) {
		this.readable = read;
	}

	@Override
	public void doAction() {
		Reader r = new Reader(this.readable);
		JScrollPane pane = new JScrollPane(r);
		Window.getTabbedPaneChapeter().add(readable.toString(), pane);
		
	}

}
