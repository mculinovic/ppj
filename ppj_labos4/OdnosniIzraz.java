import java.io.IOException;


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
		
		//GENERIRANJE KODA
		
		GeneratorKoda.odnosniIzraz = true;
		try {
			GeneratorKoda.out.write("\tMOVE R6, R4\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//GENERIRANJE GOTOVO
		
		
		
		
		ad.provjeri();
		if (!provjeriImplicitnost(ad.tip, BrojevniTip.tipINT)) {
			String znak = "<odnosni_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <aditivni_izraz>");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
		
		//GENERIRANJE KODA
				try {
					GeneratorKoda.out.write("\tMOVE R6, R5\n");
					GeneratorKoda.out.write("\tCMP R4,R5\n");
					
					if (list.uniformniZnak.equals("OP_LT")) {
						GeneratorKoda.out.write("\tJP_SGE DALJE\n");
					} else if (list.uniformniZnak.equals("OP_GT")) {
						GeneratorKoda.out.write("\tJP_SLE DALJE\n");
					} else if (list.uniformniZnak.equals("OP_LTE")) {
						GeneratorKoda.out.write("\tJP_SGT DALJE\n");
					} else if (list.uniformniZnak.equals("OP_GTE")) {
						GeneratorKoda.out.write("\tJP_SLT DALJE\n");
					}
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//GENERIRANJE GOTOVO
		
		
	}

}
