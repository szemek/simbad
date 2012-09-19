package agents.robots;

import simbad.sim.Agent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

import javax.vecmath.Vector3d;

/**
 * User: Marek Ł
 * Date: 17.09.2012
 * Time: 11:38
 */
public class DynamicEnvironmentRobot extends Agent {

    RangeSensorBelt sonars, bumpers;

    public DynamicEnvironmentRobot(Vector3d position, String name) {
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

        if (bumpers.oneHasHit()) {
            setTranslationalVelocity(-0.1);
            setRotationalVelocity(0.1 * Math.random());

        } else {
            // Front left obstacle ?
            if (sonars.hasHit(0) && (sonars.hasHit(1))) {
                setRotationalVelocity(-Math.PI / 4);
                setTranslationalVelocity(0.1);
            }
            // Front right obstacle ?
            else if (sonars.hasHit(0) && (sonars.hasHit(23))) {
                setRotationalVelocity(Math.PI / 4);
                setTranslationalVelocity(0.1);
            }
            // left obstacle ?
            else if (sonars.hasHit(3) || sonars.hasHit(4)) {
                setRotationalVelocity(-Math.PI / 8);
                setTranslationalVelocity(0.1);
            }
            // right obstacle ?
            else if (sonars.hasHit(21) || sonars.hasHit(20)) {
                setRotationalVelocity(Math.PI / 8);
                setTranslationalVelocity(0.1);
            } else if ((getCounter() % 100) == 0) {
                setRotationalVelocity(Math.PI / 2 * (0.5 - Math.random()));
                setTranslationalVelocity(0.5);
            }
        }
    }
}
