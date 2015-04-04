package org.jvalue.licenseintegration.modell.attributes;

import org.jvalue.licenseintegration.modell.LicenseAttribute;
import org.jvalue.licenseintegration.modell.LicenseVector;

public class Charge extends AbstraktLicenseAttribute {

	public Charge(int value) {
		this.value = value;
	}

	@Override
	public LicenseAttribute combineWith(LicenseAttribute otherAttribute,
			LicenseVector parentLicenseVector, LicenseVector otherLicenseVector) {
		Integer combinedValue = 
				(Integer) value +
				(Integer) otherAttribute.getValue();
		return new Charge(combinedValue);
	}

}
