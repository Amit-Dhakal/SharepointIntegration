package com.java.sharepointintegrationapi.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.java.sharepointintegrationapi.dto.SharePointDetail;
import com.java.sharepointintegrationapi.exception.BusinessException;
import com.java.sharepointintegrationapi.service.ConnectionService;
import com.java.sharepointintegrationapi.service.SharepointFileService;
import com.java.sharepointintegrationapi.util.Credentials;
import com.java.sharepointintegrationapi.util.FolderList;


@RestController
public class SharePointController {

    
    @Autowired
	SharepointFileService sharepointFileService;
    
    @Autowired
    FolderList folderList;
     
    @Autowired
    Credentials credentials;
    
       
    private String siteFolderUrl = Credentials.getSite() + "/Shared%20Documents";  
    
    private String siteURL = "https://" + Credentials.domain +".sharepoint.com" + Credentials.getSite();
    

      
    @GetMapping("/view")
    public ResponseEntity<?> listFilesAndFolder() throws IOException {
    	       
	    List<String> listFolder = sharepointFileService.getListOfFolders(ConnectionService.getToken(),siteURL,siteFolderUrl);  
	   	    
	    
	    
		return new ResponseEntity<>("List Files & Folder are ::"+listFolder, HttpStatus.OK);
        
    }

    
    @PostMapping("/download")
    public ResponseEntity<String> download(@RequestBody SharePointDetail sharePointDetail) throws IOException {
         	        
        sharepointFileService.downloadFoldersFiles(ConnectionService.getToken(),sharePointDetail);
        return new ResponseEntity<>("Downloaded Successfully!",HttpStatus.OK);	
        
        
    }

}
