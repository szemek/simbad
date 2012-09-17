package agents.robots;

import simbad.sim.Agent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import java.awt.*;

/**
 * User: Marek Ł
 * Date: 17.09.2012
 * Time: 11:40
 */
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
            rotateY(Math.PI);
            resetDevices();
        }
    }
}
