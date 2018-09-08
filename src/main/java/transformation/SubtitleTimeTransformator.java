package transformation;

import transform.LineTransformator;

public class SubtitleTimeTransformator implements LineTransformator {

	long shift;
	
	public SubtitleTimeTransformator(long miliSecondsShift) {
		this.shift = miliSecondsShift;
	}
	
	@Override
	public String updateLine(String line) {
		if (line.length() != 24 && line.charAt(2) != ':' && line.charAt(5) != ':') //is it time line
			return line;
		
		String[] parts = line.split(" ");
		if(parts.length != 3 || parts[0].length() != 12 || parts[2].length() != 12) //wrong format
			throw new RuntimeException("This is not time line " + line);
		
		return updateTime(parts[0], shift) 
				+ " " + parts[1] + " " + 
				updateTime(parts[2], shift);
	}

	private String updateTime(String time, long milisecondsShift){
		long resultTime = (
				Integer.parseInt( time.charAt(0) + "" + time.charAt(1)) * 3600
				+ Integer.parseInt( time.charAt(3) + "" + time.charAt(4)) * 60
				+ Integer.parseInt( time.charAt(6) + "" + time.charAt(7))) * 1000
				+ Integer.parseInt( time.charAt(9) + "" + time.charAt(10) + time.charAt(11));
		resultTime = resultTime + milisecondsShift;
		
		if (resultTime >= 0 ){
			String res = "";	
			long aux = 0;
			//hours
			aux = resultTime / 3600000;
			res += (aux < 10 ? "0" + aux : aux) + ":";
			resultTime = resultTime - aux*3600000;
			
			//minutes
			aux = resultTime / 60000;
			res += (aux < 10 ? "0" + aux : aux) + ":";
			resultTime = resultTime - aux*60000;
			
			//second
			aux = resultTime / 1000;
			res += (aux < 10 ? "0" + aux : aux) + ",";
			resultTime = resultTime - aux*1000;
			
			//miliseconds
			if (resultTime < 10)
				res += "00" + resultTime;
			else if (resultTime < 100)
				res += "0" + resultTime;
			else
				res += resultTime;
			return res;
		}
		return time;
	}
}
