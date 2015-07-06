
public class ImeTipa extends Cvor {

	Tip tip;
	
	/*PRODUKCIJE
	 * <ime_tipa> ::= <specifikator_tipa>
	 * <ime_tipa> ::= KR_CONST <specifikator_tipa>
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1) provjeri1();
		else provjeri2();
	}

	private void provjeri1() {
		SpecifikatorTipa cvor = (SpecifikatorTipa) djeca.get(0);
		cvor.provjeri();
		tip = cvor.tip;
	}

	private void provjeri2() {
		SpecifikatorTipa cvor = (SpecifikatorTipa) djeca.get(1);
		CvorList list = (CvorList) djeca.get(0);
		cvor.provjeri();
		if (cvor.tip == TipVoid.tipVoid) {
			System.out.println("<ime_tipa> ::= " + list.leksickaJedinka + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <specifikator_tipa>");
		}
		if (cvor.tip == BrojevniTip.tipCHAR)
			tip = BrojevniTip.tipConstChar;
		if (cvor.tip == BrojevniTip.tipINT)
			tip = BrojevniTip.tipConstINT;
	}

}
