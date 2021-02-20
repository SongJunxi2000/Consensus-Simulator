package Main;

import Protocol.*;
import Simulator.*;
import com.google.gson.Gson;

import java.util.LinkedList;

public class main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Gson gson = new Gson();
        Simulation_engine engine = new Simulation_engine(30,10,0,10);
        System.out.println(engine.check_output());

    }
}
