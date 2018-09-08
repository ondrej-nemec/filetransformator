package transformation;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import transform.LineTransformator;

@RunWith(JUnitParamsRunner.class)
public class ReplaceStringTransformatorTest {
	
	@Test
	@Parameters
	public void testUpdateLineWorks(String inputLine, Map<String, String> map, boolean isItRegex, String expected) {
		LineTransformator tran = new ReplaceStringTransformator(map, isItRegex);
		assertEquals(expected, tran.updateLine(inputLine));
	}
	
	public Object[] parametersForTestUpdateLineWorks() {
		return new Object[] {
				new Object[] {"This is not good", getMap("good", "bad"), false, "This is not bad"},
				new Object[] {"This is not good", getMap("o", "-", "i", "*"), false, "Th*s *s n-t g--d"},
				new Object[] {"^(-?[0-9]+)(-)(-?[0-9]+)$", getMap("(","", ")", ""), false, "^-?[0-9]+--?[0-9]+$"},
				new Object[] {"-0  (-?[0-9]+)", getMap("(-?[0-9]+)", "regex"), false, "-0  regex"},
				
				new Object[] {"This is not good", getMap("([dgo])", ""), true, "This is nt "},
				
		};
	}
	
	private Map<String, String> getMap(String... params){
		Map<String, String> result = new HashMap<>();
		for (int i = 0; i < params.length; i+=2) {
			result.put(params[i], params[i+1]);
		}
		return result;
	}
	
}
