package graphs;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Vertex implements Serializable {

    private static final long serialVersionUID = 1024578369820L;

    private static int counter = 1;
    private final int id;
    private Color color = Color.WHITE;
    private ArrayList<Vertex> vertices;
    private String title; 
    private Point position;

    public Vertex(ArrayList<Vertex> vertices) {
        this.id = counter++;
        this.vertices = vertices;
    }

    public Vertex() {
        this(new ArrayList<Vertex>());
    }

    public Vertex(int id, ArrayList<Vertex> vertices) {
        this.id = id;
        this.vertices = vertices;
    }

    public Vertex(int id) {
        this(id, new ArrayList<Vertex>());
    }

    public Vertex(int id, String title) {
        this(id, new ArrayList<Vertex>());
        this.title = title;
    }

    public int getId() {
        return this.id;
    }
    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }

    public boolean addVertex(Vertex v) {
        if(!vertices.contains(v))
            return vertices.add(v);
        return false;
    }

    public boolean removeVertex(Vertex v) {
        return this.vertices.remove(v);
    }

    public String printId() {
        String str = "Vertex Title: "+this.title+" "+this.getId()+" ----> {";
        for(Vertex v : vertices)
            str += v.getId()+" ";
        str+="}";
        return str;
    }

    public String printInfo() {
        String str = "Vertex Title: "+this.title+" "+this.printPosition()+" ----> {";
        for(Vertex v : vertices)
            str += v.getTitle()+" ";
        str+="}";
        return str;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
    public String getTitle() {
        return this.title;
    }

    @Override
    public String toString() {
        return "Vertex Id:"+id + "Vertex Title: "+this.title;
    }

    public void setPosition(int x, int y) {
        position = new Point(x, y);
    }

    public void setPosition(Point p) {
        this.position = p;
    }

    public Point getPosition() {
        return position;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public String printPosition() {
        if (this.position != null) {
            return "["+this.getX()+";"+this.getY()+"]";
        }
        return "[?; ?]";
    }

}
