package org.ljrobotics.frc2018.commands; 
import org.ljrobotics.frc2018.Constants;
import org.ljrobotics.frc2018.OI;
import org.ljrobotics.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeJoystick extends Command {

	Joystick joystick;

	public IntakeJoystick() {
		this.requires(Intake.getInstance());
		this.joystick = OI.getInstance().stick2;
	}

	protected void initialize() {

	}

	protected void execute() {
		double leftSpeed = -this.joystick.getRawAxis(1) * Constants.SUCK_SPEED;
		double rightSpeed = -this.joystick.getRawAxis(5) * Constants.SUCK_SPEED;
		SmartDashboard.putNumber("leftIntakeSpeed",leftSpeed);
		SmartDashboard.putNumber("rightIntakeSpeed",rightSpeed);
		Intake.getInstance().setWantedSpeed(leftSpeed,rightSpeed);
	}

	protected void end() {
	}

	protected void interrupted() {
	}

	protected boolean isFinished() {
		return false;
	}
}
