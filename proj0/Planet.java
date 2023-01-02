/** this is the class representing the planet
 *  to be simulated in this proj
 */
public class Planet {
	public double G = 6.67e-11;

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV,
				  double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

/** this method copy a existing planet
 */
	public Planet(Planet p) {
		this(p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
	}

/** this method computes the distance
 *  between the target planet and the planet to be computed
 */
	public double calcDistance(Planet tarplanet) {
		double deltax = tarplanet.xxPos - this.xxPos;
		double deltay = tarplanet.yyPos - this.yyPos;
		double squaredeltax = deltax * deltax;
		double squaredeltay = deltay * deltay;
		double squaredist = squaredeltax + squaredeltay;
		return Math.sqrt(squaredist);
	}

/** this method computes the force exerted
 *  on this planet by the given planet
 */
	public double calcForceExertedBy(Planet tarplanet) {
		double dist = this.calcDistance(tarplanet);
		return G * this.mass * tarplanet.mass / (dist * dist);
	}

/** this method computes the x component
 *  of the force exerted on this planet by the given
 */
	public double calcForceExertedByX(Planet tarplanet) {
		double dist = this.calcDistance(tarplanet);
		double force = this.calcForceExertedBy(tarplanet);
		return force * (tarplanet.xxPos - this.xxPos) / dist;
	}

/** this method computes the y component
 *  of the force exerted on this planet by the given
 */
	public double calcForceExertedByY(Planet tarplanet) {
		double dist = this.calcDistance(tarplanet);
		double force = this.calcForceExertedBy(tarplanet);
		return force * (tarplanet.yyPos - this.yyPos) / dist;
	}

/** this method computes the x component
 *  of the netforce exerted by an array of planets on this
 */
	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double netforcex = 0;
		for (Planet target : allPlanets) {
			if (!this.equals(target)) {
				netforcex = netforcex + this.calcForceExertedByX(target);
			}
		}
		return netforcex;
	}

/** this method computes the y component
 *  of the netforce exerted by an array of planets on this
 */
	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double netforcey = 0;
		for (Planet target : allPlanets) {
			if (!this.equals(target)) {
				netforcey = netforcey + this.calcForceExertedByY(target);
			}
		}
		return netforcey;
	}

/** this method simulates a step of simulation
 *  in a period of deltat with netforce of x/y components
 */
	public void update(double dt, double fX, double fY) {
		double accelerateX = fX / this.mass;
		double accelerateY = fY / this.mass;
		this.xxVel = this.xxVel + dt * accelerateX;
		this.yyVel = this.yyVel + dt * accelerateY;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}

	/** draw is used to draw the planet at appropriate place
	 *
	 */
	public void draw() {
		String planetpic = "images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, planetpic);
	}

}
