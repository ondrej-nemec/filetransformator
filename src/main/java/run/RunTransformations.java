package run;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import transform.ContentTransformator;
import transformation.ReplaceStringTransformator;
import transformation.SubtitleTimeTransformator;

public class RunTransformations {

	public RunTransformations(String... args) {
		/*sygnature 
		 * origin resource
		 *  -co originCharset
		 *  -cr resultCharset
		 *  -do -dr
		 * -t(u)  time H/M/S/N
		 * -m key value // more time
		 */
		String originPath = null;
		String originCharset = null;
		String resultPath = null;
		String resultCharset = null;
		boolean deleteExistingResult = false;
		boolean deleteOrigin = false;
		char unitOfTime = 'N';
		long time = 0;
		Map<String, String> map = new HashMap<>();
		
		System.out.println("Prepate transformator");
		if (args.length < 2) {
			System.err.println("You have a few arguments, paths to origin and result is required");
			System.exit(0);
		}
			
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
				System.err.println("Unknown " + args[i]);
				System.exit(0);
			}
		}
		
		System.out.println("Transform...");
				
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
		
		System.out.println("Finished");
	}

	private long convertTimeToMiliseconds(long time, char key) {
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
	
	public static void main(String[] args) {
		new RunTransformations(args);
	}

}
