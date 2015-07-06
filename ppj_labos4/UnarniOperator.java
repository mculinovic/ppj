
public class UnarniOperator extends Cvor {

	@Override
	public void provjeri() {
		
		CvorList list = (CvorList) djeca.get(0);
		if ((list.uniformniZnak).equals("MINUS")) {
			if (GeneratorKoda.negativan == true) 
				GeneratorKoda.negativan = false;
			else
				GeneratorKoda.negativan = true;
		}
	}

}
