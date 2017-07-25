/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.fb.service;

import com.mpcc.springmvc.configuration.Constants;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Account;
import com.restfb.types.Comment;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hieuhd
 */
public class SimpleExample {

    public static void main(String[] args) {
        //tạo đối tượng FacebookClient
        FacebookClient facebookClient = new DefaultFacebookClient(Constants.MY_ACCESS_TOKEN);

        //User là 1 class có săn của RestFb mô tả thông tin User        
        Connection<Account> page = facebookClient.fetchConnection("me/accounts", Account.class);

        String str = page.getData().get(0).getId();
        Date temMinuteAgo = new Date(System.currentTimeMillis() - 1000L * 60L
                * 45L);
        Connection<Post> post = facebookClient.fetchConnection("/" + str + "/feed", Post.class, Parameter.with("since", temMinuteAgo));

//        String postId = post.getData().get(1).getId();
//        Connection<Comment> comment = facebookClient.fetchConnection(postId + "/comments", Comment.class);
        System.out.println(post.getData().get(0).getFrom());
        System.out.println(post.getData().get(0).getMessage());
    }

}
