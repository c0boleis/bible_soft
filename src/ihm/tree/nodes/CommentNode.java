package ihm.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import books.model.interfaces.IComment;
import books.model.interfaces.IReadable;

public class CommentNode extends DefaultMutableTreeNode 
implements IReadable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8285219244193209931L;
	
	private IComment text;
	
	public CommentNode(IComment t){
		super();
		this.text = t;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString(){
		return this.text.getName();
	}

	@Override
	public String read() {
		return this.text.read();
	}

	@Override
	public String read(String translation) {
		return this.text.read(translation);
	}
	
	public IComment getIComment(){
		return text;
	}

}
