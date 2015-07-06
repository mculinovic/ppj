
public class NaredbaSkoka extends Cvor {

	/*PRODUKCIJE
	 * <naredba_skoka> ::= (KR_CONTINUE | KR_BREAK) TOCKAZAREZ
	 * <naredba_skoka> ::= KR_RETURN TOCKAZAREZ
	 * <naredba_skoka> ::= KR_RETURN <izraz> TOCKAZAREZ
	 * */
	
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 2)
			provjeri2();
		else
			provjeri3();
	}

	private void provjeri2() {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(1);
		if ((list1.uniformniZnak).equals("KR_RETURN")) {
			if (SemantickiAnalizator.unutarFunkcije == 0) {
				System.out.println("<naredba_skoka> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka +") "
									+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka +") ");
				System.exit(1);
			}
			else if (!(SemantickiAnalizator.globalnaFunkcija.povratnaVrijednost instanceof TipVoid)) {
				System.out.println("<naredba_skoka> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka +") "
						+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka +") ");
				System.exit(1);
			}
		} else {
			if (SemantickiAnalizator.unutarPetlje == 0) {
				System.out.println("<naredba_skoka> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka +") "
						+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka +") ");
				System.exit(1);
			}
		}
		
	}

	private void provjeri3() {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(2);
		Izraz izraz = (Izraz) djeca.get(1);
		izraz.provjeri();
		if (SemantickiAnalizator.unutarFunkcije == 0 || SemantickiAnalizator.globalnaFunkcija.povratnaVrijednost instanceof TipVoid) {
			System.out.println("<naredba_skoka> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka +") <izraz> "
					+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka +") ");
			System.exit(1);
		}
		if (!provjeriImplicitnost(izraz.tip, SemantickiAnalizator.globalnaFunkcija.povratnaVrijednost)) {
			System.out.println("<naredba_skoka> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka +") <izraz> "
					+ list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka +") ");
			System.exit(1);
		}
		
	}
}
