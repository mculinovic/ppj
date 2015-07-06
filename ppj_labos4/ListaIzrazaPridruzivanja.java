import java.util.ArrayList;
import java.util.List;


public class ListaIzrazaPridruzivanja extends Cvor {

	List<Tip> tipovi;
	int brojElemenata;
	
	
	/*PRODUKCIJE
	 * <lista_izraza_pridruzivanja> ::= <izraz_pridruzivanja>
	 * <lista_izraza_pridruzivanja> ::= <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>
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
		tipovi = new ArrayList<Tip>();
		tipovi.add(izraz.tip);
		brojElemenata = 1;
	}


	private void provjeri3() {
		ListaIzrazaPridruzivanja lista = (ListaIzrazaPridruzivanja) djeca.get(0);
		IzrazPridruzivanja izraz = (IzrazPridruzivanja) djeca.get(2);
		lista.provjeri();
		izraz.provjeri();
		tipovi = new ArrayList<Tip>(lista.tipovi);
		tipovi.add(izraz.tip);
		brojElemenata = lista.brojElemenata + 1;
	}

}
