import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
//import java.util.Scanner;


/**
 * @author mculinovic
 * 
 * LA - leksicki analizator
 * 
 * Razred cita izvorni kod iz datoteke te nad njim
 * obavlja leksicku analizu
 * 
 * Pronadene leksicke jedinke ispisuju se na standardan izlaz
 */
public class LA {
	
	public static void main(String[] args) throws IOException{
		
		//Scanner in = new Scanner (System.in);
		
		//ucitavanje izvornog koda iz datoteke
		String izvorniKod = new String();
		
		/*while (in.hasNextLine()) {
			String redak = in.nextLine();
			redak += Character.toString('\n');
			izvorniKod += redak;
		}*/
		
		while (System.in.available() > 0) {
			char ulazniZnak = (char) System.in.read();
			izvorniKod += Character.toString(ulazniZnak);
		}
		
		DefinicijaLA definicija = new DefinicijaLA();
		String trenutnoStanje;
		int brojacRedova;
		int zavrsetak;
		int indexPravila;
		
		indexPravila = -1;
		brojacRedova = 1;
		
		//deserijalizacija - dohvacanje objekta definicije leksickog analizatora
		try {
			FileInputStream fin = new FileInputStream("lex_analizator.dat");
			ObjectInputStream ois = new ObjectInputStream(fin);
			definicija = (DefinicijaLA) ois.readObject();
			ois.close();
		}
		catch (Exception e) {e.printStackTrace(); }
		
		//analiza datoteke
		trenutnoStanje = definicija.getStanjeAt(0);
		int brojAutomata = definicija.getBrojPravila();
		int duljinaIzvornogKoda = izvorniKod.length();
		
		for (zavrsetak = 0; zavrsetak < duljinaIzvornogKoda; ++zavrsetak) {
			
			int najduziNiz = 0;
			indexPravila = -1;
			
			for (int i = 0; i < brojAutomata; ++i) {
				Pravilo pravilo = definicija.getPraviloAt(i);
				if (pravilo.getStanje().equals(trenutnoStanje)) {
					int duljinaNiza = pravilo.getAutomat().simulirajPrijelaz(izvorniKod, zavrsetak);
					if (duljinaNiza > najduziNiz) {
						najduziNiz = duljinaNiza;
						indexPravila = i;
					}
				}
			}
			
			String leksickaJedinka;
			String uniformniZnak;
			boolean vratiSe = false;

			if (indexPravila != -1) {
				leksickaJedinka = izvorniKod.substring(zavrsetak, zavrsetak + najduziNiz);
				
				Pravilo pravilo = definicija.getPraviloAt(indexPravila);
				int brojArgumenata = pravilo.getBrojArgumenata();
				uniformniZnak = pravilo.getArgumentAt(0).trim();			
					
				int brojReda = brojacRedova;
					
				for (int i = 1; i < brojArgumenata; ++i) {
					if (pravilo.getArgumentAt(i).equals("NOVI_REDAK")) {
						brojacRedova++;
					}
					else {
						String[] akcija = pravilo.getArgumentAt(i).split(" ");
						if (akcija[0].equals("UDJI_U_STANJE")) {
							trenutnoStanje = akcija[1];
						}
						else if (akcija[0].equals("VRATI_SE")) {
							int brojZnakova = Integer.parseInt(akcija[1]);
							leksickaJedinka = izvorniKod.substring(zavrsetak, zavrsetak + brojZnakova);
							zavrsetak = zavrsetak + brojZnakova - 1;							
							vratiSe = true;
						}
					}
				}
				
				if (!uniformniZnak.equals("-"))
					System.out.println(uniformniZnak + " " + brojReda + " " + leksickaJedinka);	//ispis pronadene leksicke jedinke
					
				if (!vratiSe) zavrsetak = zavrsetak + najduziNiz - 1;
			}
			else {
				System.err.println("Greska u redu: " + brojacRedova + ", Izbacen znak: " + izvorniKod.charAt(zavrsetak)); //ispis znaka koji je uzrokovao gresku na stderr
			}
			
		}
		
	}	
}
