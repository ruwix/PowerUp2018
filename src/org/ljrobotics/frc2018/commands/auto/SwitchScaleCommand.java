package org.ljrobotics.frc2018.commands.auto;

import org.ljrobotics.frc2018.commands.ArmSetpoint;
import org.ljrobotics.frc2018.commands.DriveForward;
import org.ljrobotics.frc2018.commands.FollowPath;
import org.ljrobotics.frc2018.commands.IntakeIdle;
import org.ljrobotics.frc2018.commands.IntakeSpitSlow;
import org.ljrobotics.frc2018.commands.IntakeSuck;
import org.ljrobotics.frc2018.commands.ResetToPathHead;
import org.ljrobotics.frc2018.commands.TurnToAngle;
import org.ljrobotics.frc2018.commands.WaitSecond;
import org.ljrobotics.frc2018.commands.ZeroArm;
import org.ljrobotics.frc2018.paths.RightRightSwitchRightScalePart2;
import org.ljrobotics.frc2018.subsystems.Arm;
import org.ljrobotics.lib.util.control.PathContainer;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchScaleCommand extends CommandGroup{

	public SwitchScaleCommand(PathContainer path) {
		PathContainer path2 =new RightRightSwitchRightScalePart2();
		this.addParallel(new ZeroArm());
		this.addSequential(new ResetToPathHead(path));
//		this.addSequential(new ArmSetpoint(Arm.ArmPosition.INTAKE));
		this.addSequential(new ArmSetpoint(Arm.ArmPosition.STOWED));
		this.addSequential(new WaitSecond(300));
		this.addSequential(new FollowPath(path));
//		this.addSequential(new DriveForward(0.2, 2));
		this.addSequential(new TurnToAngle(-90));
		this.addSequential(new ArmSetpoint(Arm.ArmPosition.SCALE));
		this.addSequential(new WaitSecond(2000));
		this.addSequential(new IntakeSpitSlow());
		this.addSequential(new WaitSecond(1000));
		this.addSequential(new ArmSetpoint(Arm.ArmPosition.INTAKE));
		this.addSequential(new IntakeSuck());
		this.addSequential(new TurnToAngle(-210));
		this.addSequential(new WaitSecond(300));

		this.addSequential(new ResetToPathHead(path));
		this.addSequential(new FollowPath(path2));



//		this.addSequential(new IntakeSuck());
		
//		this.addSequential(new DriveForward(-0.3, 1));
//		this.addSequential(new ArmSetpoint(Arm.ArmPosition.INTAKE));
//		this.addSequential(new TurnToAngle(-30));
//		this.addSequential(new WaitSecond(100));
//		this.addSequential(new ResetToPathHead(path2));
//		this.addSequential(new FollowPath(path2));	

	}
	
}
