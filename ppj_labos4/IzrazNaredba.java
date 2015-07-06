
public class IzrazNaredba extends Cvor {

	Tip tip;
	
	/*PRODUKCIJE
	 * <izraz_naredba> ::= TOCKAZAREZ
	 * <izraz_naredba> ::= <izraz> TOCKAZAREZ
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1) 
			provjeri1();
		else 
			provjeri2();
	}

	private void provjeri1() {
		tip = BrojevniTip.tipINT;
	}

	private void provjeri2() {
		Izraz izraz = (Izraz) djeca.get(0);
		izraz.provjeri();
		tip = izraz.tip;
	}

}
