package org.jvalue.licenseintegration.modell.attributes;

import java.util.ArrayList;

import org.jvalue.licenseintegration.modell.IllegalLicenseCombinationException;
import org.jvalue.licenseintegration.modell.LicenseAttribute;
import org.jvalue.licenseintegration.modell.LicenseVector;

public class ShareAlike extends AbstraktLicenseAttribute {

	final static String lineSeparator = System.getProperty("line.separator");

	
	
	public ShareAlike(int value) {
		this.value = value;
	}

	@Override
	public LicenseAttribute combineWith(LicenseAttribute otherAttribute,
			LicenseVector parentLicenseVector, LicenseVector otherLicenseVector) throws IllegalLicenseCombinationException {
		int value1 = (Integer) value;
		int value2 = (Integer) otherAttribute.getValue();
		Integer combinedValue;
		if(value1 == 0 && value2 == 0) {
			combinedValue = 0;
		} else if(value1 == 1 && value2 == 1) {
			//Specialcase A:
			if(parentLicenseVector.equals(otherLicenseVector)) {
				combinedValue = 1;
			} else {
				throw new IllegalLicenseCombinationException();
			}
		} else {
			//Specialcase B:
			if(value1 == 1) {
				//can trow IllegalLicenseCombinationException
				checkSpecialcaseB(parentLicenseVector, otherLicenseVector);
			} else {
				//can trow IllegalLicenseCombinationException
				checkSpecialcaseB(otherLicenseVector, parentLicenseVector);
			}
			//if there was no exception, combined value is 1:
			combinedValue = 1;
		}
		return new ShareAlike(combinedValue);
	}
	
	private void checkSpecialcaseB(LicenseVector shareAlikeLicenseVector, LicenseVector notShareAlikeLicenseVector) throws IllegalLicenseCombinationException {
		ArrayList<LicenseAttribute> attributesFromShareAlikeLicenseVector = shareAlikeLicenseVector.getAttributes();
		ArrayList<LicenseAttribute> attributesFromNotShareAlikeLicenseVector = notShareAlikeLicenseVector.getAttributes();

		for(int i=0; i<attributesFromShareAlikeLicenseVector.size();i++) {
			if(attributesFromShareAlikeLicenseVector.get(i) == this ||
					attributesFromNotShareAlikeLicenseVector.get(i) == this) {
				//Ignore share alike - that's what we are currently combining.
			} else {
				LicenseAttribute combinedAttribute;
				//Combine attribute
				combinedAttribute = attributesFromShareAlikeLicenseVector.get(i).combineWith(
						attributesFromNotShareAlikeLicenseVector.get(i),
						shareAlikeLicenseVector,
						notShareAlikeLicenseVector);
				//Combined attribute should not change from
				//shareAlike license to result.
				if(isChangingValueInResult(combinedAttribute.getValue(),
						attributesFromShareAlikeLicenseVector.get(i).getValue())) {
					throw new IllegalLicenseCombinationException(
							"The attribute " + combinedAttribute.getName() 
							+ " of the resulting licensevector would be: " 
							+ combinedAttribute.getValue() + "." + lineSeparator 
							+ "This conflicts with the attribute " 
							+ attributesFromShareAlikeLicenseVector.get(i).getName() 
							+ " of the shareAlike licensevector which is: " 
							+ attributesFromShareAlikeLicenseVector.get(i).getValue() + ".");
				}
				
			}
		}
	}

	private boolean isChangingValueInResult(Object result, Object shouldBe) {
		//Simple comparison
		if(result.equals(shouldBe))
			return false;
		
		//Compare concated strings
		//the searched part shouldbe included in result string		
		String[] shouldBeParts = shouldBe.toString().split(" \\+ ");
		String[] resultParts = result.toString().split(" \\+ ");
		
		for(String shouldBePart : shouldBeParts) {
			Boolean foundMatch = false;
			for(String resultPart : resultParts) {
				if(resultPart.equals(shouldBePart)) {
					foundMatch = true;
					break;
				}
			}
			if(!foundMatch)
				return true;
		}
	
		return false;
	}

}
