
public class BinIliIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <bin_ili_izraz> ::= <bin_xili_izraz>
	 * <bin_ili_izraz> ::= <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>
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
		BinXiliIzraz cvor = (BinXiliIzraz) djeca.get(0);
		cvor.provjeri();
		tip = cvor.tip;
		lijeviIzraz = cvor.lijeviIzraz;
	}

	private void provjeri3() {
		BinIliIzraz binI = (BinIliIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		BinXiliIzraz binX = (BinXiliIzraz) djeca.get(2);
		binI.provjeri();
		if (!provjeriImplicitnost(binI.tip, BrojevniTip.tipINT)) {
			String znak = "<bin_ili_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <bin_xili_izraz>");
			System.exit(1);
		}
		binX.provjeri();
		if (!provjeriImplicitnost(binX.tip, BrojevniTip.tipINT)) {
			String znak = "<bin_ili_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <bin_xili_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
		
	}

}
