import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */


    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    private HashMap<Long, GraphNode> Graph = new HashMap<>();

    static class GraphNode {
        long id;
        double lon;
        double lat;
        //String name;
        HashSet<Long> edges;

        GraphNode(long id, double lon, double lat) {
            this.id = id;
            this.lon = lon;
            this.lat = lat;
            edges = new HashSet<>();
        }
    }

    /*
    boolean edgeValid(Long source, Long target) {
        if (!Graph.get(target).edges.containsKey(source)) {
            return true;
        }
        return Graph.get(target).edges.get(source);
    }
    */

    void addNode(GraphNode node) {
        Graph.put(node.id, node);
    }

    void deleteNode(long id) {
        Graph.remove(id);
    }

    void addEdge(long id, long neighborId) {
        Graph.get(id).edges.add(neighborId);
        Graph.get(neighborId).edges.add(id);
    }

    void deleteEdge(long id, long neighborId) {
        if (Graph.containsKey(id)) {
            Graph.get(id).edges.remove(neighborId);
        }
        if (Graph.containsKey(neighborId)) {
            Graph.get(neighborId).edges.remove(id);
        }
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        HashSet<Long> ids = new HashSet<>();
        //HashSet<Long> neighbors;
        for (Long id : Graph.keySet()) {
            ids.add(id);
        }
        for (Long id : ids) {
            /*
            neighbors = new HashSet<>();
            for (Long neighbor : Graph.get(id).edges) {
                neighbors.add(neighbor);
            }
            for (Long neighbor : neighbors) {
                if (!Graph.get(id).edges) {
                    deleteEdge(id, neighbor);
                }
                if (!Graph.containsKey(neighbor) || !Graph.get(neighbor).edges.containsKey(id)) {
                    deleteEdge(id, neighbor);
                }
            }
            */
            if (Graph.get(id).edges.isEmpty()) {
                deleteNode(id);
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        HashSet<Long> ver = new HashSet<>();
        for (Long id : Graph.keySet()) {
            ver.add(id);
        }
        return ver;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        /*
        HashSet<Long> neighbors = new HashSet<>();
        for (Long id : Graph.get(v).edges) {
            neighbors.add(id);
        }
        */
        return Graph.get(v).edges;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double minDistance = Double.POSITIVE_INFINITY;
        Long closestId = null;
        double distance;
        for (Long id : Graph.keySet()) {
            distance = distance(Graph.get(id).lon, Graph.get(id).lat, lon, lat);
            if (distance < minDistance) {
                minDistance = distance;
                closestId = id;
            }
        }
        return closestId;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return Graph.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return Graph.get(v).lat;
    }
}
