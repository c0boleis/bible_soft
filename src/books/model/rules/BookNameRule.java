/**
 * 
 */
package books.model.rules;

import books.model.interfaces.IRule;

/**
 * @author C.B.
 *
 */
public class BookNameRule implements IRule<String> {

	/* (non-Javadoc)
	 * @see books.model.rules.IRule#checkRule(java.lang.Object)
	 */
	@Override
	public String checkRule(String object) {
		if(object == null){
			return "Le nom est null";
		}
		if(object.trim().length()==0){
			return "Le nom est vide";
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see books.model.rules.IRule#modifyWithRule(java.lang.Object)
	 */
	@Override
	public String modifyWithRule(String object) {
		if(object == null){
			return null;
		}
		String tmp = object.trim();
		if(tmp.length()==0){
			return null;
		}
		return tmp;
	}

}
