
public class LogIliIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <log_ili_izraz> ::= <log_i_izraz>
	 * <log_ili_izraz> ::= <log_ili_izraz> OP_ILI <log_i_izraz>
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();

		if (brojDjece == 1) provjeri1();
		else provjeri3();
	}

	private void provjeri1() {
		LogIIzraz log = (LogIIzraz) djeca.get(0);
		log.provjeri();
		tip = log.tip;
		lijeviIzraz = log.lijeviIzraz;
	}

	private void provjeri3() {
		LogIliIzraz logILI = (LogIliIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		LogIIzraz logI = (LogIIzraz) djeca.get(2);
		logILI.provjeri();
		if (!provjeriImplicitnost(logILI.tip, BrojevniTip.tipINT)) {
			String znak = "<log_ili_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <log_i_izraz>");
			System.exit(1);
		}
		logI.provjeri();
		if (!provjeriImplicitnost(logI.tip, BrojevniTip.tipINT)) {
			String znak = "<log_ili_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <log_i_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
	}

}
