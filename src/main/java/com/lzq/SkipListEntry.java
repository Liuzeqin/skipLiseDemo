package com.lzq;

//skipList节点类
//skipList实际上是一个多层次的链表结构
public class SkipListEntry {
    Integer key;
    Integer value;
    SkipListEntry left;
    SkipListEntry right;
    SkipListEntry up;
    SkipListEntry down;

    public SkipListEntry(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + key + "," + value + ")";
    }

    public int pos;
}
