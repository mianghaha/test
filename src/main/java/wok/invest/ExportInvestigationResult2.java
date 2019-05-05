package wok.invest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import poi.PoiUtil;
import utils.JsonUtil;

/**
 * 解析所有答案
 * @author miang
 *
 */
public class ExportInvestigationResult2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			// TODO Auto-generated method stub
			File inFile = new File("C://Users/miang/workspace/TEST/src/wok/invest/invest_result_6_17.txt");
			if(!inFile.exists()){
				System.out.println("in file not exist");
				return;
			}
			
			List<String> contentList = new ArrayList<String>();
			InputStreamReader read = new InputStreamReader(new FileInputStream(inFile), "UTF-8");//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while((lineTxt = bufferedReader.readLine()) != null){
				contentList.add(lineTxt);
			}
			read.close();
			

			String date = "2017-06-17";
			File outFile = new File("C://Users/miang/workspace/TEST/src/wok/invest/燃烧之门调查 " + date + ".xls");
			if(!outFile.exists()){
				outFile.createNewFile();
			}
			List<List<String>> colsList = new ArrayList<List<String>>();
			
			FileOutputStream fos = new FileOutputStream(outFile);
			String[] rows = {"userid","roleid","1【单选题】您在本次测试中总的游戏时长是？","2【单选题】对于《万王之王3D》，您的整体评价是？",
					"3【多选题】您不太喜欢的主要原因有哪些？","4【多选题】《万王之王3D》主要是哪些方面吸引您？",
					"5【单选题】对游戏中的剧情故事，您的评价是？","6【多选题】对游戏中的剧情故事，您主要有哪些不满点？",
					"7【单选题】对游戏中的职业和技能，您的评价是？","8【多选题】对游戏中的职业和技能，您主要有哪些不满点？",
					"9【单选题】对游戏中的副本系统，您的评价是？",	"10【多选题】对游戏中的副本系统，您主要有哪些不满点？",
					"11【单选题】对游戏中的美术品质，您的评价是？","12【多选题】对游戏中的美术品质，您主要有哪些不满点？",
					"13【单选题】以下角色形象你最喜欢哪一个？","14【多选题】您在游戏过程中，有哪些界面或操作曾让您感到困惑或难操作吗？",
					"15【多选题】让您感到困惑或产生不适的具体原因是？","16【多选题】近3个月，您玩过以下哪些类型的手机游戏？ ",
					"17【多选题】近3个月，您常登录的应用/网站有哪些呢？ ","18【单选题】以下关于一款西方魔幻题材的大型多人在线角色扮演类手游的定位，哪个标题对您更有吸引力？",
					"19【单选题】请问您的年龄处于哪个阶段？","20【单选题】请问您的所在地是？","21【单选题】请问您是否愿意参加线下访问或电话访问？",
					"23【填空】请问您的姓名是？","24【填空】您的联系手机是？"};
			
			 for(int i = 0; i < contentList.size(); i ++){
				 List<String> cols = new ArrayList<>();
				 
				 String content = contentList.get(i);
				 String[] temp1 = content.split("\\{");
				 String[] temp2 = temp1[1].split("\\}");
				 String[] temp3 = temp1[0].split(",");
				 String userid = temp3[2];
				 String roleid = temp3[4];
				 cols.add(userid);
				 cols.add(roleid);
				 
				 String answear = temp2[0];
				 answear = "{" + answear + "}";
				 Map<String, String> map = JsonUtil.TransToObject(answear, HashMap.class);
				 
				 String answer79 = map.get("79");
				 String[] answer79Array = answer79.split("#");
				 StringBuilder sb79 = new StringBuilder();
				 for(int j = 0; j < answer79Array.length; j ++){
					 String answer79Id = answer79Array[j];
					 String strAnswer79 = "对应错误";
					 if(answer79Id.equals("350")){
						 strAnswer79 = "只玩了1天，大概1小时以内";
					 }else if(answer79Id.equals("351")){
						 strAnswer79 = "只玩了1天，大概1小时以上";
					 }else if(answer79Id.equals("352")){
						 strAnswer79 = "玩了2天";
					 }else if(answer79Id.equals("353")){
						 strAnswer79 = "玩了3天以上";
					 }else if(answer79Id.equals("354")){
						 strAnswer79 = "几乎每天都玩";
					 }
					 if(sb79.length() > 0){
						 sb79.append("\r\n");
					 }
					 sb79.append(strAnswer79);
				 }
				 cols.add(sb79.toString());
				 
				String answer80 = map.get("80");
				String[] answer80Array = answer80.split("#");
				StringBuilder sb80 = new StringBuilder();
				for(int j = 0; j < answer80Array.length; j ++){
					 String answer80Id = answer80Array[j];
					 String strAnswer80 = "对应错误";
					 if(answer80Id.equals("355")){
						 strAnswer80 = "5分，太棒了，会向身边的朋友推荐";
					 }else if(answer80Id.equals("356")){
						 strAnswer80 = "4分，会持续关注，期待不删档测试";
					 }else if(answer80Id.equals("357")){
						 strAnswer80 = "3分，中规中矩，有时间会再玩一下";
					 }else if(answer80Id.equals("358")){
						 strAnswer80 = "2分，不太喜欢，不如玩其他游戏";
					 }else if(answer80Id.equals("359")){
						 strAnswer80 = "1分，太没意思了，不会再尝试";
					 }
					 if(sb80.length() > 0){
						 sb80.append("\r\n");
					 }
					 sb80.append(strAnswer80);
				 }
				 cols.add(sb80.toString());
				 
				 String answer81 = map.get("81");
				 String[] answer81Array = answer81.split("#");
				 String strAnswer81 = "对应错误";
				 StringBuilder sb81 = new StringBuilder();
				 for(int j = 0; j < answer81Array.length; j ++){
					 String answer81Id = answer81Array[j];
					 if(answer81Id.equals("360")){
						 strAnswer81 = "不喜欢游戏玩法（即时战斗，组队副本）";
					 }else if(answer81Id.equals("361")){
						 strAnswer81 = "不喜欢游戏题材（欧美画风，西方魔幻故事）";
					 }else if(answer81Id.equals("362")){
						 strAnswer81 = "不喜欢3D风格和自由视角";
					 }else if(answer81Id.equals("363")){
						 strAnswer81 = "游戏玩法和其它游戏差不多、没新意";
					 }else if(answer81Id.equals("364")){
						 strAnswer81 = "游戏太复杂、不知道怎么玩";
					 }else if(answer81Id.equals("365")){
						 strAnswer81 = "剧情故事不吸引人";
					 }else if(answer81Id.equals("366")){
						 strAnswer81 = "战斗画面不够炫，打起来不爽";
					 }else if(answer81Id.equals("367")){
						 strAnswer81 = "玩了一阵子后缺少目标感，不知道要做什么";
					 }else if(answer81Id.equals("368")){
						 strAnswer81 = "组队困难，组不到队";
					 }else if(answer81Id.equals("369")){
						 strAnswer81 = "没有人一起聊天";
					 }else if(answer81Id.equals("370")){
						 strAnswer81 = "界面、菜单等操作太复杂";
					 }else if(answer81Id.equals("371")){
						 strAnswer81 = "日常玩法太少，做完后很无聊";
					 }else if(answer81Id.equals("372")){
						 strAnswer81 = "游戏里面bug多，如卡顿、掉线、闪退";
					 }else if(answer81Id.equals("373")){
						 strAnswer81 = "个人原因，如没时间";
					 }else if(answer81Id.equals("374")){
						 strAnswer81 = "本次测试会删档，不想投入太多精力";
					 }else if(answer81Id.equals("375")){
						 strAnswer81 = "其他";
					 }
					 if(sb81.length() > 0){
						 sb81.append("\r\n");
					 }
					 sb81.append(strAnswer81);
				 }
				 cols.add(sb81.toString());
				 
				String answer82 = map.get("82");
				String[] answer82Array = answer82.split("#");
				StringBuilder sb82 = new StringBuilder();
				for(int j = 0; j < answer82Array.length; j ++){
					 String answer82Id = answer82Array[j];
					 String strAnswer82 = "对应错误";
					 if(answer82Id.equals("376")){
						 strAnswer82 = "觉得游戏画面和人物好看";
					 }else if(answer82Id.equals("377")){
						 strAnswer82 = "有很多不同的职业可以选择";
					 }else if(answer82Id.equals("378")){
						 strAnswer82 = "喜欢西方魔幻故事";
					 }else if(answer82Id.equals("379")){
						 strAnswer82 = "喜欢组队下副本这类玩法";
					 }else if(answer82Id.equals("380")){
						 strAnswer82 = "可认识很多志同道合的朋友，喜欢一起玩";
					 }else if(answer82Id.equals("381")){
						 strAnswer82 = "游戏玩法丰富";
					 }else if(answer82Id.equals("382")){
						 strAnswer82 = "游戏成长线合理";
					 }else if(answer82Id.equals("383")){
						 strAnswer82 = "体验接近客户端游戏";
					 }else if(answer82Id.equals("384")){
						 strAnswer82 = "无聊，打发时间";
					 }else if(answer82Id.equals("385")){
						 strAnswer82 = "战斗表现良好，操作顺畅";
					 }else if(answer82Id.equals("386")){
						 strAnswer82 = "飞行玩法比较有特色";
					 }else if(answer82Id.equals("387")){
						 strAnswer82 = "对游戏后续开放内容有期待";
					 }else if(answer82Id.equals("388")){
						 strAnswer82 = "其他";
					 }
					 if(sb82.length() > 0){
						 sb82.append("\r\n");
					 }
					 sb82.append(strAnswer82);
				 }
				 cols.add(sb82.toString());
				 
				String answer83 = map.get("83");
				String[] answer83Array = answer83.split("#");
				StringBuilder sb83 = new StringBuilder();
				for(int j = 0; j < answer83Array.length; j ++){
					 String answer83Id = answer83Array[j];
					 String strAnswer83 = "对应错误";
					 if(answer83Id.equals("389")){
						 strAnswer83 = "5分，不错，很值得期待";
					 }else if(answer83Id.equals("390")){
						 strAnswer83 = "4分，比市面上一般的游戏好些";
					 }else if(answer83Id.equals("391")){
						 strAnswer83 = "3分，感觉一般，没有特别之处";
					 }else if(answer83Id.equals("392")){
						 strAnswer83 = "2分，没什么意思，不如大部分其他游戏";
					 }else if(answer83Id.equals("393")){
						 strAnswer83 = "1分，不好玩，简直无法忍受";
					 }
					 if(sb83.length() > 0){
						 sb83.append("\r\n");
					 }
					 sb83.append(strAnswer83);
				 }
				 cols.add(sb83.toString());
					 
				 
				 String answer84 = map.get("84");
				 String[] answer84Array = answer84.split("#");
				 StringBuilder sb84 = new StringBuilder();
				 for(int j = 0; j < answer84Array.length; j ++){
					 String strAnswer84 = "对应错误";
					 String answer84Id = answer84Array[j];
					 if(answer84Id.equals("394")){
						 strAnswer84 = "世界观太小，没有史诗感";
					 }else if(answer84Id.equals("395")){
						 strAnswer84 = "故事平淡，难以引人入胜";
					 }else if(answer84Id.equals("396")){
						 strAnswer84 = "角色和英雄太平淡，很难记住";
					 }else if(answer84Id.equals("397")){
						 strAnswer84 = "剧情内容太少，感知较弱";
					 }else if(answer84Id.equals("398")){
						 strAnswer84 = "配音、对话、动画表现形式单调";
					 }else if(answer84Id.equals("399")){
						 strAnswer84 = "其他";
					 }
					 if(sb84.length() > 0){
						 sb84.append("\r\n");
					 }
					 sb84.append(strAnswer84);
				 }
				 cols.add(sb84.toString());
				 
				String answer85 = map.get("85");
				String[] answer85Array = answer85.split("#");
				StringBuilder sb85 = new StringBuilder();
				for(int j = 0; j < answer85Array.length; j ++){
					 String answer85Id = answer85Array[j];
					 String strAnswer85 = "对应错误";
					 if(answer85Id.equals("400")){
						 strAnswer85 = "5分，不错，很值得期待";
					 }else if(answer85Id.equals("401")){
						 strAnswer85 = "4分，比市面上一般的游戏好些";
					 }else if(answer85Id.equals("402")){
						 strAnswer85 = "3分，感觉一般，没有特别之处";
					 }else if(answer85Id.equals("403")){
						 strAnswer85 = "2分，没什么意思，不如大部分其他游戏";
					 }else if(answer85Id.equals("404")){
						 strAnswer85 = "1分，不好玩，简直无法忍受";
					 }
					if(sb85.length() > 0){
						sb85.append("\r\n");
					}
					sb85.append(strAnswer85);
				}
				cols.add(sb85.toString());

				 
				String answer86 = map.get("86");
				String[] answer86Array = answer86.split("#");
				StringBuilder sb86 = new StringBuilder();
				for(int j = 0; j < answer86Array.length; j ++){
					 String answer86Id = answer86Array[j];
					 String strAnswer86 = "对应错误";
					 if(answer86Id.equals("406")){
						 strAnswer86 = "职业太多，不知道什么职业适合自己";
					 }else if(answer86Id.equals("407")){
						 strAnswer86 = "职业差异化不够，感觉都差不多";
					 }else if(answer86Id.equals("408")){
						 strAnswer86 = "职业搭配太单调，组队缺少乐趣";
					 }else if(answer86Id.equals("409")){
						 strAnswer86 = "职业之间不平衡";
					 }else if(answer86Id.equals("410")){
						 strAnswer86 = "技能只需要无脑点点点，没有策略性";
					 }else if(answer86Id.equals("411")){
						 strAnswer86 = "技能的学习和培养太单调";
					 }else if(answer86Id.equals("412")){
						 strAnswer86 = "技能读条太多导致操作不顺畅";
					 }else if(answer86Id.equals("413")){
						 strAnswer86 = "其他";
					 }
					if(sb86.length() > 0){
						sb86.append("\r\n");
					}
					sb86.append(strAnswer86);
				}
				cols.add(sb86.toString());
				 
				String answer87 = map.get("87");
				String[] answer87Array = answer87.split("#");
				StringBuilder sb87 = new StringBuilder();
				for(int j = 0; j < answer87Array.length; j ++){
					 String answer87Id = answer87Array[j];
					 String strAnswer87 = "对应错误";
					 if(answer87Id.equals("414")){
						 strAnswer87 = "5分，不错，很值得期待";
					 }else if(answer87Id.equals("415")){
						 strAnswer87 = "4分，比市面上一般的游戏好些";
					 }else if(answer87Id.equals("416")){
						 strAnswer87 = "3分，感觉一般，没有特别之处";
					 }else if(answer87Id.equals("417")){
						 strAnswer87 = "2分，没什么意思，不如大部分其他游戏";
					 }else if(answer87Id.equals("418")){
						 strAnswer87 = "1分，不好玩，简直无法忍受";
					 }
					if(sb87.length() > 0){
						sb87.append("\r\n");
					}
					sb87.append(strAnswer87);
				}
				cols.add(sb87.toString());	 
				 
				String answer88 = map.get("88");
				String[] answer88Array = answer88.split("#");
				StringBuilder sb88 = new StringBuilder();
				for(int j = 0; j < answer88Array.length; j ++){
					 String answer88Id = answer88Array[j];
					 String strAnswer88 = "对应错误";
					 if(answer88Id.equals("419")){
						 strAnswer88 = "boss设计单调缺少挑战性";
					 }else if(answer88Id.equals("420")){
						 strAnswer88 = "boss难度太高，容易团灭";
					 }else if(answer88Id.equals("421")){
						 strAnswer88 = "关卡指引和boss说明太少，不知道怎么玩";
					 }else if(answer88Id.equals("422")){
						 strAnswer88 = "小怪战斗太长太无聊";
					 }else if(answer88Id.equals("423")){
						 strAnswer88 = "副本数量太少，每天重复刷很无聊";
					 }else if(answer88Id.equals("424")){
						 strAnswer88 = "每天拿满副本和团队副本奖励太费时间";
					 }else if(answer88Id.equals("425")){
						 strAnswer88 = "其他";
					 }
					if(sb88.length() > 0){
						sb88.append("\r\n");
					}
					sb88.append(strAnswer88);
				}
				cols.add(sb88.toString());
				 
				String answer89 = map.get("89");
				String[] answer89Array = answer89.split("#");
				StringBuilder sb89 = new StringBuilder();
				for(int j = 0; j < answer89Array.length; j ++){
					 String answer89Id = answer89Array[j];
					 String strAnswer89 = "对应错误";
					 if(answer89Id.equals("426")){
						 strAnswer89 = "5分，不错，很值得期待";
					 }else if(answer89Id.equals("427")){
						 strAnswer89 = "4分，比市面上一般的游戏好些";
					 }else if(answer89Id.equals("428")){
						 strAnswer89 = "3分，感觉一般，没有特别之处";
					 }else if(answer89Id.equals("429")){
						 strAnswer89 = "2分，没什么意思，不如大部分其他游戏";
					 }else if(answer89Id.equals("430")){
						 strAnswer89 = "1分，不好玩，简直无法忍受";
					 }
					if(sb89.length() > 0){
						sb89.append("\r\n");
					}
					sb89.append(strAnswer89);
				}
				cols.add(sb89.toString());
				 
				String answer90 = map.get("90");
				String[] answer90Array = answer90.split("#");
				StringBuilder sb90 = new StringBuilder();
				for(int j = 0; j < answer90Array.length; j ++){
					 String answer90Id = answer90Array[j];
					 String strAnswer90 = "对应错误";
					 if(answer90Id.equals("431")){
						 strAnswer90 = "画面风格不喜欢";
					 }else if(answer90Id.equals("432")){
						 strAnswer90 = "画面质量不高，不清晰";
					 }else if(answer90Id.equals("433")){
						 strAnswer90 = "地图场景不够好看";
					 }else if(answer90Id.equals("434")){
						 strAnswer90 = "角色动作僵直，不连贯";
					 }else if(answer90Id.equals("435")){
						 strAnswer90 = "不喜欢角色形象";
					 }else if(answer90Id.equals("436")){
						 strAnswer90 = "技能不够酷炫，施法不够流畅";
					 }else if(answer90Id.equals("437")){
						 strAnswer90 = "UI操作难用，逻辑混乱";
					 }else if(answer90Id.equals("438")){
						 strAnswer90 = "BOSS不够震撼，没有代入感";
					 }else if(answer90Id.equals("439")){
						 strAnswer90 = "其他";
					 }
					if(sb90.length() > 0){
						sb90.append("\r\n");
					}
					sb90.append(strAnswer90);
				}
				cols.add(sb90.toString());
				 
				String answer91 = map.get("91");
				String[] answer91Array = answer91.split("#");
				StringBuilder sb91 = new StringBuilder();
				for(int j = 0; j < answer91Array.length; j ++){
					 String answer91Id = answer91Array[j];
					 String strAnswer91 = "对应错误";
					 if(answer91Id.equals("440")){
						 strAnswer91 = "人类男";
					 }else if(answer91Id.equals("441")){
						 strAnswer91 = "人类女";
					 }else if(answer91Id.equals("442")){
						 strAnswer91 = "兽人男";
					 }else if(answer91Id.equals("515")){
						 strAnswer91 = "矮人男";
					 }else if(answer91Id.equals("443")){
						 strAnswer91 = "矮人女";
					 }else if(answer91Id.equals("444")){
						 strAnswer91 = "精灵男";
					 }else if(answer91Id.equals("445")){
						 strAnswer91 = "精灵女";
					 }else if(answer91Id.equals("446")){
						 strAnswer91 = "都不喜欢";
					 }
					if(sb91.length() > 0){
						sb91.append("\r\n");
					}
					sb91.append(strAnswer91);
				}
				cols.add(sb91.toString());
				 
				String answer92 = map.get("92");
				String[] answer92Array = answer92.split("#");
				StringBuilder sb92 = new StringBuilder();
				for(int j = 0; j < answer92Array.length; j ++){
					 String answer92Id = answer92Array[j];
					 String strAnswer92 = "对应错误";
					 if(answer92Id.equals("447")){
						 strAnswer92 = "主界面技能区域";
					 }else if(answer92Id.equals("448")){
						 strAnswer92 = "主界面聊天功能";
					 }else if(answer92Id.equals("449")){
						 strAnswer92 = "主界面摇杆功能";
					 }else if(answer92Id.equals("450")){
						 strAnswer92 = "主界面任务区域";
					 }else if(answer92Id.equals("451")){
						 strAnswer92 = "主界面队伍区域";
					 }else if(answer92Id.equals("452")){
						 strAnswer92 = "主界面头像区域";
					 }else if(answer92Id.equals("453")){
						 strAnswer92 = "包裹系统";
					 }else if(answer92Id.equals("454")){
						 strAnswer92 = "装备系统";
					 }else if(answer92Id.equals("455")){
						 strAnswer92 = "活动界面";
					 }else if(answer92Id.equals("456")){
						 strAnswer92 = "地下城界面";
					 }else if(answer92Id.equals("457")){
						 strAnswer92 = "商店界面";
					 }else if(answer92Id.equals("458")){
						 strAnswer92 = "公会界面";
					 }else if(answer92Id.equals("459")){
						 strAnswer92 = "技能界面";
					 }else if(answer92Id.equals("460")){
						 strAnswer92 = "其他";
					 }
					if(sb92.length() > 0){
						sb92.append("\r\n");
					}
					sb92.append(strAnswer92);
				}
				cols.add(sb92.toString());
				 
				String answer93 = map.get("93");
				String[] answer93Array = answer93.split("#");
				StringBuilder sb93 = new StringBuilder();
				for(int j = 0; j < answer93Array.length; j ++){
					 String answer93Id = answer93Array[j];
					 String strAnswer93 = "对应错误";
					 if(answer93Id.equals("462")){
						 strAnswer93 = "文字内容显示杂乱";
					 }else if(answer93Id.equals("463")){
						 strAnswer93 = "按钮分布混乱，操作不便";
					 }else if(answer93Id.equals("464")){
						 strAnswer93 = "操作逻辑太复杂";
					 }else if(answer93Id.equals("465")){
						 strAnswer93 = "响应速度慢、点击无反应等";
					 }else if(answer93Id.equals("466")){
						 strAnswer93 = "界面功能看不懂";
					 }else if(answer93Id.equals("467")){
						 strAnswer93 = "画面图形、文字不好看";
					 }else if(answer93Id.equals("468")){
						 strAnswer93 = "其他";
					 }
					if(sb93.length() > 0){
						sb93.append("\r\n");
					}
					sb93.append(strAnswer93);
				}
				cols.add(sb93.toString());
				 
				String answer94 = map.get("94");
				String[] answer94Array = answer94.split("#");
				StringBuilder sb94 = new StringBuilder();
				for(int j = 0; j < answer94Array.length; j ++){
					 String answer94Id = answer94Array[j];
					 String strAnswer94 = "对应错误";
					 if(answer94Id.equals("469")){
						 strAnswer94 = "即时制MMORPG（如剑侠情缘、御龙在天、六龙争霸3D等）";
					 }else if(answer94Id.equals("470")){
						 strAnswer94 = "回合制MMORPG（如梦幻诛仙、梦幻西游、大话西游等）";
					 }else if(answer94Id.equals("471")){
						 strAnswer94 = "moba（如王者荣耀、全民超神等）";
					 }else if(answer94Id.equals("472")){
						 strAnswer94 = "卡牌（如刀塔传奇、战斗吧剑灵、封神英雄榜、龙珠激斗等）";
					 }else if(answer94Id.equals("473")){
						 strAnswer94 = "格斗（如火影忍者、天天炫斗、时空猎人、三国之刃等）";
					 }else if(answer94Id.equals("474")){
						 strAnswer94 = "飞行射击（如全民飞机大战、雷霆战机等）";
					 }else if(answer94Id.equals("475")){
						 strAnswer94 = "跑酷躲避（如天天酷跑、天天风之旅、爸爸去哪儿等）";
					 }else if(answer94Id.equals("476")){
						 strAnswer94 = "竞速（极品飞车、天天飞车、狂野飚车等）";
					 }else if(answer94Id.equals("477")){
						 strAnswer94 = "益智（如猎鱼达人、捕鱼来了、保卫萝卜等）";
					 }else if(answer94Id.equals("478")){
						 strAnswer94 = "枪战射击（如穿越火线手游、全民突击、全民枪战等）";
					 }else if(answer94Id.equals("479")){
						 strAnswer94 = "体育竞技（如FIFA、街头篮球、足球经理等）";
					 }else if(answer94Id.equals("480")){
						 strAnswer94 = "战争策略（如部落冲突、皇室战争、列王的纷争等）";
					 }else if(answer94Id.equals("481")){
						 strAnswer94 = "桌游棋牌类（如欢乐麻将、欢乐斗地主、天天德州等）";
					 }else if(answer94Id.equals("519")){
						 strAnswer94 = "ARPG游戏（如龙之谷、全民斗战神等）";
					 }else if(answer94Id.equals("482")){
						 strAnswer94 = "其他";
					 }
					if(sb94.length() > 0){
						sb94.append("\r\n");
					}
					sb94.append(strAnswer94);
				}
				cols.add(sb94.toString());
				 
				String answer95 = map.get("95");
				String[] answer95Array = answer95.split("#");
				StringBuilder sb95 = new StringBuilder();
				for(int j = 0; j < answer95Array.length; j ++){
					 String answer95Id = answer95Array[j];
					 String strAnswer95 = "对应错误";
					 if(answer95Id.equals("483")){
						 strAnswer95 = "视频类（如爱奇艺、腾讯视频、bilibili等）";
					 }else if(answer95Id.equals("484")){
						 strAnswer95 = "小说类（如起点中文网、创世中文网、潇湘书院、红袖添香等）";
					 }else if(answer95Id.equals("485")){
						 strAnswer95 = "漫画类（如漫域网、178在线动漫、爱漫画网站等）";
					 }else if(answer95Id.equals("486")){
						 strAnswer95 = "音乐类（如QQ音乐、网易云音乐、虾米等）";
					 }else if(answer95Id.equals("487")){
						 strAnswer95 = "购物类（如淘宝、京东、闲鱼等）";
					 }else if(answer95Id.equals("488")){
						 strAnswer95 = "社区类（如贴吧、微博、知乎、豆瓣等）";
					 }else if(answer95Id.equals("489")){
						 strAnswer95 = "新闻类（如凤凰网、腾讯新闻、新华社、网易新闻等）";
					 }else if(answer95Id.equals("490")){
						 strAnswer95 = "游戏行业资讯类（如中国手游观察网、游戏茶馆、口袋巴士、手游那点事等）";
					 }else if(answer95Id.equals("491")){
						 strAnswer95 = "直播类（如斗鱼、虎牙、龙珠、熊猫等）";
					 }else if(answer95Id.equals("492")){
						 strAnswer95 = "生活类（如大众点评、美团、58同城等）";
					 }else if(answer95Id.equals("493")){
						 strAnswer95 = "健康类（如keep、咕咚、动动、蜗牛睡眠等）";
					 }else if(answer95Id.equals("494")){
						 strAnswer95 = "杂志类（如国家地理、商业周刊、家具杂志、南方周末等）";
					 }else if(answer95Id.equals("495")){
						 strAnswer95 = "其他";
					 }
					if(sb95.length() > 0){
						sb95.append("\r\n");
					}
					sb95.append(strAnswer95);
				}
				cols.add(sb95.toString());
					 
				String answer96 = map.get("96");
				String[] answer96Array = answer96.split("#");
				StringBuilder sb96 = new StringBuilder();
				for(int j = 0; j < answer96Array.length; j ++){
					 String answer96Id = answer96Array[j];
					 String strAnswer96 = "对应错误";
					 if(answer96Id.equals("496")){
						 strAnswer96 = "3DMMO热血城邦手游";
					 }else if(answer96Id.equals("497")){
						 strAnswer96 = "3D魔幻大世界";
					 }else if(answer96Id.equals("498")){
						 strAnswer96 = "3D战法牧开荒手游";
					 }else if(answer96Id.equals("499")){
						 strAnswer96 = "3D大世界探索手游";
					 }else if(answer96Id.equals("500")){
						 strAnswer96 = "3D全自由魔幻史诗手游";
					 }else if(answer96Id.equals("501")){
						 strAnswer96 = "以上都没有吸引力";
					 }
					if(sb96.length() > 0){
						sb96.append("\r\n");
					}
					sb96.append(strAnswer96);
				}
				cols.add(sb96.toString());
				 
				String answer97 = map.get("97");
				String[] answer97Array = answer97.split("#");
				StringBuilder sb97 = new StringBuilder();
				for(int j = 0; j < answer97Array.length; j ++){
					 String answer97Id = answer97Array[j];
					 String strAnswer97 = "对应错误";
					 if(answer97Id.equals("502")){
						 strAnswer97 = "15-19";
					 }else if(answer97Id.equals("503")){
						 strAnswer97 = "20-24";
					 }else if(answer97Id.equals("504")){
						 strAnswer97 = "25-29";
					 }else if(answer97Id.equals("505")){
						 strAnswer97 = "30-34";
					 }else if(answer97Id.equals("506")){
						 strAnswer97 = "35-39";
					 }else if(answer97Id.equals("516")){
						 strAnswer97 = "40及以上";
					 }
					if(sb97.length() > 0){
						sb97.append("\r\n");
					}
					sb97.append(strAnswer97);
				}
				cols.add(sb97.toString());
				 
				String answer98 = map.get("98").trim();
				String[] answer98Array = answer98.split("#");
				StringBuilder sb98 = new StringBuilder();
				for(int j = 0; j < answer98Array.length; j ++){
					 String answer98Id = answer98Array[j];
					 String strAnswer98 = "对应错误";
					 if(answer98Id.equals("508")){
						 strAnswer98 = "北京";
					 }else if(answer98Id.equals("509")){
						 strAnswer98 = "上海";
					 }else if(answer98Id.equals("510")){
						 strAnswer98 = "深圳";
					 }else if(answer98Id.equals("511")){
						 strAnswer98 = "广州";
					 }else if(answer98Id.equals("512")){
						 strAnswer98 = "其他";
					 }
					if(sb98.length() > 0){
						sb98.append("\r\n");
					}
					sb98.append(strAnswer98);
				}
				cols.add(sb98.toString());
				 
				 String answer99 = map.get("99").trim();
				 String[] answer99Array = answer99.split("#");
				 String answer99Id = answer99Array[0];
				 String strAnswer99 = "对应错误";
				 if(answer99Id.equals("513")){
					 strAnswer99 = "愿意";
				 }else if(answer99Id.equals("514")){
					 strAnswer99 = "不太愿意";
				 }
				 cols.add(strAnswer99);
				 
				 String nameAnswer = map.get("100");
				 String[] nameArray = nameAnswer.split("#");
				 String name = null;
				 if(nameArray.length > 1){
					 name  = nameArray[1];
					 cols.add(name);
				 }else{
					 cols.add("");
				 }
				 
				 String cellphoneAnswer = map.get("101");
				 String[] array = cellphoneAnswer.split("#");
				 if(array.length == 2){
					 String cellphone = array[1];
					 cols.add(cellphone);
				 }else{
					 cols.add("");
				 }
				 
				 colsList.add(cols);
			}
			 
			PoiUtil.generateExcel(rows, colsList, fos);
			fos.flush();
			fos.close();
			 
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
