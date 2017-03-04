package books.model.interfaces;

public interface ISubDivisonContainer {
	
	public static final String DEFAULT_HIERARCHY = "<folder>";
	
	public ISubDivision[] getSubDivisions();
	
	public void sortSubdivisions();
	
	public void loadSubDivisions();
	
	public boolean isSubDivisionEmpty();
	
	public ISubDivision getSubDivision(String name);
	
	public int getNbrOfTexts();
	
	public int calculNbrOfTexts();
	
	public boolean isAutoOpen();

}
