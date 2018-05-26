package org.ljrobotics.frc2018.paths;

import java.util.ArrayList;

import org.ljrobotics.lib.util.control.Path;
import org.ljrobotics.lib.util.control.PathBuilder;
import org.ljrobotics.lib.util.control.PathBuilder.Waypoint;
import org.ljrobotics.lib.util.control.PathContainer;
import org.ljrobotics.lib.util.math.RigidTransform2d;
import org.ljrobotics.lib.util.math.Rotation2d;
import org.ljrobotics.lib.util.math.Translation2d;

public class RightRightSwitchRightScalePart2 implements PathContainer {

    @Override
    public Path buildPath() {
        ArrayList<Waypoint> sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(325,50,0,60));
        sWaypoints.add(new Waypoint(250,70,10,60));
        sWaypoints.add(new Waypoint(190,120,0,60));


        return PathBuilder.buildPathFromWaypoints(sWaypoints);
    }

    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(325, 50), Rotation2d.fromDegrees(-210));
    }

    @Override
    public boolean isReversed() {
        return false;
    }
    
	@Override
	public boolean isInverted() {
		return false;
	}
	
}