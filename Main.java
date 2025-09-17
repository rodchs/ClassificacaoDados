import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Resistor r1 = new Resistor(200);
        Resistor r2 = new Resistor(300);
        Resistor r3 = new Resistor(400);
        Resistor r4 = new Resistor(150);
        Resistor r5 = new Resistor(250);
        Resistor r6 = new Resistor(350);

        Parallel circuitoParalelo = new Parallel();
        Parallel circuitoParalelo2 = new Parallel();
        Serial circuitoSerie = new Serial();
        Serial circuitoFinal = new Serial();

        circuitoParalelo.addRes(r1);
        circuitoParalelo.addRes(r2);
        circuitoParalelo.addRes(r3);

        Resistor resEq1= new Resistor(circuitoParalelo.getResistance());

        circuitoParalelo2.addRes(r5);
        circuitoParalelo2.addRes(r6);

        Resistor resEq2 = new Resistor(circuitoParalelo2.getResistance());

        circuitoFinal.addRes(resEq1);
        circuitoFinal.addRes(r4);
        circuitoFinal.addRes(resEq2);

        System.out.println("Resistencia equivalente = " + circuitoFinal.getResistance());

    }
}