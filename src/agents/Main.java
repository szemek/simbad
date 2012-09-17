package agents;

import simbad.gui.Simbad;

public class Main {

    public static void main(String[] args) {
        // request antialising
        System.setProperty("j3d.implicitAntialiasing", "true");
        // create Simbad instance with given environment
        new Simbad(new DynamicEnvironmentSimulation(), false);
    }
} 