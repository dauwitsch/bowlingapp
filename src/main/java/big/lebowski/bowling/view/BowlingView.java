package big.lebowski.bowling.view;

import java.io.Console;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import big.lebowski.bowling.BowlingFrame;
import big.lebowski.bowling.util.CommandLineTable;

public class BowlingView {
	
	public final static int TOTAL_NUMB_OF_FRAMES = 10;
	private final static int MAX_ROLL_SCORE = 10;
	private final static int MIN_ROLL_SCORE = 0;
	private static Console console = System.console();	
	
	private static final Logger logger = LogManager.getLogger(BowlingView.class);
	 
	/**
	 * Read in frame score from console 
	 * @param bowlingFrameList
	 */
	public static void setFrameScore(List<BowlingFrame> bowlingFrameList) {
		
		logger.info("Start reading frame score from console... {}");
		
		int i = 1;
		int firstRollNmbOfPins = 0;
		int secondRollNmbOfPins = 0;
		int thirdRollNmbOfPins = 0;
		
		System.out.println("#####################################################################");
		System.out.println("########## Welcome to the Big Lebowski Bowling Calculator  ##########");
		System.out.println("#####################################################################");
		
		do {
			
			firstRollNmbOfPins = parseScoreInput(i, 1, null);
			
			if(firstRollNmbOfPins != MAX_ROLL_SCORE) {
				secondRollNmbOfPins = parseScoreInput(i, 2, (MAX_ROLL_SCORE - firstRollNmbOfPins));	
			}
			
			BowlingFrame tmpFrame = new BowlingFrame((i == (TOTAL_NUMB_OF_FRAMES)) ? true : false,firstRollNmbOfPins, secondRollNmbOfPins);
			
			bowlingFrameList.add(tmpFrame);
			
			if((tmpFrame.hasStrike()) && (i == TOTAL_NUMB_OF_FRAMES)) {
				
				secondRollNmbOfPins = parseScoreInput(i, 2, null);
				tmpFrame.setSecondRollNmbOfPins(secondRollNmbOfPins);
				
				thirdRollNmbOfPins = parseScoreInput(i, 3, null);
				tmpFrame.setThirdRollNmbOfPins(thirdRollNmbOfPins);
			}
			
			if((tmpFrame.hasSpare()) && (i == TOTAL_NUMB_OF_FRAMES)) {
				
				thirdRollNmbOfPins = parseScoreInput(i, 3, null);
				tmpFrame.setThirdRollNmbOfPins(thirdRollNmbOfPins);
			}
			
			firstRollNmbOfPins = 0;
			secondRollNmbOfPins = 0;
			++i;
		
		}while(i <= TOTAL_NUMB_OF_FRAMES);
	}
	
	/**
	 * Validate if console input for roll-score is valid
	 * @param rollScore
	 * @param maxRollScore
	 * @return
	 */
	private static boolean isValidRollScore(int rollScore, Integer maxRollScore){
		if((rollScore < MIN_ROLL_SCORE)|| (rollScore > ((maxRollScore != null) ? maxRollScore :MAX_ROLL_SCORE))) {
			
			StringBuffer sb = new StringBuffer();
			sb.append("Please enter a valid value(0 - ");
			sb.append(maxRollScore != null ? maxRollScore : MAX_ROLL_SCORE);
			sb.append(")!");
			
			logger.error("Invalid value: {} entered by user, maximum valid roll score is: {} ", rollScore, maxRollScore != null ? maxRollScore : MAX_ROLL_SCORE);
			System.out.println(sb.toString());
			return false;
		}
		return true;
	};
	
	/**
	 * Check if console input is valid, expects an parsable integer value
	 * @param nmbOfFrame
	 * @param nmbOfRoll
	 * @param maxRollScore
	 * @return
	 */
	private static int parseScoreInput(int nmbOfFrame, int nmbOfRoll, Integer maxRollScore) {
		
		int tmpRollNmbOfPins = 0;
		String frameNmb = String.valueOf(String.format("%02d", nmbOfFrame));
		String rollNmb = String.valueOf(String.format("%02d", nmbOfRoll));
		do {
			try {
				tmpRollNmbOfPins = Integer.parseInt(console.readLine(frameNmb + ". frame, " + rollNmb + ". roll, Enter value: "));

			}catch(NumberFormatException e) {
				logger.error("Invalid value entered by user, could not be parsed to Integer. ERROR -> {}} ", e.getMessage());
				System.out.println("Please enter a valid number!");
				tmpRollNmbOfPins = parseScoreInput(nmbOfFrame,nmbOfRoll, maxRollScore);
			}	
		}while(!isValidRollScore(tmpRollNmbOfPins, maxRollScore));
		
		return tmpRollNmbOfPins;
	}
	
	/**
	 * Print out a table to console, presenting the game-score results
	 * @param bowlingFrameList
	 */
	public static void writeGameScoreResult(List<BowlingFrame> bowlingFrameList) {
		
		 logger.info("Start generating game-score result list...");
		 CommandLineTable stdout = new CommandLineTable();
	    
		 stdout.setShowVerticalLines(true);
		 stdout.setHeaders("Frame", "1. Roll", "2. Roll", "3. Roll", "Frame score", "Game score");
		 
		 BowlingFrame frame = null;
		 int gameScore = 0;
		 for(int i = 1; i <= bowlingFrameList.size(); i++) {
			 frame = bowlingFrameList.get(i-1);
			 gameScore += frame.getFrameScore();
			 
			 String secondVal = "--";
			 if((frame.getSecondRollNmbOfPins() > 0) || 
					 ((frame.getSecondRollNmbOfPins() == 0) && (!frame.hasStrike() || frame.isLastFrame()))) {
				 
				 secondVal = String.valueOf(frame.getSecondRollNmbOfPins());
			 }
			 
			 String thirdVal = "--";
			 if((frame.isLastFrame()) && (frame.hasStrike() || frame.hasSpare())) {
				 thirdVal = String.valueOf(frame.getThirdRollNmbOfPins());
			 }
			 
			 stdout.addRow(String.valueOf(i), 
					 String.valueOf(frame.getFirstRollNmbOfPins()),
					 secondVal, 
					 thirdVal, 
					 String.valueOf(frame.getFrameScore()), 
					 String.valueOf(gameScore));
		 }
		 
		 logger.info("Print game-score result list...");
		 stdout.print();
	}
}
