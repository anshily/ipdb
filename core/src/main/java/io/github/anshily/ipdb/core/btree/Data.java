package io.github.anshily.ipdb.core.btree;

import java.io.Serializable;

public class Data implements Key<Data>, Value<String>, Serializable {
    Integer key;
    String value;
    Boolean isnull;

    Data(Integer key, String value){
        this.key = key;
        this.value = value;
        this.isnull = false;
    }

    public void remove(){
        this.isnull = true;
    }

    public Boolean isNull(){
        return isnull;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean keyEq(Data key) {
        return this.key == key.key;
    }

    @Override
    public boolean keyGt(Data key) {
        return this.key > key.key;
    }

    @Override
    public boolean keyLt(Data key) {
        return this.key < key.key;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getIsnull() {
        return isnull;
    }

    public void setIsnull(Boolean isnull) {
        this.isnull = isnull;
    }
}
