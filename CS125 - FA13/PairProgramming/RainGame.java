//UIUC CS125 FALL 2013 MP. File: RainGame.java, CS125 Project: PairProgramming, Version: 2013-10-01T10:59:40-0500.935843434
/**
 * @author btsulli2, dwoskin2
 * @Review ejricht2, bjgoodm2
 */
public class RainGame {

	public static void main(String[] args) {
		// To get points type your netids above (CORRECTLY-Please double check your partner correctly spells your netid correctly!!)
		// Your netid is the unique part of your @illinois email address
		// Do not put your name or your UIN. 
		// REMEMBER TO COMMIT this file...
	
		int x=0, y=0, dx=0, dy=2, scoreShown = 0, scoreTemp = 0, scoreHighest = 0, level = 1, levelText = 10;
		boolean levelChanged = false, missedText = false, ready = false; //, input = false;
		String text = "";
		long startTime = System.currentTimeMillis();
		
		Zen.setFont("Helvetica-24");
		while (Zen.isRunning()){
			while (!ready){
				Zen.drawText("Level? (1-9, then click Enter)", Zen.getZenWidth() / 5, Zen.getZenHeight() / 2);
				String userLevel = Zen.getEditText();
				if(userLevel.length() != 0)
				{
					level = (int)(userLevel.charAt(0)) - 48; 
					levelText = (int) (Math.pow(10,level));
					ready = true;
					scoreShown --;
					scoreTemp --;
					dy = dy + level - 1;
				}
				Zen.setEditText("");
			}
			
		while (ready) {
			if (text.length() == 0 && !missedText) {
				x = Zen.getZenWidth() / 2;
				y = 0;
				dx = 0;
				text = "" + (int) (Math.random() * levelText - 1);
				long elapsed = System.currentTimeMillis() - startTime;
				startTime = System.currentTimeMillis();
				scoreShown += 3000 / elapsed;
				scoreTemp += 3000 / elapsed;
			}
			else if(text.length() == 0 && missedText)
			{
				x = Zen.getZenWidth() / 2;
				y = 0;
				dx = 0;
				text = "" + (int) (Math.random() * levelText - 1);
				long elapsed = System.currentTimeMillis() - startTime;
				startTime = System.currentTimeMillis();
				missedText = false;
				scoreShown -= 5;
				scoreTemp -= 5;
			}
			
			if(scoreTemp >= 10)
			{
				level++;
				levelText *= 10;
				scoreTemp = 0;
				levelChanged = true;
			}
			
			if(levelChanged)
			{
				levelChanged = false;
				dy = dy + level - 1;
			}
			
			Zen.setColor(0, 0, 0);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());

			Zen.setColor(0, 255, 0);
			Zen.drawText(text, x, y);
			
			Zen.drawText("Level:" + level,10,60);
			Zen.drawText("Score:" + scoreShown,10,120);
			
			if(y + dy > Zen.getZenHeight())
			{
				text = "";
				missedText = true;
			}
			else
			{
				x += dx;
				y += dy;
			}
			
			// Find out what keys the user has been pressing.
			String user;
			//while (!input){
				user = Zen.getEditText();
				//input = true;
				// Reset the keyboard input to an empty string
				// So next iteration we will only get the most recently pressed keys.
				Zen.setEditText("");
				
				for(int i=0; user != null && i < user.length();i++) {
					char c = user.charAt(i);
					if(c == text.charAt(0))
					{
						text = text.substring(1,text.length()); // all except first character
						//input = false;
					}
					else
					{
						scoreShown -= 1;
						scoreTemp -= 1;
						//input = false;
					}
				}
			//}
			if (scoreShown > scoreHighest)
				scoreHighest = scoreShown;
			if (scoreShown < 0){
				Zen.setFont("Helvetica-36");
				Zen.setColor(0, 0, 0);
				Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
				Zen.setColor(0, 255, 0);
				Zen.drawText("GAME OVER", Zen.getZenWidth() / 3, Zen.getZenHeight() / 2);
				Zen.drawText("Score: " + scoreHighest, Zen.getZenWidth() / 3, Zen.getZenHeight() -200);
				ready = false;}
			
			Zen.flipBuffer();
			Zen.sleep(90);// sleep for 90 milliseconds

		}
	}

}
}
