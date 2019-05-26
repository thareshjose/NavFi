package com.example.krishnendhu.main_project;
/**
 * Created by krishnendhu on 11-04-2017.
 */
import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.path;

class Vertex implements Comparable<Vertex>
{
    public String name;
    //public boolean visited;
    public List<Edge> adjaceniesList;
    public double minDistance = Double.MAX_VALUE;
    public Vertex previousVertex;

    public Vertex(String name)
    {
        this.name=name;
        this.adjaceniesList=new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        adjaceniesList.add(edge);
    }

    /*public void setPreviousVertex(Vertex previousVertex)
    {
        previousVertex=previousVertex;
    }*/

    @Override
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}
class Edge {
    public  double weight;
    public final Vertex startVertex;
    public final Vertex targetVertex;
    public Edge(double argWeight, Vertex argStartVertex, Vertex argTargetVertex)
    {
        weight=argWeight;
        startVertex=argStartVertex;
        targetVertex=argTargetVertex;
    }
}

public class BellmanFord {
    public  static List<Vertex> vertexList;
    public  static List<Edge> edgeList;

    Resources resources;
    private Context c;
    public BellmanFord(Context c,List<Vertex> vertexList, List<Edge> edgeList){
        this.c = c;
        resources = this.c.getResources();
        this.vertexList=vertexList;
        this.edgeList=edgeList;
    }

   /* public BellmanFord(List<Vertex> vertexList, List<Edge> edgeList) {
        this.vertexList=vertexList;
        this.edgeList=edgeList;
    }*/

    public  static void shortestPath(Vertex sourceVertex) {
        sourceVertex.minDistance=0;

        int length=vertexList.size();

        for(int i=0;i<length-1;i++) {

            for(Edge e : edgeList) {

                if(e.startVertex.minDistance == Double.MAX_VALUE) continue;

                Vertex v= e.startVertex;
                Vertex u= e.targetVertex;

                double newDistance = v.minDistance + e.weight;

                if(newDistance < u.minDistance )
                {
                    u.minDistance=newDistance;
                    u.previousVertex=v;

                }
            }
        }

        /*for(Edge edge : this.edgeList) {
            if( edge.getStartVertex().getMinDistance()!= Double.MAX_VALUE) {
                if(hasCycle(edge)) {

                }
            }
        }*/
    }

    public static List<String> getShortestPathTo(Vertex target)
    {
        List<String> path = new ArrayList<String>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previousVertex)
            path.add(vertex.name);
        Collections.reverse(path);
        return path;
    }

    public static String mainMethod(Context c,String inp, String dst)
    {

        String temp_string2=dst.trim();
        String temp_string1="";
        int i=Integer.parseInt(inp);

        vertexList=new ArrayList<>();

        vertexList.add(new Vertex("1"));
        vertexList.add(new Vertex("2"));
        vertexList.add(new Vertex("3"));
        vertexList.add(new Vertex("4"));
        vertexList.add(new Vertex("5"));
        vertexList.add(new Vertex("6"));
        vertexList.add(new Vertex("7"));
        vertexList.add(new Vertex("8"));
        vertexList.add(new Vertex("9"));
        vertexList.add(new Vertex("10"));
        vertexList.add(new Vertex("11"));
        vertexList.add(new Vertex("12"));
        vertexList.add(new Vertex("13"));
        vertexList.add(new Vertex("14"));
        vertexList.add(new Vertex("15"));
        vertexList.add(new Vertex("16"));
        vertexList.add(new Vertex("17"));
        vertexList.add(new Vertex("18"));
        vertexList.add(new Vertex("19"));
        vertexList.add(new Vertex("20"));
        vertexList.add(new Vertex("21"));
        vertexList.add(new Vertex("22"));
        vertexList.add(new Vertex("23"));
        vertexList.add(new Vertex("24"));
        vertexList.add(new Vertex("25"));
        vertexList.add(new Vertex("26"));
        vertexList.add(new Vertex("27"));
        vertexList.add(new Vertex("28"));
        vertexList.add(new Vertex("29"));
        vertexList.add(new Vertex("30"));
        vertexList.add(new Vertex("31"));
        vertexList.add(new Vertex("32"));
        vertexList.add(new Vertex("33"));
        vertexList.add(new Vertex("34"));
        vertexList.add(new Vertex("35"));
        vertexList.add(new Vertex("36"));
        vertexList.add(new Vertex("37"));
        vertexList.add(new Vertex("38"));
        vertexList.add(new Vertex("39"));
        vertexList.add(new Vertex("40"));
        vertexList.add(new Vertex("41"));
        vertexList.add(new Vertex("42"));
        vertexList.add(new Vertex("43"));
        vertexList.add(new Vertex("44"));
        vertexList.add(new Vertex("45"));
        vertexList.add(new Vertex("46"));
        vertexList.add(new Vertex("47"));
        vertexList.add(new Vertex("48"));

        edgeList = new ArrayList<>();

        edgeList.add(new Edge(69, vertexList.get(0), vertexList.get(1)));
        edgeList.add(new Edge(90, vertexList.get(1), vertexList.get(2)));
        edgeList.add(new Edge(81, vertexList.get(2), vertexList.get(3)));
        edgeList.add(new Edge(201, vertexList.get(3), vertexList.get(4)));
        edgeList.add(new Edge(384, vertexList.get(4), vertexList.get(5)));
        edgeList.add(new Edge(198, vertexList.get(5), vertexList.get(6)));
        edgeList.add(new Edge(144, vertexList.get(6), vertexList.get(7)));
        edgeList.add(new Edge(326, vertexList.get(7), vertexList.get(8)));
        edgeList.add(new Edge(78, vertexList.get(8), vertexList.get(9)));

        edgeList.add(new Edge(132, vertexList.get(11), vertexList.get(10)));
        edgeList.add(new Edge(242, vertexList.get(12), vertexList.get(11)));
        edgeList.add(new Edge(293, vertexList.get(10), vertexList.get(3)));
        edgeList.add(new Edge(105, vertexList.get(13), vertexList.get(12)));
        edgeList.add(new Edge(105, vertexList.get(12), vertexList.get(13)));//rev
        edgeList.add(new Edge(273, vertexList.get(14), vertexList.get(13)));
        edgeList.add(new Edge(390, vertexList.get(15), vertexList.get(14)));
        edgeList.add(new Edge(390, vertexList.get(14), vertexList.get(15)));//rev
        edgeList.add(new Edge(553, vertexList.get(16), vertexList.get(17)));
        edgeList.add(new Edge(148, vertexList.get(17), vertexList.get(18)));
        edgeList.add(new Edge(308, vertexList.get(18), vertexList.get(19)));
        edgeList.add(new Edge(288, vertexList.get(19), vertexList.get(20)));
        edgeList.add(new Edge(288, vertexList.get(20), vertexList.get(19)));//rev
        edgeList.add(new Edge(272, vertexList.get(20), vertexList.get(21)));
        edgeList.add(new Edge(60, vertexList.get(21), vertexList.get(22)));
        edgeList.add(new Edge(60, vertexList.get(22), vertexList.get(21)));//rev
        edgeList.add(new Edge(156, vertexList.get(22), vertexList.get(23)));
        edgeList.add(new Edge(245, vertexList.get(23), vertexList.get(24)));
        edgeList.add(new Edge(245, vertexList.get(24), vertexList.get(23)));//rev
        edgeList.add(new Edge(782, vertexList.get(24), vertexList.get(28)));
        edgeList.add(new Edge(363, vertexList.get(23), vertexList.get(25)));
        edgeList.add(new Edge(208, vertexList.get(25), vertexList.get(26)));
        edgeList.add(new Edge(211, vertexList.get(26), vertexList.get(27)));
        edgeList.add(new Edge(245, vertexList.get(27), vertexList.get(28)));
        edgeList.add(new Edge(245, vertexList.get(28), vertexList.get(27)));//rev
        edgeList.add(new Edge(126, vertexList.get(28), vertexList.get(29)));
        edgeList.add(new Edge(126, vertexList.get(29), vertexList.get(28)));//rev
        edgeList.add(new Edge(239, vertexList.get(29), vertexList.get(30)));
        edgeList.add(new Edge(295, vertexList.get(31), vertexList.get(30)));
        edgeList.add(new Edge(238, vertexList.get(30), vertexList.get(32)));
        edgeList.add(new Edge(40, vertexList.get(32), vertexList.get(33)));
        edgeList.add(new Edge(218, vertexList.get(33), vertexList.get(34)));
        edgeList.add(new Edge(259, vertexList.get(34), vertexList.get(35)));
        edgeList.add(new Edge(223, vertexList.get(35), vertexList.get(36)));
        edgeList.add(new Edge(174, vertexList.get(37), vertexList.get(36)));
        edgeList.add(new Edge(264, vertexList.get(38), vertexList.get(37)));
        edgeList.add(new Edge(165, vertexList.get(39), vertexList.get(38)));
        edgeList.add(new Edge(495, vertexList.get(27), vertexList.get(39)));
        edgeList.add(new Edge(20, vertexList.get(36), vertexList.get(9)));
        edgeList.add(new Edge(469, vertexList.get(19), vertexList.get(40)));
        edgeList.add(new Edge(298, vertexList.get(40), vertexList.get(41)));
        edgeList.add(new Edge(112, vertexList.get(41), vertexList.get(42)));
        edgeList.add(new Edge(350, vertexList.get(42), vertexList.get(43)));
        edgeList.add(new Edge(290, vertexList.get(43), vertexList.get(8)));
        edgeList.add(new Edge(358, vertexList.get(41), vertexList.get(44)));
        edgeList.add(new Edge(358, vertexList.get(44), vertexList.get(41)));//rev
        edgeList.add(new Edge(640, vertexList.get(44), vertexList.get(6)));
        edgeList.add(new Edge(243, vertexList.get(45), vertexList.get(44)));
        edgeList.add(new Edge(184, vertexList.get(46), vertexList.get(45)));
        edgeList.add(new Edge(284, vertexList.get(47), vertexList.get(46)));
        edgeList.add(new Edge(224, vertexList.get(13), vertexList.get(46)));



        BellmanFord al=new BellmanFord(c,vertexList,edgeList);
         shortestPath(vertexList.get(i-1));

        //specific rout making
        for (Vertex v : vertexList)
        {
            List<String> path = getShortestPathTo(v);
            if(v.name.equals(dst.trim())){
                temp_string1+=path;
            }
        }


        return temp_string1;
        // temp_string1="hi";
        // return temp_string1;
    }
    public static String mainPark(Context c,String inp, String dst)
    {

        String temp_string2=dst.trim();
        String temp_string1="";
        int i=Integer.parseInt(inp);

        vertexList=new ArrayList<>();

        vertexList.add(new Vertex("100"));
        vertexList.add(new Vertex("101"));
        vertexList.add(new Vertex("102"));


        edgeList = new ArrayList<>();

        edgeList.add(new Edge(576, vertexList.get(0), vertexList.get(1)));
        edgeList.add(new Edge(303, vertexList.get(1), vertexList.get(2)));

        BellmanFord al=new BellmanFord(c,vertexList,edgeList);
        //if(inp.equals("1"))
        shortestPath(vertexList.get(i-100));

        //specific rout making
        for (Vertex v : vertexList)
        {
            List<String> path = getShortestPathTo(v);
            if(v.name.equals(dst.trim())){
                temp_string1+=path;
            }
        }


        return temp_string1;
        // temp_string1="hi";
        // return temp_string1;
    }
}