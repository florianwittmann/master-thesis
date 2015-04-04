package org.jvalue.licenseintegration.modell.attributes;

import org.jvalue.licenseintegration.modell.AttributeCombinations;
import org.jvalue.licenseintegration.modell.LicenseAttribute;
import org.jvalue.licenseintegration.modell.LicenseVector;

public class Source extends AbstraktLicenseAttribute {

	public Source(String value) {
		this.value = value;
	}

	@Override
	public LicenseAttribute combineWith(LicenseAttribute otherAttribute,
			LicenseVector parentLicenseVector, LicenseVector otherLicenseVector) {
		String combinedValue = 	AttributeCombinations.combineTextAttribute(
				(String) value,
				(String) otherAttribute.getValue()
		);
		return new Source(combinedValue);
	}

}
