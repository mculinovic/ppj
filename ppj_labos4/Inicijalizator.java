import java.util.ArrayList;
import java.util.List;


public class Inicijalizator extends Cvor {

	int brojElemenata;
	List<Tip> tipovi;
	Tip tip;
	
	/*PRODUKCIJE
	 * <inicijalizator> ::= <izraz_pridruzivanja>
	 * <inicijalizator> ::= L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA
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
		
		IzrazPridruzivanja izraz = (IzrazPridruzivanja) djeca.get(0);
		izraz.provjeri();
		if (GeneratorKoda.nizZnakova) {
			brojElemenata = GeneratorKoda.duljinaZnakova + 1;
			tipovi = new ArrayList<Tip>(brojElemenata);
			//for (int i = 0; i < brojElemenata; ++i)
			//	tipovi.set(i, BrojevniTip.tipCHAR);
			for (Tip t: tipovi)
				t = BrojevniTip.tipCHAR;
		}
		else {
			tip = izraz.tip;
		}
		GeneratorKoda.duljinaZnakova = 0;
		GeneratorKoda.nizZnakova = false;
	}

	private void provjeri3() {
		ListaIzrazaPridruzivanja lista = (ListaIzrazaPridruzivanja) djeca.get(1);
		lista.provjeri();
		brojElemenata = lista.brojElemenata;
		tipovi = new ArrayList<Tip>(lista.tipovi);
	}

}
