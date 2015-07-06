
public class CastIzraz extends Cvor {
	
	Tip tip;
	boolean lijeviIzraz;

	/*PRODUKCIJE
	 * <cast_izraz> ::= <unarni_izraz>
	 * <cast_izraz> ::= L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>
	 * 
	 * 
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		if (brojDjece == 1) provjeri1();
		else provjeri4();
	}

	private void provjeri1() {
		Cvor cvor = djeca.get(0);
		cvor.provjeri();
		tip = ((UnarniIzraz) cvor).tip;
		lijeviIzraz = ((UnarniIzraz) cvor).lijeviIzraz;		
	}

	private void provjeri4() {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(2);
		ImeTipa ime = (ImeTipa) djeca.get(1);
		CastIzraz cast = (CastIzraz) djeca.get(3);
		ime.provjeri();
		cast.provjeri();
		if (!(cast.tip instanceof BrojevniTip && ime.tip instanceof BrojevniTip)) { 
		//if (!provjeriImplicitnost(cast.tip, ime.tip) && !(ime.tip == BrojevniTip.tipCHAR && cast.tip == BrojevniTip.tipINT)) { eksplicitno int u char
			String znak = "<cast_izraz>";
			System.out.println(znak + " ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ")" +
							   " <ime_tipa> " + list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ")" + " <cast_izraz>");
			System.exit(1);
		}
		tip = ime.tip;
		lijeviIzraz = false;		
	}

}
