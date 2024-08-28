/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2021
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.drg.helper;


import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.drg.constants.Constants;
import com.ericsson.drg.service.Service;
import com.ericsson.drg.utility.DRGUtility;

public class FileFormatOperations {


    private static final Logger logger = LoggerFactory.getLogger(FileFormatOperations.class);

    /**
     * choice to make multiple query operations
     * @param choice
     * @return
     */
    public static String menuSelection(int choice) {
        String url = DRGUtility.getCommandLineParams().get(Constants.CATALOG_URL).toString()
                + DRGUtility.getCommandLineParams().get(Constants.FILEFORMAT_ENDPOINT).toString();
        try {          
            Scanner scanner = new Scanner(System.in);
            String dataprovider;
            String dataSpace;
            String dataCategory;
            switch (choice) {
            case 1:
                break;
            case 2:
                System.out.print("Enter File Format Id : ");
                int id = scanner.nextInt();
                url += "/" + id;
                break;
            case 3:
                System.out.print("Enter FileFormat dataProviderType : ");
                dataprovider = scanner.next();
                System.out.print("Enter FileFormat dataSpace : ");
                dataSpace = scanner.next();
                System.out.print("Enter FileFormat dataCategory : ");
                dataCategory = scanner.next();

                url += "?dataProviderType=" + dataprovider + "&dataSpace=" + dataSpace + "&dataCategory=" + dataCategory;
                break;
            case 4:
                System.out.print("Enter FileFormat dataCategory : ");
                dataCategory = scanner.next();
                
                url += "?dataCategory=" + dataCategory;
                break;
            case 5:
                System.out.print("Enter FileFormat dataSpace : ");
                dataSpace = scanner.next();
                
                url += "?dataSpace=" + dataSpace;
                break;
            case 6:
                System.out.print("Enter FileFormat dataProviderType : ");
                dataprovider = scanner.next();
                System.out.print("Enter FileFormat dataSpace : ");
                dataSpace = scanner.next();
                
                url += "?dataProviderType=" + dataprovider + "&dataSpace=" + dataSpace;
                break;
            case 7:
                System.out.print("Enter FileFormat dataCategory : ");
                dataCategory = scanner.next();
                System.out.print("Enter FileFormat dataSpace : ");
                dataSpace = scanner.next();
                
                url += "?dataCategory=" + dataCategory + "&dataSpace=" + dataSpace;
                break;
            default:
                url = null;
                logger.info("Wrong Choice");

            }
        } catch (Exception e) {
            logger.error("Error : {} ", e.toString());
            url = null;
        }
        return url;
    }

    /**
     * get File Format
     */
    public static void getFileFormat() {
        
        boolean flag = true;
        while(flag) {
            try {
    
                logger.info("\nSelect FileFormat menu :\n" 
                        + "1) Get All FileFormat Details\n"
                        + "2) Get FileFormat Details by Id\n"
                        + "3) Get FileFormat Details using dataProviderType,dataSpace and dataCategory\n"
                        + "4) Get FileFormat Details using dataCategory\n"
                        + "5) Get FileFormat Details using dataSpace\n"
                        + "6) Get FileFormat Details using dataProviderType and dataSpace\n"
                        + "7) Get FileFormat Details using dataCategory and dataSpace\n"
                        + "8) Exit");
    
                Scanner scanner = new Scanner(System.in);
    
                int choice = scanner.nextInt();
                if(choice == 8) {
                    flag=false;
                }else {                
                    String url = menuSelection(choice);
                    Service.printGetCatalog(url);
                }
                 
            } catch (Exception e) {
                logger.error("Error : {} ", e.getMessage());
            }
        }
    }

}
