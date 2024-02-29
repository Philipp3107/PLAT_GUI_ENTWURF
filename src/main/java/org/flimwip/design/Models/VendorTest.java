package org.flimwip.design.Models;

import org.flimwip.design.utility.XML_Vendor_Parser;

import java.util.List;

public class VendorTest {
    public static void main(String[] args) {
        String vendorFile = "src/main/resources/Test.xml";
        List<PopulationFile> files = new XML_Vendor_Parser().read_confif(vendorFile);

        for(PopulationFile pf: files){
            System.out.println(pf.toString());
        }

        String vendorFileOutput = "src/main/resources/Test2.xml";
        new XML_Vendor_Parser().write_config(vendorFileOutput, files);

    }
}
