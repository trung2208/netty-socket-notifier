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
public class FBChangeData {

    private String field;
    private FBContentChangeData value;

    public FBChangeData() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public FBContentChangeData getValue() {
        return value;
    }

    public void setValue(FBContentChangeData value) {
        this.value = value;
    }

}
