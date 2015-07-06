import java.util.ArrayList;
import java.util.List;


public class ListaParametara extends Cvor {

	List<Tip> tipovi;
	List<String> imena;
	
	/*PRODUKCIJE
	 * <lista_parametara> ::= <deklaracija_parametra>
	 * <lista_parametara> ::= <lista_parametara> ZAREZ <deklaracija_parametra>
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
		DeklaracijaParametra dekl = (DeklaracijaParametra) djeca.get(0);
		dekl.provjeri();
		tipovi = new ArrayList<Tip>();
		tipovi.add(dekl.tip);
		imena = new ArrayList<String>();
		imena.add(dekl.ime);
		
	}

	private void provjeri3() {
		ListaParametara lista = (ListaParametara) djeca.get(0);
		CvorList list = (CvorList) djeca.get(1);
		DeklaracijaParametra dekl = (DeklaracijaParametra) djeca.get(2);
		lista.provjeri();
		dekl.provjeri();
		if (lista.imena.contains(dekl.ime)) {
			System.out.println("<lista_parametara ::= <lista_parametara " + list.uniformniZnak + "(" + list.brojRetka + "," + list.leksickaJedinka + ") <deklaracija_parametra>");
			System.exit(1);
		}
		tipovi = new ArrayList<Tip>(lista.tipovi);
		tipovi.add(dekl.tip);
		imena = new ArrayList<String>(lista.imena);
		imena.add(dekl.ime);
	}

}
