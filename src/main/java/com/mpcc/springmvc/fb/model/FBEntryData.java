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
public class FBEntryData {

    private FBChangeData[] changes;
    private String id;
    private String time;

    public FBEntryData() {
    }

    public FBChangeData[] getChanges() {
        return changes;
    }

    public void setChanges(FBChangeData[] changes) {
        this.changes = changes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
