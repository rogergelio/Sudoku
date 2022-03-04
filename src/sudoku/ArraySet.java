package sudoku;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class ArraySet<T> implements SetADT <T> {
     
    private static final int DEFAULT_CAPACITY=100;
    private static Random rand=new Random();
    private static final int NOT_FOUND=-1;
    private int count;  // the current number of elements in the set
    private T[] contents;

    public ArraySet(int initialCapacity) {
        count=0;
        contents=(T[])(new Object[initialCapacity]);
    }

    public ArraySet() {
        this(DEFAULT_CAPACITY);
    }

    public void add(T element) {
        if(!(contains(element))) {
            if(size()==contents.length)
                expandCapacity();
            contents[count]=element;
            count++;
        }
    }
    
    public boolean addB(T element){
            boolean resp=false;
        if(!contains(element)){
            resp=true;
            if(size()==contents.length)
                expandCapacity();
            contents[count]=element;
            count++;
        }
        return resp;
    
    }

    private void expandCapacity() {
        T[] larger=(T[])(new Object[contents.length*2]);
        for(int index=0;index<contents.length;index++)
            larger[index]=contents[index];
        contents=larger;
    }

    public boolean contains(T target) {
        return searchTarget(target)!=NOT_FOUND;
    }

    private int searchTarget(T target) {
        int search=NOT_FOUND;
        int index=0;
        while(index<count&&search==NOT_FOUND) {
            if(contents[index].equals(target))
                search=index;
            index++;
        }
        return search;
    }

    public T removeRandom() {
        if(isEmpty())
            throw new EmptyCollectionException();
        else {
            int choice=rand.nextInt(count);
            T result=contents[choice];
            contents[choice]=contents[count-1];  // fill the gap
            contents[count-1]=null;
            count--;
            return result;
        }
    }

    public T remove(T target) {
        if(isEmpty())
            throw new EmptyCollectionException();
        else {
            int search=searchTarget(target);
            if(search==NOT_FOUND)
                throw new NoSuchElementException();
            else {
                T result=contents[search];
                contents[search]=contents[count-1];
                contents[count-1]=null;
                count--;
                return result;
            }
        }
    }

    public boolean isEmpty() {
        return size()==0;
    }

    public int size() {
        return count;
    }

    public void addAll(SetADT<T> set) {
        for(T element:set)
            add(element);
    }

    public boolean equals(SetADT<T> set) {
        boolean result=false;
        int countEquals=0;
        
        if(size()==set.size()) {
            for(T element:set)
                if(contains(element))
                    countEquals++;
            result=countEquals==size();
        }
        
        return result;
    }

    // Podría haberse escrito de una manera un poco más eficiente así:
/*    public boolean equals(SetADT<T> set) {
        boolean result = false;
        Iterator<T> it=set.iterator();

        if(size()==set.size()) {
            while(it.hasNext()&&contains(it.next()));
            if(!it.hasNext())
                result=true;
        }

        return result;
    }*/
    
    public Iterator<T> iterator() {
        return new ArrayIterator<T>(contents, count);
    }

    public String toString() {
    	StringBuilder result=new StringBuilder();
    	for(int index=0;index<count;index++)
            result.append(contents[index].toString()+"\n");
    	return result.toString();
    }
}


