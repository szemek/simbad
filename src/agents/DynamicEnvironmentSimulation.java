package agents;

import agents.robots.DynamicEnvironmentElement;
import agents.robots.DynamicEnvoronmentRobot;
import simbad.demo.Demo;
import simbad.sim.Arch;
import simbad.sim.Box;
import simbad.sim.Wall;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class DynamicEnvironmentSimulation extends Demo {

    private final int NUMBER_OF_DYNAMIC_OBSTACLES = 20;

    public DynamicEnvironmentSimulation() {
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
        add(new DynamicEnvoronmentRobot(new Vector3d(0, 0, 0), "avoider"));

        //TODO: Check for collision and replace
        for (int i = 0; i < NUMBER_OF_DYNAMIC_OBSTACLES; i++) {
            DynamicEnvironmentElement dynamicElement = new DynamicEnvironmentElement(new Vector3d((int) (Math.random() * 18) - 9, 0,
                    (int) (Math.random() * 18) - 9), "cherry", 0.5);
            add(dynamicElement);
        }
    }
}
