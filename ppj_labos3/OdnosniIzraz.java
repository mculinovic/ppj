
public class OdnosniIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <odnosni_izraz> ::= <aditivni_izraz>
	 * <odnosni_izraz> ::= <odnosni_izraz> (OP_LT | OP_GT | OP_LTE | OP_GTE) <aditivni_izraz>
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
		AditivniIzraz cvor = (AditivniIzraz) djeca.get(0);
		cvor.provjeri();
		tip = cvor.tip;
		lijeviIzraz = cvor.lijeviIzraz;		
	}

	private void provjeri3() {
		OdnosniIzraz odn = (OdnosniIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		AditivniIzraz ad = (AditivniIzraz) djeca.get(2);
		odn.provjeri();
		if (!provjeriImplicitnost(odn.tip, BrojevniTip.tipINT)) {
			String znak = "<odnosni_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <aditivni_izraz>");
			System.exit(1);
		}
		ad.provjeri();
		if (!provjeriImplicitnost(ad.tip, BrojevniTip.tipINT)) {
			String znak = "<odnosni_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <aditivni_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
		
	}

}
