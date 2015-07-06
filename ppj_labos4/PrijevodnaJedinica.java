import java.util.HashMap;



public class PrijevodnaJedinica extends Cvor {
	
	
	public PrijevodnaJedinica() {
		tablicaZnakova = new HashMap<String, Tip>();
	}

	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1) {
			Cvor cvor = djeca.get(0);
			cvor.provjeri();
		} else {
			Cvor cvor1 = djeca.get(0);
			Cvor cvor2 = djeca.get(1);
			cvor1.provjeri();
			cvor2.provjeri();
		}
 
	}

}
