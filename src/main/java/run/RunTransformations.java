package run;

import java.util.HashMap;
import java.util.Map;

public class RunTransformations {

	public static void main(String[] args) {
		/*sygnature 
		 * origin resource
		 *  -co originCharset
		 *  -cr resultCharset
		 * -t(u)  time H/M/S/N
		 * -m key value // more time
		 */
		String originPath = null;
		String originCharset = null;
		String resultPath = null;
		String resultCharset = null;
		char unitOfTime = 'N';
		long time = 0;
		//TODO delete exising files
		Map<String, String> map = new HashMap<>();
		
		for (int i = 0; i < args.length; i++) {
			if (i == 0)
				originPath = args[0];
			else if (i == 1)
				resultPath = args[1];
			else if (args[i].equals("-co") && i < args.length -1)
				originCharset = args[i+1];
			else if (args[i].equals("-cr") && i < args.length -1)
				resultCharset = args[i+1];
			else if (args[i].equals("-t") && i < args.length -1)
				time = Long.parseLong(args[i+1]);
			else if (args[i].equals("-tu") && i < args.length -2) {
				time = Long.parseLong(args[i+1]);
				unitOfTime = args[i+2].charAt(0);
			} else if (args[i].equals("-m") && i < args.length -2)
				map.put(args[i+1], args[i+2]);
			
		}
		
		//TODO call content transformator plus add two more constructors
		
	}

}
