import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SemantickiAnalizator {
	
	public static TipFunkcija globalnaFunkcija;
	public static int unutarFunkcije;
	public static int unutarPetlje;
	public static boolean nizZnakova;
	public static int duljinaZnakova;
	public static boolean postojiMain;
	public static Set<String> deklariraneFunkcije = new HashSet<String>();
	public static Set<String> definiraneFunkcije = new HashSet<String>();	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String red;
		Cvor roditelj = null;
		Cvor privremeni = null;
		Cvor korijen = null;
		Cvor imaTablicu = null;
		Cvor nadredenaTablica = null;
		int hijerarhijskiNivo = 0;
		int trenutniNivo;
		
		while (in.hasNextLine()) {
			red = in.nextLine();
			if (red.isEmpty()) break;
			trenutniNivo = izracunajNivo(red);
			red = preurediString(red);
			Cvor cvor = null;
			if (red.contains(" ")) {
				cvor = new CvorList();
				String[] podaci = red.split(" ");
				((CvorList) cvor).uniformniZnak = (podaci[0]);
				((CvorList) cvor).brojRetka = (Integer.parseInt(podaci[1]));
				((CvorList) cvor).leksickaJedinka = (podaci[2]);
				cvor.imaTablicu = imaTablicu;
				/*for (int i = 0; i < trenutniNivo; ++i)
					System.out.print(" ");
				System.out.println("Stvoren list");*/
				//imaTablicu.tablicaZnakova.put(((CvorList) cvor).leksickaJedinka, BrojevniTip.dohvatiBrojevniTip(((CvorList) cvor).uniformniZnak));
			}
			else {
				try {
					/*for (int i = 0; i < trenutniNivo; ++i)
						System.out.print(" ");*/
					cvor = CvorFactory.stvoriCvor(red);
					if (cvor instanceof SlozenaNaredba || cvor instanceof DefinicijaFunkcije || cvor instanceof PrijevodnaJedinica) {
						cvor.nadredenaTablica = imaTablicu;
						nadredenaTablica = imaTablicu;
						cvor.imaTablicu = cvor;
						imaTablicu = cvor;
					}
					else {
						cvor.nadredenaTablica = nadredenaTablica;
						cvor.imaTablicu = imaTablicu;
					}
					//if (cvor.nadredenaTablica == null) System.out.println(" Tablica od " + (cvor.imaTablicu).getClass().toString());
					//else System.out.println(" Tablica od " + (cvor.imaTablicu).getClass().toString() + " Nadredena " + (cvor.nadredenaTablica).getClass().toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (trenutniNivo > hijerarhijskiNivo) {
				hijerarhijskiNivo = trenutniNivo;
				roditelj = privremeni;
				cvor.roditelj = roditelj;
				roditelj.dodajDijete(cvor);
				privremeni = cvor;
			}
			else if (trenutniNivo < hijerarhijskiNivo) {
				while (hijerarhijskiNivo > trenutniNivo) {
					roditelj = privremeni.roditelj;
					privremeni = roditelj;
					hijerarhijskiNivo--;
				}
				roditelj = privremeni.roditelj;
				roditelj.dodajDijete(cvor);
				cvor.roditelj = roditelj;
				privremeni = cvor;
			}
			else {
				if (hijerarhijskiNivo == 0) korijen = cvor;
				cvor.roditelj = roditelj;
				if (roditelj != null) roditelj.dodajDijete(cvor);
				privremeni = cvor;
			}
		}
		in.close();
		
		
		/*System.out.println("KRAJ");
		System.out.println();
		
		System.out.println(korijen.djeca.get(0).getClass().toString());*/
		
		korijen.provjeri();
		
		if (!postojiMain) {
			System.out.println("main");
			System.exit(1);
		}
		for (String s: deklariraneFunkcije) {
			if (!definiraneFunkcije.contains(s)) {
				System.out.println("funkcija");
				System.exit(1);
			}
		}
		
	}

	private static String preurediString(String red) {
		
		red = red.trim();
		if (red.contains("<") && red.contains(">")) {
			return red.substring(1,red.length() - 1);
		} else
			return red;
		
	}

	private static int izracunajNivo(String red) {
		int i = 0;
		while (red.charAt(i) == ' ') i++;
		return i;
	}


}
