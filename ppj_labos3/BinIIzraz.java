
public class BinIIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <bin_i_izraz> ::= <jednakosni_izraz>
	 * <bin_i_izraz> ::= <bin_i_izraz> OP_BIN_I <jednakosni_izraz>
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
		JednakosniIzraz cvor = (JednakosniIzraz) djeca.get(0);
		cvor.provjeri();
		tip = cvor.tip;
		lijeviIzraz = cvor.lijeviIzraz;
		
	}

	private void provjeri3() {
		BinIIzraz bin = (BinIIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		JednakosniIzraz jed = (JednakosniIzraz) djeca.get(2);
		bin.provjeri();
		if (!provjeriImplicitnost(bin.tip, BrojevniTip.tipINT)) {
			String znak = "<bin_i_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <jednakosni_izraz>");
			System.exit(1);
		}
		jed.provjeri();
		if (!provjeriImplicitnost(jed.tip, BrojevniTip.tipINT)) {
			String znak = "<bin_i_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <jednakosni_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
	}

}
