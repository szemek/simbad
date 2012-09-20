package agents.robots;

import simbad.sim.Agent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

import javax.vecmath.Vector3d;

/**
 * User: Marek Ł
 * Date: 20.09.2012
 * Time: 06:05
 */
public class CVMRobot extends Agent {

    private static final double MAX_VELOCITY = 0.5;
    private static final double MAX_ANGULAR_VELOCITY = Math.PI;
    private RangeSensorBelt sonars, bumpers;
    Sensor minAngle;

    private class Sensor {
        public double angle;
        public double measurement;

        public Sensor(double angle, double measurement) {
            this.angle = angle;
            this.measurement = measurement;
        }

        public Sensor() {
            this(0, 0);
        }
    }

    public CVMRobot(Vector3d position, String name) {
        super(position, name);
        // Add sensors
        bumpers = RobotFactory.addBumperBeltSensor(this);
        sonars = RobotFactory.addSonarBeltSensor(this, 24);
    }

    /**
     * Initialize Agent's Behavior
     */
    public void initBehavior() {
        // nothing particular in this case
    }

    /**
     * Perform one step of Agent's Behavior
     */
    public void performBehavior() {

        Sensor minPositiveAngle = new Sensor();
        Sensor minNegativeAngle = new Sensor();

        if (bumpers.oneHasHit()) {
            setTranslationalVelocity(-0.1);
            setRotationalVelocity(0.1 * Math.random());

        } else {
            for (int i = 0; i < sonars.getNumSensors(); i++) {
                if (sonars.hasHit(i)) {
                    if (sonars.getSensorAngle(i) <= minPositiveAngle.angle + (2 * Math.PI / sonars.getNumSensors())) {
                        minPositiveAngle.angle = sonars.getSensorAngle(i) + (2 * Math.PI / sonars.getNumSensors());
                        minPositiveAngle.measurement = sonars.getMeasurement(i);
                    }
                    if ((sonars.getSensorAngle(i) - 2 * Math.PI) >= minNegativeAngle.angle - (2 * Math.PI / sonars.getNumSensors())) {
                        minNegativeAngle.angle = sonars.getSensorAngle(i) - (2 * Math.PI / sonars.getNumSensors());
                        minNegativeAngle.measurement = sonars.getMeasurement(i);
                    }
                }
            }
            minAngle = Math.abs(minNegativeAngle.angle) < Math.abs(minPositiveAngle.angle) ?
                    minNegativeAngle : minPositiveAngle;

            double nextVel = MAX_VELOCITY;
            double nextAngVel = 0;

            if (minAngle.angle != 0) {
                double maxHappiness = 0;

                for (double i = 0; i < MAX_VELOCITY; i += MAX_VELOCITY / 10) {
                    for (double j = 0; j < MAX_ANGULAR_VELOCITY; j += MAX_ANGULAR_VELOCITY / 10) {
                        double happiness = happinessFunction(i, Math.signum(minAngle.angle) * j, 0.1, 1, 1);
                        if (happiness > maxHappiness) {
                            maxHappiness = happiness;
                            nextVel = i;
                            nextAngVel = j;
                        }
                    }
                }
            }

            setRotationalVelocity(nextAngVel);
            setTranslationalVelocity(nextVel);
        }
    }

    private double happinessFunction(double velocity, double angularVelocity, double velocityWeight, double obstacleWeight, double aimWeight) {
        return velocityHappinessFunction(velocity, angularVelocity) * velocityWeight
                + obstacleHappinessFunction(velocity, angularVelocity) * obstacleWeight
                + aimHappinessFunction(velocity, angularVelocity) * aimWeight;
    }

    private double aimHappinessFunction(double velocity, double angularVelocity) {
        return 1;
    }

    private double obstacleHappinessFunction(double velocity, double angularVelocity) {
        double luk = Math.abs(Math.sin(minAngle.angle) / minAngle.measurement);
        if (2 * minAngle.angle / luk > angularVelocity / velocity) {
            return 1;
        } else {
            return 0;
        }
    }

    private double velocityHappinessFunction(double velocity, double angularVelocity) {
        return velocity / MAX_VELOCITY;
    }


}
