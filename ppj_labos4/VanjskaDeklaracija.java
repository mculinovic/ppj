
public class VanjskaDeklaracija extends Cvor {

	@Override
	public void provjeri() {
		
		Cvor cvor = djeca.get(0);
		cvor.provjeri();
	}

}
