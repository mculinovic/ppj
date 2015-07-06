//import java.util.ArrayList;
//import java.util.List;


public class PrimarniIzraz extends Cvor{
	
	Tip tip;
	boolean lijeviIzraz;
	
	//List<List<String>> produkcije;
	
	
	public PrimarniIzraz() {
		/*produkcije = new ArrayList<List<String>>();
		
		//izgradnja produkcija
		ArrayList<String> produkcija;
		//<primarni_izraz> ::= IDN
		produkcija = new ArrayList<String>();
		produkcija.add("IDN");
		produkcije.add(produkcija);
		//<primarni_izraz> ::= BROJ
		produkcija = new ArrayList<String>();
		produkcija.add("BROJ");
		produkcije.add(produkcija);
		//<primarni_izraz> ::= ZNAK
		produkcija = new ArrayList<String>();
		produkcija.add("ZNAK");
		produkcije.add(produkcija);
		//<primarni_izraz> ::= NIZ_ZNAKOVA
		produkcija = new ArrayList<String>();
		produkcija.add("NIZ_ZNAKOVA");
		produkcije.add(produkcija);
		//<primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA
		produkcija = new ArrayList<String>();
		produkcija.add("L_ZAGRADA");
		produkcija.add("<izraz>");
		produkcija.add("D_ZAGRADA");
		produkcije.add(produkcija);*/
	}

	@Override
	public void provjeri() {
		
		int brojDjece = this.djeca.size();
		switch(brojDjece) {
		case 1: provjeriProdukcijeJedan();
				break;
		case 3: provjeriProdukcijeTri();
				break;
		default: break;
		
		}
		
	}

	private void provjeriProdukcijeJedan() {
		
		CvorList list = (CvorList) djeca.get(0);
		//<primarni_izraz> ::= IDN
		if (list.uniformniZnak.equals("IDN")) {
			Tip tipTablica = dohvatiVarijablu(list.leksickaJedinka);
			if (tipTablica == null) {
				String znak = "<primarni_izraz>";
				System.out.println(znak + " ::= IDN(" + list.brojRetka + "," + list.leksickaJedinka + ")");
				System.exit(1);
			}
			else {
				tip = tipTablica;
				if (tipTablica == BrojevniTip.tipINT || tipTablica == BrojevniTip.tipCHAR) {
					lijeviIzraz = true;
				}
				else 
					lijeviIzraz = false;
			}
		} //<primarni_izraz> ::= BROJ 
		else if (list.uniformniZnak.equals("BROJ")) {
			try {
				Integer.parseInt(list.leksickaJedinka);
			}
			catch (NumberFormatException e) {
				String znak = "<primarni_izraz>";
				System.out.println(znak + " ::= BROJ(" + list.brojRetka + "," + list.leksickaJedinka + ")");
				System.exit(1);
			}
			this.tip = BrojevniTip.tipINT;
			lijeviIzraz = false;
		} //<primarni_izraz> ::= ZNAK 
		else if (list.uniformniZnak.equals("ZNAK")) {
			list.leksickaJedinka = list.leksickaJedinka.substring(1, list.leksickaJedinka.length() - 1);
			if (list.leksickaJedinka.length() > 1) {
				if (list.leksickaJedinka.length() > 2) {
					String znak = "<primarni_izraz>";
					System.out.println(znak + " ::= ZNAK(" + list.brojRetka + "," + list.leksickaJedinka + ")");
					System.exit(1);
				}
				else {
					char znak1 = list.leksickaJedinka.charAt(1);
					if (list.leksickaJedinka.charAt(0) != '\\' ||
						znak1 != 't' || znak1 != 'n' || znak1 != '0' || znak1 != '\'' || znak1 != '\"' || znak1 != '\\') {
						String znak = "<primarni_izraz>";
						System.out.println(znak + " ::= ZNAK(" + list.brojRetka + "," + list.leksickaJedinka + ")");
						System.exit(1);
					}
				}
			}
			this.tip = BrojevniTip.tipCHAR;
			lijeviIzraz = false;
		} //<primarni_izraz> ::= NIZ_ZNAKOVA 
		else if (list.uniformniZnak.equals("NIZ_ZNAKOVA")) {
			SemantickiAnalizator.nizZnakova = true;
			SemantickiAnalizator.duljinaZnakova = list.leksickaJedinka.length() - 2;
			String leksicka = list.leksickaJedinka;
			if (leksicka.charAt(0) != '"' || leksicka.charAt(leksicka.length() - 1) != '"') {
				String znak = "<primarni_izraz>";
				System.out.println(znak + " ::= NIZ_ZNAKOVA(" + list.brojRetka + "," + list.leksickaJedinka + ")");
				System.exit(1);
			}
			if (leksicka.contains("\\")) {
				for (int i = 0; i < leksicka.length(); ++i) {
					if (leksicka.charAt(i) == '\\') {
						if (i < leksicka.length() - 1) {
							char znak1 = leksicka.charAt(i + 1);
							if (znak1 != 't' || znak1 != 'n' || znak1 != '0' || znak1 != '\'' || znak1 != '\"' || znak1 != '\\') {
										String znak = "<primarni_izraz>";
										System.out.println(znak + " ::= NIZ_ZNAKOVA(" + list.brojRetka + "," + list.leksickaJedinka + ")");
										System.exit(1);
							}	
						}
					}
				}
			}
			this.tip = TipNiz.nizConstCHAR;
			lijeviIzraz = false;
		}
		
	}

	private void provjeriProdukcijeTri() {
		Cvor cvor = djeca.get(1);
		cvor.provjeri();
		tip = ((Izraz)cvor).tip;
		lijeviIzraz = ((Izraz) cvor).lijeviIzraz;
	}

}
