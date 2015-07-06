import java.io.IOException;




public class InitDeklarator extends Cvor {

	@Override
	public void provjeri() {
		// TODO Auto-generated method stub

	}
	
	
	/*PRODUKCIJE 
	 * <init_deklarator> ::= <izravni_deklarator>
	 * <init_deklarator> ::= <izravni_deklarator> OP_PRIDRUZI <inicijalizator>
	 * */

	public void provjeri(Tip ntip) {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1)
			provjeri1(ntip);
		else
			provjeri3(ntip);
		
	}


	private void provjeri1(Tip ntip) {
		IzravniDeklarator dekl = (IzravniDeklarator) djeca.get(0);
		dekl.provjeri(ntip);
		if (dekl.tip == BrojevniTip.tipConstChar || dekl.tip == BrojevniTip.tipConstINT
			 || dekl.tip == TipNiz.nizConstCHAR || dekl.tip == TipNiz.nizConstINT) {
			System.out.println("<init_deklarator> ::= <izravni_deklarator>");
		}
	}


	private void provjeri3(Tip ntip) {
		IzravniDeklarator dekl = (IzravniDeklarator) djeca.get(0);
		Inicijalizator in = (Inicijalizator) djeca.get(2);
		CvorList list = (CvorList) djeca.get(1);
		
		if (GeneratorKoda.unutarFunkcije == 0) {
			CvorList var = (CvorList) dekl.djeca.get(0);
			String globalnaVar = new String();
			globalnaVar = globalnaVar + "G_" + var.leksickaJedinka.toUpperCase() + "\t DW %D ";
			GeneratorKoda.globalneVarijable += globalnaVar;
			
		}
		
		dekl.provjeri(ntip);
		in.provjeri();
		if (dekl.tip instanceof BrojevniTip) {
			BrojevniTip brTip = (BrojevniTip) dekl.tip;
			boolean rezultat = false;
			if (brTip == BrojevniTip.tipConstChar)
				rezultat = provjeriImplicitnost(in.tip, BrojevniTip.tipCHAR);
			else if (brTip == BrojevniTip.tipConstINT)
				rezultat = provjeriImplicitnost(in.tip, BrojevniTip.tipINT);
			else 
				rezultat = provjeriImplicitnost(in.tip, brTip);
			if (!rezultat) {
				System.out.println("<init_deklarator> ::= <izravni_deklarator> " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <inicijalizator>");
				System.exit(1);
			}
			
			
		} else if (dekl.tip instanceof TipNiz) {
			if (in.brojElemenata > dekl.brojElemenata) {
				System.out.println("<init_deklarator> ::= <izravni_deklarator> " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <inicijalizator>");
				System.exit(1);
			}
			TipNiz niz = (TipNiz) dekl.tip;
			Tip provjeri = null;
			if (niz == TipNiz.nizCHAR || niz == TipNiz.nizConstCHAR)
				provjeri = BrojevniTip.tipCHAR;
			else if (niz == TipNiz.nizINT || niz == TipNiz.nizConstINT)
				provjeri = BrojevniTip.tipINT;
			for (Tip t: in.tipovi)
				if (!provjeriImplicitnost(t, provjeri)) {
					System.out.println("<init_deklarator> ::= <izravni_deklarator> " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <inicijalizator>");
					System.exit(1);
				}
		} else {
			System.out.println("<init_deklarator> ::= <izravni_deklarator> " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <inicijalizator>");
			System.exit(1);
		}
		
		list = (CvorList) dekl.djeca.get(0);
		int odmak = GeneratorKoda.lokalneVarijable.lastIndexOf(list.leksickaJedinka.toUpperCase());
		odmak = 160 - 4*(odmak + 1);
		try {
			if (odmak != -1 && GeneratorKoda.unutarFunkcije > 0)
				GeneratorKoda.out.write("\tSTORE R6,(R1+" + odmak + ")\n");
		} catch (IOException e) {
			e.printStackTrace();
		}	

	}

}
