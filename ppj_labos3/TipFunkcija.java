import java.util.ArrayList;
import java.util.List;


public class TipFunkcija extends Tip {
	
	Tip povratnaVrijednost;
	List<Tip> parametriFunkcije;
	
	public TipFunkcija(Tip tip) {
		povratnaVrijednost = tip;
		parametriFunkcije = new ArrayList<Tip>();
	}
	
	public void dodajParametar(Tip tip) {
		parametriFunkcije.add(tip);
	}

}
