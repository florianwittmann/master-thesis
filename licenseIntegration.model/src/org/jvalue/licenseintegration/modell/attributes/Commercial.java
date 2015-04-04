package org.jvalue.licenseintegration.modell.attributes;

import org.jvalue.licenseintegration.modell.AttributeCombinations;
import org.jvalue.licenseintegration.modell.LicenseAttribute;
import org.jvalue.licenseintegration.modell.LicenseVector;

public class Commercial extends AbstraktLicenseAttribute {
	
	public Commercial(int value) {
		this.value = value;
	}

	@Override
	public LicenseAttribute combineWith(LicenseAttribute otherAttribute,
			LicenseVector parentLicenseVector, LicenseVector otherLicenseVector) {
		Integer combinedValue = AttributeCombinations.combineLogicalAnd(
				(Integer) value,
				(Integer) otherAttribute.getValue()
		);
		return new Commercial(combinedValue);
	}
}
