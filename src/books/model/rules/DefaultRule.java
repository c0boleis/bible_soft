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
public class DefaultRule implements IRule<String> {

	/* (non-Javadoc)
	 * @see books.model.rules.IRule#checkRule(java.lang.Object)
	 */
	@Override
	public String checkRule(String object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see books.model.rules.IRule#modifyWithRule(java.lang.Object)
	 */
	@Override
	public String modifyWithRule(String object) {
		return object;
	}

}
