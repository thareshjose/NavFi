package com.example.krishnendhu.main_project;

/**
 * Created by bill on 19-Apr-16.
 */
        import android.content.Context;
        import android.content.res.Resources;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;
        import java.util.PriorityQueue;


class Vertex implements Comparable<Vertex>
{
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    @Override
    public String toString() { return name; }
    @Override
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

public class Dijkstra
{
    Resources resources;
    private Context c;
    public Dijkstra(Context c){
        this.c = c;
        resources = this.c.getResources();
    }
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU ;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public String newMain(String inp, String dst)
    {
        String temp_string2=dst.trim();
        String temp_string1="";
//main points

        Vertex v1 = new Vertex("1");//V9 14
        Vertex v2 = new Vertex("2");//13 V5 15
        Vertex v3 = new Vertex("3");//40 14  16
        Vertex v4 = new Vertex("4");//15 DN 17
        Vertex v5 = new Vertex("5");//16 V4 18


        v1.adjacencies = new Edge[]{new Edge(v2, 576), new Edge(v4,305)};//V2
        v2.adjacencies = new Edge[]{new Edge(v1, 576), new Edge(v3,303)};//V1 V0
        v3.adjacencies = new Edge[]{new Edge(v2, 303),new Edge(v5, 2)};//18
        v4.adjacencies = new Edge[]{new Edge(v1, 305),new Edge(v5, 576)};//17
        v5.adjacencies = new Edge[]{new Edge(v4, 576),new Edge(v3, 2)};//14


        Vertex[] vertices = { v1, v2, v3, v4, v5};
//path calculating
        if (inp.trim().equals("1"))
            computePaths(v1);
        else if (inp.trim().equals("3"))
            computePaths(v3);

//specific rout making
        for (Vertex v : vertices)
        {
            List<Vertex> path = getShortestPathTo(v);
            if(v.name.equals(dst.trim())){
                temp_string1+=path;
            }
        }


        return temp_string1;
    }
}