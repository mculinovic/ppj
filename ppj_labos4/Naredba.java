
public class Naredba extends Cvor {

	@Override
	public void provjeri() {
		
		Cvor cvor = djeca.get(0);
		if (cvor instanceof SlozenaNaredba) ((SlozenaNaredba)cvor).provjeri(null, null);
		else cvor.provjeri();
	}

}
