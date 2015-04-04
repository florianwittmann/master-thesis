package org.jvalue.licenseintegration.modell;

public interface LicenseAttribute {
	
	public Object getValue();
	public void setValue(Object object);
	public String explain();
	public LicenseAttribute combineWith(LicenseAttribute otherAttribute, LicenseVector parentLicenseVector, LicenseVector otherLicenseVector) throws IllegalLicenseCombinationException;
	public String getName();
}
