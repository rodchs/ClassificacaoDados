import java.util.ArrayList;

public class Serial extends Circuit{
    ArrayList<Resistor> resistorArray;

    public Serial(){
        this.resistorArray = new ArrayList<Resistor>();
    }

    public void addRes(Resistor resistor) {
        this.resistorArray.add(resistor);
    }

    public double getResistance(){
        double res = 0;
        for(Resistor resistor : this.resistorArray){
            res += resistor.getResistance();
        }
        return res;
    }
}
