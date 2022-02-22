package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

import graphs.Graph;
import graphs.Vertex;

/**
 * The Converter class, transforms a csv file into a graph. 
 */
public class Converter {
    static String saveFolder = "src/resources/";
    static public String graphName;

    public static Graph mapToGraph(String filepath) throws FileNotFoundException {
        Graph graph = new Graph(graphName);

        Scanner scanner = new Scanner(new File(filepath));
        scanner.useDelimiter("\n");
        if(!scanner.hasNextLine())
            return null;
        scanner.nextLine();

        while (scanner.hasNext()) {
            // System.out.println(scanner.next());
            String line = scanner.next();
            String[] vertices = line.split(",\"");
            String main = vertices[0].strip();
            String[] borders = vertices[1].replace("\"", "").split(",");
            Vertex v = graph.getVertex(main);
            if(v == null) {
                v = graph.addVertex(main);
            }
            if(vertices.length == 3) {
                String[] points = vertices[2].split(",");
                points = new String[] {getNumber(points[0]), getNumber(points[1])};
                v.setPosition(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
            }

            for(String s : borders) {
                s = s.strip();
                Vertex border = graph.getVertex(s);
                if (border == null) {
                    border = graph.addVertex(s);
                }
                graph.addEdge(v, border);
            }         
        }
        scanner.close();
        return graph;
    }

    public static String getNumber(String nb) {
    	String n = "";
    	for(char c : nb.toCharArray())
    		if(Character.isDigit(c))
    			n += c;
    	return n;
    }
    
    public static void checkCSV(String filepath, String filename) throws Exception{
        /**
         * 1. Check if CSV file.
         * 2. Read and Load File into resources folder.
         * 3. Raise Error otherwise.
         */
        String extension = getExtension(filepath).get();
        if (!extension.equals("csv")) {
            throw new Exception("\n[LOG]: Extension \""+extension+"\" not supported. Please use a .csv file\n");
        }

        File file = new File(filepath);
        boolean can_use_file = file.exists() && file.canRead();
        boolean saved = false;

        saveFile(filename, file, can_use_file, saved, extension);
    }

    
    public static void checkImage(String path, String filename) throws Exception {
        /**
         * 1. Check if Image file.
         * 2. Read and Load File into resources folder.
         * 3. Raise Error otherwise.
         */
        String[] allowed_extensions = {"png", "jpeg", "jpg"};
        String extension = getExtension(path).get();
        boolean extension_ok = false;

        for (String ex : allowed_extensions) {
            if(extension.equals(ex)) {
                extension_ok = true;
            }
        }
        if (!extension_ok) throw new Exception("[LOG]: Image Extension not valid. Please insert a png, jpeg or jpg image");

        
        File file = new File(path);
        boolean can_use_file = file.exists() && file.canRead();
        boolean saved = false;

        saveFile(filename, file, can_use_file, saved, extension);
    }

    public static void saveFile(String filename, File file, boolean can_use_file, boolean saved, String extension) throws Exception {
        File f = new File(saveFolder+filename+"."+extension);

        if(can_use_file) {
            if (f.exists()) {
                System.out.println("\n[LOG] File exists already, please delete file or rename file to save current file");
            }else {
                saved = file.renameTo(new File(saveFolder+filename+"."+extension));
            }
        }
         else {
            throw new Exception("\n[LOG]: File not found or not readable. Please insert a valid file\n");
        }

        if(saved) {
            System.out.println("\n[LOG]: File saved at resources folder resources/"+filename+"."+extension+"!\n");

        } else {
            throw new Exception("\n[LOG]: File could not be saved.\n");
        }
    }

    public static void writeCoordinates(Vertex v, String filepath) throws IOException {
        String str = new String(Files.readAllBytes(Paths.get(filepath)));
        Scanner sc = new Scanner(str);
        String newStr = sc.nextLine()+"\n";
        while(sc.hasNext()) {
            String line = sc.nextLine();
            String[] split = line.split(",\"");
            if(split != null && split[0].equals(v.getTitle())) {
                String main = split[0].strip()+",";
                main += "\""+split[1].strip();
                main = main.substring(0, main.lastIndexOf("\"")+1)+",";
                main += "\""+v.getX()+","+v.getY()+"\"";
                newStr += main+"\n";
                System.out.println("Vertex: "+v.getTitle()+" updated.");
            }
            else {
                newStr += line+"\n";
            }
        }
        sc.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(newStr);
        writer.close();
    }

    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
