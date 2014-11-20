/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author florian
 */
public class Node<T> implements Iterable<T> {
    private ArrayList<T> children;

    public Node() {
        this.children = new ArrayList<>();
    }
    
    public void addChild(T n) {
        this.children.add(n);
    }
    
    public void removeChild(int i) {
        this.children.remove(i);
    }
    
    public void removeChild(T n) {
        this.children.remove(n);
    }
    
    public boolean isLeaf() {
        return this.children.isEmpty();
    }
    
    public T getChildByIndex(int index) {
        return this.children.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return this.children.iterator();
    }
}
