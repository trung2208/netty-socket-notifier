/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.utils;

/**
 *
 * @author 24h
 */
public class LookupUtils {

    public static <T extends Enum<T>> T Lookup(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                return Enum.valueOf(c, "ERROR");
            }
        }
        return Enum.valueOf(c, "ERROR");
    }
}
