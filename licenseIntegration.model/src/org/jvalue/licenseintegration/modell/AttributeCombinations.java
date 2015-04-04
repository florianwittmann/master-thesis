package org.jvalue.licenseintegration.modell;

public class AttributeCombinations {

	
	public static int combineLogicalOr(int value1, int value2) {
		
		Integer combinedValue = 
				(value1 == 1 || value2 == 1) ? 1 : 0;
		return combinedValue;
	}
	
	public static int combineLogicalAnd(int value1, int value2) {
		
		Integer combinedValue = 
				(value1 == 1 && value2 == 1) ? 1 : 0;
		return combinedValue;
	}
	
	public static String combineTextAttribute(String value1, String value2) {
		String combinedValue = null;
		
		if(!value1.equals("0")) {
			combinedValue = value1;
		}
		if(!value2.equals("0")) {
			combinedValue = combinedValue == null ? value2 : combinedValue + " + " + value2;
		}			

		return combinedValue == null ? "0" : combinedValue;
	}

}
