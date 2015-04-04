package org.jvalue.licenseintegration.modell;

public class IllegalLicenseCombinationException extends Exception{
	
	private static final long serialVersionUID = -5694259305138018208L;

	public IllegalLicenseCombinationException() {}

    public IllegalLicenseCombinationException(String message)
    {
       super(message);
    }
}
