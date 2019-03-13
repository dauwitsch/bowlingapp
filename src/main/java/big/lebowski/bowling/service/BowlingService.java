package big.lebowski.bowling.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import big.lebowski.bowling.BowlingFrame;

public class BowlingService {
	
	private static final Logger logger = LogManager.getLogger(BowlingService.class);
	
	/**
	 * Calculates the frame score including the bonus score for strikes and spares
	 * @param frameList
	 */
	public static void calcFrameScore(List<BowlingFrame> frameList) {
		
		logger.info("Start caculating frame-score...");
		
		frameList.stream().forEach(frame-> {
			
			int basicScore = frame.getFirstRollNmbOfPins() + frame.getSecondRollNmbOfPins();
			
			if(frame.hasStrike() || frame.hasSpare()) {
				
				int indexOfFrame = frameList.indexOf(frame);
				
				if(frame.isLastFrame()) {
					
					frame.setFrameScore(basicScore + frame.getThirdRollNmbOfPins());
					
				}else {
					
					BowlingFrame nextFrame = frameList.get(++indexOfFrame);
					frame.setFrameScore(basicScore
							+ nextFrame.getFirstRollNmbOfPins() 	
							+ (frame.hasStrike() ? nextFrame.getSecondRollNmbOfPins() : 0));
				}	
			}else {
				frame.setFrameScore(basicScore);
			}
		});
	}
}
