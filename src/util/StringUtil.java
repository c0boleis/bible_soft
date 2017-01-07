package util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {
	public static String sansAccent(String s) 
	{

		String strTemp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(strTemp)
				.replaceAll("")
				.replaceAll("œ", "oe");
	}
}
