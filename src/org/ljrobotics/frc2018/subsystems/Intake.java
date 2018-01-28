package org.ljrobotics.frc2018.subsystems;

import org.ljrobotics.frc2018.Constants;
import org.ljrobotics.frc2018.commands.IntakeAlign;
import org.ljrobotics.frc2018.commands.IntakeIdle;
import org.ljrobotics.frc2018.commands.IntakeJoystick;

import org.ljrobotics.frc2018.loops.Loop;
import org.ljrobotics.frc2018.loops.Looper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem implements LoopingSubsystem {

	private static Intake instance;

	public static Intake getInstance() {
		if (instance == null) {
			TalonSRX left = new TalonSRX(Constants.LEFT_INTAKE_MOTOR_ID);
			TalonSRX right = new TalonSRX(Constants.RIGHT_INTAKE_MOTOR_ID);
			TalonSRX tension = new TalonSRX(Constants.TENSION_INTAKE_MOTOR_ID);
			AnalogInput leftDistnace = new AnalogInput(0);
			AnalogInput rightDistance = new AnalogInput(1);
			instance = new Intake(left, right, tension, leftDistnace, rightDistance);
		}
		return instance;
	}

	private TalonSRX left;
	private TalonSRX right;

	private TalonSRX tension;

	private AnalogInput leftDistance;
	private AnalogInput rightDistance;

	private IntakeControlState controlState;

	private double overCurrentProtectionTimeStart;

	private double wantedLeftSpeed;
	private double wantedRightSpeed;
	private double wantedTensionPower;

	public static enum IntakeControlState {
		Suck, // Pull in the Cube
		Spit, // Spit out the Cube
		Align, // Align a crooked cube
		Idle // Do Nothing

	}

	private class IntakeLoop implements Loop {

		@Override
		public void onStart(double timestamp) {

		}

		@Override
		public void onLoop(double timestamp) {
			switch (controlState) {
			case Suck:
				setSpeed(Constants.SUCK_SPEED, -Constants.SUCK_SPEED);
				break;
			case Spit:
				setSpeed(Constants.SPIT_SPEED, -Constants.SPIT_SPEED);
				break;
			case Align:
				// setSpeed(0, 0);
				setAlignMotors();
				break;
			case Idle:
				 setSpeed(0, 0);
//				updateSpeed();
				break;
			default:
				break;
			}
			updateTensionMotor();

		}

		@Override
		public void onStop(double timestamp) {

		}

	}

	public Intake(TalonSRX left, TalonSRX right, TalonSRX tension, AnalogInput leftDistance,
			AnalogInput rightDistance) {
		this.leftDistance = leftDistance;
		this.rightDistance = rightDistance;
		this.left = left;
		this.right = right;
		this.tension = tension;

		setCurrentLimit(tension, 20, 10, 500);
		setCurrentLimit(left, Constants.MAX_SUCK_CURRENT, Constants.NOMINAL_SUCK_CURRENT,
				Constants.MAX_SUCK_CURRENT_TIME);
		setCurrentLimit(right, Constants.MAX_SUCK_CURRENT, Constants.NOMINAL_SUCK_CURRENT,
				Constants.MAX_SUCK_CURRENT_TIME);

		this.left.setInverted(false);
		this.right.setInverted(true);

		this.left.configOpenloopRamp(0.125, 0);
		this.right.configOpenloopRamp(0.125, 0);

		this.controlState = IntakeControlState.Idle;

		this.wantedTensionPower = 0;
	}

	private void setCurrentLimit(TalonSRX talon, int max, int nominal, int time) {
		talon.configPeakCurrentDuration(time, 0);
		talon.enableCurrentLimit(true);
		talon.configContinuousCurrentLimit(nominal, 0);
		talon.configPeakCurrentLimit(max, 0);
	}

	@Override
	public void stop() {
		this.setSpeed(0, 0);
	}

	@Override
	public void reset() {

	}

	public void updateSpeed() {
		this.left.set(ControlMode.PercentOutput, this.wantedLeftSpeed);
		this.right.set(ControlMode.PercentOutput, this.wantedRightSpeed);
	}

	public void updateTensionMotor() {
		this.tension.set(ControlMode.PercentOutput, this.wantedTensionPower);
	}

	private void setSpeed(double leftSpeed, double rightSpeed) {
		this.left.set(ControlMode.PercentOutput, leftSpeed);
		this.right.set(ControlMode.PercentOutput, rightSpeed);
	}

	public void setTensionPower(double power) {
		this.wantedTensionPower = power;
	}

	@Override
	public void outputToSmartDashboard() {
		SmartDashboard.putNumber("Intake Motor Current Right", this.right.getOutputCurrent());
		SmartDashboard.putNumber("Intake Motor Current Left", this.left.getOutputCurrent());
		// SmartDashboard.putNumber("boxAngle", getBoxAngle());
	}

	@Override
	public void writeToLog() {

	}

	@Override
	public void zeroSensors() {

	}

	public void setWantedState(IntakeControlState state) {
		this.controlState = state;
		// System.out.println(state);
	}

	public void setWantedSpeed(double leftSpeed, double rightSpeed) {
		this.wantedLeftSpeed = leftSpeed;
		this.wantedRightSpeed = rightSpeed;
	}

	private double getSensorDistance(double voltage) {
		return 27.86 * Math.pow(voltage, -1.15);
	}

	// public double getBoxAngle() {
	// double leftDistance = getSensorDistance(this.leftDistance.getVoltage());
	// double rightDistance = getSensorDistance(this.rightDistance.getVoltage());
	// SmartDashboard.putNumber("leftDistance", leftDistance);
	// SmartDashboard.putNumber("rightDistance", rightDistance);
	//
	// double slope = (leftDistance - rightDistance /
	// Constants.DISTANCE_BETWEEN_INTAKE_SENSORS);
	// double angle = Math.atan(this.leftDistance.getVoltage() -
	// this.rightDistance.getVoltage());
	// angle = Math.toDegrees(angle);
	// return angle;
	// }
	public boolean isAligned() {
		double threshold = 0.3;

		double leftDistance = this.leftDistance.getVoltage();
		double rightDistance = this.rightDistance.getVoltage();
		double distanceDifference = leftDistance - rightDistance;

		SmartDashboard.putNumber("leftDistance", leftDistance);
		SmartDashboard.putNumber("rightDistance", rightDistance);
		SmartDashboard.putNumber("distanceDifference", leftDistance - rightDistance);

		return Math.abs(distanceDifference) < threshold;

	}

	private void setAlignMotors() {
		double multiplier = 0.2;
		double speed = Constants.SUCK_SPEED * multiplier;
		Intake.getInstance().setWantedSpeed(speed, speed);
	}

	public void intakeDecisions() {
		DigitalInput front_beam = new DigitalInput((int) Math.random() * 20);
		DigitalInput back_beam = new DigitalInput((int) Math.random() * 20);
		if (!front_beam.get() && !back_beam.get()) { // No box in, go find one
			setSpeed(0, 0);
		} else if (!front_beam.get() && back_beam.get()) { // Impossible
			System.out.println("ERROR! YOU ARE THE WEAKEST LINK!");
		} else if (front_beam.get() && !back_beam.get()) { // The box is going in
			setSpeed(Constants.SUCK_SPEED, -Constants.SUCK_SPEED);
		} else if (front_beam.get() && back_beam.get()) { // You're great
			setSpeed(0, 0);
		}
		return;
	}

	@Override
	public void registerEnabledLoops(Looper enabledLooper) {
		enabledLooper.register(new IntakeLoop());
	}

	@Override
	protected void initDefaultCommand() {
		// this.setDefaultCommand(new IntakeIdle());
	}

}
