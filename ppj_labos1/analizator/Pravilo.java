import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author mculinovic
 *
 * Razred predstavlja pravilo leksickog analizatora
 */
public class Pravilo implements Serializable {
	
	private static final long serialVersionUID = -4205957055769469398L;
	private String stanje;
	private String regularniIzraz;
	private Automat automat;
	private List<String> argumentiAkcije;
	
	public Pravilo() {	
		argumentiAkcije = new ArrayList<String>();
	}

	public String getStanje() {
		return stanje;
	}

	public void setStanje(String stanje) {
		this.stanje = stanje;
	}

	public String getRegularniIzraz() {
		return regularniIzraz;
	}

	public void setRegularniIzraz(String regularniIzraz) {
		this.regularniIzraz = regularniIzraz;
		automat = new Automat();
		stvoriAutomat();
	}

	private void stvoriAutomat() {
		ParStanja rezultat = pretvori(regularniIzraz, automat);
		automat.pocetnoStanje = rezultat.stanjeLijevo;
		automat.prihvatljivoStanje = rezultat.stanjeDesno;		
	}

	
	/**
	 * Metoda stvara prijelaze za automat
	 * @see ppj-lab1.pdf, str.20 - 25
	 * @param regularniIzraz regularni izraz za koji se gradi automat
	 * @param automat automat cije prijelaze kreiramo
	 * @return pocetno i prihvatljivo stanje automata
	 */
	public ParStanja pretvori(String regularniIzraz, Automat automat) {
		List<String> grupirani = new ArrayList<String>();
		int brojZagrada = 0;
		int duljinaIzraza = regularniIzraz.length();
		boolean operatorIzbora = false;
		int pozicijaOdvajanja = 0;
		
		for (int i = 0; i < duljinaIzraza; ++i) {
			if (regularniIzraz.charAt(i) == '(' && jeOperator(regularniIzraz,i)) {
				brojZagrada++;
			}
			else if (regularniIzraz.charAt(i) == ')' && jeOperator(regularniIzraz, i)) {
				brojZagrada--;
			}
			else if (brojZagrada == 0 && regularniIzraz.charAt(i) == '|' && jeOperator(regularniIzraz, i)) {
				String grupiran = regularniIzraz.substring(pozicijaOdvajanja, i);
				grupirani.add(grupiran);
				pozicijaOdvajanja = i + 1;
				operatorIzbora = true;
			}
		}
		
		if (operatorIzbora) {
			grupirani.add(regularniIzraz.substring(pozicijaOdvajanja));
		}
		
		int stanjeLijevo = automat.novoStanje();
		int stanjeDesno = automat.novoStanje();
		
		if (operatorIzbora) {
			int brojGrupa = grupirani.size();
			for (int i = 0; i < brojGrupa; ++i) {
				ParStanja privremeno = pretvori(grupirani.get(i), automat);
				automat.dodajEpsilonPrijelaz(stanjeLijevo, privremeno.stanjeLijevo);
				automat.dodajEpsilonPrijelaz(privremeno.stanjeDesno, stanjeDesno);
			}
		}
		else {
			boolean prefiksirano = false;
			int zadnjeStanje = stanjeLijevo;
			
			for (int i = 0; i < duljinaIzraza; ++i) {
				int a, b;
				char trenutacniZnak = regularniIzraz.charAt(i);
				
				if (prefiksirano) {
					prefiksirano = false;
					char prijelazniZnak;
			
					if (trenutacniZnak == 't')
						prijelazniZnak = '\t';
					else if (trenutacniZnak == 'n')
						prijelazniZnak = '\n';
					else if (trenutacniZnak == '_')
						prijelazniZnak = ' ';
					else
						prijelazniZnak = trenutacniZnak;
					
					a = automat.novoStanje();
					b = automat.novoStanje();
					automat.dodajPrijelaz(a, b, prijelazniZnak);
				}
				else {
					
					if (trenutacniZnak == '\\') {
						prefiksirano = true;
						continue;
					}
					if (trenutacniZnak != '(') {
						a = automat.novoStanje();
						b = automat.novoStanje();
						if (trenutacniZnak == '$') {
							automat.dodajEpsilonPrijelaz(a,b);
						}
						else {
							automat.dodajPrijelaz(a, b, trenutacniZnak);
						}
					}
					else {
						int j = pronadjiZatvorenuZagradu(regularniIzraz, i);
						ParStanja  privremeno = pretvori(regularniIzraz.substring(i + 1, j), automat);
						a = privremeno.stanjeLijevo;
						b = privremeno.stanjeDesno;
						i = j;
					}
				}
				
				if (i + 1 < duljinaIzraza && regularniIzraz.charAt(i+1) == '*') {
					int x = a;
					int y = b;
					a = automat.novoStanje();
					b = automat.novoStanje();
					automat.dodajEpsilonPrijelaz(a, x);
					automat.dodajEpsilonPrijelaz(y, b);
					automat.dodajEpsilonPrijelaz(a, b);
					automat.dodajEpsilonPrijelaz(y, x);
					++i;
				}
				
				automat.dodajEpsilonPrijelaz(zadnjeStanje, a);
				zadnjeStanje = b;
			}
			
			automat.dodajEpsilonPrijelaz(zadnjeStanje, stanjeDesno);
		}
		
		return new ParStanja(stanjeLijevo, stanjeDesno);
	}
	
	private int pronadjiZatvorenuZagradu(String regularniIzraz, int i) {
		
		int duljinaIzraza = regularniIzraz.length();
		int brojZagrada = 1;
		int j = i + 1;
		for (; j < duljinaIzraza; ++j) {
			if (regularniIzraz.charAt(j) == '(' && jeOperator(regularniIzraz, j))
				brojZagrada++;
			if (regularniIzraz.charAt(j) == ')' && jeOperator(regularniIzraz, j))
				brojZagrada--;
			if (brojZagrada == 0) break;
		}
		return j;		
	}

	private boolean jeOperator(String regularniIzraz, int index) {
		int brojac = 0;
		while (index - 1 >= 0 && regularniIzraz.charAt(index - 1) == '\\') {
			brojac = brojac + 1;
			index = index - 1;
		}
		return brojac % 2 == 0;
	}

	public void dodajArgument(String argument) {
		argumentiAkcije.add(argument);
	}
	
	public int getBrojArgumenata() {
		return argumentiAkcije.size();
	}

	public String getArgumentAt(int index) {
		return argumentiAkcije.get(index);
	}

	public Automat getAutomat() {
		return automat;
	}
	
}
