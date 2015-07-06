import java.util.ArrayList;


public class IzravniDeklarator extends Cvor {

	Tip tip;
	int brojElemenata;
	
	@Override
	public void provjeri() {
		// TODO Auto-generated method stub

	}

	/*PRODUKCIJE
	 * <izravni_deklarator> ::= IDN
	 * <izravni_deklarator> ::= IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA
	 * <izravni_deklarator> ::= IDN L_ZAGRADA KR_VOID D_ZAGRADA
	 * <izravni_deklarator> ::= IDN L_ZAGRADA <lista_parametara> D_ZAGRADA
	 * */
	
	public void provjeri(Tip ntip) {		
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1)
			provjeri1(ntip);
		else 
			provjeri4(ntip);
		
	}

	private void provjeri1(Tip ntip) {
		CvorList list = (CvorList) djeca.get(0);
		if (ntip instanceof TipVoid) {
			System.out.println("<izravni_deklarator> ::= " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ")");
			System.exit(1);
		}
		if (imaTablicu.tablicaZnakova.containsKey(list.leksickaJedinka)) {
			System.out.println("<izravni_deklarator> ::= " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ")");
			System.exit(1);
		}
		imaTablicu.tablicaZnakova.put(list.leksickaJedinka, ntip);
		tip = ntip;		
		
	}

	private void provjeri4(Tip ntip) {
		
		if (djeca.get(2) instanceof CvorList)
			provjeriKonacne(ntip);
		else
			provjeriOstale(ntip);
		
	}

	private void provjeriKonacne(Tip ntip) {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(1);
		CvorList list3 = (CvorList) djeca.get(2);
		CvorList list4 = (CvorList) djeca.get(3);
		if (list2.uniformniZnak.equals("L_UGL_ZAGRADA")) {
			if (ntip instanceof TipVoid) {
				System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
									+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
									+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
									+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ")");
				System.exit(1);
			}
			if (imaTablicu.tablicaZnakova.containsKey(list1.leksickaJedinka)) {
				System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
						+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
						+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
						+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ")");
				System.exit(1);
			}
			int broj = Integer.parseInt(list3.leksickaJedinka);
			if (broj < 0 || broj > 1024) {
				System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
						+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
						+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
						+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ")");
				System.exit(1);
			}
			imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, TipNiz.dohvatiTipNiz((BrojevniTip) ntip));
			tip = TipNiz.dohvatiTipNiz((BrojevniTip) ntip);
			brojElemenata = broj;
			
			
		} else {
			if (imaTablicu.tablicaZnakova.containsKey(list1.leksickaJedinka)) {
				Tip lokalno = imaTablicu.tablicaZnakova.get(list1.leksickaJedinka);
				if (!(lokalno instanceof TipFunkcija)) {
				System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
						+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
						+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
						+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ")");
				System.exit(1);
				} else {
					TipFunkcija lokalnoF = (TipFunkcija) lokalno;
					if (!(lokalnoF.parametriFunkcije.isEmpty())) {
						System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
								+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
								+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
								+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ")");
						System.exit(1);
					}
					if (!(lokalnoF.povratnaVrijednost == ntip)) {
						System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
								+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
								+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") "
								+ list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ")");
						System.exit(1);
					}
				}
			} 
			else {
				TipFunkcija f = new TipFunkcija(ntip);
				f.povratnaVrijednost = ntip;
				imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, f);
				String def = new String();
				def = list1.leksickaJedinka + " " + Cvor.toString(f.povratnaVrijednost);
				for (Tip tip: f.parametriFunkcije)
					def = def + " " + Cvor.toString(tip);
				SemantickiAnalizator.deklariraneFunkcije.add(def);
			}
		}
		
	}

	private void provjeriOstale(Tip ntip) {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(1);
		ListaParametara lista = (ListaParametara) djeca.get(2);
		CvorList list3 = (CvorList) djeca.get(3);
		
		lista.provjeri();
		
		if (imaTablicu.tablicaZnakova.containsKey(list1.leksickaJedinka)) {
			Tip lokalno = imaTablicu.tablicaZnakova.get(list1.leksickaJedinka);
			if (!(lokalno instanceof TipFunkcija)) {
			System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
					+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <lista_parametara> "
					+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ")");
			System.exit(1);
			} else {
				TipFunkcija lokalnoF = (TipFunkcija) lokalno;
				if (!(lokalnoF.parametriFunkcije.equals(lista.tipovi))) {
					System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
							+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <lista_parametara> "
							+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ")");
					System.exit(1);
				}
				if (!(lokalnoF.povratnaVrijednost == ntip)) {
					System.out.println("<izravni_deklarator> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
							+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <lista_parametara> "
							+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ")");
					System.exit(1);
				}
			}
		}
		else {
			TipFunkcija f = new TipFunkcija(ntip);
			f.povratnaVrijednost = ntip;
			f.parametriFunkcije = new ArrayList<Tip>(lista.tipovi);
			imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, f);
			String def = new String();
			def = list1.leksickaJedinka + " " + Cvor.toString(f.povratnaVrijednost);
			for (Tip tip: f.parametriFunkcije)
				def = def + " " + Cvor.toString(tip);
			SemantickiAnalizator.deklariraneFunkcije.add(def);
		}
	}

}
