/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.fb.controller;

import com.google.gson.Gson;
import static com.mpcc.springmvc.configuration.Constants.FILE_UPLOAD_LOCATION_FOLDER;
import com.mpcc.springmvc.fb.model.FBRealtimeData;
import com.mpcc.springmvc.fb.service.FacebookService;
import com.mpcc.springmvc.socket.excuters.ServerHandler;
import com.mpcc.springmvc.socket.excuters.ServerUtils;
import com.mpcc.springmvc.socket.object.AgentChannelHandleContext;
import com.mpcc.springmvc.socket.object.AgentChannels;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author hieuhd
 */
@RestController
public class FacebookController {

    private static Logger log = LoggerFactory.getLogger(FacebookController.class);
    @Autowired
    FacebookService fbService;

    private static final String VERYFY_TOKEN = "1234567890";
    private static final String HUB_MODE = "subscribe";

    @RequestMapping(value = "/getFbNewFeedRealtime/", method = RequestMethod.GET)
    public String getFbNewFeedRealtime(HttpServletRequest request) {
        log.info("Get acess", "accepted");
        String mode = request.getParameter("hub.mode");
        String challenge = request.getParameter("hub.challenge");
        String verify_token = request.getParameter("hub.verify_token");
        if (VERYFY_TOKEN.equals(verify_token) && HUB_MODE.equals(mode)) {
            return challenge;
        }
        return "";
    }

    @RequestMapping(value = "/getFbNewFeedRealtime/", method = RequestMethod.POST)
    public void getFbNewFeedRealtime(HttpServletRequest request, @RequestBody String data) {
        log.debug("receive webhhook");
        // ServerUtils.sendMessages(data);
        //ServerUtils.sendMessages(data);

//        Gson gson = new Gson();
//        FBRealtimeData fbData = gson.fromJson(data, FBRealtimeData.class);
//        if ("post".equals(fbData.getEntry()[0].getChanges()[0].getValue().getItem()) || "status".equals(fbData.getEntry()[0].getChanges()[0].getValue().getItem()) || "photo".equals(fbData.getEntry()[0].getChanges()[0].getValue().getItem())) {
//            if ("add".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
//                fbService.insertPost(fbData);
//            } else if ("edited".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
//                fbService.updatePost(fbData);
//            } else if ("remove".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
//                fbService.deletePost(fbData);
//            }
//
//        } else if ("comment".equals(fbData.getEntry()[0].getChanges()[0].getValue().getItem())) {
//            if ("add".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
//                fbService.insertComment(fbData);
//            } else if ("edited".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
//                fbService.updateComment(fbData);
//            } else if ("remove".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
//                fbService.deleteComment(fbData);
//            }
//        }
    }

    @Scheduled(fixedDelay = 5000L)
    public void pingToClients() {
        for (Map.Entry<String, AgentChannels> set : ServerHandler.agents.entrySet()) {
            for (Map.Entry<Integer, AgentChannelHandleContext> entry : ServerHandler.agents.get(set.getKey()).entrySet()) {
                if (entry.getValue().isDisconnected()) {
                    ServerHandler.agents.get(set.getKey()).remove(entry.getKey());
                } else {

                    entry.getValue().doPing(set.getKey());
                }
            }
        }
    }

    @RequestMapping(value = "/upload/", method = RequestMethod.POST)
    public String uploadContent(HttpServletRequest request, @RequestParam CommonsMultipartFile[] multipartFiles) throws IOException {
        if (multipartFiles == null || multipartFiles.length < 1) {
            return " no image in upload";
        }
        for (CommonsMultipartFile aFile : multipartFiles) {
            if (!aFile.getOriginalFilename().equals("")) {
                aFile.transferTo(new File(FILE_UPLOAD_LOCATION_FOLDER + aFile.getOriginalFilename()));
            }
        }
        return "success upload image";
    }

}
