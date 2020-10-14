package com.lzq;

import java.io.Serializable;
import java.util.Random;

public class SkipList<T> implements Serializable {
    // size of the entries in the skip list
    private int size;
    //height
    private int height;
    //the head of the list, point to the top level
    private SkipListEntry Head;
    //the tail of the list
    private SkipListEntry Tail;
    //生成randomLevel用到的概率值
    private Random r;

    public SkipList() {
        Head = new SkipListEntry(Integer.MIN_VALUE, Integer.MIN_VALUE);
        Tail = new SkipListEntry(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Head.right = Tail;
        Tail.left = Head;
        size = 0;
        height = 0;
        r = new Random();
    }

    public Integer get(Integer key) {
        SkipListEntry p = findEntry(key);
        if (p.key == key) {
            return p.value;
        }else {
            return null;
        }
    }

    public Integer put(Integer key, Integer value) {
        SkipListEntry p, q;
        int i = 0;
        p = findEntry(key);
        //节点已存在
        if (p.key == key) {
            Integer oldValue = p.value;
            p.value = value;
            return oldValue;
        }
        //节点不存在
        //在最底层(可以看作是原链表)进行操作。
        q = new SkipListEntry(key, value);
        q.left = p;
        q.right = p.right;
        p.right.left = q;
        p.right = q;
        //抛硬币决定是否上层插入
        while (r.nextDouble() < 0.5) {
            //插入一个空白层，以便下次插入
            if (i > height) {
                addEmptyLevel();
                break;
            }
            //找到上一层的前一个节点对应的本层节点
            while (p.up == null && p.left != null) {
                p = p.left;
            }
            //如果当前处于最顶层，此时p已经为该层的head节点
            if (p.up == null) {
                addEmptyLevel();
                break;
            }
            p = p.up;
            //只有底层节点需要value，其它层只作为索引节点
            SkipListEntry e = new SkipListEntry(key, value);
            e.left = p;
            e.right = p.right;

            p.right.left = e;
            p.right = e;

            e.down = q;
            q.up = e;

            q = e;
            i = i + 1;
        }
        size++;
        return null;
    }

    public Integer remove(Integer key) {
        SkipListEntry p, q;
        p = findEntry(key);
        if (!p.key.equals(key)) {
            return null;
        }
        Integer oldValue = p.value;
        while (p != null) {
            q = p.up;
            p.left.right = p.right;
            p.right.left = p.left;
            p = q;
        }
        return oldValue;
    }

    //查找节点的辅助方法，返回一个小于或等于给定key值的节点
    public SkipListEntry findEntry(Integer key) {
        SkipListEntry p;
        p = Head;

        while (true) {
            while (p.right.key != Integer.MAX_VALUE && p.right.key < key) {
                p = p.right;
            }
            if (p.down != null) {
                p = p.down;
            }else {
//                不管是否找到该节点，到达底层为唯一退出条件
                break;
            }
        }
        return p; //p.key <= k
    }

    private void addEmptyLevel() {
        SkipListEntry p1, p2;
        p1 = new SkipListEntry(Integer.MIN_VALUE, null);
        p2 = new SkipListEntry(Integer.MAX_VALUE, null);

        p1.right = p2;
        p1.down = Head;

        p2.left = p1;
        p2.down = Tail;

        Head.up = p1;
        Tail.up = p2;

        Head = p1;
        Tail = p2;

        height++;
    }


    public void printHorizontal()
    {
        String s = "";
        int i;

        SkipListEntry p;

	     /* ----------------------------------
		Record the position of each entry
		---------------------------------- */
        p = Head;

        while ( p.down != null )
        {
            p = p.down;
        }

        i = 0;
        while ( p != null )
        {
            p.pos = i++;
            p = p.right;
        }

	     /* -------------------
		Print...
		------------------- */
        p = Head;

        while ( p != null )
        {
            s = getOneRow( p );
            System.out.println(s);

            p = p.down;
        }
    }

    public String getOneRow( SkipListEntry p )
    {
        String s;
        int a, b, i;

        a = 0;

        s = "" + p.key;
        p = p.right;


        while ( p != null )
        {
            SkipListEntry q;

            q = p;
            while (q.down != null)
                q = q.down;
            b = q.pos;

            s = s + " <-";


            for (i = a+1; i < b; i++)
                s = s + "--------";

            s = s + "> " + p.key;

            a = b;

            p = p.right;
        }

        return(s);
    }
}
