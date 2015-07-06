
public class AditivniIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <aditivni_izraz> ::= <multiplikativni_izraz>
	 * <aditivni_izraz> ::= <aditivni_izraz> (PLUS | MINUS) <multiplikativni_izraz>
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
		MultiplikativniIzraz cvor = (MultiplikativniIzraz) djeca.get(0);
		cvor.provjeri();
		tip = cvor.tip;
		lijeviIzraz = cvor.lijeviIzraz;
		
	}

	private void provjeri3() {
		AditivniIzraz cvor = (AditivniIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		MultiplikativniIzraz mul = (MultiplikativniIzraz) djeca.get(2);
		cvor.provjeri();
		if (!provjeriImplicitnost(cvor.tip, BrojevniTip.tipINT)) {
			String znak = "<aditivni_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <multiplikativni_izraz>");
			System.exit(1);
		}
		mul.provjeri();
		if (!provjeriImplicitnost(mul.tip, BrojevniTip.tipINT)) {
			String znak = "<aditivni_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <multiplikativni_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
		
	}
}
