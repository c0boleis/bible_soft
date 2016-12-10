package bible_soft.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

public class Livre implements ILoadSave{

	private String nom;

	private String abv;

	private List<Chapitre> chapitres = new ArrayList<Chapitre>();

	private String addrInternet = null;

	private boolean isSave = false;

	private boolean isLoad = false;

	/**
	 * @param nom
	 * @param abv
	 */
	public Livre(String nom, String abv) {
		super();
		this.nom = nom;
		this.abv = abv;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the abv
	 */
	public String getAbv() {
		return abv;
	}

	/**
	 * @return the chapitres
	 */
	public Chapitre[] getChapitres() {
		return chapitres.toArray(new Chapitre[0]);
	}

	protected boolean addChapitre(Chapitre chapitre) {
		if(chapitres.contains(chapitre)){
			return false;
		}
		this.chapitres.add(chapitre);
		return true;
	}

	/**
	 * @return the addrInternet
	 */
	public String getAddrInternet() {
		return addrInternet;
	}

	/**
	 * @param addrInternet the addrInternet to set
	 */
	public void setAddrInternet(String addrInternet) {
		this.addrInternet = addrInternet;
	}

	public void fetchFromInternet() throws IOException, ParserConfigurationException, SAXException {
		char add = '\0';
		System.out.println("--------------------------------");
		System.out.print(getNom()+":\t");
		List<String> list = getListChapitreFromInternet();
		for(String numero : list){
			int num = -1;
			Chapitre chapitre = null;
			try{
				num = Integer.parseInt(numero);
				chapitre = new Chapitre(this, num);
			}catch(NumberFormatException e){
				String st = "";
				String nbr = "";
				char[] tmp = numero.toCharArray();
				for(int index = 0;index<tmp.length;index++){
					if(Character.isDigit(tmp[index])){
						nbr+=tmp[index];
					}else{
						st+=tmp[index];
					}
				}
				num = Integer.parseInt(nbr);
				if(st.length()==1){
					chapitre = new Chapitre(this, num,st.charAt(0));
				}else{
					throw e;
				}
			}
			addChapitre(chapitre);
			try {
				chapitre.fetchFromInternet();
			} catch (IOException e) {
				e.printStackTrace();
				break;

			}
			System.out.print(numero+";");
		}
		System.out.println("\n-- ["+this.getNom()+"] "+list.size()+" Chapitres fetchs --");
	}


	private List<String> getListChapitreFromInternet() throws IOException{
		List<String> listOut = new ArrayList<>();
		URL url = new URL(this.getAddrInternet());
		URLConnection con = url.openConnection();
		con.addRequestProperty("User-Agent", 
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		//		String body = IOUtils.toString(in, encoding);
		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer, encoding);
		String theString = writer.toString();
		final String textBalise = "<option value=\"";
		int indexDeb = theString.indexOf(textBalise);
		int indexFin = -1;
		while(indexDeb>0){
			indexFin = theString.indexOf("\"", indexDeb+textBalise.length());
			String text = theString.substring(indexDeb+textBalise.length(), indexFin);
			listOut.add(text.trim());
			indexDeb = theString.indexOf(textBalise,indexFin+1);
		}

		return listOut;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String txt = "Livre [ nom=" + nom + "]\n";
		for(Chapitre v : chapitres){
			txt+=v.toString()+"\n";
		}
		return txt;
	}

	@Override
	public void load() {
		File file = new File(getFolderPath());
		if(!file.exists()){
			return;
		}
		File[] files = file.listFiles();
		boolean loaded = true;
		for(File ver : files){
			if(!file.isDirectory()){
				continue;
			}
			String name = ver.getName();
			try{
				int numero = Integer.parseInt(name);
				Chapitre chapitre = new Chapitre(this, numero);
				addChapitre(chapitre);
				chapitre.load();
				if(!chapitre.isLoad()){
					loaded = false;
				}
			}catch(NumberFormatException e){
				e.printStackTrace();
				continue;
			}
		}
		isLoad = loaded;
		isSave = loaded;

	}

	@Override
	public void save() {
		File file = new File(getFolderPath());
		if(!file.exists()){
			file.mkdirs();
		}
		boolean saved = true;
		for(Chapitre chapitre : chapitres){
			chapitre.save();
			if(!chapitre.isSave()){
				saved = false;
			}
		}
		isSave = saved;
	}

	@Override
	public boolean isLoad() {
		return isLoad;
	}

	@Override
	public boolean isSave() {
		return isSave;
	}

	public String getFolderPath(){
		return LivreColector.pathFolderLivre+File.separator+this.getAbv();
	}

	public Chapitre createChapitre(int i) {
		new Chapitre(this, i);
		for(Chapitre ca : chapitres){
			if(ca.getNumero()==i){
				return ca;
			}
		}
		return null;
	}
}
