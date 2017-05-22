/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.fb.controller;

import com.google.gson.Gson;
import com.mpcc.springmvc.fb.model.FBRealtimeData;
import com.mpcc.springmvc.fb.service.FacebookService;
import com.mpcc.springmvc.configuration.ServerUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hieuhd
 */
@RestController
public class FacebookController {

    @Autowired
    FacebookService fbService;

    private static final String VERYFY_TOKEN = "1234567890";
    private static final String HUB_MODE = "subscribe";

    @RequestMapping(value = "/getFbNewFeedRealtime/", method = RequestMethod.GET)
    public String getFbNewFeedRealtime(HttpServletRequest request) {
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
        
        ServerUtils.sendMessages(data);
        
        Gson gson = new Gson();
        FBRealtimeData fbData = gson.fromJson(data, FBRealtimeData.class);
        if ("post".equals(fbData.getEntry()[0].getChanges()[0].getValue().getItem()) || "status".equals(fbData.getEntry()[0].getChanges()[0].getValue().getItem()) || "photo".equals(fbData.getEntry()[0].getChanges()[0].getValue().getItem())) {
            if ("add".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
                fbService.insertPost(fbData);
            } else if ("edited".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
                fbService.updatePost(fbData);
            } else if ("remove".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
                fbService.deletePost(fbData);
            }

        } else if ("comment".equals(fbData.getEntry()[0].getChanges()[0].getValue().getItem())) {
            if ("add".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
                fbService.insertComment(fbData);
            } else if ("edited".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
                fbService.updateComment(fbData);
            } else if ("remove".equals(fbData.getEntry()[0].getChanges()[0].getValue().getVerb())) {
                fbService.deleteComment(fbData);
            }
        }
    }
}
