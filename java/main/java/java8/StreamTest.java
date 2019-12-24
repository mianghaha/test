package java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

	public static void main(String[] args){
		
		Set<Integer> set = new HashSet<Integer>();
		for(int i = 0; i < 10000000; i++){
			set.add(i);
		}
		
		long start = System.currentTimeMillis();
//		List<Integer> list1 = new ArrayList<Integer>(set);
//		System.out.println("trans to list1,time=" + (System.currentTimeMillis() - start) + ",size=" + list1.size());
		
		int max = 0;
		for(int i : set){
			if(i > max){
				max = i;
			}
		}
		System.out.println("max1,time=" + (System.currentTimeMillis() - start) + ",max=" + max);
		
		start = System.currentTimeMillis();
		max = 0;
		
//		List<Integer> list2 = set.stream().collect(Collectors.toCollection(ArrayList::new));
//		System.out.println("trans to list2,time=" + (System.currentTimeMillis() - start) + ",size=" + list2.size());
//		Comparator<Integer> comparator = (x,y) -> x > y ? 1 : -1;
		
//		Function<Integer, Integer> func = (x) -> 1 - x;
//		Comparator<Integer> comparator = Comparator.comparing(func);
//		max = set.stream().max(comparator).get();
//		System.out.println("max2,time=" + (System.currentTimeMillis() - start) + ",max=" + max);
//		start = System.currentTimeMillis();
//		max = 0;
//		max = set.stream().collect(Collectors.maxBy(Comparator.comparing(Integer::intValue))).get();
//		System.out.println("max3,time=" + (System.currentTimeMillis() - start) + ",max=" + max);
		
		 Map<Boolean, List<Integer>> par = set.stream().collect(Collectors.partitioningBy(x -> x > 50));
		 System.out.println("partitioningBy,time=" + (System.currentTimeMillis() - start) + ",true.size=" + par.get(true).size() + ",false.size=" + par.get(false).size());
	}
	
	public int addUp(Stream<Integer> number){
		return number.reduce(0, (acc, element) -> acc + element);
	}
	
	public List<String> getNameAndNation(List<Artist> artistList){
		List<String> a = new ArrayList<String>();
		artistList.stream().forEach(artist -> {
			a.add(artist.getName());
			a.add(artist.getNation());
		});
		return a;
	}
	
	public List<Album> getAlumList(List<Album> albumList, final int count){
		return albumList.stream()
		.filter(album -> album.getSongList().size() <= count)
		.collect(Collectors.toList());
	}
	
	
}
