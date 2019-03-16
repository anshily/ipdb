package io.github.anshily.ipdb.core.btree;

public interface Key<T> {

    boolean keyEq(T key);

    boolean keyGt(T key);

    boolean keyLt(T key);

}
