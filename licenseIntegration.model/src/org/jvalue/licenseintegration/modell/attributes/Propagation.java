package org.jvalue.licenseintegration.modell.attributes;

import org.jvalue.licenseintegration.modell.AttributeCombinations;
import org.jvalue.licenseintegration.modell.LicenseAttribute;
import org.jvalue.licenseintegration.modell.LicenseVector;

public class Propagation extends AbstraktLicenseAttribute {

	public Propagation(int value) {
		this.value = value;
	}

	@Override
	public LicenseAttribute combineWith(LicenseAttribute otherAttribute,
			LicenseVector parentLicenseVector, LicenseVector otherLicenseVector) {
		Integer combinedValue = AttributeCombinations.combineLogicalOr(
				(Integer) value,
				(Integer) otherAttribute.getValue()
		);
		return new Propagation(combinedValue);
	}

}
