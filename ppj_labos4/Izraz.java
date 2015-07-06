
public class Izraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <izraz> ::= <izraz_pridruzivanja>
	 * <izraz> ::= <izraz> ZAREZ <izraz_pridruzivanja>
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1)
			provjeri1();
		else
			provjeri3();

	}

	private void provjeri1() {
		IzrazPridruzivanja izraz = (IzrazPridruzivanja) djeca.get(0);
		izraz.provjeri();
		tip = izraz.tip;
		lijeviIzraz = izraz.lijeviIzraz;
		
	}

	private void provjeri3() {
		Izraz izraz = (Izraz) djeca.get(0);
		IzrazPridruzivanja izrazP = (IzrazPridruzivanja) djeca.get(2);
		izraz.provjeri();
		izrazP.provjeri();
		tip = izrazP.tip;
		lijeviIzraz = false;		
	}

}
