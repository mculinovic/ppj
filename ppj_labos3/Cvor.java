import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Cvor {
	

	Cvor roditelj;
	List<Cvor> djeca;
	
	Map<String, Tip> tablicaZnakova;
	
	Cvor imaTablicu;
	Cvor nadredenaTablica;
	
	
	public Cvor() {
		djeca = new ArrayList<Cvor>();
	}
	
	public void dodajDijete(Cvor cvor) {
		djeca.add(cvor);
	}
	
	public void provjeri() {
		
	}
	
	public Tip dohvatiVarijablu(String var) {
		
		Cvor trenutni = imaTablicu;
		Cvor nadredeni = nadredenaTablica;
		if (trenutni.tablicaZnakova.containsKey(var))
			return trenutni.tablicaZnakova.get(var);
		else {
			while (nadredeni != null) {
				trenutni = nadredeni;
				if (trenutni.tablicaZnakova.containsKey(var))
					return trenutni.tablicaZnakova.get(var);
				nadredeni = trenutni.nadredenaTablica;
			}
		}
		return null;
	}
	
	public Cvor dohvatiFunkciju(String var) {
		Cvor trenutni = imaTablicu;
		Cvor nadredeni = nadredenaTablica;
		if (trenutni.tablicaZnakova.containsKey(var))
			return trenutni;
		else {
			while (nadredeni != null) {
				trenutni = nadredeni;
				if (trenutni.tablicaZnakova.containsKey(var))
					return trenutni;
				nadredeni = trenutni.nadredenaTablica;
			}
		}
		return null;
	}
	

	public boolean provjeriImplicitnost(Tip tip1, Tip tip2) {

		if (tip1 == BrojevniTip.tipConstChar && tip2 == BrojevniTip.tipCHAR) return true;
		if (tip1 == BrojevniTip.tipConstINT && tip2 == BrojevniTip.tipINT) return true;
		if (tip1 == BrojevniTip.tipCHAR && tip2 == BrojevniTip.tipConstChar) return true;
		if (tip1 == BrojevniTip.tipINT && tip2 == BrojevniTip.tipConstINT) return true;
		if (tip1 == BrojevniTip.tipCHAR || tip1 == BrojevniTip.tipConstChar) {
			if (tip2 == BrojevniTip.tipINT || tip2 == BrojevniTip.tipConstINT) {
				return true;
			}
		}
		if (tip1 == TipNiz.nizINT && tip2 == TipNiz.nizConstINT) return true;
		if (tip1 == TipNiz.nizCHAR && tip2 == TipNiz.nizConstCHAR) return true;
		if (tip1 == tip2) return true;

		return false;
	}

	public static String toString(Tip t) {
		
		if (t instanceof TipVoid) return "VOID";
		if (t instanceof BrojevniTip) return BrojevniTip.toString((BrojevniTip) t);
		if (t instanceof TipNiz) return TipNiz.toString((TipNiz) t);
		return null;
	}
}
