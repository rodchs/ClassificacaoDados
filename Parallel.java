import java.util.ArrayList;

public class Parallel {
    ArrayList<Resistor> resistorArray;

    public Parallel() {
        this.resistorArray = new ArrayList<Resistor>();
    }

    public void addRes(Resistor resistor) {
        this.resistorArray.add(resistor);
    }

    public double getResistance(){
        double res = 0;
        for(Resistor resistor : this.resistorArray){
            res+= (1/resistor.getResistance());
        }
        return (1/res);
    }
}
