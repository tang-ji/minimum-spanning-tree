package trees;

import java.util.*;

import graph.*;

public class SpanningTree {
    
    public static Collection<Edge> kruskal(UnionFind u, EuclideanGraph g){
    	Collection<Edge> col = new LinkedList<>();
    	LinkedList<Edge> edges = (LinkedList<Edge>) g.getAllEdges();
    	Collections.sort(edges, new EdgeComparator());
    	for(Edge e : edges) {
    		if(!u.find(e.source).equals(u.find(e.target))) {
    			col.add(e);
    			u.union(e.source, e.target);
    		}
    	}
    	return col;
    }
    
	public static Collection<Collection<Edge>> kruskal(EuclideanGraph g){
    	HashMap<Place, Collection<Edge>> edgelist = new HashMap<>();
    	UnionFind u = new UnionFind();
    	LinkedList<Edge> edges = (LinkedList<Edge>) g.getAllEdges();
    	Collections.sort(edges, new EdgeComparator());
    	
    	for(Edge e : edges) {
    		if(!u.find(e.source).equals(u.find(e.target))) {
    			if(!edgelist.containsKey(u.find(e.source))) edgelist.put(u.find(e.source), new LinkedList<Edge>());
    			if(!edgelist.containsKey(u.find(e.target))) edgelist.put(u.find(e.target), new LinkedList<Edge>());
    			edgelist.get(u.find(e.source)).add(e);
    			edgelist.get(u.find(e.source)).addAll(edgelist.get(u.find(e.target)));
    			edgelist.remove(u.find(e.target));
    			u.union(e.target, e.source);
    		}
    	}
    	return edgelist.values();
    }
    
    public static Collection<Edge> primTree(HashSet<Place> nonVisited, Place start, EuclideanGraph g){
    	nonVisited = new HashSet<>();
    	nonVisited.addAll(g.places());
    	nonVisited.remove(start);
    	
    	PriorityQueue<Edge> Edges = new PriorityQueue<>(1, new EdgeComparator());
    	Collection<Edge> ret = new LinkedList<>();
    	Edges.addAll(g.edgesOut(start));
    	
    	while(!Edges.isEmpty()) {
    		Edge edge = Edges.poll();
    		if(nonVisited.contains(edge.target)) {
    			nonVisited.remove(edge.target);
    			Edges.addAll(g.edgesOut(edge.target));
    			ret.add(edge);
    		}
    	}
    	return ret;
    }
    
    public static Collection<Collection<Edge>> primForest(EuclideanGraph g){
    	HashSet<Place> nonVisited = new HashSet<>();
    	nonVisited.addAll(g.places());
    	
    	PriorityQueue<Edge> Edges = new PriorityQueue<>(g.places().size(), new EdgeComparator());
    	Collection<Collection<Edge>> ret = new LinkedList<>();
    	
    	while(!nonVisited.isEmpty()) {
    		Place start = peekOnePlace(nonVisited);
    		nonVisited.remove(start);
    		for(Edge e : g.edgesOut(start)) {
    			if(nonVisited.contains(e.target)) Edges.add(e);
    		}
    		Collection<Edge> comp = new LinkedList<>();
    		while(!Edges.isEmpty()) {
    			Edge edge = Edges.poll();
    			if(nonVisited.contains(edge.target)) {
    				nonVisited.remove(edge.target);
    				Edges.addAll(g.edgesOut(edge.target));
    				comp.add(edge);
    			}
    		}
    		if(comp.size() > 0) ret.add(comp);
    	}
    	return ret;
    }
    
    public static Place peekOnePlace(HashSet<Place> set) {
    	Iterator<Place> i = set.iterator();
    	return i.next();
    }
   
}
