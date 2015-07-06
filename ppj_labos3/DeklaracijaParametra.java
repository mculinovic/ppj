
public class DeklaracijaParametra extends Cvor {

	Tip tip;
	String ime;
	
	/*PRODUKCIJE
	 * <deklaracija_parametra> ::= <ime_tipa> IDN
	 * <deklaracija_parametra> ::= <ime_tipa> IDN L_UGL_ZAGRADA D_UGL_ZAGRADA
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 2) 
			provjeri2();
		else 
			provjeri4();

	}

	private void provjeri2() {
		ImeTipa ime = (ImeTipa) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		ime.provjeri();
		if (ime.tip instanceof TipVoid) {
			System.out.println("<deklaracija_parametra> ::= <ime_tipa> " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ")");
			System.exit(1);
		}
		tip = ime.tip;
		this.ime = list.leksickaJedinka;
		//imaTablicu.tablicaZnakova.put(list.leksickaJedinka, tip);
	}

	private void provjeri4() {
		ImeTipa ime = (ImeTipa) djeca.get(0);
		CvorList list1 = (CvorList) djeca.get(1);
		CvorList list2 = (CvorList) djeca.get(1);
		CvorList list3 = (CvorList) djeca.get(1);
		ime.provjeri();
		if (ime.tip instanceof TipVoid) {
			System.out.println("<deklaracija_parametra> ::= <ime_tipa> " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
								+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") "
								+ list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ")");
			System.exit(1);
		}
		tip = TipNiz.dohvatiTipNiz((BrojevniTip) ime.tip);
		this.ime = list1.leksickaJedinka;
		//imaTablicu.tablicaZnakova.put(list1.leksickaJedinka, tip);
	}

}
