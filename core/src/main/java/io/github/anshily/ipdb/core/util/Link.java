package io.github.anshily.ipdb.core.util;

public class Link {
    int point;
    boolean empty;
//    Node node;

    Link(){
        this.empty = true;
    }
    Link(int point){
        this.empty = false;
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
