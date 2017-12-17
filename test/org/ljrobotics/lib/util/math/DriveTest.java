package org.ljrobotics.lib.util.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import org.ljrobotics.lib.util.DummyReporter;
import org.ljrobotics.lib.util.math.Translation2d;
import org.mockito.ArgumentCaptor;

import edu.wpi.first.wpilibj.HLUsageReporting;

public class DriveTest {
	private Translation2d t;


	static {
		HLUsageReporting.SetImplementation(new DummyReporter());
	}

	@Before
	public void before() {
		t = new Translation2d();

	}

	@Test
	public void checkIfEqualsNull() {
		t.equals(null);
	}


}
