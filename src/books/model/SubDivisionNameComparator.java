package books.model;

import java.util.Comparator;

import books.model.interfaces.ISubDivision;

public class SubDivisionNameComparator implements Comparator<ISubDivision> {

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(ISubDivision o1, ISubDivision o2) {
		String text1 = "";
		String number1 = "";
		char[] tab1 = o1.getName().toCharArray();
		for(int index = 0;index<tab1.length;index++){
			char c = tab1[index];
			if(Character.isDigit(c)){
				number1 +=String.valueOf(c);
			}else{
				text1+=String.valueOf(c);
			}
		}
		if(number1.length()==0){
			number1="0";
		}
		int nbr1 = Integer.parseInt(number1);
		
		String text2 = "";
		String number2 = "";
		char[] tab2 = o2.getName().toCharArray();
		for(int index = 0;index<tab2.length;index++){
			char c = tab2[index];
			if(Character.isDigit(c)){
				number2 +=String.valueOf(c);
			}else{
				text2+=String.valueOf(c);
			}
		}
		if(number2.length()==0){
			number2="0";
		}
		int nbr2 = Integer.parseInt(number2);
		int cmp = Integer.compare(nbr1, nbr2);
		if(cmp!=0){
			return cmp;
		}
		return text1.compareTo(text2);
	}

}
