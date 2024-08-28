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

public class DataSpaceOperations {
    
    private static final Logger logger = LoggerFactory.getLogger(DataSpaceOperations.class);
    
    private static String menuSelection(int choice) {
        String url = DRGUtility.getCommandLineParams().get(Constants.CATALOG_URL).toString() + DRGUtility.getCommandLineParams().get(Constants.DATASPACE_ENDPOINT).toString() ;
        Scanner scanner = new Scanner(System.in);
        try {
            switch(choice) {
            case 1:                    
                break;
            case 2:
                System.out.print("Enter DataSpace id : ");
                int id = scanner.nextInt();
                url += "/" +id;
                break;
            case 3:
                String name;
               
                System.out.print("Enter DataSpace name : ");
                name = scanner.next();
                
                url +="?name="+name;
                break;
            default :
                url = null;
                logger.info("Wrong Choice");
                
            }
        } catch (Exception e) {
            logger.info("Error : {} ", e.toString());
        }
        return url;
    }
    public static void getDataSpace() {        
        boolean flag = true;
        while(flag) {
            try {            
                 logger.info("\nSelect DataSpace menu :\n" 
                         + "1) Get All DataSpace Details\n"
                         + "2) Get DataSpace by Id\n" 
                         + "3) Get DataSpace Details using name\n"
                         + "4) Exit");
                 
                 Scanner scanner = new Scanner(System.in);            
                 int choice = scanner.nextInt();
                 if(choice == 4) { 
                     flag=false;                   
                 }else {
                     String url = menuSelection(choice);
                     Service.printGetCatalog(url);
                 }
             } catch(Exception e) {
                 logger.info("Error : {} ", e.getMessage());
             }           
        }
    }
}
