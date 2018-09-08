package transformation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import transform.LineTransformator;

@RunWith(JUnitParamsRunner.class)
public class SubtitleTimeTransformatorTest {
	
	@Test(expected = RuntimeException.class)
	public void testUpdateLineThrowsWhenTimeLineIsInvalid() {
		LineTransformator tran = new SubtitleTimeTransformator(123);
		tran.updateLine("00:00:00 ,000-->m00:00:00,000");
	}
	
	@Test
	@Parameters
	public void testUpdateLineWorks(String inputLine, long miliseconds, String expected) {
		LineTransformator tran = new SubtitleTimeTransformator(miliseconds);
		assertEquals(expected, tran.updateLine(inputLine));
	}
	
	public Object[] parametersForTestUpdateLineWorks() {
		return new Object[] {
				new Object[] {"not time line", 1223, "not time line"},
				new Object[] {"00:00:00,000 --> 00:00:00,000", -123, "00:00:00,000 --> 00:00:00,000"}, //nothing change
				
				new Object[] {"00:00:00,000 --> 00:00:00,000", 123, "00:00:00,123 --> 00:00:00,123"}, //miliseconds
				new Object[] {"00:00:01,000 --> 00:00:01,500", 1500, "00:00:02,500 --> 00:00:03,000"}, //seconds
				new Object[] {"00:20:00,000 --> 00:40:00,000", 1200456, "00:40:00,456 --> 01:00:00,456"}, //minutes
				new Object[] {"01:15:25,789 --> 01:15:25,789", 31505000, "10:00:30,789 --> 10:00:30,789"}, //hours
				
				new Object[] {"00:00:00,123 --> 00:00:00,123", -123, "00:00:00,000 --> 00:00:00,000"}, //-miliseconds
				new Object[] {"00:00:02,500 --> 00:00:03,000", -1500, "00:00:01,000 --> 00:00:01,500"}, //-seconds
				new Object[] {"00:40:00,456 --> 01:00:00,456", -1200456, "00:20:00,000 --> 00:40:00,000"}, //-minutes
				new Object[] {"10:00:30,789 --> 10:00:30,789", -31505000, "01:15:25,789 --> 01:15:25,789"}, //-hours
		};
	}
}
