import java.util.HashMap;
import java.util.List;



public class SlozenaNaredba extends Cvor {

	
	public SlozenaNaredba() {
		tablicaZnakova = new HashMap<String, Tip>();
	}
	
	/*PRODUKCIJE
	 * <slozena_naredba> ::= L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
	 * <slozena_naredba> ::= L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA
	 * */
	
	public void provjeri(List<Tip> tipovi, List<String> imena) {
		
		if (tipovi != null) {
			for (int i = 0; i < tipovi.size(); ++i) {
				imaTablicu.tablicaZnakova.put(imena.get(i), tipovi.get(i));
			}
		}
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 3)
			provjeri3();
		else 
			provjeri4();
	}

	private void provjeri3() {
		Cvor cvor = djeca.get(1);
		cvor.provjeri();
	}

	private void provjeri4() {
		Cvor cvor = djeca.get(1);
		Cvor cvor2 = djeca.get(2);
		cvor.provjeri();
		cvor2.provjeri();
	}

}
