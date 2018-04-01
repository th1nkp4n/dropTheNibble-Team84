package edu.gatech.shelterme.model;

import java.io.Serializable;

/**
 * Created by Meha on 2/18/2018.
 */

public class User implements Serializable {
    private boolean weCanAccessThisInsideOnDataChangeIsntThatGreat;
    public User() {}

    public boolean getData() {return weCanAccessThisInsideOnDataChangeIsntThatGreat;}
    public void setData(boolean weCanAccessThisInsideOnDataChangeIsntThatGreat) {this.weCanAccessThisInsideOnDataChangeIsntThatGreat = weCanAccessThisInsideOnDataChangeIsntThatGreat;}


}
