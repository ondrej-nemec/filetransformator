package run;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class RunTransformationsTest {
	
	@Test
    @Parameters
	public void testFillVariablesWorks(
			String originPath, String resutlPath,
			String originCharset, String resultCharset,
			boolean deleteOrigin, boolean deleteResult,
			char unitOfTime, long time,
			Map<String, String> map,
			String... args
		) {
		RunTransformations t = new RunTransformations();
		t.fillVariables(args);
		
		assertEquals(originPath, t.getOriginPath());
		assertEquals(originCharset, t.getOriginCharset());
		assertEquals(resutlPath, t.getResultPath());
		assertEquals(resultCharset, t.getResultCharset());
		assertEquals(deleteOrigin, t.isDeleteOrigin());
		assertEquals(deleteResult, t.isDeleteExistingResult());
		assertEquals(unitOfTime, t.getUnitOfTime());
		assertEquals(time, t.getTime());
		assertEquals(map, t.getMap());
	}
	
	public Object[] parametersForTestFillVariablesWorks() {
		Map<String, String> map = new HashMap<>();
		map.put("a", "b");
		map.put("c", "v");
		
    	return new Object[] {
    			new Object[] {"", null, null, null,  false, false, '\u0000', 0, new HashMap<>(),
    				""},
    			new Object[] {"origin", null, null, null,  false, false, '\u0000', 0, new HashMap<>(),
    				"origin"},
    			
    			new Object[] {"origin", "result", null, null,  false, false, '\u0000', 0, new HashMap<>(),
    				"origin", "result"},
    			
    			new Object[] {"origin", "result", "orChar", null,  false, false, '\u0000', 0, new HashMap<>(),
    				"origin", "result", "-co", "orChar"},
    			
    			new Object[] {"origin", "result", "orChar", "resCha",  false, false, '\u0000', 0, new HashMap<>(),
					"origin", "result", "-co", "orChar", "-cr", "resCha"},
    			
    			new Object[] {"origin", "result", "orChar", "resCha", true, false, '\u0000', 0, new HashMap<>(),
					"origin", "result", "-co", "orChar", "-cr", "resCha", "-do"},
    			
    			new Object[] {"origin", "result", "orChar", "resCha", true, true, '\u0000', 0, new HashMap<>(),
					"origin", "result", "-co", "orChar", "-cr", "resCha", "-do", "-dr"},
    			
    			new Object[] {"origin", "result", "orChar", "resCha", true, true, '\u0000', 100, new HashMap<>(),
    				"origin", "result", "-co", "orChar", "-cr", "resCha", "-do", "-dr", "-t", "100"},
    			
    			new Object[] {"origin", "result", "orChar", "resCha", true, true, 'S', 6, new HashMap<>(),
    				"origin", "result", "-co", "orChar", "-cr", "resCha", "-do", "-dr", "-tu", "6", "S"},
    			
    			new Object[] {"origin", "result", "orChar", "resCha", true, true, 'S', 6, map,
    				"origin", "result", "-co", "orChar", "-cr", "resCha", "-do", "-dr", "-tu", "6", "S", "-m", "a", "b", "-m", "c", "v"},
    	};
    }
	
	
	
    @Test
    @Parameters
	public void testConvertTimeToMilisecondsWorks(long time, char unit, long expected) {
    	RunTransformations t = new RunTransformations();
    	assertEquals(expected, t.convertTimeToMiliseconds(time, unit));
	}
    
    public Object[] parametersForTestConvertTimeToMilisecondsWorks() {
    	return new Object[] {
    			new Object[] {45566, ' ', 45566,},
    			new Object[] {1000, 'N', 1000},
    			new Object[] {60, 'S', 60000},
    			new Object[] {20, 'M', 1200000},
    			new Object[] {1, 'H', 3600000}
    	};
    }

}
