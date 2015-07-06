import java.util.List;

//import java.util.ArrayList;
//import java.util.List;

public class PostfiksIzraz extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	//List<List<String>> produkcije;
	
	/*PRODUKCIJE
	 * <postfiks_izraz> ::= <primarni_izraz> broj = 1
	 * <postfiks_izraz> ::= <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA broj = 4
	 * <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA D_ZAGRADA broj = 3
	 * <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA broj = 4
	 * <postfiks_izraz> ::= <postfiks_izraz> (OP_INC | OP_DEC) broj = 2
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();

		switch(brojDjece) {
		case 1: provjeri1();
				break;
		case 2: provjeri2();
				break;
		case 3: provjeri3();
				break;
		case 4: provjeri4();
				break;
		default: break;
		}
	}

	private void provjeri1() {
		Cvor cvor = djeca.get(0);
		cvor.provjeri();
		tip = ((PrimarniIzraz) cvor).tip;
		lijeviIzraz = ((PrimarniIzraz) cvor).lijeviIzraz;
	}

	private void provjeri2() {
		PostfiksIzraz cvor = (PostfiksIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		cvor.provjeri();
		if (!cvor.lijeviIzraz || (cvor.tip != BrojevniTip.tipCHAR && cvor.tip != BrojevniTip.tipINT)) {
			String znak = "<postfiks_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ")");
			System.exit(1);
		}
		tip = BrojevniTip.tipINT;
		lijeviIzraz = false;
	}

	private void provjeri3() {
		PostfiksIzraz cvor = (PostfiksIzraz) djeca.get(0);
		CvorList list1 = (CvorList) djeca.get(1);
		CvorList list2 = (CvorList) djeca.get(2);
		cvor.provjeri();
		Tip tip = cvor.tip;
		if (!(tip instanceof TipFunkcija)) {
			String znak = "<postfiks_izraz>";
			System.out.println(znak + " ::= " + znak + " " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
								+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ")");
			System.exit(1);
		}
		else {
			TipFunkcija funkcija = (TipFunkcija) tip;
			if (!funkcija.parametriFunkcije.isEmpty()) {
				String znak = "<postfiks_izraz>";
				System.out.println(znak + " ::= " + znak + " " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") "
									+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ")");
				System.exit(1);
			}
			this.tip = funkcija.povratnaVrijednost;
			lijeviIzraz = false;
		}
	}

	private void provjeri4() {
		CvorList list1 = (CvorList) djeca.get(1);
		PostfiksIzraz cvor = (PostfiksIzraz) djeca.get(0);
		if (list1.uniformniZnak.equals("L_UGL_ZAGRADA")) {
			cvor.provjeri();
			CvorList list2 = (CvorList) djeca.get(3);
			if (!(cvor.tip instanceof TipNiz)) {
				String znak = "<postfiks_izraz>";
				System.out.println(znak + " ::= " + znak + " " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") <izraz> " + 
								   list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ")");
				System.exit(1);
			}
			Izraz izraz = (Izraz) djeca.get(2);
			izraz.provjeri();
			if (!(izraz.tip instanceof BrojevniTip)) {
				String znak = "<postfiks_izraz>";
				System.out.println(znak + " ::= " + znak + " " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") <izraz> " + 
						   list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ")");
				System.exit(1);
			}
			tip = TipNiz.dohvatiTip((TipNiz) cvor.tip);
			if (tip != BrojevniTip.tipConstChar && tip != BrojevniTip.tipConstINT)
				lijeviIzraz = true;
			else 
				lijeviIzraz = false;
			
		} else {
			cvor.provjeri();
			CvorList list2 = (CvorList) djeca.get(3);
			ListaArgumenata lista = (ListaArgumenata) djeca.get(2); 
			lista.provjeri();
			if (!(cvor.tip instanceof TipFunkcija)) {
				String znak = "<postfiks_izraz>";
				System.out.println(znak + " ::= " + znak + " " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") <lista_argumenata> " + 
								   list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ")");
				System.exit(1);
			}
			TipFunkcija funkcija = (TipFunkcija) cvor.tip;
			if (!provjeriArgumente(lista.tipovi, funkcija.parametriFunkcije)) {
				String znak = "<postfiks_izraz>";
				System.out.println(znak + " ::= " + znak + " " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") <lista_argumenata> " + 
								   list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ")");
				System.exit(1);
			}
			this.tip = funkcija.povratnaVrijednost;
			lijeviIzraz = false;
		}
		
	}

	private boolean provjeriArgumente(List<Tip> tipovi, List<Tip> parametriFunkcije) {
		if (tipovi.size() != parametriFunkcije.size() || parametriFunkcije.size() == 0)
			return false;
		for (int i = 0; i < tipovi.size(); ++i) {
			if (!provjeriImplicitnost(tipovi.get(i), parametriFunkcije.get(i)))
				return false;
		}
		return true;
	}

}
