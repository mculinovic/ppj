import java.io.IOException;
import java.util.ArrayList;
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
		
		GeneratorKoda.unutarFunkcije++;
		
		GeneratorKoda.lokalneVarijable = new ArrayList<String>();
		
		
		if (djeca.get(3) instanceof CvorList)
			provjeri1();
		else
			provjeri2();
		
		GeneratorKoda.lokalneVarijable.clear();
		GeneratorKoda.unutarFunkcije--;
	}

	private void provjeri1() {
		ImeTipa ime = (ImeTipa) djeca.get(0);
		CvorList list1 = (CvorList) djeca.get(1);
		CvorList list2 = (CvorList) djeca.get(2);
		CvorList list3 = (CvorList) djeca.get(3);
		CvorList list4 = (CvorList) djeca.get(4);
		SlozenaNaredba naredba = (SlozenaNaredba) djeca.get(5);
		
		generirajKodVoid(list1.leksickaJedinka);
		
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
			if (GeneratorKoda.definiraneFunkcije.contains(def)) {
				System.out.println("<definicija_funkcije> ::= <ime_tipa> " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
									+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
									+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
									+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ") <slozena_naredba>");
				System.exit(1);
			}				
			else {
				TipFunkcija noviTip = new TipFunkcija(ime.tip);
				imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, noviTip);
				GeneratorKoda.globalnaFunkcija = noviTip;
				def = new String();
				def = list1.leksickaJedinka + " " + Cvor.toString(noviTip.povratnaVrijednost);
				for (Tip tip: noviTip.parametriFunkcije)
					def = def + " " + Cvor.toString(tip);
				GeneratorKoda.definiraneFunkcije.add(def);
				
				if (list1.leksickaJedinka.equals("main") && noviTip.povratnaVrijednost == BrojevniTip.tipINT  && noviTip.parametriFunkcije.isEmpty())
					GeneratorKoda.postojiMain = true;
			}
		} else {
			TipFunkcija noviTip = new TipFunkcija(ime.tip);
			imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, noviTip);
			GeneratorKoda.globalnaFunkcija = noviTip;
			String def = new String();
			def = list1.leksickaJedinka + " " + Cvor.toString(noviTip.povratnaVrijednost);
			for (Tip tip: noviTip.parametriFunkcije)
				def = def + " " + Cvor.toString(tip);
			GeneratorKoda.definiraneFunkcije.add(def);
			
			if (list1.leksickaJedinka.equals("main") && noviTip.povratnaVrijednost == BrojevniTip.tipINT  && noviTip.parametriFunkcije.isEmpty())
				GeneratorKoda.postojiMain = true;
		}
		
		try {
			if (GeneratorKoda.unutarFunkcije != 0)
				GeneratorKoda.out.write("\tSUB R7,160,R7\n");
				GeneratorKoda.out.write("\tMOVE R7, R1\n");
				GeneratorKoda.out.write("\tPUSH R1\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		naredba.provjeri(null, null);
		
	}


	private void generirajKodVoid(String ime) {

		try {
			GeneratorKoda.out.write("\n");
			GeneratorKoda.out.write("F_" + ime.toUpperCase());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void provjeri2() {
		ImeTipa ime = (ImeTipa) djeca.get(0);
		CvorList list1 = (CvorList) djeca.get(1);
		CvorList list2 = (CvorList) djeca.get(2);
		ListaParametara lista = (ListaParametara) djeca.get(3);
		CvorList list3 = (CvorList) djeca.get(4);
		SlozenaNaredba naredba = (SlozenaNaredba) djeca.get(5);
		
		GeneratorKoda.funkcParametri = true;
		GeneratorKoda.brojacParametara = 0;
		generirajKodParametri(list1.leksickaJedinka);
		
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
			if (GeneratorKoda.definiraneFunkcije.contains(def)) {
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
				GeneratorKoda.globalnaFunkcija = noviTip;
				def = new String();
				def = list1.leksickaJedinka + " " + Cvor.toString(noviTip.povratnaVrijednost);
				for (Tip tip: noviTip.parametriFunkcije)
					def = def + " " + Cvor.toString(tip);
				GeneratorKoda.definiraneFunkcije.add(def);
				
				if (list1.leksickaJedinka.equals("main") && noviTip.povratnaVrijednost == BrojevniTip.tipINT  && noviTip.parametriFunkcije.isEmpty())
					GeneratorKoda.postojiMain = true;
			}
		} else {
			lista.provjeri();
			TipFunkcija noviTip = new TipFunkcija(ime.tip);
			noviTip.parametriFunkcije = lista.tipovi;
			imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, noviTip);
			GeneratorKoda.globalnaFunkcija = noviTip;
			String def = new String();
			def = list1.leksickaJedinka + " " + Cvor.toString(noviTip.povratnaVrijednost);
			for (Tip tip: noviTip.parametriFunkcije)
				def = def + " " + Cvor.toString(tip);
			GeneratorKoda.definiraneFunkcije.add(def);
			
			if (list1.leksickaJedinka.equals("main") && noviTip.povratnaVrijednost == BrojevniTip.tipINT  && noviTip.parametriFunkcije.isEmpty())
				GeneratorKoda.postojiMain = true;
		}
		
		try {
			if (GeneratorKoda.unutarFunkcije != 0)
				GeneratorKoda.out.write("\tSUB R7,160,R7\n");
				GeneratorKoda.out.write("\tMOVE R7, R1\n");
				GeneratorKoda.out.write("\tPUSH R1\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		naredba.provjeri(lista.tipovi, lista.imena);
		
		
		GeneratorKoda.funkcParametri = false;

	}

	private void generirajKodParametri(String ime) {
		
		try {
			GeneratorKoda.out.write("\n");
			GeneratorKoda.out.write("F_" + ime.toUpperCase());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
