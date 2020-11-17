package edu.uwm.cs351.util;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import edu.uwm.cs.junit.LockedTestCase;

/**
 * A simple hash table using Collections for buckets.
 */
public class MyHashTable<K,V> extends AbstractMap<K,V> {
	
	private static class MyEntry<K,V> extends AbstractEntry<K,V> {
		public MyEntry(K k, V v) {super(k, v);}
	}
	
	private Collection<MyEntry<K,V>>[] _table;
	private int _numItems;
	private int _version; // used to check iterator staleness
	
	
	/**	Compute hash code using table size.
	 * The result must be in the range [0,table.length-1].
	 * Do NOT either check or depend on the invariant.
	 * This method may be called when the invariant is broken, e.g. during a rehash()
	 * and it is ALSO called while CHECKING the invariant!
	 * @param key the object to hash
	 * @return the key's hash value
	 */
	private int hash(Object key) {
		// TODO
		return 0;
	}
	
	private static boolean _report(String problem) {
		System.out.println("Invariant error: " + problem);
		return false;
	}
	
	private boolean _wellFormed() {
		// Invariant:
		// 0. the table is not null
		// 1. The total number of entries in all collections is equal to _numItems
		// 2. No entry has a null key or value
		// 3. Each entry is in the correct bucket: the hash code mod the table length is the bucket it is in.
		// 4. There are no duplicate entries or keys in each bucket.
		//		NB: We only have to check for duplicates within the context of each bucket: Why?
		// 5. The table never has more than LOAD_FACTOR*_table.length entries
		// TODO
		return true;
	}
	
	private static int DEFAULT_CAPACITY = 10;
	private static double LOAD_FACTOR = 0.75;
	

	@SuppressWarnings("unchecked")
	private Collection<MyEntry<K,V>>[] makeArray(int length) {
		return (Collection<MyEntry<K,V>>[]) new Collection[length];}
	
	/**	The following is a private method to cast the
	 * given object to the key type. If it is null this method returns null.
	 * This method is useful only to contain warnings in one place.
	 * NB: if it is not of the key type a runtime exception may occur later.
	 * But as long as you only assume it is an Object, you won't have troubles.
	 * @param x object to cast
	 * @return x cast as type K, or null
	 */
	@SuppressWarnings("unchecked")
	private K asKey(Object x) {return (K) x;}
	
	/**	The following is a private method to cast the
	 * given object to the entry type. If it is null this method returns null.
	 * This method is useful only to contain warnings in one place.
	 * NB: if it is not of the entry type a runtime exception will occur.
	 * @param x object to cast
	 * @return x cast as type MyEntry<K,V>, or null
	 */
	@SuppressWarnings("unchecked")
	private MyEntry<K,V> asEntry(Object x) {return (MyEntry<K,V>) x;}
	
	/**
	 * Create an empty hash table. 
	 */
	public MyHashTable() {
		// TODO
	}
	
	/*
	 * @see java.util.AbstractMap#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object o) {
		// TODO easy
		return false;
	}

	/** 
	 * Return value mapped to parameter key, or null.
	 * @param o, object which may or may not be a key
	 * @return value mapped to key, or null if none exists
	 * @throws IllegalArgumentException if o is null
	 */
	@Override
	public V get(Object o) {
		// TODO
		return null;

	}

	/**
	 * Add an entry to the table.
	 * @param key
	 * @param value
	 * @return former value mapped to key (or null, if none)
	 * @throws IllegalArgumentException if parameter key or value is null
	 */
	public V put(K key, V value) {
		// TODO
		return null;
	}
	
	/**
	 * Remove any entry with the parameter key from the table.
	 * Return value mapped to key (if any).
	 * @param o, object which may or may not be a key
	 * @return value mapped to key (if any)
	 * @throws IllegalArgumentException if o is null
	 */
	public V remove(Object o) {
		// TODO
		return null;
	}
	
	/**
	 * Replace table with a new array just over twice (2n+1) as big as before.
	 * All keys are rehashed.
	 */
	private void rehash() {
		// TODO: Implement this method
		// NB: Do not check invariant
	}
	
	private volatile Set<Entry<K, V>> entrySet;
	
	/*
	 * @see java.util.AbstractMap#entrySet()
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		assert _wellFormed() : "invariant broken at beginning of entrySet";
		if (entrySet == null)
			entrySet = new EntrySet();
		return entrySet;
	}
	
	private class EntrySet extends AbstractSet<Entry<K,V>> {
		// NO FIELDS! (why not?)

		@Override
		public Iterator<Entry<K,V>> iterator() {
			return new MyIterator();
		}

		@Override
		public int size() {
			// TODO: AbstractMap utilizes this for its own size() method
			return 0;
		}
		
		@Override 
		public boolean contains(Object o) {
			// TODO: Hint: Use the outer class to your advantage
			return false;
		}
		
		@Override 
		public boolean remove(Object o) {
			// TODO: Hint: Use the outer class to your advantage
			return false;
		}
		
		@Override 
		public void clear() {
			// TODO: easy
		}
	}
	
	
	private class MyIterator implements Iterator<Entry<K,V>> {
		private Iterator<MyEntry<K,V>> _bucketIt;
		private int _nextBucket;
		private int _myVersion;
		
		private boolean _wellFormed() {
			// Invariant - don't check if iterator is stale!
			// 0. Outer invariant holds
			// TODO
			// 1. 0 < next bucket <= table length
			// TODO
			// 2. if next bucket is less than table length then it exists and has entries
			// TODO
			// 3. _bucketIt is only null if next bucket == table length
			return true;
		}
		
		//You may want to define a helper method.

		public MyIterator() {
			// TODO: set fields
		}
		
		@Override
		public boolean hasNext() {
			// TODO
			return false;
		}

		@Override
		public Entry<K,V> next() {
			// TODO
			return null;
		}

		@Override
		public void remove() {
			// TODO
		}
	}
	
	public abstract static class TestInvariant extends LockedTestCase {

		private MyHashTable<String,Integer> ht;
		private MyHashTable<String,Integer>.MyIterator it;
		
		@Override
		protected void setUp() throws Exception {
			super.setUp();
			ht = new MyHashTable<>();
		}
		
		//Hash
		public void test00(){
			assertEquals(10,ht._table.length);
			assertEquals(101,"e".hashCode());
			assertEquals(109,"m".hashCode());
			assertEquals(110,"n".hashCode());
			assertEquals(Ti(1640663467), ht.hash("e"));
			assertEquals(Ti(1805173865), ht.hash("m"));
			assertEquals(Ti(873481876), ht.hash("n"));
			
			Object bad = new Object(){
				@Override
				public int hashCode(){return -1;}};
				
			assertEquals(Ti(1098580279), ht.hash(bad));
			
			ht._table = null;
			assertEquals("table is null",0,ht.hash("e"));
			ht._table = ht.makeArray(0);
			assertEquals("table has zero length",0,ht.hash("e"));
		}
		
		public void testHash2(){
			for (int i=0;i<10000;i++){
				assertTrue(ht.hash(new TerribleHasher())>=0 &&
							ht.hash(new TerribleHasher())<=ht._table.length);}
		}
		
		private class TerribleHasher{
			@Override
			public int hashCode(){return (int)-(Math.random()*10000);}
		}

		public void testInvariant0() {
			ht._table=null;
			assertFalse(ht._wellFormed());
		}
		
		//Invariant 1- NumItems
		public void test01() {
			assertTrue(ht._wellFormed());

			for (int i=0;i<6;i++){
				ht._table[ht.hash("e"+i)] = new ArrayList<>();
				ht._table[ht.hash("e"+i)].add(new MyEntry<String,Integer>("e"+i,i));}
			
			ht._numItems=6;
			assertTrue(ht._wellFormed());
			
			ht._numItems=5;
			assertFalse(ht._wellFormed());
			
			ht._numItems=6;
			for (int i=0;i<6;i++)
					ht._table[ht.hash("e"+i)].clear();
			
			assertFalse(ht._wellFormed());
			ht._numItems=0;
			assertTrue(ht._wellFormed());
		}
		
		//Null Data
		public void testInvariant2() {
			ht._table[3] = new ArrayList<>();
			ht._numItems=1;
			ht._table[3].add(new MyEntry<>(null,6));
			assertFalse(ht._wellFormed());
			ht._table[3].clear();
			ht._table[3].add(new MyEntry<>("bad",null));
			assertFalse(ht._wellFormed());
			ht._table[3].clear();
			ht._table[3].add(new MyEntry<>(null,null));
			assertFalse(ht._wellFormed());
		}
		
		//Duplicates/Wrong Bucket
		public void testInvariant3and4() {
			for (int i=0;i<6;i++){
				ht._table[ht.hash("e"+i)] = new ArrayList<>();
				ht._table[ht.hash("e"+i)].add(new MyEntry<String,Integer>("e"+i,i));}
			
			ht._numItems=6;
			assertTrue(ht._wellFormed());
			
			ht._table[ht.hash("e1")].add(new MyEntry<String,Integer>("e1",9));
			ht._numItems++;
			assertFalse("duplicate key",ht._wellFormed());
			
			ht._table[ht.hash("e1")].clear();
			ht._numItems-=2;
			ht._table[ht.hash("e1")].add(new MyEntry<String,Integer>("e8",0));
			assertFalse("incorrect bucket for hash",ht._wellFormed());
			
			ht = new MyHashTable<String,Integer>();
			ht._table[ht.hash("e1")]= new ArrayList<>();
			ht._table[ht.hash("e1")].add(new MyEntry<>("e1",3));
			ht._table[ht.hash("e1")].add(new MyEntry<>("e1",2));
			assertFalse("duplicate key",ht._wellFormed());
			
			ht = new MyHashTable<String,Integer>();
			ht._table[ht.hash("e1")]= new ArrayList<>();
			ht._table[ht.hash("e2")]= new ArrayList<>();
			ht._table[ht.hash("e2")].add(new MyEntry<>("e2",3));
			ht._table[ht.hash("e1")].add(new MyEntry<>("e2",2));
			assertFalse("incorrect bucket for hash",ht._wellFormed());
			
			MyEntry<String,Integer> e1 = new MyEntry<>("e1",2);
			ht = new MyHashTable<String,Integer>();
			ht._table[ht.hash("e1")]= new ArrayList<>();
			ht._table[ht.hash("e1")].add(e1);
			ht._table[ht.hash("e1")].add(e1);
			assertFalse("duplicate entry",ht._wellFormed());
		}
		
		public void testInvariant4() {
			for (int i=0;i<6;i++){
				ht._table[ht.hash("e"+i)] = new ArrayList<>();
				ht._table[ht.hash("e"+i)].add(new MyEntry<String,Integer>("e"+i,i));}
			
			ht._numItems=6;
			assertTrue(ht._wellFormed());
			
			ht._table[ht.hash("e1")].add(new MyEntry<String,Integer>("e1",9));
			ht._numItems++;
			assertFalse("duplicate key in bucket",ht._wellFormed());
			
			ht._table[ht.hash("e1")].clear();
			ht._table[ht.hash("e1")].add(new MyEntry<String,Integer>("e1",3));
			ht._numItems--;
			assertTrue("duplicate values are allowed",ht._wellFormed());
			
			ht._table[ht.hash("e1")].clear();
			ht._table[ht.hash("e1")].add(new MyEntry<String,Integer>("e8",0));
			assertFalse("incorrect bucket for hash",ht._wellFormed());
			
			ht = new MyHashTable<String,Integer>();
			ht._table[ht.hash("e1")]= new ArrayList<>();
			ht._table[ht.hash("e1")].add(new MyEntry<>("e1",3));
			ht._table[ht.hash("e1")].add(new MyEntry<>("e1",2));
			assertFalse("duplicates",ht._wellFormed());
			
			ht = new MyHashTable<String,Integer>();
			ht._table[ht.hash("e1")]= new ArrayList<>();
			ht._table[ht.hash("e2")]= new ArrayList<>();
			ht._table[ht.hash("e2")].add(new MyEntry<>("e2",3));
			ht._table[ht.hash("e1")].add(new MyEntry<>("e2",2));
			assertFalse("incorrect bucket for hash",ht._wellFormed());
		}
		
		//Invariant 5- Load Factor
		public void test02() {
			for (int i=0;i<10;i++)
				ht._table[i] = new ArrayList<>();
			
			for (int i=1;i<=6;i++)
				ht._table[ht.hash("e"+i)].add(new MyEntry<>("e"+i,i));
			ht._numItems=6;
			
			assertTrue("6 entries", ht._wellFormed());
			
			ht._table[ht.hash("e7")].add(new MyEntry<>("e7",2));
			ht._numItems++;
			assertEquals("no rehash has occured",Tb(2026660400),ht._wellFormed());
			
			ht._table[ht.hash("e8")].add(new MyEntry<>("e8",2));
			ht._numItems++;
			assertEquals("no rehash has occured",Tb(1562905430),ht._wellFormed());
		}
		
		//Iterator Invariant 0
		public void test03() {
			for (int i=1;i<6;i++){
				ht._table[ht.hash("e"+i)] = new ArrayList<>();
				ht._table[ht.hash("e"+i)].add(new MyEntry<String,Integer>("e"+i,i));}
			ht._numItems=5;
			
			//Table: [{e1},{e2},{e3},{e4},{e5},{},{},{},{},{}]
			
			it = makeIterator();
			assertTrue(it._wellFormed());
			ht._table[3].clear(); //held an entry
			assertEquals("versions match",Tb(1484164987),it._wellFormed());
			it._myVersion++;
			assertEquals("versions don't match",Tb(897981420),it._wellFormed());
		}
		
		//Iterator Invariant 123
		public void testIteratorInvariant123() {
			for (int i=1;i<6;i++){
				ht._table[ht.hash("e"+i)] = new ArrayList<>();
				ht._table[ht.hash("e"+i)].add(new MyEntry<String,Integer>("e"+i,i));}
			ht._numItems=5;
			
			//Table: [{e1},{e2},{e3},{e4},{e5},{},{},{},{},{}]
			it = makeIterator();
			
			it._nextBucket = -3;
			assertFalse(it._wellFormed());
			it._nextBucket = 0;
			assertEquals(Tb(282309365), it._wellFormed());
			it._nextBucket = 11;
			assertFalse(it._wellFormed());
			
			it._nextBucket = 6;
			assertEquals(Tb(1407649017), it._wellFormed());
			
			it._nextBucket = 1;
			ht._table[it._nextBucket].clear();
			ht._numItems--;
			assertFalse(it._wellFormed());
			it._myVersion++;
			assertTrue(it._wellFormed());
			it._myVersion--;
			
			it._nextBucket=2;
			assertTrue(it._wellFormed());
			
			it._bucketIt = null;
			assertFalse(it._wellFormed());
			it._nextBucket=ht._table.length;
			assertTrue(it._wellFormed());
		}

		
		private MyHashTable<String,Integer>.MyIterator makeIterator(){
			return (MyHashTable<String,Integer>.MyIterator) ht.entrySet().iterator();}
	}
	
	//don't change- used for stats
	public double[] getStats(){
		double[] result = new double[]{_table.length,0.0,0.0,0.0};
		result[1]=getBucketSize(0);
		result[2]=getBucketSize(0);
		for (int i=0;i<_table.length;i++){
			int size = getBucketSize(i);
			if (size<result[1]) result[1]=size;
			if (size>result[2]) result[2]=size;
			result[3]+=size;}
		result[3] = result[3]/result[0];
		return result;
	}

	//don't change- used for stats
	private int getBucketSize(int index){
		if (index < 0 || index >= _table.length || _table[index] == null) return 0;
		return _table[index].size();}
}
