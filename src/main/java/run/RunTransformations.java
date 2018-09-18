package run;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import transform.ContentTransformator;
import transformation.ReplaceStringTransformator;
import transformation.SubtitleTimeTransformator;

public class RunTransformations {
	
	public static final int OUT = 0;
	public static final int ERR = 1;
	public static final int EXC = 2;
	
	
	private String originPath;// = null;
	private String originCharset;// = null;
	private String resultPath;// = null;
	private String resultCharset;// = null;
	private boolean deleteExistingResult;// = false;
	private boolean deleteOrigin;// = false;
	private char unitOfTime;// = ' ';
	private long time;// = 0;	
	private Map<String, String> map;
		
	/**
	 * transform.jar <originFile> <resultFile>
	 * [-co <originCharset>] [-cr <resultCharset>]
	 * [-do] [-dr]
	 * [-t <time>]/[-tu <time> <H/M/S/N>]
	 * [-m <replaceWhat> <repaceWith>]
	 * 
	 * windows:
	 * start javaw -jar ./file-transformator-<version>.jar
	 * 
	 * linux:
	 * java -jar ./file-transformator-<version>.jar
	 * @param args
	 */	
	public void transform(String... args) {
		print("Prepate transformator", OUT);
		
		if (args.length < 2) {
			print("You have a few arguments, paths to origin and result is required", ERR);
			System.exit(0);
		}
		
		fillVariables(args);
		
		print("Transforming...", OUT);
				
		runTrans();
		
		print("Finished", OUT);
	}
	
	protected void fillVariables(String... args) {
		map = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			//paths
			if (i == 0)
				originPath = args[0];
			else if (i == 1)
				resultPath = args[1];
			//delete
			else if (args[i].equals("-do"))
				deleteOrigin = true;
			else if (args[i].equals("-dr"))
				deleteExistingResult = true;
			//charset
			else if (args[i].equals("-co") && i < args.length -1){
				originCharset = args[i+1];
				i++;
			} else if (args[i].equals("-cr") && i < args.length -1){
				resultCharset = args[i+1];
				i++;
			//time	
			} else if (args[i].equals("-t") && i < args.length -1){
				time = Long.parseLong(args[i+1]);
				i++;
			} else if (args[i].equals("-tu") && i < args.length -2) {
				time = Long.parseLong(args[i+1]);
				unitOfTime = args[i+2].charAt(0);
				i+=2;
			//map
			} else if (args[i].equals("-m") && i < args.length -2){
				map.put(args[i+1], args[i+2]);
				i+=2;
			} else {
				//TODO print help
				print("Unknown " + args[i], ERR);
				System.exit(0);
			}
		}
	}

	protected void runTrans() {
		new ContentTransformator(
				originPath,
				originCharset,
				deleteOrigin, 
				resultPath,
				resultCharset,
				deleteExistingResult,
				Arrays.asList(
					new SubtitleTimeTransformator(convertTimeToMiliseconds(time, unitOfTime)),
					new ReplaceStringTransformator(map)
				)
			);
	}
	
	protected long convertTimeToMiliseconds(long time, char key) {
		switch (key) {
		case 'H':
			return time * 1000 *60 *60;
		case 'M':
			return time * 1000 *60;
		case 'S':			
			return time * 1000;
		case 'N':
		default:
			return time;
		}
	}	
	
	protected String getOriginPath() {
		return originPath;
	}

	protected String getOriginCharset() {
		return originCharset;
	}

	protected String getResultPath() {
		return resultPath;
	}

	protected String getResultCharset() {
		return resultCharset;
	}

	protected boolean isDeleteExistingResult() {
		return deleteExistingResult;
	}

	protected boolean isDeleteOrigin() {
		return deleteOrigin;
	}

	protected char getUnitOfTime() {
		return unitOfTime;
	}

	protected long getTime() {
		return time;
	}

	protected Map<String, String> getMap() {
		return map;
	}

	
	public static void main(String[] args) {
		try{
			 new RunTransformations().transform(args);
		}catch(Throwable e) {
			e.printStackTrace();
			print(e.getMessage(), EXC);
		}
	}
	
	
	public static synchronized void print(String message, int status) {
		switch (status) {
		case OUT:
			System.out.println(message);
			break;
		case ERR:
		case EXC:
			System.err.println(message);
			break;
		default:
			break;
		}
	}
}
