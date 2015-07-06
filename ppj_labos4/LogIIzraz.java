
public class LogIIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <log_i_izraz> ::= <bin_ili_izraz>
	 * <log_i_izraz> ::= <log_i_izraz> OP_I <bin_ili_izraz>
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
		BinIliIzraz bin = (BinIliIzraz) djeca.get(0);
		bin.provjeri();
		tip = bin.tip;
		lijeviIzraz = bin.lijeviIzraz;
	}

	private void provjeri3() {
		LogIIzraz log = (LogIIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		BinIliIzraz bin = (BinIliIzraz) djeca.get(2);
		log.provjeri();
		if (!provjeriImplicitnost(log.tip, BrojevniTip.tipINT)) {
			String znak = "<log_i_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <bin_ili_izraz>");
			System.exit(1);
		}
		bin.provjeri();
		if (!provjeriImplicitnost(bin.tip, BrojevniTip.tipINT)) {
			String znak = "<log_i_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <bin_ili_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
	}

}
