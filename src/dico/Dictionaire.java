package dico;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bible_soft.recherche.CompteurDeMots;
import bible_soft.recherche.Mot;

public class Dictionaire {

	private static List<Mot> mots = new ArrayList<Mot>();

	public static void main(String[] args) {
		try {
			load();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Mot mot :mots){
			System.out.println(mot);
		}
	}

	public static void load() throws ParserConfigurationException, SAXException, IOException{
//		Verbe ver = new Verbe("abatardir");
//		ver.fetchFromInternet();
		
		String path = "/home/bata/xmlittre-data/";
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		for(int k = (int)'a';k<=(int)'z';k++){
			String pt = path+(char)k+".xml";
			System.out.println("file: "+pt);
			File fil = new File(pt);
			Document doc = db.parse(fil);
			NodeList listNode = doc.getElementsByTagName("entree");
			int size = listNode.getLength();
			for(int index = 0;index<size;index++){
				Node node = listNode.item(index);
				NamedNodeMap param = node.getAttributes();
				Node nodeName = param.getNamedItem("terme");
				if(nodeName==null){
					continue;
				}

				NodeList listNode2 = node.getChildNodes();
				int size2 = listNode2.getLength();
				for(int index2 = 0;index2<size2;index2++){
					Node node2 = listNode2.item(index2);
					if(node2.getNodeName().equals("entete")){
					}
					NodeList listNode3 = node2.getChildNodes();
					int size3 = listNode3.getLength();
					for(int index3 = 0;index3<size3;index3++){
						Node node3 = listNode3.item(index3);
						if(node3.getNodeName().equals("nature")){
							String nat = node3.getTextContent();
							if(nat.length()<2){
								continue;
							}
							if(nat.charAt(0)!='v'){
								continue;
							}
							if(nat.charAt(1)!='.'){
								continue;
							}
							if(nodeName.getTextContent().contains(",")){
								continue;
							}
							if(nodeName.getTextContent().contains(" ")){
								continue;
							}
							if(nodeName.getTextContent().contains("(")){
								continue;
							}
							if(nodeName.getTextContent().contains(")")){
								continue;
							}
							System.out.println("\tTerme: "+nodeName.getTextContent());

							Verbe v = new Verbe(CompteurDeMots.sansAccent(nodeName.getTextContent().toLowerCase().trim()));
							try{
								v.fetchFromInternet();
								v.save();
							}catch (Exception e) {
								e.printStackTrace();
							}
							
						}

					}

				}
			}
		}
	}

}
