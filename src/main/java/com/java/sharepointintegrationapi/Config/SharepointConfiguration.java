package com.java.sharepointintegrationapi.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@ConfigurationProperties(prefix = "sharepoint")
@Component
public class SharepointConfiguration {

    public SharepointConfiguration(){
//        System.out.println(getClientId());
//        System.out.println(getClientSecret());
//        System.out.println(getDomain());
    }
    @Value("${sharepoint.clientId}")
    private String clientId;

    @Value("${sharepoint.clientSecret}")
    private String clientSecret;

    @Value("${sharepoint.tenantId}")
    private String tenantId;

    @Value("${sharepoint.domain}")
    private String domain;
//    @Value("${sharepoint.sharedFolder}")
//    private String sharedFolder;
    @Value("${sharepoint.site}")
    private String site;
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public String getTenantId() {
        return tenantId;
    }
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
//    public String getSharedFolder() {
//        return sharedFolder;
//    }
//
//    public void setSharedFolder(String sharedFolder) {
//        this.sharedFolder = sharedFolder;
//    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public void show(){
        System.out.println(getClientId());
        System.out.println(getClientSecret());
        System.out.println(getDomain());
    }

}
