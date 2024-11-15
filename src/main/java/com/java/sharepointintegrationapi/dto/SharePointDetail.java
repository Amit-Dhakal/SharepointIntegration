package com.java.sharepointintegrationapi.dto;

import java.io.File;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class SharePointDetail {
    private String siteName="/sites/raju-dev";
   // private String downloadDirectory="D:\\result\\";   
    File convFile = new File("src/main/resources/downloads/");   
    private String downloadDirectory=convFile.getAbsolutePath();
    
    
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getDownloadDirectory() {
		return downloadDirectory;
	}
	public void setDownloadDirectory(String downloadDirectory) {
		this.downloadDirectory = downloadDirectory;
	}
    
    
}
