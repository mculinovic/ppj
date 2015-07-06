import java.util.HashMap;

public class DefinicijaFunkcije extends Cvor {

	
	/*PRODUKCIJE
	 * <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA KR_VOID D_ZAGRADA <slozena_naredba>
	 * <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA <lista_parametara> D_ZAGRADA <slozena_naredba>
	 * */
	
	public DefinicijaFunkcije() {
		tablicaZnakova = new HashMap<String, Tip>();
	}
	
	@Override
	public void provjeri() {
		
		SemantickiAnalizator.unutarFunkcije++;
		
		
		if (djeca.get(3) instanceof CvorList)
			provjeri1();
		else
			provjeri2();
		
		SemantickiAnalizator.unutarFunkcije--;
	}

	private void provjeri1() {
		ImeTipa ime = (ImeTipa) djeca.get(0);
		CvorList list1 = (CvorList) djeca.get(1);
		CvorList list2 = (CvorList) djeca.get(2);
		CvorList list3 = (CvorList) djeca.get(3);
		CvorList list4 = (CvorList) djeca.get(4);
		SlozenaNaredba naredba = (SlozenaNaredba) djeca.get(5);
		ime.provjeri();
		if (ime.tip == BrojevniTip.tipConstINT || ime.tip == BrojevniTip.tipConstChar) {
			System.out.println("<definicija_funkcije> ::= <ime_tipa> " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
					+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
					+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
					+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ") <slozena_naredba>");
			System.exit(1);
		}
		Cvor cvor = dohvatiFunkciju(list1.leksickaJedinka);
		if (cvor != null) {
			Tip tipFunkcija = cvor.tablicaZnakova.get(list1.leksickaJedinka);
			String def = new String();
			if (tipFunkcija instanceof TipFunkcija) {	
				TipFunkcija f = (TipFunkcija) tipFunkcija;
				def = list1.leksickaJedinka + " " + Cvor.toString(f.povratnaVrijednost);
				for (Tip tip: f.parametriFunkcije)
					def = def + " " + Cvor.toString(tip);
			}
			if (SemantickiAnalizator.definiraneFunkcije.contains(def)) {
				System.out.println("<definicija_funkcije> ::= <ime_tipa> " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
									+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
									+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
									+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ") <slozena_naredba>");
				System.exit(1);
			}				
			else {
				TipFunkcija noviTip = new TipFunkcija(ime.tip);
				imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, noviTip);
				SemantickiAnalizator.globalnaFunkcija = noviTip;
				def = new String();
				def = list1.leksickaJedinka + " " + Cvor.toString(noviTip.povratnaVrijednost);
				for (Tip tip: noviTip.parametriFunkcije)
					def = def + " " + Cvor.toString(tip);
				SemantickiAnalizator.definiraneFunkcije.add(def);
				
				if (list1.leksickaJedinka.equals("main") && noviTip.povratnaVrijednost == BrojevniTip.tipINT  && noviTip.parametriFunkcije.isEmpty())
					SemantickiAnalizator.postojiMain = true;
			}
		} else {
			TipFunkcija noviTip = new TipFunkcija(ime.tip);
			imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, noviTip);
			SemantickiAnalizator.globalnaFunkcija = noviTip;
			String def = new String();
			def = list1.leksickaJedinka + " " + Cvor.toString(noviTip.povratnaVrijednost);
			for (Tip tip: noviTip.parametriFunkcije)
				def = def + " " + Cvor.toString(tip);
			SemantickiAnalizator.definiraneFunkcije.add(def);
			
			if (list1.leksickaJedinka.equals("main") && noviTip.povratnaVrijednost == BrojevniTip.tipINT  && noviTip.parametriFunkcije.isEmpty())
				SemantickiAnalizator.postojiMain = true;
		}
		naredba.provjeri(null, null);
	}


	private void provjeri2() {
		ImeTipa ime = (ImeTipa) djeca.get(0);
		CvorList list1 = (CvorList) djeca.get(1);
		CvorList list2 = (CvorList) djeca.get(2);
		ListaParametara lista = (ListaParametara) djeca.get(3);
		CvorList list3 = (CvorList) djeca.get(4);
		SlozenaNaredba naredba = (SlozenaNaredba) djeca.get(5);
		
		ime.provjeri();
		if (ime.tip == BrojevniTip.tipConstChar || ime.tip == BrojevniTip.tipConstChar) {
			System.out.println("<definicija_funkcije ::= <ime_tipa> " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
								+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <lista_parametara> "
								+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") <slozena_naredba>");
			System.exit(1);
		}
		Cvor cvor = dohvatiFunkciju(list1.leksickaJedinka);
		if (cvor != null) {
			Tip tipFunkcija = cvor.tablicaZnakova.get(list1.leksickaJedinka);
			String def = new String();
			if (tipFunkcija instanceof TipFunkcija) {	
				TipFunkcija f = (TipFunkcija) tipFunkcija;
				def = list1.leksickaJedinka + " " + Cvor.toString(f.povratnaVrijednost);
				for (Tip tip: f.parametriFunkcije)
					def = def + " " + Cvor.toString(tip);
			}
			if (SemantickiAnalizator.definiraneFunkcije.contains(def)) {
				System.out.println("<definicija_funkcije ::= <ime_tipa> " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
						+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <lista_parametara> "
						+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") <slozena_naredba>");
				System.exit(1);
			}				
			else {
				lista.provjeri();
				TipFunkcija noviTip = new TipFunkcija(ime.tip);
				noviTip.parametriFunkcije = lista.tipovi;
				imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, noviTip);
				SemantickiAnalizator.globalnaFunkcija = noviTip;
				def = new String();
				def = list1.leksickaJedinka + " " + Cvor.toString(noviTip.povratnaVrijednost);
				for (Tip tip: noviTip.parametriFunkcije)
					def = def + " " + Cvor.toString(tip);
				SemantickiAnalizator.definiraneFunkcije.add(def);
				
				if (list1.leksickaJedinka.equals("main") && noviTip.povratnaVrijednost == BrojevniTip.tipINT  && noviTip.parametriFunkcije.isEmpty())
					SemantickiAnalizator.postojiMain = true;
			}
		} else {
			lista.provjeri();
			TipFunkcija noviTip = new TipFunkcija(ime.tip);
			noviTip.parametriFunkcije = lista.tipovi;
			imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, noviTip);
			SemantickiAnalizator.globalnaFunkcija = noviTip;
			String def = new String();
			def = list1.leksickaJedinka + " " + Cvor.toString(noviTip.povratnaVrijednost);
			for (Tip tip: noviTip.parametriFunkcije)
				def = def + " " + Cvor.toString(tip);
			SemantickiAnalizator.definiraneFunkcije.add(def);
			
			if (list1.leksickaJedinka.equals("main") && noviTip.povratnaVrijednost == BrojevniTip.tipINT  && noviTip.parametriFunkcije.isEmpty())
				SemantickiAnalizator.postojiMain = true;
		}
		naredba.provjeri(lista.tipovi, lista.imena);
		
	}

}
