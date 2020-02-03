package com.mithrilmania.blocktopograph.util;

@FunctionalInterface
public interface Consumer<T> {

    void accept(T param);
}
