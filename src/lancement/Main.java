package lancement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import bible_soft.model.Chapitre;
import bible_soft.model.Livre;
import bible_soft.model.Verset;
import bible_soft.recherche.CompteurDeMots;
import dico.VerbeCollector;

public class Main {


	static int nbrVersetAT = 0;
	static int nbrVersetNT = 0;
	static int nbrMot = 0;
	static String mot = "[\\W][E|e]mmanuel";
	static CompteurDeMots compteurDeMots = new CompteurDeMots();
	static List<Thread> ths = new ArrayList<Thread>();
	static HashMap<String, Integer> maps = new HashMap<String, Integer>();
	static final Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		//init logger
		System.setProperty("log4j.configuration", "log4Jconfig.properties");
		LOGGER.info("Start programme");
		//		System.out.println(CompteurDeMots.sansAccent("Cœur").split("[\\W&&[^œ]]")[0]);
		//		VerbeCollector.loadVerbe();
		/**********************/
		/** ANCIEN TESTAMENT **/
		/**********************/

		List<Livre> AncienTestament = new ArrayList<Livre>();

		/*
		 * PENTATEUQUE
		 */
		Livre livreGn = new Livre("Genèse", "Gn",true);
		livreGn.setAddrInternet("http://www.aelf.org/bible-liturgie/Gn/Livre+de+la+Gen%C3%A8se/chapitre/");
		AncienTestament.add(livreGn);
		Livre livreEx = new Livre("Exode", "Ex",true);
		livreEx.setAddrInternet("http://www.aelf.org/bible-liturgie/Ex/Livre+de+l'Exode/chapitre/");
		AncienTestament.add(livreEx);
		Livre livreLv = new Livre("Lévitique", "Lv",true);
		livreLv.setAddrInternet("http://www.aelf.org/bible-liturgie/Lv/Livre+du+L%C3%A9vitique/chapitre/");
		AncienTestament.add(livreLv);
		Livre livreNb = new Livre("Nombres", "Nb",true);
		livreNb.setAddrInternet("http://www.aelf.org/bible-liturgie/Nb/Livre+des+Nombres/chapitre/");
		AncienTestament.add(livreNb);
		Livre livreDt = new Livre("Deutéronome", "Dt",true);
		livreDt.setAddrInternet("http://www.aelf.org/bible-liturgie/Dt/Livre+du+Deut%C3%A9ronome/chapitre/");
		AncienTestament.add(livreDt);

		/*
		 * LIVRES HISTORIQUE
		 */
		Livre livreJos = new Livre("Josué", "Jos",true);
		livreJos.setAddrInternet("http://www.aelf.org/bible-liturgie/Jos/Livre+de+Josu%C3%A9/chapitre/");
		AncienTestament.add(livreJos);
		Livre livreJg = new Livre("Juges", "Jg",true);
		livreJg.setAddrInternet("http://www.aelf.org/bible-liturgie/Jg/Livre+des+Juges/chapitre/");
		AncienTestament.add(livreJg);
		Livre livreRuth = new Livre("Ruth", "Rt",true);
		livreRuth.setAddrInternet("http://www.aelf.org/bible-liturgie/Rt/Livre+de+Ruth/chapitre/");
		AncienTestament.add(livreRuth);
		Livre Livre1S = new Livre("1 Samuel", "1S",true);
		Livre1S.setAddrInternet("http://www.aelf.org/bible-liturgie/1S/Premier+livre+de+Samuel/chapitre/");
		AncienTestament.add(Livre1S);
		Livre Livre2S = new Livre("2 Samuel", "2S",true);
		Livre2S.setAddrInternet("http://www.aelf.org/bible-liturgie/2S/Deuxi%C3%A8me+livre+de+Samuel/chapitre/");
		AncienTestament.add(Livre2S);
		Livre Livre1R = new Livre("1 Roi", "1R",true);
		Livre1R.setAddrInternet("http://www.aelf.org/bible-liturgie/1R/Premier+livre+des+Rois/chapitre/");
		AncienTestament.add(Livre1R);
		Livre Livre2R = new Livre("2 roi", "2R",true);
		Livre2R.setAddrInternet("http://www.aelf.org/bible-liturgie/2R/Deuxi%C3%A8me+livre+des+Rois/chapitre/");
		AncienTestament.add(Livre2R);
		Livre Livre1Ch = new Livre("1 Chroniques", "1Ch",true);
		Livre1Ch.setAddrInternet("http://www.aelf.org/bible-liturgie/1Ch/Premier+livre+des+Chroniques/chapitre/");
		AncienTestament.add(Livre1Ch);
		Livre Livre2Ch = new Livre("2 Chroniques", "2Ch",true);
		Livre2Ch.setAddrInternet("http://www.aelf.org/bible-liturgie/2Ch/Deuxi%C3%A8me+livre+des+Chroniques/chapitre/");
		AncienTestament.add(Livre2Ch);
		Livre LivreEsd = new Livre("Esdras", "Esd",true);
		LivreEsd.setAddrInternet("http://www.aelf.org/bible-liturgie/Esd/Livre+d%27Esdras/chapitre/");
		AncienTestament.add(LivreEsd);
		Livre LivreNe = new Livre("Néhémie", "Ne",true);
		LivreNe.setAddrInternet("http://www.aelf.org/bible-liturgie/Ne/Livre+de+N%C3%A9h%C3%A9mie/chapitre/");
		AncienTestament.add(LivreNe);
		Livre LivreTb = new Livre("Tobie", "Tb",true);
		LivreTb.setAddrInternet("http://www.aelf.org/bible-liturgie/Tb/Livre+de+Tobie/chapitre/");
		AncienTestament.add(LivreTb);
		Livre LivreJdt = new Livre("Judith", "Jdt",true);
		LivreJdt.setAddrInternet("http://www.aelf.org/bible-liturgie/Jdt/Livre+de+Judith/chapitre/");
		AncienTestament.add(LivreJdt);
		Livre LivreEst = new Livre("Esther", "Est",true);
		LivreEst.setAddrInternet("http://www.aelf.org/bible-liturgie/Est/Livre+d%27Esther/chapitre/");
		AncienTestament.add(LivreEst);
		Livre Livre1M = new Livre("1 Macabées", "1M",true);
		Livre1M.setAddrInternet("http://www.aelf.org/bible-liturgie/1M/Premier+Livre+des+Martyrs+d%27Isra%C3%ABl/chapitre/");
		AncienTestament.add(Livre1M);
		Livre Livre2M = new Livre("2 Macabées", "2M",true);
		Livre2M.setAddrInternet("http://www.aelf.org/bible-liturgie/2M/Deuxi%C3%A8me+Livre+des+Martyrs+d%27Isra%C3%ABl/chapitre/");
		AncienTestament.add(Livre2M);

		/*
		 * LIVRES POETIQUES ET SAPIENTIAUX
		 */
		Livre LivreJb = new Livre("Job", "Jb",true);
		LivreJb.setAddrInternet("http://www.aelf.org/bible-liturgie/Jb/Livre+de+Job/chapitre/");
		AncienTestament.add(LivreJb);
		Livre livrePs = new Livre("Psaumes", "Ps",true);
		livrePs.setAddrInternet("http://www.aelf.org/bible-liturgie/Ps/Psaumes/chapitre/");
		AncienTestament.add(livrePs);
		Livre LivrePr = new Livre("Proverbes", "Pr",true);
		LivrePr.setAddrInternet("http://www.aelf.org/bible-liturgie/Pr/Livre+des+Proverbes/chapitre/");
		AncienTestament.add(LivrePr);
		Livre LivreQo = new Livre("Qohèleth", "Qo",true);
		LivreQo.setAddrInternet("http://www.aelf.org/bible-liturgie/Qo/L%27%C3%A9ccl%C3%A9siaste/chapitre/");
		AncienTestament.add(LivreQo);
		Livre LivreCt = new Livre("Cantique des Cantiques", "Ct",true);
		LivreCt.setAddrInternet("http://www.aelf.org/bible-liturgie/Ct/Cantique+des+cantiques/chapitre/");
		AncienTestament.add(LivreCt);
		Livre LivreSg = new Livre("Sagesse", "Sg",true);
		LivreSg.setAddrInternet("http://www.aelf.org/bible-liturgie/Sg/Livre+de+la+Sagesse/chapitre/");
		AncienTestament.add(LivreSg);
		Livre LivreSi = new Livre("Ben Sira", "Si",true);
		LivreSi.setAddrInternet("http://www.aelf.org/bible-liturgie/Si/Livre+de+Ben+Sirac+le+Sage/chapitre/");
		AncienTestament.add(LivreSi);

		/*
		 * Livres Prophétiques
		 */
		Livre LivreIs = new Livre("Isaïe", "Is",true);
		LivreIs.setAddrInternet("http://www.aelf.org/bible-liturgie/Is/Livre+d%27Isa%C3%AFe/chapitre/");
		AncienTestament.add(LivreIs);
		Livre LivreJr = new Livre("Jérémie", "Jr",true);
		LivreJr.setAddrInternet("http://www.aelf.org/bible-liturgie/Jr/Livre+de+J%C3%A9r%C3%A9mie/chapitre/");
		AncienTestament.add(LivreJr);
		Livre LivreLm = new Livre("Lamentations", "Lm",true);
		LivreLm.setAddrInternet("http://www.aelf.org/bible-liturgie/Lm/Livre+des+lamentations+de+J%C3%A9r%C3%A9mie/chapitre/");
		AncienTestament.add(LivreLm);
		Livre LivreBa = new Livre("Baruch", "Ba",true);
		LivreBa.setAddrInternet("http://www.aelf.org/bible-liturgie/Ba/Livre+de+Baruch/chapitre/");
		AncienTestament.add(LivreBa);
		Livre LivreEz = new Livre("Ezéchiel", "Ez",true);
		LivreEz.setAddrInternet("http://www.aelf.org/bible-liturgie/Ez/Livre+d%27Ezekiel/chapitre/");
		AncienTestament.add(LivreEz);
		Livre LivreDn = new Livre("Daniel", "Dn",true);
		LivreDn.setAddrInternet("http://www.aelf.org/bible-liturgie/Dn/Livre+de+Daniel/chapitre/");
		AncienTestament.add(LivreDn);
		Livre LivreOs = new Livre("Osée", "Os",true);
		LivreOs.setAddrInternet("http://www.aelf.org/bible-liturgie/Os/Livre+d%27Os%C3%A9e/chapitre/");
		AncienTestament.add(LivreOs);
		Livre LivreJl = new Livre("Joël", "Jl",true);
		LivreJl.setAddrInternet("http://www.aelf.org/bible-liturgie/Jl/Livre+de+Jo%C3%ABl/chapitre/");
		AncienTestament.add(LivreJl);
		Livre LivreAm = new Livre("Amos", "Am",true);
		LivreAm.setAddrInternet("http://www.aelf.org/bible-liturgie/Am/Livre+d%27Amos/chapitre/");
		AncienTestament.add(LivreAm);
		Livre livreAb = new Livre("Abdias", "Ab",true,true);
		livreAb.setAddrInternet("http://www.aelf.org/bible-liturgie/Ab/Livre+d%27Abdias");
		AncienTestament.add(livreAb);
		Livre LivreJon = new Livre("Jonas", "Jon",true);
		LivreJon.setAddrInternet("http://www.aelf.org/bible-liturgie/Jon/Livre+de+Jonas/chapitre/");
		AncienTestament.add(LivreJon);
		Livre LivreMi = new Livre("Michée", "Mi",true);
		LivreMi.setAddrInternet("http://www.aelf.org/bible-liturgie/Mi/Livre+de+Mich%C3%A9e/chapitre/");
		AncienTestament.add(LivreMi);
		Livre LivreNa = new Livre("Nahum", "Na",true);
		LivreNa.setAddrInternet("http://www.aelf.org/bible-liturgie/Na/Livre+de+Nahum/chapitre/");
		AncienTestament.add(LivreNa);
		Livre LivreHa = new Livre("Habaquq", "Ha",true);
		LivreHa.setAddrInternet("http://www.aelf.org/bible-liturgie/Ha/Livre+d%27Habaquc/chapitre/");
		AncienTestament.add(LivreHa);
		Livre LivreSo = new Livre("Sophonie", "So",true);
		LivreSo.setAddrInternet("http://www.aelf.org/bible-liturgie/So/Livre+de+Sophonie/chapitre/");
		AncienTestament.add(LivreSo);
		Livre LivreAg = new Livre("Aggée", "Ag",true);
		LivreAg.setAddrInternet("http://www.aelf.org/bible-liturgie/Ag/Livre+d%27Agg%C3%A9e/chapitre/");
		AncienTestament.add(LivreAg);
		Livre LivreZa = new Livre("Zacharie", "Za",true);
		LivreZa.setAddrInternet("http://www.aelf.org/bible-liturgie/Za/Livre+de+Zacharie/chapitre/");
		AncienTestament.add(LivreZa);
		Livre LivreMl = new Livre("Malachie", "Ml",true);
		LivreMl.setAddrInternet("http://www.aelf.org/bible-liturgie/Ml/Livre+de+Malachie/chapitre/");
		AncienTestament.add(LivreMl);


		/***********************/
		/** NOUVEAU TESTAMENT **/
		/***********************/


		List<Livre> NouveauTestament = new ArrayList<Livre>();

		/*
		 * EVANGILES
		 */

		Livre LivreMt = new Livre("Matthieu", "Mt");
		LivreMt.setAddrInternet("http://www.aelf.org/bible-liturgie/Mt/Evangile+de+J%C3%A9sus-Christ+selon+saint+Matthieu/chapitre/");
		NouveauTestament.add(LivreMt);
		Livre LivreMa = new Livre("Marc", "Mc");
		LivreMa.setAddrInternet("http://www.aelf.org/bible-liturgie/Mc/Evangile+de+J%C3%A9sus-Christ+selon+saint+Marc/chapitre/");
		NouveauTestament.add(LivreMa);
		Livre LivreLc = new Livre("Luc", "Lc");
		LivreLc.setAddrInternet("http://www.aelf.org/bible-liturgie/Lc/Evangile+de+J%C3%A9sus-Christ+selon+saint+Luc/chapitre/");
		NouveauTestament.add(LivreLc);
		Livre LivreJn = new Livre("Jean", "Jn");
		LivreJn.setAddrInternet("http://www.aelf.org/bible-liturgie/Jn/Evangile+de+J%C3%A9sus-Christ+selon+saint+Jean/chapitre/");
		NouveauTestament.add(LivreJn);

		Livre LivreAc = new Livre("Actes", "Ac");
		LivreAc.setAddrInternet("http://www.aelf.org/bible-liturgie/Ac/Livre+des+Actes+des+Ap%C3%B4tres/chapitre/");
		NouveauTestament.add(LivreAc);

		/*
		 * LETTRES PAULINIENNES
		 */
		Livre livreRm = new Livre("Romains", "Rm");
		livreRm.setAddrInternet("http://www.aelf.org/bible-liturgie/Rm/Lettre+de+saint+Paul+Ap%C3%B4tre+aux+Romains/chapitre/");
		NouveauTestament.add(livreRm);		
		Livre livre1Co = new Livre("1 Corinthiens", "1Co");
		livre1Co.setAddrInternet("http://www.aelf.org/bible-liturgie/1Co/Premi%C3%A8re+lettre+de+saint+Paul+Ap%C3%B4tre+aux+Corinthiens/chapitre/");
		NouveauTestament.add(livre1Co);
		Livre livre2Co = new Livre("2 Corinthiens", "2Co");
		livre2Co.setAddrInternet("http://www.aelf.org/bible-liturgie/2Co/Deuxi%C3%A8me+lettre+de+saint+Paul+Ap%C3%B4tre+aux+Corinthiens/chapitre/");
		NouveauTestament.add(livre2Co);		
		Livre livreGa = new Livre("Galates", "Ga");
		livreGa.setAddrInternet("http://www.aelf.org/bible-liturgie/Ga/Lettre+de+saint+Paul+Ap%C3%B4tre+aux+Galates/chapitre/");
		NouveauTestament.add(livreGa);		
		Livre livreEp = new Livre("Ephésiens", "Ep");
		livreEp.setAddrInternet("http://www.aelf.org/bible-liturgie/Ep/Lettre+de+saint+Paul+Ap%C3%B4tre+aux+Eph%C3%A9siens/chapitre/");
		NouveauTestament.add(livreEp);		
		Livre livrePh = new Livre("Philippiens", "Ph");
		livrePh.setAddrInternet("http://www.aelf.org/bible-liturgie/Ph/Lettre+de+saint+Paul+Ap%C3%B4tre+aux+Philippiens/chapitre/");
		NouveauTestament.add(livrePh);		
		Livre livreCol = new Livre("Colossiens", "Col");
		livreCol.setAddrInternet("http://www.aelf.org/bible-liturgie/Col/Lettre+de+saint+Paul+Ap%C3%B4tre+aux+Colossiens/chapitre/");
		NouveauTestament.add(livreCol);		
		Livre livre1Th = new Livre("1 Thessaloniciens", "1Th");
		livre1Th.setAddrInternet("http://www.aelf.org/bible-liturgie/1Th/Premi%C3%A8re+lettre+de+saint+Paul+Ap%C3%B4tre+aux+Thessaloniciens/chapitre/");
		NouveauTestament.add(livre1Th);		
		Livre livre2Th = new Livre("2 Thessaloniciens", "2Th");
		livre2Th.setAddrInternet("http://www.aelf.org/bible-liturgie/2Th/Deuxi%C3%A8me+lettre+de+saint+Paul+Ap%C3%B4tre+aux+Thessaloniciens/chapitre/");
		NouveauTestament.add(livre2Th);		
		Livre livre1Tm = new Livre("1 Timotée", "1Tm");
		livre1Tm.setAddrInternet("http://www.aelf.org/bible-liturgie/1Tm/Premi%C3%A8re+lettre+de+saint+Paul+Ap%C3%B4tre+%C3%A0+Timoth%C3%A9e/chapitre/");
		NouveauTestament.add(livre1Tm);		
		Livre livre2Tm = new Livre("2 Timotée", "2Tm");
		livre2Tm.setAddrInternet("http://www.aelf.org/bible-liturgie/2Tm/Deuxi%C3%A8me+lettre+de+saint+Paul+Ap%C3%B4tre+%C3%A0+Timoth%C3%A9e/chapitre/");
		NouveauTestament.add(livre2Tm);		
		Livre livreTt = new Livre("Tite", "Tt");
		livreTt.setAddrInternet("http://www.aelf.org/bible-liturgie/Tt/Lettre+de+saint+Paul+Ap%C3%B4tre+%C3%A0+Tite/chapitre/");
		NouveauTestament.add(livreTt);		
		Livre livrePhm = new Livre("Philémon", "Phm");
		livrePhm.setAddrInternet("http://www.aelf.org/bible-liturgie/Phm/Lettre+de+saint+Paul+Ap%C3%B4tre+%C3%A0+Phil%C3%A9mon/chapitre/");
		NouveauTestament.add(livrePhm);		
		Livre livreHe = new Livre("Hébreux", "He");
		livreHe.setAddrInternet("http://www.aelf.org/bible-liturgie/He/Lettre+aux+H%C3%A9breux/chapitre/");
		NouveauTestament.add(livreHe);	

		/*
		 * LETTRES CATHOLIQUES
		 */

		Livre livreJc = new Livre("Jacques", "Jc");
		livreJc.setAddrInternet("http://www.aelf.org/bible-liturgie/Jc/Lettre+de+saint+Jacques+Ap%C3%B4tre/chapitre/");
		NouveauTestament.add(livreJc);
		Livre livre1P = new Livre("1 Pierre", "1P");
		livre1P.setAddrInternet("http://www.aelf.org/bible-liturgie/1P/Premi%C3%A8re+lettre+de+saint+Pierre+Ap%C3%B4tre/chapitre/");
		NouveauTestament.add(livre1P);
		Livre livre2P = new Livre("2 Pierre", "2P");
		livre2P.setAddrInternet("http://www.aelf.org/bible-liturgie/2P/Deuxi%C3%A8me+lettre+de+saint+Pierre+Ap%C3%B4tre/chapitre/");
		NouveauTestament.add(livre2P);	
		Livre livre1Jn = new Livre("1 Jean", "1J");
		livre1Jn.setAddrInternet("http://www.aelf.org/bible-liturgie/1Jn/Premi%C3%A8re+lettre+de+saint+Jean/chapitre/");
		NouveauTestament.add(livre1Jn);	
		Livre livre2Jn = new Livre("2 Jean", "2J");
		livre2Jn.setAddrInternet("http://www.aelf.org/bible-liturgie/2Jn/Deuxi%C3%A8me+lettre+de+saint+Jean/chapitre/");
		NouveauTestament.add(livre2Jn);	
		Livre livre3Jn = new Livre("3 Jean", "3J");
		livre3Jn.setAddrInternet("http://www.aelf.org/bible-liturgie/3Jn/Troisi%C3%A8me+lettre+de+saint+Jean/chapitre/");
		NouveauTestament.add(livre3Jn);	
		Livre livreJude = new Livre("Jude", "Jude");
		livreJude.setAddrInternet("http://www.aelf.org/bible-liturgie/Jude/Lettre+de+saint+Jude/chapitre/");
		NouveauTestament.add(livreJude);	
		Livre livreAp = new Livre("Apocalypse", "Ap");
		livreAp.setAddrInternet("http://www.aelf.org/bible-liturgie/Ap/Livre+de+l%27Apocalypse/chapitre/");
		NouveauTestament.add(livreAp);		

		long timeStart = System.currentTimeMillis();
		System.out.println("/**********************/");
		System.out.println("/** ANCIEN TESTAMENT **/");
		System.out.println("**********************/");
		for(final Livre liv : AncienTestament){
			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						liv.load();
						int k = 0;
						Chapitre[] chapitres = liv.getChapitres();
						for(Chapitre chap : chapitres){
							k+=chap.getVersets().length;
							nbrVersetAT+=chap.getVersets().length;
							Verset[] versets = chap.getVersets();
							for(Verset ver : versets){
								//								ver.load();
								//								nbrMot+=ver.compteMotRegex(mot);
								nbrMot+=ver.compteMotRegex(mot);
								//								String[] tab = ver.getMots();
								//								for(String st : tab){
								//									compteurDeMots.addMots(st);
								//								}
							}
						}
						//						System.out.println("#### "+liv.getNom()+" à "+chapitres.length +" Chapitres et "+k+" versets");

					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("#### ERREUR : "+liv.getNom());
					}

				}
			});
			ths.add(th);
			th.run();

		}
		//		System.out.println("IL Y A "+nbrVersetAT+" DANS L'ANCIEN TESTAMENT");
		System.out.println("/***********************/");
		System.out.println("/** NOUVEAU TESTAMENT **/");
		System.out.println("/***********************/");
		for(final Livre liv : NouveauTestament){
			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						liv.load();
						int k = 0;
						Chapitre[] chapitres = liv.getChapitres();
						for(Chapitre chap : chapitres){
							k+=chap.getVersets().length;
							nbrVersetNT+=chap.getVersets().length;
							Verset[] versets = chap.getVersets();
							for(Verset ver : versets){
								//															ver.load();
								nbrMot+=ver.compteMotRegex(mot);
								//								String[] tab = ver.getMots();
								//								for(String st : tab){
								//									compteurDeMots.addMots(st);
								//								}
							}
						}
						//						System.out.println("#### "+liv.getNom()+" à "+liv.getChapitres().length +" Chapitres et "+k+" versets");
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("#### ERREUR : "+liv.getNom());
					}

				}

			});
			ths.add(th);
			th.run();
		}
		while(nbrThreadAlive()>0){
			try {
				Thread.sleep(100);
				Thread.yield();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long time = System.currentTimeMillis()-timeStart;
		int milli = (int) time  % 1000 ;
		int seconds = (int) (time / 1000) % 60 ;
		int minutes = (int) ((time / (1000*60)) % 60);
		int hours   = (int) ((time / (1000*60*60)) % 24);
		//		System.out.println("IL Y A "+nbrVersetNT+" DANS LE NOUVEAU TESTAMENT");
		//		System.out.println("------------------------------------------------");
		//		System.out.println("IL Y A "+(nbrVersetNT+nbrVersetAT)+" DANS LA BIBLE");
		System.out.println("IL Y A "+(nbrMot)+" FOIS LE MOT: \""+mot+"\" DANS LA BIBLE");
		compteurDeMots.sort();
		System.out.println("IL Y A "+(compteurDeMots.size())+" MOTS DIFFERENTS DANS LA BIBLE");
		compteurDeMots.print(30000);
		if(hours>0){
			System.out.println("temps de la recherche: "+hours+" h: "+minutes+" m: "+seconds+" s: "+milli+" milli");
		}else if(minutes>0){
			System.out.println("temps de la recherche: "+minutes+" m: "+seconds+" s: "+milli+" milli");
		}else{
			System.out.println("temps de la recherche: "+seconds+" s: "+milli+" milli");
		}

	}

	public static boolean isAlive(){
		for(Thread th : ths){
			if(th.isAlive()){
				return true;
			}
		}
		//		System.err.println(nbr+" thread alive");
		return false;
	}
	public static int nbrThreadAlive(){
		int nbr = 0;
		for(Thread th : ths){
			if(th.isAlive()){
				nbr++;
			}
		}
		//		System.err.println(nbr+" thread alive");
		System.err.flush();
		return nbr;
	}

}
