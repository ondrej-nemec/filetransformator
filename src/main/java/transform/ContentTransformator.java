package transform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import text.plaintext.PlainTextCreator;
import text.plaintext.PlainTextLoader;


public class ContentTransformator {
	
	public ContentTransformator(
			String originPath, String originCharset,
			String resultPath, String resultCharset,
			boolean deleteExistingResult,
			boolean deleteOrigin,
			List<LineTransformator> transformators) {
		//TODO
	}
	
	public ContentTransformator(
			String originPath,
			String resultPath,
			boolean deleteExistingResult,
			boolean deleteOrigin,
			List<LineTransformator> transformators) {
		//TODO
	}
	
	public ContentTransformator() {}

	public void transform(BufferedReader br, BufferedWriter bw, List<LineTransformator> transformators) throws IOException {
		PlainTextLoader l = new PlainTextLoader();
		PlainTextCreator c = new PlainTextCreator();		
		l.read(br, (a)->{
			String res = a;
			for (LineTransformator transformator : transformators) {
				res = transformator.updateLine(res);
			}				
			try {
				c.write(bw, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
		
	public void deleteExistingFileIfExist(String path) {
		throw new UnsupportedOperationException("Not finished yet");
	}	

}
