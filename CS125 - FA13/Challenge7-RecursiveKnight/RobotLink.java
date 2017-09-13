//UIUC CS125 FALL 2013 MP. File: RobotLink.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2013-11-12T09:42:21-0600.264411210
/**
 * 
 * @author Dwoskin2
 *
 */
public class RobotLink {

	private RobotLink next; 	
	private final Robot robot;

	public Robot getRobot() {
		return robot;
	}
	/** Constructs this link.
	 * @param next ; the next item in the chain (null, if there are no more items).
	 * @param robot ; a single robot (never null).
	 */
	public RobotLink(RobotLink next,Robot robot) {
		this.robot = robot;
		this.next = next;
	}

	/**
	 * Returns the number of entries in the linked list.
	 * @return number of entries.
	 */
	public int count() {
		if (next == null){return 1;} //base case
		return 1 + next.count(); //recursive case
	}
	/**
	 * Counts the number of flying robots in the linked list.
	 * Hint: robot.isFlying is useful here.
	 */
	public int countFlyingRobots() {
		int count = 0;
        if (next == null && robot.isFlying()){return 1 + count;}
        if (next == null) {return count;}
        if (next != null && robot.isFlying()){count++;}
        return count + next.countFlyingRobots();
	}
	/**
	 * Counts the number of flying robots up to and excluding a sad robot.
	 * (i.e. do not count the sad robot if it is flying).
	 * If there are no sad robots this function will return the same value as countFlyingRobots().
	 * Hint: robot.isHappy() is useful.
	 */
	public int countFlyingRobotsBeforeSadRobot() {
		int count = 0;
		if (next == null && robot.isFlying()){return 1 + count;}
		else if (next == null){return count;}
		else {
			if (robot.isFlying() && next.getRobot().isHappy())
				return count + 1 +next.countFlyingRobotsBeforeSadRobot();
			if (robot.isFlying() && next.robot.isHappy() == false)
				return count + 1;
			return count + next.countFlyingRobotsBeforeSadRobot();
		}
	}
	/** Creates a new LinkedList entry at the end of this linked list.
	 * Recursively finds the last entry then adds a new link to the end.
	 * @param robot - the robot value of the new last link
	 */
	public void append(Robot robot) {
		if(next == null)
			next = new RobotLink(next,robot);
		else
			next.append(robot);
	}
	/**
	 * Returns the first flying unhappy robot, or null
	 * if there are not robots that are flying and unhappy.
	 * @return
	 */
	public Robot getFirstFlyingUnhappyRobot() {
		if (robot.isFlying() && !robot.isHappy()){return robot;}
		else if (next == null) {return null;}
		return next.getFirstFlyingUnhappyRobot();
	}
	/**
	 * Returns the last flying unhappy robot in the linked list, or null
	 * if there are not robots that are flying and unhappy.
	 * @return
	 */
	public Robot getLastFlyingUnhappyRobot() {
		if (next == null && robot.isFlying() == true && robot.isHappy() == false){return robot;}
		if (next == null && (!robot.isFlying() || robot.isHappy())){return null;}
		if (robot.isFlying() && !robot.isHappy() && next.getFirstFlyingUnhappyRobot() == null) return robot;
		return next.getLastFlyingUnhappyRobot();
	}
	/**
	 * Returns a reference to the happy most distant explorer.
	 * Returns null if there are no happy robots
	 * @return reference to the most distant happy robot
	 */
	public Robot findHappyRobotFurthestFromHome() {
		if (next == null && !robot.isHappy()){return null;}
		if (robot.isHappy())
			if (next.robot.getDistanceFromHome() > robot.getDistanceFromHome()) {return next.findHappyRobotFurthestFromHome();}
		return robot;
	}
	/**
	 * Returns true if linked list contains the robot.
	 * Hint: Use robot.equals(other).
	 * @param robot
	 * @return
	 */
	public boolean contains(Robot other) {
		if (robot.equals(other)) {return true;}
		if (next == null && !robot.equals(other)) {return false;}
		else return next.contains(other);
	}

	
}
