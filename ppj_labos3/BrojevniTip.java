
public class BrojevniTip extends Tip {
	
	public enum TipPodatka {
		INT,
		constINT,
		CHAR,
		constCHAR,
	}
	
	public TipPodatka tip;
	
	public static BrojevniTip tipINT = new BrojevniTip(TipPodatka.INT);
	public static BrojevniTip tipConstINT = new BrojevniTip(TipPodatka.constINT);
	public static BrojevniTip tipCHAR = new BrojevniTip(TipPodatka.CHAR);
	public static BrojevniTip tipConstChar = new BrojevniTip(TipPodatka.constCHAR);
	
	
	private BrojevniTip(TipPodatka tip) {
		this.tip = tip;
	}
	
	public static BrojevniTip dohvatiBrojevniTip (String podatak) {
		if (podatak.equals("KR_INT")) return tipINT;
		else if (podatak.equals("KR_CHAR"))return tipCHAR;
		else return null;
	}

	public static String toString(BrojevniTip t) {
		if (t == BrojevniTip.tipCHAR) return "CHAR";
		if (t == BrojevniTip.tipINT) return "INT";
		if (t == BrojevniTip.tipConstChar) return "CONST_CHAR";
		if (t == BrojevniTip.tipConstINT) return "CONST_INT";
		return null;
	}

}
