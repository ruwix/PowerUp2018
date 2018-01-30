package org.ljrobotics.frc2018.commands;

import org.ljrobotics.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeAlign extends Command {

	Joystick joystick;

	public IntakeAlign() {
		this.requires(Intake.getInstance());
	}

	@Override
	protected void initialize() {
		Intake.getInstance().setWantedState(Intake.IntakeControlState.Align);

	}

	@Override
	protected boolean isFinished() {

		return Intake.getInstance().isAligned();
	}

	@Override
	protected void end() {
		// System.out.println("done aligning");
		// IntakeSuck suck = new IntakeSuck();
		Intake.getInstance().setWantedState(Intake.IntakeControlState.Idle);
	}

	@Override
	protected void interrupted() {
		Intake.getInstance().setWantedState(Intake.IntakeControlState.Idle);
	}

	@Override
	protected void execute() {
		System.out.println("aligning");
	}

}
