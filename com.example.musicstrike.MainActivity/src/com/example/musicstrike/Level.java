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
	 *  120		   2000			  1
	 *  125		   1920			  2
	 *  130		   1846			  3
	 *  135		   1778			  4
	 *  140		   1714			  5
	 * 
	*/
	
	public Level()
	{
		levelMax = 2;
		actualLevel = 1;
		backgroundMusic = R.raw.compastempo120;
		roundTime = 2000;
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
			changeBackgroundMusic();
		}
	}
	
	public void changeBackgroundMusic()
	{	
		if (actualLevel == 2)
		{
			backgroundMusic = R.raw.compastempo125d;
			roundTime = 1920;
		}
		else if (actualLevel == 3)
		{
			//backgroundMusic = R.raw.compastempo130;
			roundTime = 1846;
		}
		else if (actualLevel == 4)
		{
			//backgroundMusic = R.raw.compastempo135;
			roundTime = 1778;
		}
		else if (actualLevel == 5)
		{
			//backgroundMusic = R.raw.compastempo140;
			roundTime = 1714;
		}
		
	}

}
