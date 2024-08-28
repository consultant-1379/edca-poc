package com.ericsson.oss.edca.simulators.customcollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "edca.sftp")
public class SFTPConfiguration {
    private String host;
    private Integer port;
    private String user;
    private String password;

    public String getSFTPAccessEndpoint() {
        return host + ":" + port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SFTPConfiguration [host=" + host + ", password=" + password + ", port=" + port + ", user=" + user + "]";
    }

}
