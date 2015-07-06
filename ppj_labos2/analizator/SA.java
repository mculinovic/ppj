import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;


public class SA {
	
	//TODO nadziranje gresaka
	
	private static Map<Integer,Produkcija> produkcije;
	private static Set<String> sinkronizacijskiZnakovi;
	private static Set<String> zavrsniZnakovi;
	private static Map<String, String> akcija;
	private static Map<String, String> novoStanje;
	private static Stack<ZnakStoga> stogSimulatora;
	
	public static void main(String[] args) {
		
		//deserijalizacija
		DefinicijaSA definicija = new DefinicijaSA();		
		try {
			FileInputStream fin = new FileInputStream("syntax_analizator.dat");
			ObjectInputStream ois = new ObjectInputStream(fin);
			definicija = (DefinicijaSA) ois.readObject();
			ois.close();
		}
		catch (Exception e) {e.printStackTrace(); }
		
		Scanner in = new Scanner(System.in);
		
		produkcije = definicija.getProdukcije();
		sinkronizacijskiZnakovi = definicija.getSinkronizacijskiZnakovi();
		akcija = definicija.getAkcija();
		novoStanje = definicija.getNovoStanje();
		zavrsniZnakovi = definicija.getZavrsniZnakovi();
		
		stogSimulatora = new Stack<ZnakStoga>();
		
		Cvor cvor = new Cvor();
		cvor.setUniformniZnak("?");
		stogSimulatora.push(cvor);
		Stanje stanje = new Stanje(0);
		stogSimulatora.push(stanje);
		
		String input = new String();
		String redak = new String();
		while (in.hasNextLine()) {
			redak = in.nextLine();
			if (redak.isEmpty()) break;
			input += redak;
			input += Character.toString('\n');
		}
		input += "!";
		boolean redukcija = false;
		in.close();
		
		String[] redovi = input.split("\\n");
		
		boolean greska = false;
	
		int k = 0;
		while (k < redovi.length) {
			redak = redovi[k];
			if (redak.isEmpty()) break;
			
			String uniformniZnak;
			if (!redak.equals("!")) {
				String[] elementi = redak.split(" ");
				uniformniZnak = elementi[0];
				Integer brojReda = Integer.parseInt(elementi[1]);
				String leksickaJedinka = new String();
				cvor = new Cvor();
				cvor.setBrojRetka(brojReda);
				for (int i = 2; i < elementi.length; ++i) {
					if (i == elementi.length - 1) leksickaJedinka += (elementi[i]);
					else leksickaJedinka += (elementi[i] + " ");
				}
				cvor.setLeksickaJedinka(leksickaJedinka);
				cvor.setUniformniZnak(uniformniZnak);
			}
			else {
				cvor = new Cvor();
				uniformniZnak = redak;
				cvor.setUniformniZnak(uniformniZnak);
			}
			
			if (greska && sinkronizacijskiZnakovi.contains(uniformniZnak)) {
				while (!stogSimulatora.isEmpty()) {
					String redTablice = Integer.toString(((Stanje)stogSimulatora.peek()).getStanje());
					String akcijaZnak = redTablice + "," + uniformniZnak;
					if (akcija.containsKey(akcijaZnak)) {
						redukcija = napraviPotez(akcija.get(akcijaZnak), cvor);
						greska = false;
						break;
					}
					else {
						stogSimulatora.pop();
						stogSimulatora.pop();
					}
				}
				if (stogSimulatora.isEmpty()) System.exit(1);
			}
			else if (greska) {
				k++;
				continue;
			}
			else {
				String redTablice = Integer.toString(((Stanje)stogSimulatora.peek()).getStanje());
				String akcijaZnak = redTablice + "," + uniformniZnak;
				if (akcija.containsKey(akcijaZnak)) {
					String potez = akcija.get(akcijaZnak);
					redukcija = napraviPotez(potez, cvor);
				}
				else if (novoStanje.containsKey(akcijaZnak)) {
					String potez = akcija.get(akcijaZnak);
					redukcija = napraviPotez(potez, cvor);
				}
				else {
					System.err.println("Sintaksna greska u redu: " + cvor.getBrojRetka());
					System.err.println("Uniformni znak:  " +  cvor.getUniformniZnak() + ", Leksicka jedinka: " + cvor.getLeksickaJedinka());
					greska = true;
					if (sinkronizacijskiZnakovi.contains(cvor.getUniformniZnak())) k--;
					System.err.print("Moguce rjesenje greske - upotrijebi neki od sljedecih znakova: ");
					for (String znak: zavrsniZnakovi) {
						if (akcija.containsKey(redTablice + "," + znak))
							System.err.print(znak + " ");
					}
					System.err.println();
					
				}
			}
			k++;
			if (redukcija) k--;
		}
		
	}

	private static void ispisiGenerativnoStablo(Cvor cvor, String pomak) {
		
		
		if (cvor.isJeList()) {
			if (cvor.getUniformniZnak().equals("$")) System.out.println(pomak + "$");
			else System.out.println(pomak + cvor.getUniformniZnak() + " " + cvor.getBrojRetka() + " " + cvor.getLeksickaJedinka());
		}
		else {
			System.out.println(pomak + cvor.getUniformniZnak());
			List<Cvor> djeca = cvor.getDjeca();
			for (Cvor dijete: djeca)
				ispisiGenerativnoStablo(dijete, pomak + " ");				
		}
		
	}

	private static boolean napraviPotez(String potez, Cvor cvor) {
		
		
		
		if (potez.contains("Pomakni")) {
			cvor.setJeList(true);
			stogSimulatora.push(cvor);
			String broj = new String();
			for (int i = 8; potez.charAt(i) != ')'; ++i) {
				broj += Character.toString(potez.charAt(i));
			}			
			Stanje sljedeceStanje = new Stanje(Integer.parseInt(broj));
			stogSimulatora.push(sljedeceStanje);
		}
		else if (potez.contains("Reduciraj")) {
			String brojProdukcije = new String();
			for (int i = 10; potez.charAt(i) != ')'; ++i) {
				brojProdukcije += Character.toString(potez.charAt(i));
			}
			Produkcija produkcija = produkcije.get(Integer.parseInt(brojProdukcije));
			Cvor novi = new Cvor();
			novi.setUniformniZnak(produkcija.getLijevaStrana());
			
			String[] desnaStrana = produkcija.getDesnaStrana();
			int brojZnakova = desnaStrana.length;
			if (!(brojZnakova == 1 && desnaStrana[0].equals("$"))) {
				for (int i = 0; i < brojZnakova*2; ++i) {
					ZnakStoga znak = stogSimulatora.pop();
					if (znak instanceof Cvor) {
						novi.dodajDijete((Cvor) znak);
					}
				}				
			}
			else {
				Cvor prazan = new Cvor();
				prazan.setJeList(true);
				prazan.setUniformniZnak("$");
				novi.dodajDijete(prazan);
			}
			String broj = new String();
			String stavi =  novoStanje.get(((Stanje)stogSimulatora.peek()).getStanje() + "," + produkcija.getLijevaStrana());
			stogSimulatora.push(novi);
			for (int i = 6; stavi.charAt(i) != ')'; ++i) {
				broj += Character.toString(stavi.charAt(i));
			} 
			Stanje sljedeceStanje = new Stanje(Integer.parseInt(broj));
			stogSimulatora.push(sljedeceStanje);
			return true;
		}
		else if (potez.contains("Prihvati")) {
			stogSimulatora.pop();
			cvor = (Cvor) stogSimulatora.pop();
			ispisiGenerativnoStablo(cvor, "");
		}
		
		return false;
		
	}

}
