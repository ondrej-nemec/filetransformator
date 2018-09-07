package transform;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import text.InputTextBuffer;

public class ContentTransformatorTest {
	
	@Test
	public void testTransformCallsLineTransformator() {
		List<LineTransformator> transf = getMockedTransformators();
		try (
			BufferedReader br = new InputTextBuffer().buffer(
					getClass().getResourceAsStream("/transform-test.txt")
					);
			BufferedWriter bw = mock(BufferedWriter.class);
		) {
			new ContentTransformator().transform(br, bw, transf);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		for (LineTransformator l : transf) {
			verify(l, times(3)).updateLine(anyString());
		}
	}
	
	private List<LineTransformator> getMockedTransformators() {
		return Arrays.asList(
				mock(LineTransformator.class),
				mock(LineTransformator.class)
			);
	}
	
}
