import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Automat {

	
	public List<Set<Stavka>> stanja;
	public Map<String, Integer> tablicaPrijelaza;
	
	public Automat() {
		stanja= new ArrayList<Set<Stavka>>();
		tablicaPrijelaza = new HashMap<String, Integer>();
	}
	
	
}
