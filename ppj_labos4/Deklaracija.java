
public class Deklaracija extends Cvor {

	@Override
	public void provjeri() {
		
		ImeTipa ime = (ImeTipa) djeca.get(0);
		ListaInitDeklaratora lista = (ListaInitDeklaratora) djeca.get(1);
		ime.provjeri();
		lista.provjeri(ime.tip);

	}

}
