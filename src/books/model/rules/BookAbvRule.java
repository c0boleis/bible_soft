/**
 * 
 */
package books.model.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import books.model.interfaces.IRule;

/**
 * @author C.B.
 *
 */
public class BookAbvRule implements IRule<String> {

	/* (non-Javadoc)
	 * @see books.model.rules.IRule#checkRule(java.lang.Object)
	 */
	@Override
	public String checkRule(String object) {
		if(object == null){
			return "L'abréviation est null";
		}
		String tmp = object.trim();
		if(tmp.length()==0){
			return "L'abréviation est vide";
		}
		Pattern pattern = Pattern.compile("/s");
		Matcher matcher = pattern.matcher(tmp);
		if(matcher.find()){
			return "L'abréviation contient des espace";
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
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(tmp);
		if(matcher.find()){
			return null;
		}
		return tmp;
	}

}
