package com.ericsson.oss.edca.simulators.customcollector.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.ericsson.oss.edca.simulators.customcollector.config.SFTPConfiguration;
import com.ericsson.oss.edca.simulators.customcollector.config.TestConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Component;

@Component
public class SFTPService {
    @Autowired
    private SFTPConfiguration sftp;
    @Autowired
    private TestConfiguration test;

    /**
     * This method create session factory for SFTP server
     * 
     * @return
     */
    public DefaultSftpSessionFactory sftpSession() {
        System.out.println(sftp.toString());
        DefaultSftpSessionFactory sessionFactory = new DefaultSftpSessionFactory();
        sessionFactory.setHost(sftp.getHost());
        sessionFactory.setPort(sftp.getPort());
        sessionFactory.setAllowUnknownKeys(true);
        sessionFactory.setUser(sftp.getUser());
        sessionFactory.setPassword(sftp.getPassword());
        return sessionFactory;
    }

    /**
     * This method return list of the available files in SFTP directory
     * 
     * @param filePath
     * @return
     */
    public List<String> sftpDownload(String filePath) {
        String[] fileNames = null;
        try {
            SftpSession session = sftpSession().getSession();
            if (filePath == null) {
                fileNames = session.listNames(test.getSftpPath());
            } else {
                fileNames = session.listNames(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(fileNames);
    }
}
