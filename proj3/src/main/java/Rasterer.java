import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double lrlon = params.get("lrlon"), lrlat = params.get("lrlat"),
                ullon = params.get("ullon"), ullat = params.get("ullat"),
                w = params.get("w"), h = params.get("h");
        if (lrlon <= MapServer.ROOT_ULLON || ullon >= MapServer.ROOT_LRLON
                || ullat <= MapServer.ROOT_LRLAT || lrlat >= MapServer.ROOT_ULLAT
                || ullat <= lrlat || lrlon <= ullon) {
            results.put("query_success", false);
            return results;
        }
        double lonDPP = (lrlon - ullon) / w;
        int depth;
        for (depth = 0; depth < 7; depth++) {
            double londpp = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON)
                    / (MapServer.TILE_SIZE * Math.pow(2, depth));
            if (londpp <= lonDPP) {
                break;
            }
        }
        int gridSize = (int) Math.pow(2, depth);
        double lonPerGrid = ((MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / gridSize);
        double latPerGrid = ((MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / gridSize);
        int leftEnd = (int) Math.floor((ullon - MapServer.ROOT_ULLON)
                / lonPerGrid);
        int rightEnd = (int) Math.floor((lrlon - MapServer.ROOT_ULLON)
                / lonPerGrid);
        int upEnd = (int) Math.floor((ullat - MapServer.ROOT_LRLAT)
                / latPerGrid);
        int lowEnd = (int) Math.floor((lrlat - MapServer.ROOT_LRLAT)
                / latPerGrid);
        leftEnd = Math.max(leftEnd, 0);
        rightEnd = Math.min(rightEnd, gridSize - 1);
        upEnd = Math.min(upEnd, gridSize - 1);
        lowEnd = Math.max(lowEnd, 0);
        String[][] renderGrid = new String[upEnd - lowEnd + 1][rightEnd - leftEnd + 1];
        for (int y = lowEnd; y <= upEnd; y++) {
            for (int x = leftEnd; x <= rightEnd; x++) {
                renderGrid[upEnd - y][x - leftEnd] = "d" + depth + "_x"
                        + x + "_y" + (gridSize - 1 - y) + ".png";
            }
        }
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", MapServer.ROOT_ULLON + lonPerGrid * leftEnd);
        results.put("raster_ul_lat", MapServer.ROOT_LRLAT + latPerGrid * (upEnd + 1));
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + lonPerGrid * (rightEnd + 1));
        results.put("raster_lr_lat", MapServer.ROOT_LRLAT + latPerGrid * lowEnd);
        results.put("depth", depth);
        results.put("query_success", true);
        return results;
    }
}
