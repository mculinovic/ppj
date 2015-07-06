import java.io.IOException;


public class BinXiliIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	/*PRODUKCIJE
	 * <bin_xili_izraz> ::= <bin_i_izraz>
	 * <bin_xili_izraz> ::= <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>
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
		BinIIzraz cvor = (BinIIzraz) djeca.get(0);
		cvor.provjeri();
		tip = cvor.tip;
		lijeviIzraz = cvor.lijeviIzraz;
	}

	private void provjeri3() {
		BinXiliIzraz binX = (BinXiliIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		BinIIzraz binI = (BinIIzraz) djeca.get(2);
		binX.provjeri();
		if (!provjeriImplicitnost(binX.tip, BrojevniTip.tipINT)) {
			String znak = "<bin_xili_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <bin_i_izraz>");
			System.exit(1);
		}
		
		try {
			GeneratorKoda.out.write("\tPUSH R6\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		binI.provjeri();
		
		try {
			GeneratorKoda.out.write("\tPUSH R6\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if (!provjeriImplicitnost(binI.tip, BrojevniTip.tipINT)) {
			String znak = "<bin_xili_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <bin_i_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
		
		try {
			GeneratorKoda.out.write("\tPOP R0\n");
			GeneratorKoda.out.write("\tPOP R6\n");
			GeneratorKoda.out.write("\tXOR R0,R6,R6\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
