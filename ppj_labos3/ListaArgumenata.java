import java.util.ArrayList;
import java.util.List;


public class ListaArgumenata extends Cvor {
	
	List<Tip> tipovi;

	/*PRODUKCIJE
	 * <lista_argumenata> ::= <izraz_pridruzivanja>
	 * <lista_argumenata> ::= <lista_argumenata> ZAREZ <izraz_pridruzivanja>
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		if (brojDjece == 1) provjeri1();
		else provjeri3();
	}

	private void provjeri1() {
		IzrazPridruzivanja cvor = (IzrazPridruzivanja) djeca.get(0);
		cvor.provjeri();
		tipovi = new ArrayList<Tip>();
		tipovi.add(cvor.tip);
		
	}

	private void provjeri3() {
		Cvor cvor1 = djeca.get(0);
		Cvor cvor2 = djeca.get(2);
		cvor1.provjeri();
		cvor2.provjeri();
		tipovi = new ArrayList<Tip>(((ListaArgumenata)cvor1).tipovi);
		tipovi.add(((IzrazPridruzivanja) cvor2).tip);
		
	}

}
