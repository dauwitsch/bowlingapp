package big.lebowski.bowling;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import big.lebowski.bowling.data.BowlingFrame;
import big.lebowski.bowling.service.BowlingService;
import big.lebowski.bowling.view.BowlingView;

@SpringBootApplication
public class BowlingApplication {
	
	public static void main(String[] args) {
		
		List<BowlingFrame> bowlingFrameList = new LinkedList<>();
	
		// Reading score for all frames from console
		BowlingView.setFrameScore(bowlingFrameList);
		// Calculating the frame score including bonus score 
		BowlingService.calcFrameScore(bowlingFrameList); 
		// Write the game score and print formatted table on console
		BowlingView.writeGameScoreResult(bowlingFrameList);
	}
}
