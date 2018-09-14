package transform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

import text.plaintext.PlainTextCreator;
import text.plaintext.PlainTextLoader;


public class ContentTransformator {
	
	private PlainTextLoader l = new PlainTextLoader();
	private PlainTextCreator c = new PlainTextCreator();
	
	public ContentTransformator(
			String originPath, String originCharset,
			boolean deleteOrigin,
			String resultPath, String resultCharset,
			boolean deleteExistingResult,
			List<LineTransformator> transformators) {
		if (deleteExistingResult)
			deleteExistingFile(new File(resultPath));
		try(
				BufferedWriter bw = (resultCharset == null)
						? c.buffer(resultPath, true)
						: c.buffer(resultPath, resultCharset, true);
				BufferedReader br = (originCharset == null)
						? l.buffer(originPath)
						: l.buffer(originPath, originCharset);
			) {
			transform(br, bw, transformators);
			if (deleteOrigin)
			deleteExistingFile(new File(originPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ContentTransformator() {}

	public void transform(BufferedReader br, BufferedWriter bw, List<LineTransformator> transformators) throws IOException {
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
		
	public void deleteExistingFile(File file) {
		if(file.exists())
			file.delete();
	}	

}
