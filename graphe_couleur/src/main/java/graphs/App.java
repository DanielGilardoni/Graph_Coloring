package graphs;

import gui.GUI;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        System.out.println("Test Graph : ");
        Graph graph = Graph.randomGraph(6);
        System.out.println(graph);

        GUI gui = new GUI(1200, 800);

    }
}
