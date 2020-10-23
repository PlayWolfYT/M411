/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author PlayWolfYT
 */
public class SearchResult {
    
    private final long time;
    private final Object[][] data;
    
    public SearchResult(long time, Object[][] data) {
        this.time = time;
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public Object[][] getData() {
        return data;
    }
    
}
