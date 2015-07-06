
public class IzrazPridruzivanja extends Cvor {

	Tip tip;
	boolean lijeviIzraz;
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 1)
			provjeri1();
		else 
			provjeri3();

	}

	private void provjeri1() {
		LogIliIzraz log = (LogIliIzraz) djeca.get(0);
		log.provjeri();
		tip = log.tip;
		lijeviIzraz = log.lijeviIzraz;		
	}

	private void provjeri3() {
		PostfiksIzraz post = (PostfiksIzraz) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		IzrazPridruzivanja izraz = (IzrazPridruzivanja) djeca.get(2);
		post.provjeri();
		if (!post.lijeviIzraz) {
			String znak = "<izraz_pridruzivanja>";
			System.out.println(znak + " ::= <postfiks_izraz> " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") " + znak);
			System.exit(1);
		}
		izraz.provjeri();
		if (!provjeriImplicitnost(izraz.tip, post.tip)) {
			String znak = "<izraz_pridruzivanja>";
			System.out.println(znak + " ::= <postfiks_izraz> " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") " + znak);
			System.exit(1);
		}
		tip = post.tip;
		lijeviIzraz = false;
	}

}
