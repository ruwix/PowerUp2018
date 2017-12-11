package org.ljrobotics.frc2018.commands;

import org.ljrobotics.frc2018.subsystems.Drive;
import org.ljrobotics.frc2018.utils.Motion;
import org.ljrobotics.frc2018.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickDrive extends Command {

	Joystick joystick;
	double multiplier;

	public JoystickDrive() {
		this.requires(Drive.getInstance());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		this.joystick = new Joystick(Constants.JOYSTICK_DRIVE_ID);
		this.multiplier=1;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double y = -this.joystick.getRawAxis(1)*this.multiplier;
		double rotation = this.joystick.getRawAxis(2)*this.multiplier;
		Drive.getInstance().move(new Motion(y,rotation));
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	protected boolean isFinished(){
		return false;
	}

}
