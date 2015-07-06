import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

/**
 * @author mculinovic
 * 
 * GLA - generator leksickog analizatora
 * Razred sluzi za generiranje leksickog analizatora
 * prema definiciji koju prima iz ulazne datoteke
 * 
 * Izlaz generatora je datoteka u koju je spremljena
 * definicija leksickog analizatora
 */
public class GLA {
	
	public static void main(String[] args) throws IOException {
		
		Scanner in = new Scanner (System.in);
		
		//deklaracija struktura
		DefinicijaLA definicijaLA = new DefinicijaLA();
		Map<String, String> regularneDefinicije = new HashMap<String, String>();
		List<String> stanjaAnalizatora = new ArrayList<String>();
		Set<String> leksickeJedinke = new HashSet<String>();
		
		//citanje ulaznih podataka
		String redak = new String();
		
		while (in.hasNextLine()) {
			redak = in.nextLine();
			
			char prviZnak = redak.charAt(0);
			char drugiZnak = redak.charAt(1);
			
			if (prviZnak == '{') {	
				String[] definicija = redak.split(" ");
				definicija = procistiRegularniIzraz(definicija, regularneDefinicije);
				regularneDefinicije.put(definicija[0], definicija[1]);
				
			}
			else if (prviZnak == '%' && drugiZnak == 'X') {
				String[] stanja = redak.split(" ");
				for (int i = 1; i < stanja.length; ++i) 
					stanjaAnalizatora.add(stanja[i]);
				
			}
			else if (prviZnak == '%' && drugiZnak == 'L') {
				String[] imena = redak.split(" ");
				for (int i = 1; i < imena.length; ++i) 
					leksickeJedinke.add(imena[i]);
				
			}
			else if (prviZnak == '<') {
				Pravilo pravilo = new Pravilo();
				String[] parametriPravila = redak.split(">",2);
				parametriPravila[0] = parametriPravila[0].substring(1);
				pravilo.setStanje(parametriPravila[0]);
				parametriPravila = procistiRegularniIzraz(parametriPravila, regularneDefinicije);
				pravilo.setRegularniIzraz(parametriPravila[1]);
				redak = in.nextLine();
				redak = in.nextLine().trim();
				pravilo.dodajArgument(redak);
				redak = in.nextLine();
				while (redak.charAt(0) != '}') {
					pravilo.dodajArgument(redak);
					redak = in.nextLine();
				}
				definicijaLA.dodajPravilo(pravilo);
				
			}
		}		
		in.close();
		
		definicijaLA.dodajStanja(stanjaAnalizatora);
		
		//serijalizacija
		try {
			FileOutputStream fout = new FileOutputStream("analizator/lex_analizator.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(definicijaLA);
			oos.close();
		}
		catch (Exception e) { e.printStackTrace(); }
		
	}


	/**
	 * Metoda pojednostavljuje regularni izraz primljene regularne definicije
	 * Nakon zavrsetka metode u regularnom izrazu svi reference na regularne definicije
	 * zamijenjene su regularnim izrazima tih definicija
	 * @param definicija definicija[0] je ime regularne definicije
	 * 					 definicija[1] je regularni izraz
	 * @param regularneDefinicije mapa u kojoj su spremljene sve regularne definicije
	 * @return vraca se argument definicija kojemu je pojednostavljen regularni izraz (definicija[1])
	 */
	private static String[] procistiRegularniIzraz(String[] definicija, Map<String, String> regularneDefinicije) {
		
		String regularniIzraz = definicija[1];
		
		for (Map.Entry<String, String> regularnaDefinicija: regularneDefinicije.entrySet()) {
			if (regularniIzraz.contains(regularnaDefinicija.getKey())) {
				int pocetniIndeks = regularniIzraz.indexOf(regularnaDefinicija.getKey());
				int duljinaDefinicije =  regularnaDefinicija.getKey().length();
				while (pocetniIndeks != -1) {
					String noviIzraz = regularniIzraz.substring(0, pocetniIndeks) + "(" + regularnaDefinicija.getValue() + ")"
										+ regularniIzraz.substring(pocetniIndeks + duljinaDefinicije, regularniIzraz.length());
					regularniIzraz = noviIzraz;
					pocetniIndeks = regularniIzraz.indexOf(regularnaDefinicija.getKey());
				}
			}
		}
		definicija[1] = regularniIzraz;
		return definicija;
	}
}
