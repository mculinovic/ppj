
public class MultiplikativniIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	
	/*PRODUKCIJE
	 * <multiplikativni_izraz> ::= <cast_izraz>
	 * <multiplikativni_izraz> ::= <multiplikativni_izraz> (OP_PUTA | OP_DIJELI | OP_MOD) <cast_izraz>
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
		CastIzraz cast = (CastIzraz) djeca.get(0);
		cast.provjeri();
		tip = cast.tip;
		lijeviIzraz = cast.lijeviIzraz;
	}


	private void provjeri3() {
		MultiplikativniIzraz mul = (MultiplikativniIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		CastIzraz cast = (CastIzraz) djeca.get(2);
		mul.provjeri();
		if (!provjeriImplicitnost(mul.tip, BrojevniTip.tipINT)) {
			String znak = "<multiplikativni_izraz>";
			System.out.println(znak + " ::= " + znak + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <cast_izraz>");
			System.exit(1);
		}
		cast.provjeri();
		if (!provjeriImplicitnost(cast.tip, BrojevniTip.tipINT)) {
			String znak = "<multiplikativni_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <cast_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
		
	}


}
