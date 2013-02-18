package agents;

import agents.robots.CVMRobot;
import agents.robots.DynamicEnvironmentElement;
import simbad.demo.Demo;
import simbad.sim.Agent;
import simbad.sim.Arch;
import simbad.sim.Box;
import simbad.sim.Wall;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class DynamicEnvironmentSimulation extends Demo {

    private final int NUMBER_OF_DYNAMIC_OBSTACLES = 10;
    //private final Agent DYNAMIC_ENVIRONMENT_ROBOT = new DynamicEnvironmentRobot(new Vector3d(0, 0, 0), "avoider");
    private final Agent DYNAMIC_ENVIRONMENT_ROBOT = new CVMRobot(new Vector3d(-9, 0, -9), "avoider");

    public DynamicEnvironmentSimulation() {
        light1IsOn = true;
        light1SetPosition(8, .6f, 8);
        light2IsOn = false;
        ambientLightColor = ligthgray;
        light1Color = white;
        light2Color = white;

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
        add(new Box(new Vector3d(-5, 0, 5), new Vector3f(1, 1, 2), this));
        add(new Box(new Vector3d(0, 0, 0), new Vector3f(1, 1, 2), this));
        add(new Box(new Vector3d(5, 0, 5), new Vector3f(2, 1, 2), this));
        add(new Box(new Vector3d(5, 0, 9), new Vector3f(1, 1, 1), this));
        add(new Box(new Vector3d(9, 0, 5), new Vector3f(1, 1, 1), this));
        add(new Box(new Vector3d(7, 0, -6), new Vector3f(2, 1, 2), this));
        add(DYNAMIC_ENVIRONMENT_ROBOT);

    }
}
