package org.jvalue.licenseintegration.modell.attributes;

import org.jvalue.licenseintegration.modell.LicenseAttribute;

public abstract class AbstraktLicenseAttribute implements LicenseAttribute {

	protected Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public String explain() {
		return this.getClass().getSimpleName();
		
	}
	
	
	  @Override public String toString() {
		    return getName() + ": "+ value;
		}
	  
	  public String getName() {
		  return decapitalize(this.getClass().getSimpleName());
	  }
	  
	  private String decapitalize(String string) {
		  return Character.toLowerCase(
				  string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
	  }
	
}
