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

public class MessageBusOperation {

    private static final Logger logger = LoggerFactory.getLogger(MessageBusOperation.class);

    public static String menuSelection(int choice) {
        String uri = DRGUtility.getCommandLineParams().get(Constants.CATALOG_URL).toString()
                + DRGUtility.getCommandLineParams().get(Constants.MESSAGEBUS_ENDPOINT).toString();
        Scanner scanner = new Scanner(System.in);
        try {
            switch (choice) {
            case 1:
                break;
            case 2:
                System.out.print("Enter Message Bus Id : ");
                int id = scanner.nextInt();
                uri += "/" + id;
                break;
            case 3:
                String name;
                String nameSpace;
                System.out.print("Enter MessageBus name : ");
                name = scanner.next();
                System.out.print("Enter MessageBus namespace : ");
                nameSpace = scanner.next();
                uri += "?name=" + name + "&nameSpace=" + nameSpace;
                break;
            default:
                uri = null;
                logger.info("Wrong Choice");

            }
        } catch (Exception e) {
            logger.error("Error : {} ", e.toString());
            uri = null;
        }
        return uri;
    }

    public static void getMessageBus() {
        
        boolean flag = true;
        while(flag) {
            try {
    
                logger.info("\nSelect MessageBus menu :\n" 
                        + "1) Get All MessageBus Details\n"
                        + "2) Get MessageBus Details by Id\n" 
                        + "3) Get MessageBus Details using name and namespace\n"
                        + "4) Exit");
    
                Scanner scanner = new Scanner(System.in);
    
                int choice = scanner.nextInt();
                if(choice == 4) {
                    flag = false;
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
