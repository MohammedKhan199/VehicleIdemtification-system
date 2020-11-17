import java.time.Duration;
import java.time.Instant;

import edu.uwm.cs351.util.MyHashTable;

public class TestStats{
	private static MyHashTable<String,Integer> ht;
	public static void main(String[] args){
		ht = new MyHashTable<>();
		Instant start;
		int i=1;
		while(true){
			i++;
			start = Instant.now();
			for (int j=0;j<(2<<i);j++){
				if (takingTooLong(start)){System.out.println("Goodbye!");ht.clear();System.exit(0);}
				ht.put(Long.toHexString(Double.doubleToLongBits(Math.random())), j);}
			doStats(i);
			ht.clear();
		}
	}
	
	private static boolean takingTooLong(Instant start){
		return Duration.between(start, Instant.now()).compareTo(Duration.ofSeconds(20))>0;}
	
	private static void doStats(int size){
		double[] stats = ht.getStats();
		System.out.print("["+(2<<size)+" entries uses "+(int)stats[0]+" buckets]");
		System.out.print("[Smallest: "+(int)stats[1]+"]");
		System.out.print("[Largest: "+(int)stats[2]+"]");
		System.out.println("[Mean: "+stats[3]+"]");
	}
}
