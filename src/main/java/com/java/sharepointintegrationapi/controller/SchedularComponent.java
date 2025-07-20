
/*
package com.java.sharepointintegrationapi.controller;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.java.sharepointintegrationapi.service.TokenService.ConnectionService;
import com.java.sharepointintegrationapi.service.SharepointDocumentService.SharepointFileService;


@Component
public class SchedularComponent {

    private static final Logger LOG = LoggerFactory.getLogger(SchedularComponent.class);

    @Autowired
    SharepointFileService sharepointFileService;
    
    
    com.java.sharepointintegrationapi.dto.SharePointDetail SharePointDetail=new com.java.sharepointintegrationapi.dto.SharePointDetail();

    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void synchSharePoint() {
        final LocalDateTime start = LocalDateTime.now();
        LOG.info("download start -" + start);
        String token = ConnectionService.getToken();
        sharepointFileService.downloadFoldersFiles(token,SharePointDetail);
        final LocalDateTime end = LocalDateTime.now();
        LOG.info("download end -" + end);

    }

}
*/