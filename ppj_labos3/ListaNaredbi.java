
public class ListaNaredbi extends Cvor {

	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1)
			provjeri1();
		else
			provjeri2();
	}

	private void provjeri1() {
		Cvor cvor = djeca.get(0);
		cvor.provjeri();
	}

	private void provjeri2() {
		Cvor cvor = djeca.get(0);
		Cvor cvor2 = djeca.get(1);
		cvor.provjeri();
		cvor2.provjeri();
		
	}

}
