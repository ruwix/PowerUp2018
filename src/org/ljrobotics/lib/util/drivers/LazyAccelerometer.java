package org.ljrobotics.lib.util.drivers;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

/**
 * This class is a thin wrapper around the builtin accelerometer
 * 
 * @author Grant
 */
public class LazyAccelerometer {
	private static LazyAccelerometer instance;
	private BuiltInAccelerometer accel;

	public static LazyAccelerometer getInstance() {
		if (instance == null) {
			instance = new LazyAccelerometer();
		}
		return instance;
	}

	private LazyAccelerometer() {
		accel = new BuiltInAccelerometer();
	}

	public double getRoll() {
		double radians = Math.atan2(-getX(), Math.sqrt(Math.pow(getY(), 2) + Math.pow(getZ(), 2)));
		return Math.toDegrees(radians);
	}

	public double getPitch() {
		double radians = Math.atan2(getZ(), getX());
		return Math.toDegrees(radians);
	}
	public double getX() {
		return accel.getX();
	}
	public double getY() {
		return accel.getY();
	}
	public double getZ() {
		return accel.getZ();
	}
}
