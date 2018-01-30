package org.ljrobotics.frc2018.commands;

import org.ljrobotics.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GoodSucker extends CommandGroup {

	public GoodSucker() {
		this.addSequential(new IntakeAlign());
		this.addSequential(new IntakeSuck());
	}

	@Override
	public void interrupted() {
		Intake.getInstance().setWantedState(Intake.IntakeControlState.Idle);

	}

}
