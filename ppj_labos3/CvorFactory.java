import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;



class CvorFactory {


	public static Cvor stvoriCvor (String naziv) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String razred = toCamelCase(naziv);

		Class<?> c;
		Constructor<?> konstruktor = null;
		try {
			c = (Class<?>) Class.forName(razred);
			konstruktor = c.getConstructor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Cvor cvor = (Cvor) konstruktor.newInstance();	
		
		//System.out.print(cvor.getClass().toString());
		return cvor;
		
	}

	private static String toCamelCase(String naziv) {
		String razred = "";
		String[] dijelovi = naziv.split("_");
		
		for (String dio: dijelovi) {
			razred = razred + toProperCase(dio);
		}
				
		return razred;			
	}

	private static String toProperCase(String dio) {
		return dio.substring(0,1).toUpperCase() + dio.substring(1).toLowerCase();
	}
}