package edu.gatech.shelterme.model;

import java.io.Serializable;

/**
 * Created by Meha on 2/18/2018.
 */

public class User implements Serializable {
    private boolean weCanAccessThisInsideOnDataChangeIsThatNotGreat;
    public User() {}

    public boolean getData() {return weCanAccessThisInsideOnDataChangeIsThatNotGreat;}
    public void setData(boolean weCanAccessThisInsideOnDataChangeIsThatNotGreat) {this.weCanAccessThisInsideOnDataChangeIsThatNotGreat = weCanAccessThisInsideOnDataChangeIsThatNotGreat;}


}
