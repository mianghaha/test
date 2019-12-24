package sensetiveword;
/** 
 *@Description: 屏蔽词功能实现  
 */

  
import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.HashSet; 
  
public class Forbidden { 
   
	private static Forbidden forbidden = new Forbidden(); 
	//屏蔽词HashSet 
 	private HashSet<String> keyString = new HashSet<String>(); 
	private final static int maxLength = Character.MAX_VALUE; 
	//屏蔽词长度HashSet数组 
	@SuppressWarnings("unchecked") 
	private HashSet<Integer>[] keyLength = new HashSet[maxLength]; 
   
 	private Forbidden() { 
// 		loadForbidden(Config.getClassRoot() + "forbidden.txt"); 
 	} 
 	public static Forbidden getForbidden(){ 
 		return forbidden; 
 	} 
   
	/** 
	* @param str 
	* @return 
	* @Description: 输入的字符串通过屏蔽处理，实现最大长度匹配 
	*/
	public String read(String str){ 
		if (str == null){ 
			return null; 
		} 
		StringBuffer stringBuffer = new StringBuffer(); 
		int start = 0; 
		for (int i = 0; i < str.length();){
			int at = str.charAt(i); 
			if (keyLength[at] == null){ 
				i++; 
				continue; 
			} else {
				//发现首字母匹配，开始进行最长匹配
				int ml = 0; 
				//循环所有相同首字母的敏感词长度
				for (Object obj : keyLength[at].toArray()){
					int len = ((Integer)obj).intValue();
					if (i + len <= str.length()){
						//截取匹配词语中 与敏感词相同长度的字符串
						String s = str.substring(i, i + len); 
						if (keyString.contains(s)){ 
							//最大长度匹配 
							ml = len > ml ? len : ml; 
						} 
					} 
				} 
				if (ml > 0){ 
					stringBuffer.append(str.substring(start, i)).append("***"); 
					i += ml; 
					start = i; 
				} else { 
					i++; 
				} 
			}
		} 
		if (start < str.length()){ 
			stringBuffer.append(str.substring(start)); 
		} 
		return stringBuffer.toString(); 
	} 
   
	/** 
	 * @param path 
	 * @Description: 初始化加载屏蔽词 
	 * @Description: 存储屏蔽词的数据格式逻辑如下 
	 * @Description: 构建一个HashSet<String>用于存储所有的屏蔽词 
	 * @Description: 构建长度为maxLength = Character.MAX_VALUE 的 HashSet<Integer>数组 
	 * @Description: 将加载的屏蔽中第一个字符转化成int值(asc码)
	 * @Description: 如“你好呀”得到'你'的int值为20320，即“你好呀”相关信息存储在数组的第20320位置 
	 * @Description: 数组每一位存储的HashSet<Integer>结构存储在该位置的屏蔽词的长度 
	*/
	public void loadForbidden(String path){ 
		File forbiddenFile = new File(path); 
		FileInputStream fileInputStream; 
		try { 
			fileInputStream = new FileInputStream(forbiddenFile); 
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8"); 
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
			String s; 
			while ((s = bufferedReader.readLine()) != null){ 
				s = s.trim(); 
				if (s.length() > 0){ 
					keyString.add(s); 
					int i = s.charAt(0);
					//设置所有敏感词 相同首字符的字符长度 
					if (keyLength[i] == null){
						//屏蔽词长度HashSet 
						HashSet<Integer> a = new HashSet<Integer>();
						a.add(s.length());
						keyLength[i] = a;
					} else {
						keyLength[i].add(s.length());
					}
				}
			}
			fileInputStream.close();
			bufferedReader.close();
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
  
	public static void main(String[] args) { 
	//  System.out.println(Forbidden.getForbidden().read("AV女优nihao")); 
		int i = '你'; 
		System.out.println(i); 
	} 
}