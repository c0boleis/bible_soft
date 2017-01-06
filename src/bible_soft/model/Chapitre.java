package bible_soft.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import books.model.interfaces.IPropertiesUsed;


public class Chapitre implements ILoadSaveOld{

	private Livre livre;

	private List<Verset> versets = new ArrayList<Verset>();

	private int numero = -1;

	private char cmpNumero = '\0';

	private boolean isSave = false;

	private boolean isLoad = false;

	public static final String CHAPITRE_NON_TRAD = "Chapitre non traduit";

	/**
	 * @param livre
	 */
	public Chapitre(Livre livre,int numero) {
		super();
		if(livre==null){
			throw new IllegalArgumentException("le livre ne peut pas etre null");
		}
		this.livre = livre;
		if(numero<0){
			throw new IllegalArgumentException("le numero chapitre doit etre sup. ou égal à 1");
		}
		this.numero = numero;
		if(!this.livre.addChapitre(this)){
			throw new IllegalArgumentException("le chapitre exist déjà dans le livre: "+livre.getNom()+".");
		}
	}

	public Chapitre(Livre livre,int numero,char cmp) {
		super();
		if(livre==null){
			throw new IllegalArgumentException("le livre ne peut pas etre null");
		}
		this.livre = livre;
		if(numero<=0){
			throw new IllegalArgumentException("le numero chapitre doit etre sup. ou égal à 1");
		}
		this.numero = numero;
		this.cmpNumero = cmp;
		if(!this.livre.addChapitre(this)){
			throw new IllegalArgumentException("le chapitre exist déjà dans le livre: "+livre.getNom()+".");
		}
	}

	/**
	 * 
	 * @return the livre
	 */
	public Livre getLivre(){
		return livre;
	}

	public Verset[] getVersets(){
		return versets.toArray(new Verset[0]);
	}

	protected boolean addVerset(Verset verset) {
		if(this.versets.contains(verset)){
			return false;
		}
		this.versets.add(verset);
		return true;
	}

	public int getNumero(){
		return numero;
	}

	public void fetchFromInternet() throws IOException, ParserConfigurationException, SAXException {
		String add = cmpNumero=='\0'?"":String.valueOf(cmpNumero);
		URL url = null;
		if(livre.isChapitreUnique()){
			url = new URL(getLivre().getAddrInternet());
		}else{
			url = new URL(getLivre().getAddrInternet()+getNumero()+add);
		}
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
		//		System.out.println(theString);
		if(theString.contains(CHAPITRE_NON_TRAD)){
			throw new IOException(CHAPITRE_NON_TRAD);
		}
		try{
			versets.clear();
			ParseString(theString);
			return;
		}catch (Exception e) {
			System.err.println("parse string erreur essai avec xml");
			ParseXml(theString);

		}


		//	    System.out.println(nodes.getLength());
	}

	@SuppressWarnings("unused")
	private void ParseXml(String text) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(text));

		Document doc = db.parse(is);
		NodeList nodes = doc.getElementsByTagName("span");
		int size = nodes.getLength();
		int number = -1;
		for(int index = 0; index<size;index++){
			org.w3c.dom.Node node = nodes.item(index);
			NamedNodeMap map = node.getAttributes();
			org.w3c.dom.Node cnt = map.getNamedItem("class");
			if(cnt==null){
				continue;
			}
			String txt = cnt.getTextContent();
			if(txt==null){
				continue;
			}
			if(txt.equals("numero_verset")){
				number = Integer.parseInt(node.getTextContent());
			}else if(txt.equals("content_verset")){
				Verset verset = new Verset(this, number);
				verset.setText(node.getTextContent());
				this.addVerset(verset);
			}
			System.out.println(txt);
		}
	}

	private void ParseString(String text){
		int indexBase = 0;
		String baliseNumVerset = "<span class=\"numero_verset\">";
		String baliseTextVerset = "<span class=\"content_verset\">";
		int indexNumVerset  = text.indexOf(baliseNumVerset);
		int indexTextVerset  = text.indexOf(baliseTextVerset);
		int nbrAbsent = 0;
		while(indexNumVerset>0 && indexTextVerset>0){
			String numero = null;
			String textverset = null;
			int indexFinNumVerset = text.indexOf("</span>",indexNumVerset);
			int indexFinTextVerset = text.indexOf("</span>",indexTextVerset);
			if(indexFinNumVerset<0){
				break;
			}
			if(indexFinTextVerset<0){
				break;
			}
			if(indexFinNumVerset>=indexFinTextVerset){
				numero = "0"+String.valueOf((char)(((int)'a')+nbrAbsent));
				nbrAbsent++;
//				int te = 10;
//				int min = Math.min(indexFinNumVerset, indexFinTextVerset);
//				int max = Math.max(indexFinNumVerset, indexFinTextVerset);
//				String textErreur = (min-te)+"["
//						+text.substring(min-te, max+te)
//						+"]"+(max+te);
//				System.out.println("\n");
//				String textValue = "{[indexNumVerset:"+indexNumVerset
//						+";indexFinNumVerset:"+indexFinNumVerset+"]"
//						+";[indexTextVerset:"+indexTextVerset
//						+";indexFinTextVerset:"+indexFinTextVerset+"]}";
//				throw new NullPointerException("position du text et du numéro incohérente(indexFinNumVerset>=indexFinTextVerset)\n"+textValue+"\n"+textErreur);
			}
			else{
				numero = text.substring(indexNumVerset+baliseNumVerset.length(), indexFinNumVerset);
			}
			indexBase = indexFinTextVerset+1;
			
			textverset = text.substring(indexTextVerset+baliseTextVerset.length(), indexFinTextVerset);
			if(numero != null && textverset!=null){
				int num = -1;
				Verset verset = null;
				try{
					num = Integer.parseInt(numero);
					verset = new Verset(this, num);
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
						verset = new Verset(this, num,st.charAt(0));
					}else{
						throw e;
					}
				}
				verset.setText(textverset);
				addVerset(verset);
			}else{
				break;
			}
			indexNumVerset  = text.indexOf(baliseNumVerset,indexBase);
			indexTextVerset  = text.indexOf(baliseTextVerset,indexBase);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String txt = "Chapitre [ numero=" + numero + "]\n";
		for(Verset v : versets){
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
				int numero = -1;
				try{
					numero = Integer.parseInt(nbr);
				}catch(NumberFormatException e){
					throw e;
				}
				
				Verset verset = null;
				if(st.length()==1){
					verset = new Verset(this, numero,st.charAt(0));
				}else{
					verset = new Verset(this, numero);
				}
				addVerset(verset);
				verset.load();
				if(!verset.isLoad()){
					loaded = false;
				}
			}catch(NumberFormatException e){
				e.printStackTrace();
				continue;
			}
		}
		isLoad = loaded;
		isSave = loaded;
		Collections.sort(versets,new Comparator<Verset>() {

			@Override
			public int compare(Verset o1, Verset o2) {
				return Integer.compare(o1.getNumero(), o2.getNumero());
			}
		});
	}

	@Override
	public void save() {
		File file = new File(getFolderPath());
		if(!file.exists()){
			file.mkdirs();
		}
		boolean saved = true;
		for(Verset verset : versets){
			verset.save();
			if(!verset.isSave()){
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
		String add = cmpNumero=='\0'?"":String.valueOf(cmpNumero);
		return this.getLivre().getFolderPath()+File.separator+LivreColector.getNumberString(numero)+add;
	}
	
	public int compteMot(String mot,boolean thread){
		if(thread){
			
		}else{
			int nbr = 0;
			for(Verset chap : versets){
				nbr+=chap.compteMot(mot);
			}
			return nbr;
		}
		return 0;
	}
	
	public void createPropertiesFile(){
		File file = new File(getFolderPath()+File.separator+"info.properties");
		if(file.exists()){
			return;
		}
		try {
			String add = "";
			if(cmpNumero!='\0'){
				add = String.valueOf(cmpNumero);
			}
			BufferedWriter buf = new BufferedWriter(new FileWriter(file));
			buf.write(IPropertiesUsed.KEY_NAME+"="+getNumero()+add+"\n");
			buf.write(IPropertiesUsed.KEY_ABV+"="+getNumero()+add+"\n");
			buf.write(IPropertiesUsed.KEY_HIERARCHY+"=Versets");
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
