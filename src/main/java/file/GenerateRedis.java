package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class GenerateRedis {
	public static void main(String[] args) throws Exception{
		File file = new File("./src/main/java/file/redis.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		String modelString1 = "zadd grc:226:fighting_capacity_info:";
		String modelString2 = " 1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9 10 10 11 11 12 12 13 13 14 14 15 15 16 16 17 17 18 18 19 19 20 20";
		for(int i = 1; i < 20000; i++) {
			String redisLine = modelString1 + i + modelString2;
			bw.write(redisLine);
			bw.newLine();
		}
		
		bw.close();
	}

}
