/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.fb.model;

/**
 *
 * @author hieuhd
 */
public class FBRealtimeData {

    FBEntryData[] entry;
    String object;

    public FBRealtimeData() {
    }

    public FBEntryData[] getEntry() {
        return entry;
    }

    public void setEntry(FBEntryData[] entry) {
        this.entry = entry;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

}
