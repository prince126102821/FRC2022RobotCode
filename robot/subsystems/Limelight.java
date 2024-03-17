package frc.robot.subsystems;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.math.filter.MedianFilter;


public class Limelight {

    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-unicorn");
    private static NetworkTableEntry tx = table.getEntry("tx");
    private static NetworkTableEntry ty = table.getEntry("ty");
    private static NetworkTableEntry ta = table.getEntry("ta");
    private static MedianFilter filter = new MedianFilter(7);

    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    

    public static double getarea() {
        double area = ta.getDouble(0.0);
        return area;
    }
    
    public static double getTx() {
        double x = tx.getDouble(0.0);
        return x;
    }
    
    public static double getTy() {
        return filter.calculate(ty.getDouble(0.0));
    }
    
    public static double getdistances(){
        double distance = (255-84)/(Math.tan(Math.toRadians(41+Limelight.getTy())));
        return distance;
    }

    // private static int frontCompensation = 0;
    // private static int backCompensation = 0;
    private static int compensation = 0;

    //140 ~170
    private static int one = 8200;
    //170 ~200
    private static int two = 8400;
    //200 ~230
    private static int three = 8400;
    //230 ~260
    private static int four = 8600;
    //260 ~290
    // private static int five = 11500;
    //290 ~320
    // private static int six = 11300;
    //320 ~350
    // private static int seven = 11700;
    //350 ~380
    // private static int eight = 12000;
    //380 ~410
    // private static int nine = 12050;

    public static double getTargetVelocity() {
        double velocity = 9000;
        
        double distance = 0;//Limelight.getdistances();
        if(distance >= 170 && distance < 200) {
            velocity = one + (two - one) * (distance - 170) / 30;

        }else if(distance >= 200 && distance < 230) {
            velocity = two + (three - two) * (distance - 200) / 30;

        }else if(distance >= 230 && distance < 260) {
            velocity = three + (four - three) * (distance - 230) / 30;

        }
        /*else if(distance >= 260 && distance < 290) {
            velocity = four + (five - four) * (distance - 230) / 30;
        }
        
        else if(distance >= 260 && distance < 290) {
            velocity = five + (six - five) * (distance - 260) / 30;

        }else if(distance >= 290 && distance < 320) {
            velocity = six + (seven - six) * (distance - 290) / 30;

        }else if(distance >= 320 && distance < 350) {
            velocity = seven + (eight - seven) * (distance - 320) / 30;

        }else if(distance >= 350 && distance < 380) {
            velocity = eight + (nine - eight) * (distance - 350) / 30;
            
        }else if(distance >= 380 && distance < 410) {
            velocity = nine;
        }                                                                 
        if(distance < 260) {
            velocity += frontCompensation;
        }

        if(distance > 260) {
            velocity += backCompensation;
        }
*/
        return velocity + compensation;
    }

    // @Override
    // public void periodic(){
    //     // SmartDashboard.putNumber("Distance_Limelight", getdistances());
    //     SmartDashboard.putNumber("tx", Limelight.getTx());
    //     if(Limelight.getarea()>0){
    //         SmartDashboard.putBoolean("findthething",true);
    //     }else{
    //         SmartDashboard.putBoolean("findthething",false);
    //     }
    //     SmartDashboard.putNumber("dis_program", Limelight.getdistances());
    // }
}
