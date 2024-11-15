package com.java.sharepointintegrationapi.service.ResourcseAccessService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.java.sharepointintegrationapi.service.AuthorizationService.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.java.sharepointintegrationapi.dto.SharePointDetail;
import com.java.sharepointintegrationapi.exception.BusinessException;
import com.java.sharepointintegrationapi.util.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class SharepointFileServiceImpl implements SharepointFileService {

	@Autowired
	ConnectionService connectionService;

//	   public static void main(String[] args) {
//	        SharepointFileService sharepointFileService=new SharepointFileServiceImpl();
//
//
//	         SharePointDetail sharePointDetail=new SharePointDetail();
//
//		//SharepointFileData sharepointFileData =new SharepointFileData();
//
//	        sharePointDetail.setSiteName("/sites/raju-dev");
//	        sharePointDetail.setDownloadDirectory("D:\\result\\");
//	       sharepointFileService.downloadFoldersFiles(ConnectionService.getToken(),sharePointDetail);
//
//		   sharepointFileService.downloadFoldersFiles(ConnectionService.getToken(),sharePointDetail);
//
//
//	    }
	
	

	//download files from base folder (1st call)
	    public void downloadFoldersFiles(String token, SharePointDetail sharePointDetail) {
	    	
	    	try {
	    		if(token==null || sharePointDetail.getDownloadDirectory()==null ||sharePointDetail.getSiteName()==null) {
	    			
	    			throw new BusinessException(601,"Token,download directory or sitename should not be null !!! ");
	    		
	    		}
	    			
	    	} catch(BusinessException ex) {
				// TODO Auto-generated catch block
	    		
	    		System.out.println(ex.getErrorCode()+":"+ex.getErrorMessage());
	    		
			}
	    	
	    	  String downloadFileDir = sharePointDetail.getDownloadDirectory();
	          String siteFolderUrl = sharePointDetail.getSiteName() + "/Shared%20Documents";//2 part
	          String siteURL = "https://" + Credentials.domain + ".sharepoint.com" + sharePointDetail.getSiteName();//1 part
	        try {
	           // log.info("-----First downloading files from base folder-----.");
	            System.out.println("-----First downloading files from base folder-----.");
	            //download all files from folders
	            downloadAllFilesFromFolder(token, downloadFileDir,siteURL,siteFolderUrl);
	            //list of folders
	            List<String> folderNames = getListOfFolders(token,siteURL,siteFolderUrl);
	            for (String folder : folderNames) {
	            	 System.out.println("Downloading Files from inside {} folder");    	
	                 System.out.println("Folder names are :"+folder);
	                downloadAllFilesFromFolder(token, downloadFileDir + "/" + folder + "/", siteURL, siteFolderUrl + "/" + folder);
	            }            
	        } catch (Exception exception) {
	        	exception.printStackTrace();
	            System.out.println("The file could not be downloaded/Token error/File IO : " + new Timestamp(System.currentTimeMillis()));
	        }
	    }
	    
	    
	    private void downloadAllFilesFromFolder(String token, String downloadFileDir, String siteURL, String siteFolderUrl) throws IOException {
	       
	    	
	    	try {
	    		
	    		if(downloadFileDir==null || siteURL==null || siteFolderUrl==null) {
	    			
	    			throw new BusinessException(400,"File directory cannot be null");
	    			
	    		}
	    		
				
			} catch (BusinessException ex) {
				// TODO: handle exception
				
				
	    		System.out.println(ex.getErrorCode()+":"+ex.getErrorMessage());
				
				
			}
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	   Response nameFileArray = getFilesNameFromFolder(token, siteURL, siteFolderUrl);
	    	
		   String fUrl0=siteURL + "/_api/web/Lists/GetByTitle('Documents')/Items?$expand=Folder&$select=Title,FileLeafRef,Folder/ServerRelativeUrl";            

		       
		       OkHttpClient client = new OkHttpClient();

				Request request = new Request.Builder()
					    .header("Authorization", "Bearer " + token)
					    .header("accept", "application/json;odata=verbose")
					    .url(fUrl0)
					    .build();
	               	
              	String res = null;

		 		try {
					Response response = client.newCall(request).execute();
					//res= response.peekBody(2048).toString();
					//int code=response.code();
								
					  String httpFResponseStr0 = getOKHttpResponse(token,fUrl0);  
					 //res=httpFResponseStr0;
					  
		              System.out.println("Response is::-"+httpFResponseStr0);

			         String fileName0 = "";
			         	       
			         JsonFactory factory = new JsonFactory();
			         com.fasterxml.jackson.core.JsonParser  parser  = factory.createParser(httpFResponseStr0);
			         
		              while(parser.nextToken()!=null)         
			         {  	         
			      	   JsonToken jsonToken= parser.nextToken();
			        	    if(JsonToken.FIELD_NAME.equals(jsonToken)){
			        	        String fieldName = parser.getCurrentName();
			        	        
			        	    	System.out.println("Field name:"+fieldName);

			        	     jsonToken = parser.nextToken();

			        	        if("FileLeafRef".equals(fieldName)){

			        	        	String folderName=parser.getValueAsString();
			        	        		   System.out.println("File name is::"+folderName);     	 
			        	        	//InputStream inputStream = response.body().byteStream();
			        	        		    InputStream inputStream = new ByteArrayInputStream(httpFResponseStr0.getBytes());
			        	        		    
			        	            convertToFileAndDownload(downloadFileDir,folderName,inputStream);      	            
			        	        }
			        	    }
			        	    	
			        	    }		
					
								     
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
		       
		       
		   
	    	
	    	                      
          	    }
	    
	    
	    
	    //ok no need to change
	    private void convertToFileAndDownload(String downloadFileDir, String fileName, InputStream inputStream) throws IOException {
	    	
	    	try {
	    		
	    		if(downloadFileDir==null || fileName==null) {
	    			
	    			throw new BusinessException(400,"File directory , File Name cannot be null !!!");
	    		}
				
			} catch (BusinessException e) {
				// TODO: handle exception
				
				System.out.println(e.getErrorCode()+":"+e.getErrorMessage());
			}
	    	
	        SharePointDetail sharePointDetail = new SharePointDetail();

	          String siteURL = "https://" + Credentials.domain + ".sharepoint.com" + sharePointDetail.getSiteName();//1 part
		      String fUrl= siteURL + "/_api/web/Lists/GetByTitle('Documents')/Items?$expand=Folder&$select=Title,FileLeafRef,Folder/ServerRelativeUrl";
	    	
	    	 OkHttpClient client = new OkHttpClient();
	    	    Request request = new Request.Builder()
	    	    		.header("Authorization", "Bearer " + connectionService.getToken())
	    			    .header("accept", "application/json;odata=verbose")
	    			    .url(fUrl)
	    			    .build();
	    	    		
	    	    
	    	    Response response = client.newCall(request).execute();
	    	    
	    	    
	    	    if (!response.isSuccessful()) {
	    	        throw new IOException("Failed to download file: " + response);
	    	    }
		       // String saveFilePath = downloadFileDir + fileName;
		        String saveFilePath = downloadFileDir;

	    	    FileOutputStream fos = new FileOutputStream(saveFilePath);
	    	    fos.write(response.body().bytes());
	    	    fos.close();
	                
	        System.out.println("File {} downloaded");
	          
	    }
	    
	    
	    
	    
	    
	    //remaining 
	    private Response getFilesNameFromFolder(String token, String siteURL, String siteFolderUrl) throws IOException {    	
	    	
	    		
	        String fUrl= siteURL + "/_api/web/Lists/GetByTitle('Documents')/Items?$expand=Folder&$select=Title,FileLeafRef,Folder/ServerRelativeUrl";            
	    	OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
				    .header("Authorization", "Bearer " + token)
				    .header("accept", "application/json;odata=verbose")
				    .url(fUrl)
				    .build();
               	String res = null;
               	Response response=null;
	 		try {
				 response = client.newCall(request).execute();
			//res= response.peekBody(2048).string();
				//int code=response.code();
			     
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return response;                
	       
	    }
	    
	    
	    
	    //get all folder of the sites
	    public List<String> getListOfFolders(String token, String siteURL, String siteFolderUrl) throws IOException {    	 	
	    	 List<String> folderNames = new ArrayList<>();	         
	       String fUrl0= siteURL + "/_api/web/Lists/GetByTitle('Documents')/Items?$expand=Folder&$select=Title,FileLeafRef,Folder/ServerRelativeUrl";            
	        String httpFResponseStr0 = getOKHttpResponse(token,fUrl0);  
	   	        
	        
              System.out.println("Response is::-"+httpFResponseStr0);
              
	         String fileName0="";        	       
	         JsonFactory factory = new JsonFactory();
	         com.fasterxml.jackson.core.JsonParser  parser  = factory.createParser(httpFResponseStr0);
 	              	         
	        
              while(!parser.isClosed())         
	         {  	         
	      	   JsonToken jsonToken= parser.nextToken();
	        	    if(JsonToken.FIELD_NAME.equals(jsonToken)){
	        	        String fieldName = parser.getCurrentName();      	        
	        	    	System.out.println("Field name:"+fieldName);

	        	     jsonToken = parser.nextToken();

	        	        if("FileLeafRef".equals(fieldName)){

	        	        	String folderName=parser.getValueAsString();
	        	        	System.out.println("Folders Name are :-"+folderName);        	        	 

	        	         	folderNames.add(folderName);
      	
	        	     }
	        	        
	        	    }		
	        	                    	
	        }
	        
              System.out.println("returning folder names"+folderNames);                   
              return folderNames;
              
	    }
	     
	    
	    
	   	    
	    public String getOKHttpResponse(String token, String fUrl0) throws IOException {
	    	OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
				    .header("Authorization", "Bearer " + token)
				    .header("accept", "application/json;odata=verbose")
				    .url(fUrl0)
				    .build();
               	String res = null;
	 		try {
				Response response = client.newCall(request).execute();
				res= response.peekBody(2048).string();
			//	int code=response.code();
				
			    StringBuilder httpFResponseStr0 = new StringBuilder();
		        
		        InputStreamReader inputStreamReader = getOKInputStreamReader(response);
		        
		        BufferedReader fin0 = new BufferedReader(inputStreamReader);
		        
		        String streamLine = "";
		        
		        while ((streamLine = fin0.readLine()) != null) {
		            httpFResponseStr0.append(streamLine);
		            System.out.println("streamLine==" + streamLine);
		        }
		        res= httpFResponseStr0.toString();	
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Response ok :::-"+res);

			return res;
	    }
	    
	    
	    
  private InputStreamReader getOKInputStreamReader(Response res) throws IOException {
	    	
	        InputStreamReader inputStreamReader = null;
	        if (res.code() == 200) {	        	
	            inputStreamReader = new InputStreamReader(res.body().byteStream());            		            		
	        } else 
	        {
	        	System.out.println("Inputr stream reader error");
	        }
	        return inputStreamReader;                
	    }
	     
  

	    private String getOKHttpURLConnection(String token,URL fileLstUrl0) throws IOException {
	    	OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
				    .header("Authorization", "Bearer " + token)
				    .header("accept", "application/json;odata=verbose")
				    .url(fileLstUrl0)
				    .build();
               	String res = null;
	 		try {
				Response response = client.newCall(request).execute();
				res= response.peekBody(2048).string();
				//int code=response.code();
			     
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;                
	    }
	    
	    
	    
     
}












