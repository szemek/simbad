package simbad.agents;

import simbad.demo.Demo;
import simbad.sim.*;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;

@SuppressWarnings("unused")
public class SingleAvoider extends Demo {
    public class Robot extends Agent {

        RangeSensorBelt sonars, bumpers;

        public Robot(Vector3d position, String name) {
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

    public class DynamicEnvironmentElement extends Agent {

        RangeSensorBelt bumpers;

        /**
         * Constructs an Agent.
         *
         * @param pos  start position in 3D world.
         * @param name name of the agent.
         */
        public DynamicEnvironmentElement(Vector3d pos, String name) {
            super(pos, name);
            bumpers = RobotFactory.addBumperBeltSensor(this);

        }

        @Override
        public void initBehavior() {
            setTranslationalVelocity(1);
            setColor(new Color3f(Color.blue));
        }

        @Override
        public void performBehavior() {
            if (bumpers.oneHasHit()) {
                resetDevices();
            }
        }
    }

    public SingleAvoider() {
        Wall w1 = new Wall(new Vector3d(10, 0, 0), 20, 1, this);
        w1.rotate90(1);
        add(w1);
        Wall w2 = new Wall(new Vector3d(-10, 0, 0), 20, 1, this);
        w2.rotate90(1);
        add(w2);
        Wall w3 = new Wall(new Vector3d(0, 0, 10), 20, 1, this);
        add(w3);
        Wall w4 = new Wall(new Vector3d(0, 0, -10), 20, 1, this);
        add(w4);
        Box b1 = new Box(new Vector3d(-3, 0, -3), new Vector3f(1, 1, 2), this);
        add(b1);
        Arch a1 = new Arch(new Vector3d(3, 0, -3), this);
        add(a1);
        add(new DynamicEnvironmentElement(new Vector3d(2, 0, 3), "cherry"));
        add(new DynamicEnvironmentElement(new Vector3d(2, 0, 0), "cherry"));
        add(new DynamicEnvironmentElement(new Vector3d(2, 0, 8), "cherry"));
        add(new Robot(new Vector3d(0, 0, 0), "avoider"));

    }
}
