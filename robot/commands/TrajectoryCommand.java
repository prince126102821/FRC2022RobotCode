/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.trajectory.TrajectoryFactory;
import frc.robot.subsystems.trajectory.TrajectorySystem;
/**
 * 把複雜的建構式包裝成簡單的類別，需要搭配實現了TrajectorySystem的機器人
 */
public class TrajectoryCommand extends SequentialCommandGroup{
  public static enum OutputMode{
    SPEED,
    VOLTAGE
  }

  private TrajectoryCommand(){}

  public static SequentialCommandGroup build(Trajectory trajectory, TrajectorySystem drivetrain, 
                                                  OutputMode mode, Subsystem... base){
    if(mode == OutputMode.SPEED){
      return new SequentialCommandGroup( 
        new InstantCommand(()->TrajectoryFactory.initPose(drivetrain, trajectory)),
        new RamseteCommand(
            trajectory, 
            drivetrain::getPose, 
            new RamseteController(), 
            drivetrain.getKinematics(), 
            drivetrain::setOutput, 
            base),
        new InstantCommand(()->drivetrain.setOutput(0, 0), base)
          );
    }else {
      return new SequentialCommandGroup( 
        new InstantCommand(()->TrajectoryFactory.initPose(drivetrain, trajectory)),
        new RamseteCommand(
            trajectory, 
            drivetrain::getPose, 
            new RamseteController(), 
            drivetrain.getFeedforward(),
            drivetrain.getKinematics(), 
            drivetrain::getSpeed,
            drivetrain.getLeftPidController(),
            drivetrain.getRightPidController(),
            drivetrain::voltage, 
            base),
            new InstantCommand(()->drivetrain.voltage(0.0, 0.0), base)
          );
    }
  }
//System

}
