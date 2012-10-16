package agents.robots;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import simbad.sim.Agent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
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
    public DynamicEnvironmentElement(Vector3d pos, String name, double velocity, double radiusFactor) {
        super(pos, name);
        radius *= radiusFactor;
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
            setTranslationalVelocity(velocity + (Math.random() - 0.5) * velocity);
            resetDevices();
        }
    }

    @Override
    protected void create3D() {
        Color3f color = new Color3f(Color.blue);
        Color3f black = new Color3f(Color.black);

        // body
        Appearance appear = new Appearance();
        Material material = new Material();

        color.clampMax(0.8f);
        material.setDiffuseColor(color);

        material.setSpecularColor(black);
        appear.setMaterial(material);
        int flags = Primitive.GEOMETRY_NOT_SHARED | Primitive.ENABLE_GEOMETRY_PICKING | Primitive.GENERATE_NORMALS;
        flags |= Primitive.ENABLE_APPEARANCE_MODIFY;

        body = new Box(radius, height, radius, flags, appear);

        /* // allow geom intersect on each geom of the primitive cylinder
        allowIntersect(body.getShape(Cylinder.BODY));
        allowIntersect(body.getShape(Cylinder.TOP));*/
        // we must be able to change the pick flag of the agent
        body.setCapability(Node.ALLOW_PICKABLE_READ);
        body.setCapability(Node.ALLOW_PICKABLE_WRITE);
        body.setPickable(true);
        body.setCollidable(true);
        addChild(body);

        // Add bounds for interactions and collision
        Bounds bounds = new BoundingSphere(new Point3d(0, 0, 0), radius);
        setBounds(bounds);
    }
}
