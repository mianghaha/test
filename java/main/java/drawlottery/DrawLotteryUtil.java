package drawlottery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtil;


public class DrawLotteryUtil {

	/**
	 * 单人概率类抽奖工具
	 * 整体思想：
	 * 奖品集合 + 概率比例集合
	 * 将奖品按集合中顺序概率计算成所占比例区间，放入比例集合。并产生一个随机数加入其中，排序。
	 * 排序后，随机数落在哪个区间，就表示那个区间的奖品被抽中。
	 * 返回的随机数在集合中的索引，该索引就是奖品集合中的索引。
	 * 比例区间的计算通过概率相加获得。
	 */
    public static int drawGift(List<Gift> giftList){
    	
    	 if(null == giftList || giftList.size() == 0){
    		 	return -1;
         }
    	 
         List<Double> giftProbList = new ArrayList<Double>(giftList.size());
         for(Gift gift:giftList){
             //按顺序将概率添加到集合中
        	 giftProbList.add(gift.getProb());
         }

        List<Double> sortRateList = new ArrayList<Double>();
        // 计算概率总和
        Double sumRate = 0D;
        for(Double prob : giftProbList){
            sumRate += prob;
        }

        if(sumRate == 0){
            return -1;
        }

        double rate = 0D;   //概率所占比例
        for(Double prob : giftProbList){
            rate += prob;   
            // 构建一个比例区段组成的集合(避免概率和不为1)
            sortRateList.add(rate / sumRate);
        }

        // 随机生成一个随机数，并排序
        double random = Math.random();
        sortRateList.add(random);
        Collections.sort(sortRateList);

        // 返回该随机数在比例集合中的索引
        return sortRateList.indexOf(random);
    }
    
	//双重校验锁获取一个Random单例
	public static ThreadLocalRandom getRandom() {
		return ThreadLocalRandom.current();
		/*if(random==null){
			synchronized (RandomUtils.class) {
				if(random==null){
					random =new Random();
				}
			}
		}
		
		return random;*/
	}
    
	/**
	 * 获得一个[0,max)之间的随机整数。
	 * @param max
	 * @return
	 */
	public static int getRandomInt(int max) {
		return getRandom().nextInt(max);
	}
    
	/**
	 * 获得一个[min, max]之间的随机整数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomInt(int min, int max) {
		return getRandom().nextInt(max-min+1) + min;
	}
    
    /**
	 * 从set中随机取得一个元素
	 * @param set
	 * @return
	 */
	public static <E> E getRandomElement(Set<E> set){
		int rn = getRandomInt(set.size());
		int i = 0;
		for (E e : set) {
			if(i==rn){
				return e;
			}
			i++;
		}
		return null;
	}
	
	/**
	 * 从list中随机取得一个元素
	 * @param list
	 * @return
	 */
	public static <E> E getRandomElement(List<E> list){
		int rn = getRandomInt(list.size());
		return list.get(rn);
	}

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        Gift iphone = new Gift();
        iphone.setId(101);
        iphone.setName("苹果手机");
        iphone.setProb(0.1D);

        Gift thanks = new Gift();
        thanks.setId(102);
        thanks.setName("再接再厉");
        thanks.setProb(0.5D);

        Gift vip = new Gift();
        vip.setId(103);
        vip.setName("优酷会员");
        vip.setProb(0.4D);

        List<Gift> list = new ArrayList<Gift>();
        list.add(vip);
        list.add(thanks);
        list.add(iphone);

        int[] counts = new int[list.size()];
        for(int i=0;i<1000;i++){
            int index = drawGift(list);
            counts[index]++;
            System.out.println(list.get(index) + ",count=" + counts[index]);
        }
        
        System.out.println("count=" + JsonUtil.TransToJson(counts) + ",gift=" + JsonUtil.TransToJson(list));
    }

}