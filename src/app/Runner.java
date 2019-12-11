package app;

import java.io.File;
import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {

        File circuitFile = new File("circuitFile.txt");
        Circuit c = new Circuit(circuitFile);
        Detector d = new Detector(c);

        Menu menu = new Menu(d);
        menu.loopForInput();
        
    }
}
