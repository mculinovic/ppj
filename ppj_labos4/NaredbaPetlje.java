import java.io.IOException;


public class NaredbaPetlje extends Cvor {

	
	/*PRODUKCIJE
	 * <naredba_petlje> ::= KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
	 * <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba>1 <izraz_naredba>2 D_ZAGRADA <naredba>
	 * <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba>1 <izraz_naredba>2 <izraz> D_ZAGRADA <naredba>
	 * */
	@Override
	public void provjeri() {
		
		GeneratorKoda.unutarPetlje++;
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 5)
			provjeri5();
		else if (brojDjece == 6)
			provjeri6();
		else 
			provjeri7();
		
		GeneratorKoda.unutarPetlje--;
	}
	
	private void provjeri5() {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(1);
		CvorList list3 = (CvorList) djeca.get(3);
		Izraz izraz = (Izraz) djeca.get(2);
		Naredba naredba = (Naredba) djeca.get(4);
		izraz.provjeri();
		if (!provjeriImplicitnost(izraz.tip, BrojevniTip.tipINT)) {
			System.out.println("<naredba_petlja ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") " +
								list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <izraz> " + 
								list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") <naredba>");
			System.exit(1);
		}
		naredba.provjeri();
	}

	private void provjeri6() {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(1);
		CvorList list3 = (CvorList) djeca.get(4);
		IzrazNaredba izraz1 = (IzrazNaredba) djeca.get(2);
		IzrazNaredba izraz2 = (IzrazNaredba) djeca.get(3);
		Naredba naredba = (Naredba) djeca.get(5);
		izraz1.provjeri();
		izraz2.provjeri();
		if (!provjeriImplicitnost(izraz2.tip, BrojevniTip.tipINT)) {
			System.out.println("<naredba_petlja ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") " +
								list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <izraz_naredba> <izraz_naredba> " + 
								list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") <naredba>");
			System.exit(1);
		}
		naredba.provjeri();
		
	}

	private void provjeri7() {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(1);
		CvorList list3 = (CvorList) djeca.get(5);
		IzrazNaredba izraz1 = (IzrazNaredba) djeca.get(2);
		IzrazNaredba izraz2 = (IzrazNaredba) djeca.get(3);
		Izraz izraz = (Izraz) djeca.get(4);
		Naredba naredba = (Naredba) djeca.get(6);
		izraz1.provjeri();
		izraz2.provjeri();
		if (!provjeriImplicitnost(izraz2.tip, BrojevniTip.tipINT)) {
			System.out.println("<naredba_petlja ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ") " +
								list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <izraz_naredba> <izraz_naredba> <izraz> " + 
								list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") <naredba>");
			System.exit(1);
		}
		izraz.provjeri();
		
		
		naredba.provjeri();
		
		
	}

}
