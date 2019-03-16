package io.github.anshily.ipdb.core.btree;

import java.io.Serializable;

public class Data implements Key<Data>, Value<String>, Serializable {
    Integer key;
    String value;

    Data(Integer key, String value){
        this.key = key;
        this.value = value;
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
}
