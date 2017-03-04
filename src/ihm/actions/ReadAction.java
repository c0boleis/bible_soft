package ihm.actions;

import javax.swing.JScrollPane;

import books.model.interfaces.IReadable;
import ihm.reader.Reader;
import ihm.tree.nodes.SubBookDivisionNode;
import ihm.tree.nodes.TextBookNode;
import ihm.window.Window;

public class ReadAction extends ActionPersoImplement{
	
	private IReadable readable;

	public ReadAction(IReadable read) {
		this.readable = read;
		if(this.readable instanceof SubBookDivisionNode){
			this.readable = 
					((SubBookDivisionNode) this.readable).getSubDivision();
		}else if(this.readable instanceof TextBookNode){
			this.readable = 
					((TextBookNode) this.readable).getIText();
		}
	}

	@Override
	public void doAction() {
		Reader r = new Reader(this.readable);
		JScrollPane pane = new JScrollPane(r);
		Window.getTabbedPaneChapeter().add(readable.toString(), pane);
		
	}

}
