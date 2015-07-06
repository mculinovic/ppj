
public class TipNiz extends Tip {
	
	public static TipNiz nizINT = new TipNiz(BrojevniTip.tipINT);
	public static TipNiz nizConstINT = new TipNiz(BrojevniTip.tipConstINT);
	public static TipNiz nizCHAR = new TipNiz(BrojevniTip.tipCHAR);
	public static TipNiz nizConstCHAR = new TipNiz(BrojevniTip.tipConstChar);
	
	@SuppressWarnings("unused")
	private BrojevniTip tip;
	
	private TipNiz(BrojevniTip tip) {
		this.tip = tip;
	}
	
	public static TipNiz dohvatiTipNiz (BrojevniTip tip) {
		if (tip == BrojevniTip.tipINT) return nizINT;
		else if(tip == BrojevniTip.tipConstINT) return nizConstINT;
		else if (tip == BrojevniTip.tipCHAR) return nizCHAR;
		else if (tip == BrojevniTip.tipConstChar) return nizConstCHAR;
		else return null;
	}
	
	public static BrojevniTip dohvatiTip(TipNiz t) {
		if (t == TipNiz.nizINT) return BrojevniTip.tipINT;
		if (t == TipNiz.nizConstINT) return BrojevniTip.tipConstINT;
		if (t == TipNiz.nizCHAR) return BrojevniTip.tipCHAR;
		if (t == TipNiz.nizConstCHAR) return BrojevniTip.tipConstChar;
		return null;
	}

	public static String toString(TipNiz t) {
		if (t == TipNiz.nizINT) return "NIZ_INT";
		if (t == TipNiz.nizConstINT) return "NIZ_CONST_INT";
		if (t == TipNiz.nizCHAR) return "NIZ_CHAR";
		if (t == TipNiz.nizConstCHAR) return "NIZ_CONST_CHAR";
		return null;
	}
	

}
