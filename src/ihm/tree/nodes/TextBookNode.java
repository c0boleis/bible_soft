package ihm.tree.nodes;

import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import books.model.interfaces.ILoadSaveObject;
import books.model.interfaces.IOrderedObject;
import books.model.interfaces.IReadable;
import books.model.interfaces.IShearable;
import books.model.interfaces.IShearchMatch;
import books.model.interfaces.IText;

public class TextBookNode extends DefaultMutableTreeNode 
implements IReadable,IShearable,ILoadSaveObject,IOrderedObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8285219244193209931L;
	
	private IText text;
	
	public TextBookNode(IText t){
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
	public IShearchMatch[] shearch(String regex) {
		return this.text.shearch(regex);
	}

	@Override
	public IShearchMatch[] shearch(String regex, String translation) {
		return this.text.shearch(regex, translation);
	}

	@Override
	public String read(String translation) {
		return this.text.read(translation);
	}
	
	public IText getIText(){
		return text;
	}

	@Override
	public boolean isSave() {
		return this.text.isSave();
	}

	@Override
	public void save() throws IOException {
		this.text.save();
		
	}

	@Override
	public boolean isLoad() {
		return this.text.isLoad();
	}

	@Override
	public void load() throws IOException {
		this.text.load();
		
	}

	@Override
	public String getFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilePath(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getOrder() {
		return this.text.getOrder();
	}

	@Override
	public void setOrder(int order) {
		this.text.setOrder(order);
	}

}
