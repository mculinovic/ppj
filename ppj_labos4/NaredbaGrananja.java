import java.io.IOException;


public class NaredbaGrananja extends Cvor {

	
	/*PRODUKCIJE
	 * <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
	 * <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>1 KR_ELSE <naredba>2
	 * */
	
	@Override
	public void provjeri() {
		
		int brojDjece = djeca.size();
		
		if (brojDjece == 5)
			provjeri5();
		else
			provjeri7();
	}

	private void provjeri5() {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(1);
		CvorList list3 = (CvorList) djeca.get(3);
		Izraz izraz = (Izraz) djeca.get(2);
		Naredba naredba = (Naredba) djeca.get(4);
		izraz.provjeri();
		if (!provjeriImplicitnost(izraz.tip, BrojevniTip.tipINT)) {
			System.out.println("<naredba_grananja> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ")"
								+ " " + list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <izraz> " +
								list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") <naredba>");
			System.exit(1);
		}
		
		//GENERIRANJE KODA
		
		try {
			GeneratorKoda.out.write("\tJP_NZ DALJE\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//GENERIRANJE GOTOVO
		
		naredba.provjeri();
		
		
		//GENERIRANJE KODA
		try {
			GeneratorKoda.out.write("DALJE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//GENERIRANJE GOTOVO
	}

	private void provjeri7() {
		CvorList list1 = (CvorList) djeca.get(0);
		CvorList list2 = (CvorList) djeca.get(1);
		CvorList list3 = (CvorList) djeca.get(3);
		CvorList list4 = (CvorList) djeca.get(5);
		Izraz izraz = (Izraz) djeca.get(2);
		Naredba naredba1 = (Naredba) djeca.get(4);
		Naredba naredba2 = (Naredba) djeca.get(6);
		izraz.provjeri();
		if (!provjeriImplicitnost(izraz.tip, BrojevniTip.tipINT)) {
			System.out.println("<naredba_grananja> ::= " + list1.uniformniZnak + "(" + list1.brojRetka + "," + list1.leksickaJedinka + ")"
								+ " " + list2.uniformniZnak + "(" + list2.brojRetka + "," + list2.leksickaJedinka + ") <izraz> " +
								list3.uniformniZnak + "(" + list3.brojRetka + "," + list3.leksickaJedinka + ") <naredba> " +
								list4.uniformniZnak + "(" + list4.brojRetka + "," + list4.leksickaJedinka + ") <naredba>");
			System.exit(1);
		}
		
		try {
			if (!GeneratorKoda.odnosniIzraz) 
				GeneratorKoda.out.write("\tJP_NZ DALJE\n");
			else 
				GeneratorKoda.odnosniIzraz = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//GENERIRANJE GOTOVO
		
		naredba1.provjeri();
		
		
		//GENERIRANJE KODA
				try {
					GeneratorKoda.out.write("DALJE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//GENERIRANJE GOTOVO
		
		naredba2.provjeri();
		
	}

}
