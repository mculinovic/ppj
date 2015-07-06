import java.util.HashSet;
import java.util.Set;


public class Stavka {
	
	private int brojProdukcije;
	private int pozicijaTocke;
	private Set<String> zavrsniZnakoviStavke;
	
	public Stavka(int brojProdukcije, int pozicijaTocke) {
		this.setBrojProdukcije(brojProdukcije);
		this.setPozicijaTocke(pozicijaTocke);
		this.zavrsniZnakoviStavke = new HashSet<String>();
	}
	
	public Stavka(int brojProdukcije, int pozicijaTocke, Set<String> znakovi) {
		this.setBrojProdukcije(brojProdukcije);
		this.setPozicijaTocke(pozicijaTocke);
		this.zavrsniZnakoviStavke = new HashSet<String>();
		for (String znak: znakovi)
			this.zavrsniZnakoviStavke.add(znak);
	}

	public int getBrojProdukcije() {
		return brojProdukcije;
	}

	public void setBrojProdukcije(int brojProdukcije) {
		this.brojProdukcije = brojProdukcije;
	}

	public int getPozicijaTocke() {
		return pozicijaTocke;
	}

	public void setPozicijaTocke(int pozicijaTocke) {
		this.pozicijaTocke = pozicijaTocke;
	}
	
	public void dodajZnak(String znak) {
		zavrsniZnakoviStavke.add(znak);
	}
	
	public Set<String> dohvatiZnakove() {
		return zavrsniZnakoviStavke;
	}
	
	public boolean equals (Object arg0) {
		if (arg0 == null) return false;
		if (!(arg0 instanceof Stavka)) return false;
		Stavka s = (Stavka) arg0;
		return Integer.valueOf(brojProdukcije).equals(Integer.valueOf(s.brojProdukcije))
				&& Integer.valueOf(pozicijaTocke).equals(Integer.valueOf(s.pozicijaTocke))
				&& zavrsniZnakoviStavke.equals(s.zavrsniZnakoviStavke);
	}
	
	public int hashCode() {
		return Integer.valueOf(brojProdukcije).hashCode() 
				^Integer.valueOf(pozicijaTocke).hashCode()
				^zavrsniZnakoviStavke.hashCode();
	}

}
