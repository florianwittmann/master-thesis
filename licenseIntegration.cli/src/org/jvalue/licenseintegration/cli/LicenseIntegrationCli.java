package org.jvalue.licenseintegration.cli;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jvalue.licenseintegration.common.LicenseVectorJSONLoader;
import org.jvalue.licenseintegration.modell.IllegalLicenseCombinationException;
import org.jvalue.licenseintegration.modell.LicenseVector;

public class LicenseIntegrationCli {

	private static LicenseVectorJSONLoader fileloader;
	
	
	public static void main(String[] args) {
		
		if(args.length == 0 || args[0] == "-h" || args[0] == "--help") {
            System.out.println("ERROR: No licensevector file provided.");
            System.out.println("Usage: licenseIntegrationCli LICENSEFILE");
            System.out.println("Shows license vector");
            System.out.println("Usage: licenseIntegrationCli LICENSEFILE1 LICENSEFILE2 [MORE LICENSE FILES]");
            System.out.println("Combine licenses and show resulting vector");
            return;
		}
		fileloader = new LicenseVectorJSONLoader();
		
		LicenseVector currentLicense = null;
		
	    for(int i = 0; i < args.length; i++) {
	    	
	    	LicenseVector nextLicense = loadLicenseVector(args[i]);
	    	if(currentLicense==null) {
	    		currentLicense = nextLicense;
	    	} else {
	    		try {
					currentLicense = currentLicense.combineWith(nextLicense);
				} catch (IllegalLicenseCombinationException e) {
					System.err.println("Illegal license combination!");
					System.err.println(e.getMessage());
					if(i==1) {
						System.err.println("Conflicting: " + args[i-1] + " and " +  args[i] );
					} else {
						System.err.println("Successfully combined licenses " + 1 + "-" + i + " to following licensevector:");
						System.err.println(currentLicense);
						System.err.println("Conflict in combining this with " + args[i] );
						
					}
					System.exit(1);
				}
	    	}
        }
		System.out.println(currentLicense);
	}
	
	private static LicenseVector loadLicenseVector(String filename) {
    	try {
			return fileloader.loadLicenseVectorFromFile(filename);
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filename);
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
    	return null;
	}

}
