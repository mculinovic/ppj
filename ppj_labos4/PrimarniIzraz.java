import java.io.IOException;
import java.util.ArrayList;


public class PrimarniIzraz extends Cvor{
	
	Tip tip;
	boolean lijeviIzraz;
	
	
	public PrimarniIzraz() {
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
			

			String labela = new String();
			
			if (tip instanceof TipFunkcija) {
				labela = "F_" + list.leksickaJedinka.toUpperCase();
				try {
					if (!GeneratorKoda.call)
						GeneratorKoda.out.write("\tCALL " + labela + "\n");
					else  {
						GeneratorKoda.pozivFunkcije = "\tCALL " + labela + "\n";
						GeneratorKoda.parametriFunkcije = new ArrayList<String>();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
			else {
				labela = new String();
				labela = "(G_" + list.leksickaJedinka.toUpperCase() + ")";
				try {
					if (!GeneratorKoda.funkcParametri && !GeneratorKoda.lokalneVarijable.contains(list.leksickaJedinka.toUpperCase()))
						GeneratorKoda.out.write("\tLOAD R6, " + labela + "\n");
					else if (GeneratorKoda.lokalneVarijable.contains(list.leksickaJedinka.toUpperCase())){
						int odmak = GeneratorKoda.lokalneVarijable.lastIndexOf(list.leksickaJedinka.toUpperCase());
						odmak = 160 - 4*(odmak + 1);
						GeneratorKoda.out.write("\tLOAD R6,(R1+" + odmak + ")\n");						
					}
					else {
						GeneratorKoda.brojacParametara++;
						int odmak = GeneratorKoda.parametriFunkcije.lastIndexOf(list.leksickaJedinka.toUpperCase());
						int velicina = GeneratorKoda.parametriFunkcije.size();
						velicina = velicina * 4;
						odmak = velicina - 4*odmak;
						GeneratorKoda.out.write("\tLOAD R6, (R1+" + (odmak+160) + ")\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			
			
			try {
				int broj = Integer.parseInt(list.leksickaJedinka);
				if (GeneratorKoda.negativan) {
					broj = -broj;
					GeneratorKoda.negativan = false;
				}
				if (GeneratorKoda.unutarFunkcije == 0) {
					GeneratorKoda.globalneVarijable += broj + "\n";
				} else {
					
				if (broj < 524287) {
					if (GeneratorKoda.call) {
						
						GeneratorKoda.out.write("\tMOVE " + broj + ", R6\n");
						GeneratorKoda.out.write("\tPUSH R6\n");
					}
					else {
						GeneratorKoda.out.write("\tMOVE " + broj + ", R6\n");
					}

				//GeneratorKoda.out.write("\tPUSH R6\n");
				}
				else {
					GeneratorKoda.globalneVarijable += ("BR_" + list.leksickaJedinka.toUpperCase() + "\tDW %D " + broj + "\n");
					GeneratorKoda.out.write("\tLOAD R6, (" + "BR_" + list.leksickaJedinka.toUpperCase() + ")\n");
				}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} //<primarni_izraz> ::= ZNAK 
		else if (list.uniformniZnak.equals("ZNAK")) {
			list.leksickaJedinka = list.leksickaJedinka.substring(1, list.leksickaJedinka.length() - 1);
			
			
			//Generiranje koda
			int brojevnaVrijednost = list.leksickaJedinka.codePointAt(0);
			
			try {
				if (GeneratorKoda.unutarFunkcije == 0) {
					GeneratorKoda.globalneVarijable += Integer.toString(brojevnaVrijednost) + "\n";
				} else {
					
				if (GeneratorKoda.call) {
						
					GeneratorKoda.out.write("\tMOVE " + Integer.toString(brojevnaVrijednost) + ", R6\n");
					GeneratorKoda.out.write("\tPUSH R6\n");
				}
				else {
					GeneratorKoda.out.write("\tMOVE " + Integer.toString(brojevnaVrijednost) + ", R6\n");
				}

				//GeneratorKoda.out.write("\tPUSH R6\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Zavrseno generiranje koda
			
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
			GeneratorKoda.nizZnakova = true;
			GeneratorKoda.duljinaZnakova = list.leksickaJedinka.length() - 2;
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
