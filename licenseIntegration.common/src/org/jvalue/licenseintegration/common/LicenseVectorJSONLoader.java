package org.jvalue.licenseintegration.common;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jvalue.licenseintegration.modell.LicenseAttribute;
import org.jvalue.licenseintegration.modell.LicenseVector;
import org.jvalue.licenseintegration.modell.attributes.*;

public class LicenseVectorJSONLoader {

	public LicenseVector loadLicenseVectorFromFile(String file) throws FileNotFoundException, IOException {
		
		ArrayList<LicenseAttribute> attributes = new ArrayList<LicenseAttribute>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	LicenseAttribute attribute = parseLine(line);
		    	if(attribute != null) {
		    		attributes.add(attribute);
		    	}
		    }
		}
		return new LicenseVector(attributes);
	}

	private LicenseAttribute parseLine(String line) {
		
		String[] lineParts = line.split("[\\s:]+", 2);
		if(lineParts.length != 2)
			return null;
		
		switch(lineParts[0])  {
			case "use":
				return new Use(Integer.parseInt(lineParts[1]));
			case "redistribution":
				return new Redistribution(Integer.parseInt(lineParts[1]));
			case "modification":
				return new Modification(Integer.parseInt(lineParts[1]));
			case "seperation":
				return new Seperation(Integer.parseInt(lineParts[1]));
			case "compilation":
				return new Compilation(Integer.parseInt(lineParts[1]));
			case "propagation":
				return new Propagation(Integer.parseInt(lineParts[1]));
			case "anyPurpose":
				return new AnyPurpose(Integer.parseInt(lineParts[1]));
			case "charge":
				return new Charge(Integer.parseInt(lineParts[1]));
			case "attribution":
				return new Attribution(lineParts[1]);
			case "integrity":
				return new Integrity(lineParts[1]);
			case "shareAlike":
				return new ShareAlike(Integer.parseInt(lineParts[1]));
			case "notice":
				return new Notice(lineParts[1]);
			case "source":
				return new Source(lineParts[1]);
			case "prohibitRestr":
				return new ProhibitRestr(Integer.parseInt(lineParts[1]));
			case "commercial":
				return new Commercial(Integer.parseInt(lineParts[1]));
		}
		return null;
	}
	
}
