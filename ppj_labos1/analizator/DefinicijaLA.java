import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author mculinovic
 *
 * Razred sluzi za spremanje cjelovite definicije leksickog analizatora
 */
public class DefinicijaLA implements Serializable {

	private static final long serialVersionUID = 5326545343781084588L;
	private List<Pravilo> pravila;
	private List<String> stanja;
	
	public DefinicijaLA() {
		pravila = new ArrayList<Pravilo>();
		stanja = new ArrayList<String>();
	}
	
	public void dodajPravilo(Pravilo pravilo) {
		pravila.add(pravilo);
	}
	
	public int getBrojPravila() {
		return pravila.size();
	}
	
	public Pravilo getPraviloAt(int index) {
		return pravila.get(index);
	}
	
	public Automat getAutomatAt(int index) {
		return pravila.get(index).getAutomat();
	}
	
	public void dodajStanja(List<String> stanja) {
		this.stanja = stanja;
	}
	
	public String getStanjeAt(int index) {
		return stanja.get(index);
	}
}
