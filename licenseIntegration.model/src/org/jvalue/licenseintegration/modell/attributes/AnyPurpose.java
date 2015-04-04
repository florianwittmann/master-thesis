package org.jvalue.licenseintegration.modell.attributes;

import org.jvalue.licenseintegration.modell.AttributeCombinations;
import org.jvalue.licenseintegration.modell.LicenseAttribute;
import org.jvalue.licenseintegration.modell.LicenseVector;

public class AnyPurpose extends AbstraktLicenseAttribute {
	
	public AnyPurpose(int value) {
		this.value = value;
	}
	
	public Boolean isAnyPurpose() {
		return (Integer) value == 1;		
	}
	
	@Override
	public LicenseAttribute combineWith(LicenseAttribute otherAttribute,
			LicenseVector parentLicenseVector, LicenseVector otherLicenseVector) {
		Integer combinedValue = AttributeCombinations.combineLogicalAnd(
				(Integer) value,
				(Integer) otherAttribute.getValue()
		);
		return new AnyPurpose(combinedValue);
	}
}
