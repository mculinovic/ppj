import java.util.ArrayList;
import java.util.List;


public class Cvor extends ZnakStoga{
	
	private int brojRetka;
	private String uniformniZnak;
	private String leksickaJedinka;
	private List<Cvor> djeca;
	private boolean jeList;
	
	public Cvor() {
		djeca = new ArrayList<Cvor>();
	}

	public int getBrojRetka() {
		return brojRetka;
	}

	public void setBrojRetka(int brojRetka) {
		this.brojRetka = brojRetka;
	}

	public String getUniformniZnak() {
		return uniformniZnak;
	}

	public void setUniformniZnak(String uniformniZnak) {
		this.uniformniZnak = uniformniZnak;
	}

	public String getLeksickaJedinka() {
		return leksickaJedinka;
	}

	public void setLeksickaJedinka(String leksickaJedinka) {
		this.leksickaJedinka = leksickaJedinka;
	}
	
	public void dodajDijete(Cvor dijete) {
		djeca.add(0, dijete);
	}

	public boolean isJeList() {
		return jeList;
	}

	public void setJeList(boolean jeList) {
		this.jeList = jeList;
	}

	public List<Cvor> getDjeca() {
		return djeca;
	}

	public void setDjeca(List<Cvor> djeca) {
		this.djeca = djeca;
	}

}
