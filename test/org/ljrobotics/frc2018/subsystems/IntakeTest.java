package org.ljrobotics.frc2018.subsystems;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.ljrobotics.lib.util.DummyReporter;
import org.mockito.ArgumentCaptor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.HLUsageReporting;

public class IntakeTest {

	private Intake intake;
	private TalonSRX left;
	private TalonSRX right;
	private TalonSRX tension;
	private AnalogInput leftDistnace;
	private AnalogInput rightDistance;

	static {
		// prevents exception during test
		HLUsageReporting.SetImplementation(new DummyReporter());
	}

	@Before
	public void before() {
		left = mock(TalonSRX.class);
		right = mock(TalonSRX.class);
		tension = mock(TalonSRX.class);

		leftDistnace = mock(AnalogInput.class);
		rightDistance = mock(AnalogInput.class);

		intake = new Intake(left, right, tension, leftDistnace, rightDistance);
	}

	private void verifyTalons(ControlMode mode, double frontLeft, double frontRight, int timesCalled) {
		final ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
		verify(this.left, times(timesCalled)).set(eq(mode), captor.capture());
		List<Double> captures = captor.getAllValues();
		assertEquals(frontLeft, (double) captures.get(captures.size() - 1), 0.00001);

		verify(this.right, times(timesCalled)).set(eq(mode), captor.capture());
		captures = captor.getAllValues();
		assertEquals(frontRight, (double) captures.get(captures.size() - 1), 0.00001);
	}

}
