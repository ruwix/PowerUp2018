package org.ljrobotics.frc2018.commands;

import org.ljrobotics.frc2018.subsystems.Drive;
import org.ljrobotics.frc2018.subsystems.Intake;
import org.ljrobotics.lib.util.DriveSignal;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeAlign extends Command {

	Joystick joystick;

	public IntakeAlign() {
		this.requires(Intake.getInstance());
	}

	
	@Override
	protected void initialize() {
		System.out.println("Is aligning");
		Intake.getInstance().setWantedState(Intake.IntakeControlState.Align);
		
	}
	
	@Override
	protected boolean isFinished() {
		return Intake.getInstance().isAligned();
	}
	
	@Override
	protected void end() {
		Intake.getInstance().setWantedState(Intake.IntakeControlState.Idle);
	}
	
	@Override
	protected void execute() {
	}

}
