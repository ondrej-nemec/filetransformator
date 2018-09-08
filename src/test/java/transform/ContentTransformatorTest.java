package transform;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
	
	@Test
	public void testDeleteExistingFileDeleteFile() {
		File f = mock(File.class);
		when(f.exists()).thenReturn(true);
		new ContentTransformator().deleteExistingFile(f);
		verify(f, times(1)).exists();
		verify(f, times(1)).delete();
		verifyNoMoreInteractions(f);
	}
	
	@Test
	public void testDeleteExistingFileDontDeleteNonExitingFile() {
		File f = mock(File.class);
		when(f.exists()).thenReturn(false);
		new ContentTransformator().deleteExistingFile(f);
		verify(f, times(1)).exists();
		verifyNoMoreInteractions(f);
	}
	
	
	private List<LineTransformator> getMockedTransformators() {
		LineTransformator t1 = mock(LineTransformator.class);
		when(t1.updateLine(anyString())).thenReturn("");
		LineTransformator t2 = mock(LineTransformator.class);
		when(t2.updateLine(anyString())).thenReturn("");
		return Arrays.asList(t1, t2);
	}
	
}
