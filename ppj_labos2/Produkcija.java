import java.io.Serializable;


public class Produkcija implements Serializable{
	
	private static final long serialVersionUID = 2917697832432405561L;
	private String lijevaStrana;
	private String[] desnaStrana;
	
	public Produkcija(String lijevaStrana, String desnaStrana) {
		this.setLijevaStrana(lijevaStrana);
		this.setDesnaStrana(desnaStrana.split(" "));
	}

	public String getLijevaStrana() {
		return lijevaStrana;
	}

	public void setLijevaStrana(String lijevaStrana) {
		this.lijevaStrana = lijevaStrana;
	}

	public String[] getDesnaStrana() {
		return desnaStrana;
	}

	public void setDesnaStrana(String[] desnaStrana) {
		this.desnaStrana = desnaStrana;
	}
	
}
