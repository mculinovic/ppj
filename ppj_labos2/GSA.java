import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class GSA {
	
	
	static Map<String, String> akcija;
	static Map<String, String> novoStanje;
	static Map<String, Set<Stavka>> dodaj;
	static Map<Set<Stavka>, Set<Stavka>> okruzenje;
	static Map<String, Set<Integer>> produkcijeZnak;
	static Map<Set<Stavka>, Integer> mapaStanja;
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		Set<String> nezavrsniZnakovi = new HashSet<String>();
		Set<String> zavrsniZnakovi = new HashSet<String>();
		Set<String> sinkronizacijskiZnakovi = new HashSet<String>();
		Map<Integer, Produkcija> produkcije = new HashMap<Integer, Produkcija>();
		Map<String, Set<String>> tablicaZapocinje = new HashMap<String, Set<String>>();
		Set<String> prazniZnakovi = new HashSet<String>();
		String pocetnoStanje = new String();
		
		boolean procitaneProdukcije = false;
		int brojProdukcija = 1;
		String redak;
		String desnaStrana = new String();
		produkcijeZnak = new HashMap<String, Set<Integer>>();
		
		while(in.hasNextLine()) {
			if (!procitaneProdukcije) {
				redak = in.nextLine();
			} else {
				redak = desnaStrana;
			}
			procitaneProdukcije = false;
			
			char prviZnak = redak.charAt(0);
			char drugiZnak = redak.charAt(1);
			
			if (prviZnak == '%' && drugiZnak == 'V') {
				String[] nezavrsni = redak.split(" ");
				pocetnoStanje = nezavrsni[1];
				for (int i = 1; i < nezavrsni.length; ++i)
					nezavrsniZnakovi.add(nezavrsni[i]);
			}
			else if (prviZnak == '%' && drugiZnak == 'T') {
				String[] zavrsni = redak.split(" ");
				for (int i = 1; i < zavrsni.length; ++i)
					zavrsniZnakovi.add(zavrsni[i]);
			}
			else if (prviZnak == '%' && drugiZnak == 'S') {
				String[] sinkronizacijski = redak.split(" ");
				for (int i = 1; i < sinkronizacijski.length; ++i)
					sinkronizacijskiZnakovi.add(sinkronizacijski[i]);
			}
			else if (prviZnak == '<') {
				String lijevaStrana = redak.trim();
				
				

				while (in.hasNextLine()) {
					desnaStrana = in.nextLine();
					if (desnaStrana.charAt(0) != ' ') break;
					desnaStrana = desnaStrana.trim();
					produkcije.put(brojProdukcija, new Produkcija(lijevaStrana, desnaStrana));
					if (desnaStrana.equals("$")) prazniZnakovi.add(lijevaStrana);
					
					if (!produkcijeZnak.containsKey(lijevaStrana)) {
						Set<Integer> brojevi = new HashSet<Integer>();
						brojevi.add(brojProdukcija);
						produkcijeZnak.put(lijevaStrana, brojevi);
					}
					else {
						Set<Integer> brojevi = produkcijeZnak.get(lijevaStrana);
						brojevi.add(brojProdukcija);
						produkcijeZnak.put(lijevaStrana, brojevi);					
					}
					
					brojProdukcija++;
				}
				procitaneProdukcije = true;
			}
		}
		in.close();
		
		
		Produkcija pocetnaProdukcija = new Produkcija("<S'>", pocetnoStanje);
		produkcije.put(0, pocetnaProdukcija);
		zavrsniZnakovi.add("!");
		
		tablicaZapocinje = izracunajTablicuZapocinje(tablicaZapocinje, produkcije, zavrsniZnakovi, nezavrsniZnakovi, prazniZnakovi);
		for (Map.Entry<String, Set<String>> zapocinje: tablicaZapocinje.entrySet()) {
			Set<String> noviSet = zapocinje.getValue();
			noviSet.removeAll(nezavrsniZnakovi);
			zapocinje.setValue(noviSet);
		}
		
		akcija = new HashMap<String,String>();
	    novoStanje = new HashMap<String, String>();
	    dodaj = new HashMap<String, Set<Stavka>>();
	    okruzenje = new HashMap<Set<Stavka>, Set<Stavka>>();
	    mapaStanja = new HashMap<Set<Stavka>, Integer>();
		
		Automat automat = kreirajAutomat(produkcije, nezavrsniZnakovi, zavrsniZnakovi, tablicaZapocinje, prazniZnakovi);
		
		List<Set<Stavka>> stanja = automat.stanja;
		Map<String, Integer> prijelazi = automat.tablicaPrijelaza;		
		
		//kreiranje tablica
		for (Map.Entry<String, Integer> prijelaz: prijelazi.entrySet()) {
			String lijevaStrana = prijelaz.getKey();
			String[] elementi = lijevaStrana.split(",");
			if (nezavrsniZnakovi.contains(elementi[1]))
				novoStanje.put(lijevaStrana, "Stavi(" + prijelaz.getValue() + ")");
			else if (zavrsniZnakovi.contains(elementi[1]))
				akcija.put(lijevaStrana, "Pomakni(" + prijelaz.getValue() + ")");
		}
		
		int i = 0;
		for (Set<Stavka> stanje: stanja) {
			for (Stavka stavka: stanje) {
				int brProdukcije = stavka.getBrojProdukcije();
				int pozTocke = stavka.getPozicijaTocke();
				if (brProdukcije == 0 && pozTocke == 1) 
					akcija.put(i + ",!", "Prihvati");
				else {
					Produkcija produkcija = produkcije.get(brProdukcije);
					String[] znakoviProdukcije = produkcija.getDesnaStrana();
					if (znakoviProdukcije.length == pozTocke || znakoviProdukcije[0].equals("$")){
						Set<String> znakoviStavke = stavka.dohvatiZnakove();
						for (String znak: znakoviStavke) {
							String kljuc = i + "," + znak;
							String akcijaUTablici = new String();
							if (akcija.containsKey(kljuc)) {
								akcijaUTablici = akcija.get(kljuc);
								
								if (!akcijaUTablici.contains("Pomakni")) {
									String[] elementi = akcijaUTablici.split("[(|)]");
									int stariBrProdukcije = Integer.parseInt(elementi[1]);
									if (brProdukcije < stariBrProdukcije)
										akcija.put(i + "," + znak, "Reduciraj("+ brProdukcije + ")");
								}
							}
							else {
								akcija.put(i + "," + znak, "Reduciraj("+ brProdukcije + ")");
							}
						}
					}
				}
			}
			++i;
		}
		
		
		//serijalizacija
		DefinicijaSA definicija = new DefinicijaSA();
		definicija.setAkcija(akcija);
		definicija.setNovoStanje(novoStanje);
		definicija.setProdukcije(produkcije);
		definicija.setSinkronizacijskiZnakovi(sinkronizacijskiZnakovi);
		definicija.setZavrsniZnakovi(zavrsniZnakovi);
		
		try {
			FileOutputStream fout = new FileOutputStream("analizator/syntax_analizator.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(definicija);
			oos.close();
		}
		catch (Exception e) { e.printStackTrace(); }
	
	}

	private static Map<String, Set<String>> izracunajTablicuZapocinje(Map<String, Set<String>> tablicaZapocinje, 
			Map<Integer, Produkcija> produkcije, Set<String> zavrsniZnakovi, Set<String> nezavrsniZnakovi, Set<String> prazniZnakovi) {
		
		//odredivanje praznih znakova
		while (true) {
			boolean noviPrazanZnak = false;
			for (Map.Entry<Integer, Produkcija> p : produkcije.entrySet()) {
				String[] desnaStrana = p.getValue().getDesnaStrana();
				boolean jePrazan = true;
				for (int i = 0; i < desnaStrana.length; ++i) {
					if (!prazniZnakovi.contains(desnaStrana)) {
						jePrazan = false;
						break;
					}
				}
				if (jePrazan) {
					prazniZnakovi.add(p.getValue().getLijevaStrana());
					noviPrazanZnak = true;
				}
			}
			if (!noviPrazanZnak) break;
		}
		
		//upisivanje u tablicu relacije zapocinjeIzravnoZnakom
		
		for (Map.Entry<Integer, Produkcija> p: produkcije.entrySet()) {
			String[] desnaStrana = p.getValue().getDesnaStrana();
			if (!desnaStrana[0].equals("$")) {
				String lijevaStrana = p.getValue().getLijevaStrana();
				Set<String> zapocinje;
				if (tablicaZapocinje.containsKey(lijevaStrana))
					zapocinje = tablicaZapocinje.get(lijevaStrana);
				else
					zapocinje = new HashSet<String>();
				
				for (int i = 0; i < desnaStrana.length; ++i) {
					if (zavrsniZnakovi.contains(desnaStrana[i]) || nezavrsniZnakovi.contains(desnaStrana[i])) {
						zapocinje.add(desnaStrana[i]);
						tablicaZapocinje.put(lijevaStrana, zapocinje);
					}
					if (!prazniZnakovi.contains(desnaStrana[i])) break;
				}
			}
		}
		
		//upisivanje u tablicu relacije zapocinjeZnakom
		Map<String,Set<String>> novaTablica;
		while(true) {
			novaTablica = new HashMap<String,Set<String>>();
			for (Map.Entry<String, Set<String>> redakTablice: tablicaZapocinje.entrySet()) {
				Set<String> zapocinje = redakTablice.getValue();
				Set<String> zapocinjeNovo = new HashSet<String>(zapocinje);
				for (String znak: zapocinje) {
					if (tablicaZapocinje.containsKey(znak)) {
						zapocinjeNovo.addAll(tablicaZapocinje.get(znak));
					} 
				}
				novaTablica.put(redakTablice.getKey(), zapocinjeNovo);
			}
			if(novaTablica.equals(tablicaZapocinje)) break;
			tablicaZapocinje = new HashMap<String,Set<String>>(novaTablica);
		}
		
		return novaTablica;
	}

	private static Automat kreirajAutomat (Map<Integer, Produkcija> produkcije, Set<String> nezavrsniZnakovi,  Set<String> zavrsniZnakovi, 
			Map<String, Set<String>> tablicaZapocinje, Set<String> prazniZnakovi) {
		
		Automat automat = new Automat();
		Stavka pocetnaStavka = new Stavka(0, 0);
		pocetnaStavka.dodajZnak("!");
		Set<Stavka> pocetnoStanje = new HashSet<Stavka>();
		pocetnoStanje.add(pocetnaStavka);
		
		List<Set<Stavka>> stanja = automat.stanja; 
		Map<String, Integer> prijelazi = automat.tablicaPrijelaza;
		
		pocetnoStanje = izracunajOkruzenje(pocetnoStanje, produkcije, nezavrsniZnakovi, zavrsniZnakovi, tablicaZapocinje, prazniZnakovi, stanja);
		stanja.add(pocetnoStanje);
		mapaStanja.put(pocetnoStanje, 0);
		int brojStanja = 0;
		List<Set<Stavka>> pomocnaStanja = new ArrayList<Set<Stavka>>(stanja);
		
		int zadnjeDodano = 0;
		while (true) {
			boolean dodanoStanje = false;
			for (int i = zadnjeDodano; i < pomocnaStanja.size(); ++i) {
				Set<Stavka> set = pomocnaStanja.get(i);
				for (String znak: zavrsniZnakovi) {
					Set<Stavka> novoStanje = dodajPrijelaz(set, znak, produkcije, nezavrsniZnakovi, zavrsniZnakovi, tablicaZapocinje, prazniZnakovi, stanja, i);
					
					if (!novoStanje.isEmpty()) {
						if (!mapaStanja.containsKey(novoStanje))  {
							brojStanja++;
							stanja.add(novoStanje);
							mapaStanja.put(novoStanje, brojStanja);
							dodanoStanje = true;
							prijelazi.put(i + "," + znak, stanja.size() - 1);
						}
						else {
							prijelazi.put(i + "," + znak, mapaStanja.get(novoStanje));
						}
					}
				}
				
				for (String znak: nezavrsniZnakovi) {
					Set<Stavka> novoStanje = dodajPrijelaz(set, znak, produkcije, nezavrsniZnakovi, zavrsniZnakovi, tablicaZapocinje, prazniZnakovi, stanja, i);
					
					if (!novoStanje.isEmpty()) {
						if (!mapaStanja.containsKey(novoStanje)) {
							brojStanja++;
							stanja.add(novoStanje);
							mapaStanja.put(novoStanje, brojStanja);
							dodanoStanje = true;
							prijelazi.put(i + "," + znak, stanja.size() - 1);
						}
						else {
							prijelazi.put(i + "," + znak, mapaStanja.get(novoStanje));
						}
					}
				}
			}
			
			pomocnaStanja = new ArrayList<Set<Stavka>>(stanja);
			if (!dodanoStanje) break;
			zadnjeDodano++;
		}
		
		return automat;
	}

	private static Set<Stavka> dodajPrijelaz(Set<Stavka> set, String znak, Map<Integer, Produkcija> produkcije, Set<String> nezavrsniZnakovi,  
			Set<String> zavrsniZnakovi, Map<String, Set<String>> tablicaZapocinje, Set<String> prazniZnakovi, List<Set<Stavka>> stanja, int key) {
		
		String kljuc = key + "," + znak;
		if (dodaj.containsKey(kljuc)) {
			return dodaj.get(kljuc);
		}
		
		Set<Stavka> noviSet = new HashSet<Stavka>();
		for (Stavka stavka: set) {
			Produkcija produkcija = produkcije.get(stavka.getBrojProdukcije());
			String[] desnaStrana = produkcija.getDesnaStrana();
			int pozicijaTocke = stavka.getPozicijaTocke();
			if (pozicijaTocke < desnaStrana.length && desnaStrana[pozicijaTocke].equals(znak)) {
				noviSet.add(new Stavka(stavka.getBrojProdukcije(), pozicijaTocke + 1, stavka.dohvatiZnakove()));
			}
		}
		
		if (noviSet.isEmpty()) return noviSet;
		
		dodaj.put(kljuc, izracunajOkruzenje(noviSet, produkcije, nezavrsniZnakovi, zavrsniZnakovi, tablicaZapocinje, prazniZnakovi, stanja));
		return dodaj.get(kljuc);
	}

	private static Set<Stavka> izracunajOkruzenje(Set<Stavka> stanje, Map<Integer, Produkcija> produkcije, Set<String> nezavrsniZnakovi, 
			Set<String> zavrsniZnakovi, Map<String, Set<String>> tablicaZapocinje, Set<String> prazniZnakovi, List<Set<Stavka>> stanja) {

		
		if (okruzenje.containsKey(stanje)) {
			return okruzenje.get(stanje);
		}
		Set<Stavka> kljuc = new HashSet<Stavka>(stanje);
		
		Set<Stavka> pomocnoStanje = new HashSet<Stavka>(stanje);
		
		while (true) {
			for (Stavka stavka: pomocnoStanje) {
				Produkcija produkcija = produkcije.get(stavka.getBrojProdukcije());
				String[] desnaStrana = produkcija.getDesnaStrana();
				int pozicijaTocke = stavka.getPozicijaTocke();
				if (pozicijaTocke < desnaStrana.length && nezavrsniZnakovi.contains(desnaStrana[pozicijaTocke])) {

					Set<Integer> brojeviProdukcija = produkcijeZnak.get(desnaStrana[pozicijaTocke]);
					List<String> desnoOdTocke = new ArrayList<String>();					
					for (int i = pozicijaTocke + 1; i < desnaStrana.length; ++i) {
						desnoOdTocke.add(desnaStrana[i]);
					}
					for (Integer brojProdukcije: brojeviProdukcija) {
						Stavka novaStavka = new Stavka(brojProdukcije, 0);
						
						Set<String> znakoviStavke = stavka.dohvatiZnakove();
						Set<String> noviZnakoviStavke = new HashSet<String>();
						
						for (String znak: znakoviStavke) {
							desnoOdTocke.add(znak);
							noviZnakoviStavke.addAll(zapocinje(desnoOdTocke, nezavrsniZnakovi, zavrsniZnakovi, tablicaZapocinje, prazniZnakovi));
							desnoOdTocke.remove(desnoOdTocke.size() - 1);
						}
						for(String znak: noviZnakoviStavke) {
							novaStavka.dodajZnak(znak);
						}		
						
						if (!stanje.contains(novaStavka))
							stanje.add(novaStavka);
						
					}
				}
			}
			
			if (pomocnoStanje.equals(stanje)) break;
			pomocnoStanje = new HashSet<Stavka>(stanje);
		}
			
		okruzenje.put(kljuc, stanje);
		return stanje;
	}

	private static Set<String> zapocinje(List<String> desnoOdTocke, Set<String> nezavrsniZnakovi, Set<String> zavrsniZnakovi, 
			Map<String, Set<String>> tablicaZapocinje, Set<String> prazniZnakovi) {
		
		Set<String> pocetniZnakovi = new HashSet<String>();
	
		for (String znak: desnoOdTocke) {
			if (zavrsniZnakovi.contains(znak)) {
				pocetniZnakovi.add(znak);
			}
			else {
				if (tablicaZapocinje.containsKey(znak))
					pocetniZnakovi.addAll(tablicaZapocinje.get(znak));
			}
			
			if (!prazniZnakovi.contains(znak))
				return pocetniZnakovi;
		}
		return pocetniZnakovi;
	}
	
}
