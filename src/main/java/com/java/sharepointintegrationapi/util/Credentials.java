package com.java.sharepointintegrationapi.util;

import org.springframework.stereotype.Component;

@Component
public class Credentials {
	
	public static final String clientId = "3dd046e3-65dd-41e1-a266-f17f42535a64";
    public static final String clientSecret = "8a~8Q~V.L.ggxihTBlwgkjCYGqryIJmw4Ebn5aK_";
    public static final String tenantId = "02b87979-d129-41ba-a3df-f7e17e86c8ea";
    public static final String domain = "malabarai";
    
    public static String shared_folder="Shared Documents";
    public static final String site = "/";
	public static String getShared_folder() {
		return shared_folder;
	}
	public static void setShared_folder(String shared_folder) {
		Credentials.shared_folder = shared_folder;
	}
	public static String getClientid() {
		return clientId;
	}
	public static String getClientsecret() {
		return clientSecret;
	}
	public static String getTenantid() {
		return tenantId;
	}
	public static String getDomain() {
		return domain;
	}
	public static String getSite() {
		return site;
	}


}


//https://malabarai-my.sharepoint.com/personal/donaldsoo_koizai_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fdonaldsoo%5Fkoizai%5Fcom%2FDocuments%2F13%2E%20Tolo%20Harbor%20Planners%20%2D%20Test&ga=1