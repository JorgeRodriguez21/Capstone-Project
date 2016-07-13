package com.app.bedomax.tagadata.utils;

/**
 * Created by Jorge on 12/07/16.
 */
public class Pager {

    int pageNumber;

    public Pager(){
        pageNumber = 1;
    }

    public void resetPage(){
        pageNumber = 1;
    }

    public void passPage(){
        pageNumber++;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
