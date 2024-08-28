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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;

import com.ericsson.drg.constants.Constants;
import com.ericsson.drg.utility.DRGUtility;

public class SFTPOperationsHelper {

    private String accessPoint;
    private String host;
    private int port;
    private String objectName;
    private String userName;
    private String userPassword;
    private static final Logger logger = LoggerFactory.getLogger(SFTPOperationsHelper.class);
    
    /**
     * Constructor to initialize values
     * @param accessPoint
     * @param objectName
     */
    public SFTPOperationsHelper(String accessPoint, String objectName) {

        this.objectName = objectName;
        this.accessPoint = accessPoint;
    }

    /**
     * To retun sftpsession 
     * @return DefaultSftpSessionFactory
     */
    private DefaultSftpSessionFactory getSessionFactory() {
        DefaultSftpSessionFactory sessionFactory = new DefaultSftpSessionFactory();
        sessionFactory.setHost(host);
        sessionFactory.setPort(port);
        sessionFactory.setAllowUnknownKeys(true);
        sessionFactory.setUser(userName);
        sessionFactory.setPassword(userPassword);
        sessionFactory.setChannelConnectTimeout(Duration.ofMillis(5000));
        return sessionFactory;
    }

    /**
     * To Download File
     */
    public void downloadFile() {

        try {
            URI url = new URI(this.accessPoint);
            this.host = url.getHost();
            this.port = url.getPort();
            //The target file name is same as that of source file name.
            String fileName = this.objectName;
            String fileNameWithPath = this.objectName;
            if(!url.getPath().trim().isEmpty()) {
                if(url.getPath().trim().endsWith("/")){
                        fileNameWithPath = url.getPath() + this.objectName;
                }else {
                    fileNameWithPath = url.getPath() +"/"+ this.objectName;
                }
            }
            logger.info("Host : {} Port : {} Filename including full path : {} ", host, port, fileNameWithPath );
            
            this.userName = DRGUtility.getCommandLineParams().get("SFTP_USER").toString();
            this.userPassword = DRGUtility.getCommandLineParams().get("SFTP_PASS").toString();

            SftpSession session = getSessionFactory().getSession();
             
            logger.info("Available files in SFTP server: {} ", Arrays.toString(session.listNames(url.getPath())));
            
            if (session.exists(fileNameWithPath)) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                FileOutputStream stream = null;
                try {
                    session.read(fileNameWithPath, byteArrayOutputStream);
                    File file = new File(Constants.SFTP_DOWNLOAD_FOLDER);
                    file.mkdir();

                    stream = new FileOutputStream(new File(file, fileName));

                    stream.write(byteArrayOutputStream.toByteArray());
                    logger.info(" {} is downloaded ", fileName);
                } catch (IOException e) {
                    logger.error(" Unable to download : {} ", e.getMessage());
                } finally {
                    if(session != null) {
                        session.close();
                    }
                    if(stream != null) {
                        stream.close();
                    }
                    if(byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                }

            } else {
                logger.info("File is not available");
            }

        } catch(Exception e) {
            logger.error("Exception : {} : {} : {}",e.getCause(),e.getMessage(),e.toString());
        }

    }

}
