/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.configuration;

import com.google.gson.Gson;
import com.mpcc.springmvc.utils.LookupUtils;
import java.text.SimpleDateFormat;

/**
 *
 * @author hieuhd
 */
public class Constants {

    public static final String REDIRECT_URI = "http://localhost:8080/facebookfriends/FriendsListServlet";
    public static final String MY_ACCESS_TOKEN = "EAACEdEose0cBAJOvDHMb5joLnBisToFnGi5ktSXkBZBdGklqYZAvdIZAtpWyWrrZBnJnyjlyZCzzXuiaiGOTWPIvrZA42pJUYBdetiT4QlP7RZCbZCzkeCXfumbJCjXDmhZCQzKxiOWEqTnw0pRN8d0mmaIqWgxZCmKuuzDvQhQDOb8h3qD3CLKOT1X0ALQwxuBdEZD";

    //fb app
    public static final String MY_APP_ID = "133803857168884";
    public static final String MY_APP_SECRET = "639e04cf470f3cb67ce4b4e1a6dd97c3";
    public static final Gson GSON = new Gson();
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");

}
