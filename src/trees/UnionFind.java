package trees;

import graph.Place;

import java.util.HashMap;

public class UnionFind {
	//parent relation, parent.put(src,dst) indicates that src points to dst
    private HashMap<Place,Place> parent;
    
    public UnionFind( ){
        parent = new HashMap<>();
    }
    
    public Place find( Place src ){
        if(!parent.containsKey(src)) return src;
        else while(!parent.get(src).equals(find(parent.get(src)))) parent.put(src, parent.get(parent.get(src)));
    	return parent.get(src);
    }
    
    public void union( Place v0, Place v1 ){
    	if(!find(v0).equals(find(v1))) parent.put(find(v0), find(v1));
    }
}
