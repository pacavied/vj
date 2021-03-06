package application.followandfun;

import java.util.Random;

import application.followandfun.R;

public class Behavior {

	public int behaviorType;
	public int backgroundImage;
	public int finalBackground;
	public int objectInitialSprite;
	public int objectFinalSprite;
	public int initialSound;
	public int finalSound;
	public int instructionSound;
	
	public boolean haveFinalBackground = false;
	public boolean haveObject = false;
	
	//Numero de acciones maximas
	private int randomLimit = 11;
	
	
	public Behavior(){
		
		int type = generateRandom();
		behaviorType = findBehaviorType(type);
		backgroundImage = findBackgroundImage(type);
		objectInitialSprite = findObjectInitialSprite(type);
		objectFinalSprite = findObjectFinalSprite(type);
		initialSound = findObjectInitialSound(type);
		finalSound = findObjectFinalSound(type);
		finalBackground = findFinalBackground(type);
		instructionSound = findInstructionSound(type);
		
		/*
		 * Behavior Definitions					Theme		Action
		 * 
		 * 0 -> Spider 							Normal		TAP
		 * 1 -> Overtake Left					Normal		LEFT
		 * 2 -> Ring the Bell					Normal		SHAKE
		 * 3 -> Move the Box					Normal		SCROLL
		 * 4 -> Thrown In						Football	SHAKE
		 * 5 -> Fly								Normal		TAP
		 * 6 -> Overtake Right					Normal		RIGHT
		 * 7 -> Evade Football Player Left		Football	LEFT
		 * 8 -> Evade Football Player Right		Football	RIGHT
		 * 9 -> Penalty Kick					Football	SCROLL
		 * 10 -> Free Kick						Football	SCROLL
		 * 
		*/
		
	}
	
	//Utilizado para el tutorial
	public Behavior(int type, boolean isTutorial){
		
		behaviorType = findBehaviorType(type);
		if (isTutorial)
		{
			backgroundImage = findTutorialBackgroundImage(type);
			finalBackground = findTutorialFinalBackground(type);
		}
		else
		{
			backgroundImage = findBackgroundImage(type);
			finalBackground = findTutorialFinalBackground(type);
		}
		
		objectInitialSprite = findObjectInitialSprite(type);
		objectFinalSprite = findObjectFinalSprite(type);
		initialSound = findObjectInitialSound(type);
		finalSound = findObjectFinalSound(type);
		instructionSound = findInstructionSound(type);
		
	}
	
	public int findBehaviorType(int t){
		
		/*
		 * Retornar:
		 * 0 = TAP
		 * 1 = SCROLL
		 * 2 = SHAKE
		 * 3 = LEFT
		 * 4 = RIGHT
		 */
		
		
		if(t == 0)
			return 0;
		else if (t == 1)
			return 3;
		else if (t == 2)
			return 2;
		else if (t == 3)
			return 1;
		else if (t == 4)
			return 2;
		else if(t == 5)
			return 0;			
		else if (t == 6)
			return 4;
		else if(t == 7)
			return 3;
		else if(t == 8)
			return 4;
		else if(t == 9)
			return 1;
		else if(t == 10)
			return 1;
		else 
			return 0;
		
	}
	
	public int findBackgroundImage(int t){
		
		if(t == 0)			
			return R.drawable.spiderbackground;
		else if (t == 1)
			return R.drawable.overtakeleft;
		else if (t == 2)
			return R.drawable.bell;
		else if(t == 3)
			return R.drawable.scrollbox;
		else if (t == 4)
			return R.drawable.backgroundfootballthrowin;
		else if(t == 5)
			return R.drawable.spiderbackground;
		else if (t == 6)
			return R.drawable.overtakeright;
		else if (t == 7)
			return R.drawable.backevadeleft;
		else if(t == 8)
			return R.drawable.backevaderight;
		else if(t == 9)
			return R.drawable.initialpenalty;
		else if(t == 10)
			return R.drawable.initialfreekick;
		
		else
			return 0;
		
	}
	
	public int findObjectInitialSprite(int t){
				
		if(t == 0)
		{			
			haveObject = true;
			return R.drawable.spider;			
		}
		else if(t == 1)
		{
			haveObject = false;
			return 0;
		}
		else if(t == 2)
		{
			haveObject = false;
			return 0;		
		}
		else if(t == 3)
		{
			haveObject = false;
			return 0;		
		}
		else if(t == 4)
		{
			haveObject = true;
			return R.drawable.throwininitial;
		}
		else if(t == 5)
		{			
			haveObject = true;
			return R.drawable.fly;			
		}
		else if(t == 6)
		{
			haveObject = false;
			return 0;		
		}
		else if(t == 7)
		{
			haveObject = false;
			return 0;		
		}
		else if(t == 8)
		{
			haveObject = false;
			return 0;		
		}
		else if(t == 9)
		{
			haveObject = false;
			return 0;		
		}
		else if(t == 10)
		{
			haveObject = false;
			return 0;		
		}
		
		else
			return 0;
		}
	
	public int findObjectFinalSprite(int t){
		
		if(t == 0)		
			return R.drawable.spot;
		else if(t ==1)
			return 0;
		else if (t == 2)
			return 0;
		else if (t == 3)
			return 0;
		else if(t == 4)
			return R.drawable.throwinfinal;
		else if(t == 5)		
			return R.drawable.spot;
		else if (t == 6)
			return 0;
		else if (t == 7)
			return 0;
		else if (t == 8)
			return 0;
		else if (t == 9)
			return 0;
		else if (t == 10)
			return 0;
					
		else
			return 0;
		}
	
	public int findObjectInitialSound(int t){
		
		if(t == 0)
			return R.raw.tinquetinquetinque;
		else if (t == 1)
			return R.raw.traffic;
		else if (t == 2)
			return R.raw.mosca;
		else if (t == 3)
			return R.raw.mosca;
		else if (t == 4)
			return R.raw.stadium;
		else if (t == 5)
			return R.raw.mosca;
		else if (t == 6)
			return R.raw.traffic;
		else if (t == 7)
			return R.raw.run;
		else if (t == 8)
			return R.raw.run;
		else if (t == 9)
			return R.raw.run;
		else if (t == 10)
			return R.raw.run;
					
		else
			return 0;
		}
	
	public int findObjectFinalSound(int t){
		
		if(t == 0)		
			return R.raw.slurp;
		else if(t == 1)
			return R.raw.autoarranca;
		else if(t == 2)
			return R.raw.bellsound;
		else if (t == 3)
			return R.raw.winsound;
		else if (t == 4)
			return R.raw.throwin;
		else if(t == 5)		
			return R.raw.tziuup;
		else if(t == 6)		
			return R.raw.autoarranca;
		else if(t == 7)		
			return R.raw.evadefinal;
		else if(t == 8)		
			return R.raw.evadefinal;
		else if(t == 9)		
			return R.raw.futbolpegada;
		else if(t == 10)		
			return R.raw.futbolpegada;
		else
			return 0;
		}
	
	public int findInstructionSound(int t)
	{
		if(behaviorType == 0)		
			return R.raw.tapnowsound;
		else if(behaviorType == 1)
			return R.raw.scrollnowsound;
		else if(behaviorType == 2)
			return R.raw.shakeitsound;
		else if (behaviorType == 3)
			return R.raw.moveleftsound;
		else if (behaviorType == 4)
			return R.raw.moverightsound;
		else
			return 0;
	}
	
	public int findFinalBackground(int t){
		if(t == 0)
		{
			haveFinalBackground = false;
			return 0;
		}
		else if(t == 1)
		{
			haveFinalBackground = true;
			return R.drawable.overtakeleftwin;
		}
		else if(t == 2)
		{
			haveFinalBackground = true;
			return R.drawable.bellwin2;
		}
		else if(t == 3)
		{
			haveFinalBackground = true;
			return R.drawable.scrollboxwin2;
		}
		else if(t == 4)
		{
			haveFinalBackground = false;
			return 0;
		}
		else if(t == 5)
		{
			haveFinalBackground = false;
			return 0;
		}
		else if(t == 6)
		{
			haveFinalBackground = true;
			return R.drawable.overtakerightwin;
		}
		else if(t == 7)
		{
			haveFinalBackground = true;
			return R.drawable.backevadeleftfinal;
		}
		else if(t == 8)
		{
			haveFinalBackground = true;
			return R.drawable.backevaderightfinal;
		}
		else if(t == 9)
		{
			haveFinalBackground = true;
			return R.drawable.finalpenalty;
		}
		else if(t == 10)
		{
			haveFinalBackground = true;
			return R.drawable.finalfreekick;
		}
		else
			return 0;
	}
	
	public int generateRandom(){
		Random r = new Random();
		int rnd = r.nextInt(randomLimit);
		return rnd;
	}
	
	
	//TUTORIAL
public int findTutorialBackgroundImage(int t){
		
		if(t == 0)			
			return R.drawable.tutorial_spiderbackground1;
		else if (t == 1)
			return R.drawable.tutorial_left1;
		else if (t == 2)
			return R.drawable.tutorial_bell1;
		else if(t == 3)
			return R.drawable.tutorial_scroll1;
		else if (t == 6)
			return R.drawable.tutorial_right1;		
		else
			return 0;
		
	}

public int findTutorialFinalBackground(int t){
	if(t == 0)
	{
		haveFinalBackground = true;
		return R.drawable.tutorial_spiderbackground2;
	}
	else if(t == 1)
	{
		haveFinalBackground = true;
		return R.drawable.tutorial_left2;
	}
	else if(t == 2)
	{
		haveFinalBackground = true;
		return R.drawable.tutorial_bell2;
	}
	else if(t == 3)
	{
		haveFinalBackground = true;
		return R.drawable.tutorial_scroll2;
	}
	else if(t == 6)
	{
		haveFinalBackground = true;
		return R.drawable.tutorial_right2;
	}
	else
		return 0;
}
}
