public class NBody {
    /** this method returns the radius of the universe
     *  in the file of filename
     * @param filename name of file to open
     * @return the radius
     */
    public static double readRadius(String filename) {
        In in = new In(filename);
        int numberofplanet = in.readInt();
        double radius = in.readDouble();
        return  radius;
    }

    /** this method returns an array of all planets
     *  in the file named by filename
     */
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int numberofplanet = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[numberofplanet];

        for (int i = 0; i < numberofplanet; i ++) {
            double xxpos = in.readDouble();
            double yypos = in.readDouble();
            double xxvel = in.readDouble();
            double yyvel = in.readDouble();
            double mass = in.readDouble();
            String imgname = in.readString();

            planets[i] = new Planet(xxpos, yypos, xxvel, yyvel, mass, imgname);
        }
        return planets;
    }

    /** main function to draw the initial universe state
     *
     */
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);

        StdDraw.enableDoubleBuffering();

        String background = "images/starfield.jpg";
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, background);

        for (Planet body : planets) {
            body.draw();
        }
        StdDraw.show();
        //StdDraw.pause(2000);

        double[] xForces = new double[planets.length];
        double[] yForces = new double[planets.length];
        for (double time = 0; time < T; time = time + dt) {
            for (int i = 0; i < planets.length; i ++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i ++) {
                planets[i].update(dt, xForces[i], yForces[i] );
            }
            StdDraw.picture(0, 0, background);
            for (Planet body : planets) {
                body.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}