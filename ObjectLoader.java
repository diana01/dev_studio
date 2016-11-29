import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {
	
	List <Vertex> vertexList;
	
	public ObjectLoader() {
		vertexList = new ArrayList<Vertex>();		
	}
	
	void loadFile(File file) throws IOException{
		FileReader f = new FileReader(file);
		BufferedReader reader = new BufferedReader(f);
		int lineCount = 0;
		String line = null;
		while(true) {
			line = reader.readLine();
			if(null == line) {
				break;
			}
			line = line.trim();
			if(line.length() == 0)
				continue;
			
			String [] currentLine = line.split(" ");
			if(currentLine[0].equals("v")) {
				float c1 = Float.parseFloat(currentLine[1]);
				float c2 = Float.parseFloat(currentLine[2]);
				float c3 = Float.parseFloat(currentLine[3]);
				Vertex vertex = new Vertex(c1, c2, c3);
				vertexList.add(vertex);
			}
				
		
		}
		reader.close();
	}
	
	
//	public float[] changeVertices(){
//		float[] vertices = vertexList.size();
//		
//		for(){
//			
//		}
//		return null;
//		
//	}
	

}