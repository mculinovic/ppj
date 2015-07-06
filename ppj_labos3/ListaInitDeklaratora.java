
public class ListaInitDeklaratora extends Cvor {

	@Override
	public void provjeri() {
		// TODO Auto-generated method stub

	}
	
	
	/*PRODUKCIJE
	 * <lista_init_deklaratora> ::= <init_deklarator>
	 * <lista_init_deklaratora>1 ::= <lista_init_deklaratora>2 ZAREZ <init_deklarator>
	 * */

	public void provjeri(Tip ntip) {		
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1)
			provjeri1(ntip);
		else 
			provjeri3(ntip);
		
	}


	private void provjeri1(Tip ntip) {
		InitDeklarator init = (InitDeklarator) djeca.get(0);
		init.provjeri(ntip);
		
	}


	private void provjeri3(Tip ntip) {
		ListaInitDeklaratora lista = (ListaInitDeklaratora) djeca.get(0);
		InitDeklarator init = (InitDeklarator) djeca.get(2);
		lista.provjeri(ntip);
		init.provjeri(ntip);		
	}

}
