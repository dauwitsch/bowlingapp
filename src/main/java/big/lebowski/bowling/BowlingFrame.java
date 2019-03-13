package big.lebowski.bowling;

public class BowlingFrame {

	private int firstRollNmbOfPins = 0;
	private int secondRollNmbOfPins = 0;
	private int thirdRollNmbOfPins = 0;
	private int frameScore;
	private boolean isLastFrame = false;
	
	public BowlingFrame(boolean isLastFrame, int firstRoll, int secRoll) {
		this.firstRollNmbOfPins = firstRoll;
		this.secondRollNmbOfPins = secRoll;
		this.isLastFrame = isLastFrame;
	}
	
	public int getFrameScore() {
		return frameScore;
	}

	public void setFrameScore(int frameScore) {
		this.frameScore = frameScore;
	}

	public int getFirstRollNmbOfPins() {
		return firstRollNmbOfPins;
	}

	public void setFirstRollNmbOfPins(int firstRollNmbOfPins) {
		this.firstRollNmbOfPins = firstRollNmbOfPins;
	}

	public int getSecondRollNmbOfPins() {
		return secondRollNmbOfPins;
	}

	public void setSecondRollNmbOfPins(int secondRollNmbOfPins) {
		this.secondRollNmbOfPins = secondRollNmbOfPins;
	}

	public int getThirdRollNmbOfPins() {
		return thirdRollNmbOfPins;
	}

	public void setThirdRollNmbOfPins(int thirdRollNmbOfPins) {
		this.thirdRollNmbOfPins = thirdRollNmbOfPins;
	}

	public boolean hasStrike() {
		return firstRollNmbOfPins == 10 ? true : false;
	}
	
	public boolean hasSpare() {
		int rollSum = firstRollNmbOfPins + secondRollNmbOfPins;
		return rollSum == 10 ? true : false;
	}

	public boolean isLastFrame() {
		return isLastFrame;
	}

	public void setLastFrame(boolean isLastFrame) {
		this.isLastFrame = isLastFrame;
	}
}
