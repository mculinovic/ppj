
public class UnarniIzraz extends Cvor {
	
	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <unarni_izraz> ::= <postfiks_izraz>
	 * <unarni_izraz> ::= (OP_INC | OP_DEC) <unarni_izraz>
	 * <unarni_izraz> ::= <unarni_operator> <cast_izraz>
	 * */

	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		if (brojDjece == 1) provjeri1();
		else provjeri2();

	}

	private void provjeri1() {
		PostfiksIzraz cvor = (PostfiksIzraz) djeca.get(0);
		cvor.provjeri();
		tip = cvor.tip;
		lijeviIzraz = cvor.lijeviIzraz;
		
	}

	private void provjeri2() {
		Cvor cvor = djeca.get(0);
		if (cvor instanceof UnarniOperator) {
			CastIzraz cast = (CastIzraz) djeca.get(1);
			cast.provjeri();
			if (!provjeriImplicitnost(cast.tip, BrojevniTip.tipINT)) {
				System.out.println("<unarni_izraz> ::= <unarni_operator> <cast_izraz>");
			}
			tip = BrojevniTip.tipINT;
			lijeviIzraz = false;
			
		} else {
			CvorList list = (CvorList) cvor;
			UnarniIzraz izraz = (UnarniIzraz) djeca.get(1);
			izraz.provjeri();
			if (!izraz.lijeviIzraz || !provjeriImplicitnost(izraz.tip, BrojevniTip.tipINT)) {
				String znak = "<unarni_izraz>";
				System.out.println(znak + " ::= " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <unarni_izraz>");
			}
			tip = BrojevniTip.tipINT;
			lijeviIzraz = false;
		}
		
	}

}
