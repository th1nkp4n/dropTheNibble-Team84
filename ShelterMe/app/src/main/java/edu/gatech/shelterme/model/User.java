package edu.gatech.shelterme.model;

import java.io.Serializable;


public class User implements Serializable {
    private boolean weCanAccessThisInsideOnDataChangeIsThatNotGreat;
    public User() {}

    public boolean getData() {return weCanAccessThisInsideOnDataChangeIsThatNotGreat;}
    public void setData(boolean weCanAccessThisInsideOnDataChangeIsThatNotGreat) {this.weCanAccessThisInsideOnDataChangeIsThatNotGreat = weCanAccessThisInsideOnDataChangeIsThatNotGreat;}


}
