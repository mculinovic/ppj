import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author mculinovic
 * 
 * Razred je implementacija epsilon nka automata
 *
 */
public class Automat implements Serializable {
	
	private static final long serialVersionUID = -8123445063979533388L;
	int brojStanja;
	int pocetnoStanje;
	int prihvatljivoStanje;
	
	Map<String, Integer> tablicaPrijelaza;
	Map<Integer, Set<Integer>> tablicaEPrijelaza;
	

	public Automat() {
		this.brojStanja = 0;
		tablicaPrijelaza = new HashMap<String, Integer>();
		tablicaEPrijelaza = new HashMap<Integer,Set<Integer>>();
	}

	
	public int novoStanje() {
		brojStanja++;
		return brojStanja - 1;
	}


	public void dodajEpsilonPrijelaz(int stanje1, int stanje2) {
		if (!tablicaEPrijelaza.containsKey(stanje1)) {
			Set<Integer> stanja = new HashSet<Integer>();
			stanja.add(stanje2);
			tablicaEPrijelaza.put(stanje1, stanja);
		}
		else {
			Set<Integer> stanja = tablicaEPrijelaza.get(stanje1);
			stanja.add(stanje2);
			tablicaEPrijelaza.put(stanje1, stanja);
		}
	}


	public void dodajPrijelaz(int stanje1, int stanje2, char prijelazniZnak) {
		String prijelaz = Integer.toString(stanje1) + "," + Character.toString(prijelazniZnak);
		tablicaPrijelaza.put(prijelaz, stanje2);
	}


	/**
	 * Metoda simulira prijelaze na automatu citajuci ulazni niz
	 * @see Prevodenje programskih jezika, S.Srbljic, str.61
	 * @param ulaz niz znakova
	 * @param index indeks znaka od kojeg pocinje citanje
	 * @return duljinu pronadene leksicke jedinke ili -1 ukoliko 
	 * 		   ista nije nadena
	 */
	public int simulirajPrijelaz(String ulaz, int index) {
		
		BitSet setX = new BitSet(brojStanja);
		BitSet setY = new BitSet(brojStanja);
		Stack<Integer> lifo = new Stack<Integer>();
		int posljednji = -1;
		
		setX.set(pocetnoStanje);
		
		while (true) {
			
			for (int j = 0; j < brojStanja; ++j) {
				if (setX.get(j)) {					
					if (tablicaEPrijelaza.containsKey(j)) {
						Set<Integer> stanja = tablicaEPrijelaza.get(j);
						for (Integer stanje: stanja) {
							if (!setX.get(stanje)) {
								setX.set(stanje);
								setY.set(stanje);
							}
						}
					}
				}
			}
			
			if (setY.isEmpty()) break;
			setY = new BitSet(brojStanja);
		}
		
		setY = new BitSet(brojStanja);
		int duljinaUlaza = ulaz.length();
		for (int i = index; i < duljinaUlaza; ++i) {
			char trenutniZnak = ulaz.charAt(i);
			for (int j = 0; j < brojStanja; ++j) {
				if (setX.get(j)) {
					String prijelaz = Integer.toString(j) + "," + Character.toString(trenutniZnak);
					if (tablicaPrijelaza.containsKey(prijelaz)) {
						int postavi = tablicaPrijelaza.get(prijelaz);
						setY.set(postavi);
						lifo.push(postavi);
					}
				}
			}
			
			while (!lifo.isEmpty()) {
				int stanje = lifo.pop();
				if (tablicaEPrijelaza.containsKey(stanje)) {
					Set<Integer> stanja = tablicaEPrijelaza.get(stanje);
					for (Integer postavi: stanja) {
						if (!setY.get(postavi)) {
							setY.set(postavi);
							lifo.push(postavi);
						}
					}
				}
			}
			
			setX = setY;
			setY = new BitSet(brojStanja);
		
			if (setX.get(prihvatljivoStanje)) {
				posljednji = i;
			}
			
			if (setX.isEmpty()) break;
		}
		
		return posljednji == -1 ? -1 :posljednji - index + 1;
	}
	
}
