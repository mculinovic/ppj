
public class SpecifikatorTipa extends Cvor {
	
	Tip tip;

	@Override
	public void provjeri() {
		
		CvorList cvor = (CvorList) djeca.get(0);
		String rijec = cvor.uniformniZnak;
		if (rijec.equals("KR_VOID")) 
			tip = TipVoid.tipVoid;
		else if (rijec.equals("KR_CHAR")) 
			tip = BrojevniTip.tipCHAR;
		else if (rijec.equals("KR_INT")) 
			tip = BrojevniTip.tipINT;

	}

}
