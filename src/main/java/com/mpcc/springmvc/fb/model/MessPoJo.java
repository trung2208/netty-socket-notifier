/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.fb.model;

/**
 * Aug 23, 2016
 *
 * @author Pham_Huy_Nam
 */
public class MessPoJo<T> {

    private String eventName;
    private T value;

    public MessPoJo(String eventName, T value) {
        this.eventName = eventName;
        this.value = value;
    }

    public MessPoJo() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
