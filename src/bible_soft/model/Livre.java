package bible_soft.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

import books.model.IPropertiesUsed;

public class Livre implements ILoadSave{

	private String nom;

	private String abv;

	private List<Chapitre> chapitres = new ArrayList<Chapitre>();

	private String addrInternet = null;

	private boolean isSave = false;

	private boolean isLoad = false;
	
	private boolean chapitreUnique = false;
	
	private boolean isAncienTestament = false;

	/**
	 * @param nom
	 * @param abv
	 */
	public Livre(String nom, String abv,boolean at) {
		super();
		this.nom = nom;
		this.abv = abv;
		this.isAncienTestament = at;
	}
	
	/**
	 * @param nom
	 * @param abv
	 */
	public Livre(String nom, String abv) {
		super();
		this.nom = nom;
		this.abv = abv;
		this.isAncienTestament = false;
	}
	
	/**
	 * @param nom
	 * @param abv
	 */
	public Livre(String nom, String abv,boolean at,boolean chapUnique) {
		this(nom,abv,at);
		this.chapitreUnique = chapUnique;
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
		if(chapitreUnique){
			this.chapitres.clear();
			Chapitre chapitre = new Chapitre(this, 0);
			chapitre.fetchFromInternet();
			System.out.println("\n-- ["+this.getNom()+"] 1 Chapitre fetch --");
			return;
		}
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
		File folder = new File(getFolderPath());
		if(!folder.exists()){
			return;
		}
		File[] files = folder.listFiles();
		boolean loaded = true;
		for(File file : files){
			if(!folder.isDirectory()){
				continue;
			}
			if(file.getName().contains("info.properties")){
				continue;
			}
			String name = file.getName();
			try{
				String st = "";
				String nbr = "";
				char[] tmp = name.toCharArray();
				for(int index = 0;index<tmp.length;index++){
					if(Character.isDigit(tmp[index])){
						nbr+=tmp[index];
					}else{
						st+=tmp[index];
					}
				}
				int numero = Integer.parseInt(nbr);
				Chapitre chapitre = null;
				if(st.length()==1){
					chapitre = new Chapitre(this, numero,st.charAt(0));
				}else{
					chapitre = new Chapitre(this, numero);
				}
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
		if(isAncienTestament){
			return LivreColector.pathFolderAncienTestament+File.separator+this.getAbv();
		}else{
			return LivreColector.pathFolderNouveauTestament+File.separator+this.getAbv();
		}
		
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
	
	public boolean isChapitreUnique(){
		return chapitreUnique;
	}
	
	public void createPropertiesFile(){
		File file = new File(getFolderPath()+File.separator+"info.properties");
		if(file.exists()){
			return;
		}
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(file));
			buf.write(IPropertiesUsed.KEY_NAME+"="+getNom()+"\n");
			buf.write(IPropertiesUsed.KEY_ABV+"="+getAbv()+"\n");
			buf.write(IPropertiesUsed.KEY_HIERARCHY+"=Chapitres");
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public int compteMot(final String mot,boolean thread){
//		if(thread){
//			int nbr=0;
//			for(Chapitre chap : chapitres){
//				Thread th = new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						nbr+=chap.compteMot(mot, false);
//					}
//				});
//				
//			}
//		}else{
//			int nbr=0;
//			for(Chapitre chap : chapitres){
//				nbr+=chap.compteMot(mot, false);
//			}
//		}
//	}
}
