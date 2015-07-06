
public class JednakosniIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <jednakosni_izraz> ::= <odnosni_izraz>
	 * <jednakosni_izraz> ::= <jednakosni_izraz> (OP_EQ | OP_NEQ) <odnosni_izraz>
	 * 
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
		OdnosniIzraz cvor = (OdnosniIzraz) djeca.get(0);
		cvor.provjeri();
		tip = cvor.tip;
		lijeviIzraz = cvor.lijeviIzraz;
	}

	private void provjeri3() {
		JednakosniIzraz jed = (JednakosniIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		OdnosniIzraz odn = (OdnosniIzraz) djeca.get(2);
		jed.provjeri();
		if (!provjeriImplicitnost(jed.tip, BrojevniTip.tipINT)) {
			String znak = "<jednakosni_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <odnosni_izraz>");
			System.exit(1);
		}
		odn.provjeri();
		if (!provjeriImplicitnost(odn.tip, BrojevniTip.tipINT)) {
			String znak = "<jednakosni_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <odnosni_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
	}

}
