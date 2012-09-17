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
    double velocity;

    /**
     * Constructs an Agent.
     *
     * @param pos  start position in 3D world.
     * @param name name of the agent.
     */
    public DynamicEnvironmentElement(Vector3d pos, String name, double velocity) {
        super(pos, name);
        bumpers = RobotFactory.addBumperBeltSensor(this);
        this.velocity = velocity;
    }

    @Override
    public void initBehavior() {
        setTranslationalVelocity(velocity);
        rotateY((int) (Math.random() * 3) * Math.PI / 2);
    }

    @Override
    public void performBehavior() {
        if (bumpers.oneHasHit()) {
            rotateY((int) (Math.random() * 3) * Math.PI / 2);
            setTranslationalVelocity(velocity + Math.random() * velocity / 2);
            resetDevices();
        }
    }

    @Override
    protected void create3D() {
        super.create3D();
        setColor(new Color3f(Color.blue));
    }
}
