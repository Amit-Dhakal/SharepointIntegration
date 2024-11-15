package com.java.sharepointintegrationapi.controller;

import java.io.IOException;
import java.util.List;

import com.java.sharepointintegrationapi.service.AuthorizationService.ConnectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.java.sharepointintegrationapi.dto.SharePointDetail;
import com.java.sharepointintegrationapi.service.AuthorizationService.ConnectionService;
import com.java.sharepointintegrationapi.service.ResourcseAccessService.SharepointFileService;
import com.java.sharepointintegrationapi.util.Credentials;
import com.java.sharepointintegrationapi.util.FolderList;


@RestController
public class SharePointController {

    @Autowired
	SharepointFileService sharepointFileService;

    @Autowired
    ConnectionService connectionService;


    @Autowired
    FolderList folderList;
     
    @Autowired
    Credentials credentials;
    
       
    private String siteFolderUrl = Credentials.getSite() + "/Shared%20Documents";  
    
    private String siteURL = "https://" + Credentials.domain +".sharepoint.com" + Credentials.getSite();
    

      
    @GetMapping("/view")
    public ResponseEntity<?> listFilesAndFolder() throws IOException {
    	       
	    List<String> listFolder = sharepointFileService.getListOfFolders(connectionService.getToken(),siteURL,siteFolderUrl);
	    
		return new ResponseEntity<>("List Files & Folder are ::"+listFolder, HttpStatus.OK);
        
    }

    
    @PostMapping("/download")
    public ResponseEntity<String> download(@RequestBody SharePointDetail sharePointDetail) throws IOException {

        sharepointFileService.downloadFoldersFiles(connectionService.getToken(),sharePointDetail);
        return new ResponseEntity<>("Downloaded Successfully!",HttpStatus.OK);	
        
        
    }

}
