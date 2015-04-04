package org.jvalue.licenseintegration.modell;

import java.util.ArrayList;
import java.util.Collection;
 
public class LicenseVector {
	
	private ArrayList<LicenseAttribute> attributes = new ArrayList<LicenseAttribute>();
	
	
	public ArrayList<LicenseAttribute> getAttributes() {
		return attributes;
	}


	public LicenseVector(Collection<LicenseAttribute> attributes) {
		this.attributes.addAll(attributes);
	}
		
	
  @Override public String toString() {
	StringBuilder result = new StringBuilder();
	String NEW_LINE = System.getProperty("line.separator");

	for(LicenseAttribute l : attributes) {
		result.append(l.toString() + NEW_LINE);
	}
    
    return result.toString();
  }
  
  public String explain() {
	StringBuilder result = new StringBuilder();
	String NEW_LINE = System.getProperty("line.separator");

	for(LicenseAttribute l : attributes) {
		result.append(l.explain() + NEW_LINE);
	}
    
    return result.toString();
  }
  
  
  public LicenseVector combineWith(LicenseVector otherLicenseVector) throws IllegalLicenseCombinationException {
	ArrayList<LicenseAttribute> combinedAttributes = new ArrayList<LicenseAttribute>();

	for(LicenseAttribute licenseAttribute : attributes) {
		LicenseAttribute combinedAttribute = licenseAttribute.combineWith(otherLicenseVector.getAttributeByClass(licenseAttribute.getClass()),this,otherLicenseVector);
		combinedAttributes.add(combinedAttribute);
	}
	
	return new LicenseVector(combinedAttributes);
  }


	private LicenseAttribute getAttributeByClass(
			Class<? extends LicenseAttribute> classToGet) {
		
		for(LicenseAttribute licenseAttribute : attributes) {
			if(licenseAttribute.getClass().equals(classToGet)) {
				return licenseAttribute;
			}
		}
		return null;
		
	}
  
}
