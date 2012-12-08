package com.example.musicstrike;

public class Level {
	
	public int levelMax;
	public int actualLevel;
	public int roundTime; // Miliseconds
	public int backgroundMusic;
	
	private int counter;
	
	/*
	 * Tempo	Miliseconds		Level
	 * 
	 *  100		   2400 		  1
	 *  105		   2286			  2
	 *  110		   2182			  3
	 *  115		   2087			  4
	 *  120		   2000			  5
	 *  125		   1920			  6
	 *  130		   1846			  7
	 *  135		   1778			  8
	 *  140		   1714			  9
	 * 
	*/
	
	public Level()
	{
		levelMax = 7;
		actualLevel = 1;
		backgroundMusic = R.raw.tempo100;
		roundTime = 2400;
		counter = 0;
	}
	
	public void IncreaseCounter()
	{
		counter++;		
		if (counter%10 == 0)
			IncreaseLevel();
	}
	
	public void IncreaseLevel()
	{
		if (actualLevel <= levelMax)
		{
			actualLevel++;
			changeBackgroundMusicAndRoundTime();
		}
	}
	
	public void changeBackgroundMusicAndRoundTime()
	{	
		if (actualLevel == 2)
		{
			backgroundMusic = R.raw.tempo105;
			roundTime = 2286;
		}
		else if (actualLevel == 3)
		{
			backgroundMusic = R.raw.tempo110;
			roundTime = 2182;
		}
		else if (actualLevel == 4)
		{
			backgroundMusic = R.raw.tempo115;
			roundTime = 2087;
		}
		else if (actualLevel == 5)
		{
			backgroundMusic = R.raw.tempo120;
			roundTime = 2000;
		}
		else if (actualLevel == 6)
		{
			backgroundMusic = R.raw.tempo125;
			roundTime = 1920;
		}
		else if (actualLevel == 7)
		{
			backgroundMusic = R.raw.tempo130;
			roundTime = 1846;
		}
		
	}

}
