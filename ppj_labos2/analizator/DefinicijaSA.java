import java.io.Serializable;
import java.util.Map;
import java.util.Set;


public class DefinicijaSA implements Serializable{
	
	private static final long serialVersionUID = -6339777596669220726L;
	private Set<String> sinkronizacijskiZnakovi;
	private Map<Integer, Produkcija> produkcije;
	private Map<String, String> akcija;
	private Map<String, String> novoStanje;
	private Set<String> zavrsniZnakovi;
	
	public DefinicijaSA() {
	}

	public Set<String> getSinkronizacijskiZnakovi() {
		return sinkronizacijskiZnakovi;
	}

	public void setSinkronizacijskiZnakovi(Set<String> sinkronizacijskiZnakovi) {
		this.sinkronizacijskiZnakovi = sinkronizacijskiZnakovi;
	}

	public Map<Integer, Produkcija> getProdukcije() {
		return produkcije;
	}

	public void setProdukcije(Map<Integer, Produkcija> produkcije) {
		this.produkcije = produkcije;
	}

	public Map<String, String> getAkcija() {
		return akcija;
	}

	public void setAkcija(Map<String, String> akcija) {
		this.akcija = akcija;
	}

	public Map<String, String> getNovoStanje() {
		return novoStanje;
	}

	public void setNovoStanje(Map<String, String> novoStanje) {
		this.novoStanje = novoStanje;
	}

	public Set<String> getZavrsniZnakovi() {
		return zavrsniZnakovi;
	}

	public void setZavrsniZnakovi(Set<String> zavrsniZnakovi) {
		this.zavrsniZnakovi = zavrsniZnakovi;
	}

}
