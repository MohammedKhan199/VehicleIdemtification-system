import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.util.AbstractEntry;
import edu.uwm.cs351.util.MyHashTable;

public class TestHashTable extends LockedTestCase{
	
	private MyHashTable<String,Integer> ht;
	private String[] data;
	private Iterator<Entry<String,Integer>> it;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ht = new MyHashTable<>();
		data = "abcdefghijklmnopqrstuvwxyz".split("(?!^)");
	}
	
	//Put
	public void test00(){
		ht.put("e1",1);
		ht.put("e2",2);
		ht.put("e3",3);
		ht.put("e4",4);
		ht.put("e5",5);
		assertEquals(5,ht.size());
		assertEquals(Ti(2142387030),(int) ht.put("e3", 9));
		assertEquals(Ti(164216955),(int) ht.put("e3", -1));
		assertEquals(null, ht.put("e6", 2));
	}

	public void testPut1(){
		for (String s: data)
			ht.put(s, s.hashCode());
		
		assertEquals(26,ht.size());
		
		for (String s: data)
			assertEquals(s.hashCode(),(int) ht.put(s, s.hashCode()));
		
		assertEquals(26,ht.size());
		
		try{ht.put(null, 14);}
		catch (Exception e){assertTrue(e instanceof IllegalArgumentException);}
		try{ht.put("e1", null);}
		catch (Exception e){assertTrue(e instanceof IllegalArgumentException);}
		try{ht.put(null, null);}
		catch (Exception e){assertTrue(e instanceof IllegalArgumentException);}
	}
	
	public void testGet0() {
		// e and y have same hash value for small table
		for (String s: data){
			assertEquals(null,ht.get(s));
			ht.put(s, s.hashCode());
			assertEquals(s.hashCode(),(int) ht.get(s));
		}
	}
	
	public void testGet1() {
		ht.put("e",1);
		assertEquals(1,(int)ht.get("e"));
		assertEquals(null,ht.get(new Object()));
		assertEquals(null,ht.get("y"));
		
		try{ht.get(null);}
		catch (Exception e){assertTrue(e instanceof IllegalArgumentException);}
	}
	
	public void testContainsKey(){
		for (String s: data){
			assertEquals(false,ht.containsKey(s));
			ht.put(s, s.hashCode());
			assertEquals(true, ht.containsKey(s));
			assertEquals(false, ht.containsKey(new Character(s.charAt(0))));
		}
	}
	
	public void testRemove0(){
		ht.put("e1",1);
		ht.put("e2",2);
		ht.put("e3",3);
		ht.put("e4",4);
		ht.put("e5",5);
		assertEquals(3,(int) ht.remove("e3"));
		assertEquals(null, ht.remove("e3"));
		assertEquals(null, ht.put("e3", 2));
		assertEquals(null, ht.remove(new Object()));
		
		try{ht.remove(null);}
		catch (Exception e){assertTrue(e instanceof IllegalArgumentException);}
	}
	
	public void testRehash() {
		for(int i=0; i<10; i++)
			ht.put("e" + i, i);
		for(int i=0; i<10; i++)
			assertEquals(new Integer(i), ht.get("e" + i));
		for(int i=10; i<20; i++)
			ht.put("e" + i, i);
		for(int i=0; i<20; i++)
			assertEquals(new Integer(i), ht.get("e" + i));
		for(int i=20; i<100; i++)
			ht.put("e" + i, i);
		for(int i=0; i<100; i++)
			assertEquals(new Integer(i), ht.get("e" + i));
	}
	
	public void testRemove1(){
		for (String s: data)
			ht.put(s, s.hashCode());
		
		Collection<String> removed = new ArrayList<String>();
		
		for (String s: data)
			if (Math.random()<0.5){
				removed.add(s);
				ht.remove(s);}
		
		assertEquals(removed.size(),data.length-ht.size());
		
		for (String s: removed){
			assertFalse(ht.containsKey(s));
			assertFalse(ht.containsValue(s.hashCode()));
			assertNull(ht.get(s));}
	}
	
	//Combination
	public void test01(){
		ht.put("e",1);
		ht.put("f",2);
		ht.put("g",3);
		ht.put("h",4);
		ht.put("i",5);
		
		assertEquals(null, ht.remove('e'));
		assertEquals(Ti(1255898203),(int) ht.remove("h"));
		assertEquals(Ti(463111035),ht.size());
		
		assertEquals(Tb(538873027),ht.containsKey("h"));
		assertEquals(Tb(763111404),ht.containsValue(3));
		assertEquals(Tb(1522885692),ht.containsValue(4));
		assertEquals(Ti(112629943),(int) ht.remove("e"));
		assertEquals(Ti(787946402),(int) ht.put("f",99));
		assertEquals(Ti(436276989),ht.size());
		
		assertEquals(99,(int) ht.get("f"));
		assertEquals(3,(int) ht.get("g"));
		assertEquals(5,(int) ht.get("i"));
		assertNull(ht.get("e"));
		assertNull(ht.get("h"));
	}
	
	//Entry Set
	public void testEntrySet(){
		Set<Entry<String,Integer>> entrySet = ht.entrySet();
		assertEquals(0,entrySet.size());//
		
		for (String s: data)
			ht.put(s, s.hashCode());
		assertEquals(26,entrySet.size());

		//mark for removal
		Collection<Entry<String,Integer>> removed = new ArrayList<>();
		for (Entry<String,Integer> e: entrySet)
			if (Math.random()<0.5)
				removed.add(e);
		
		//remove from set
		for (Entry<String,Integer> e: removed)
			entrySet.remove(e);
		
		assertEquals(removed.size(),data.length-ht.size());
		assertEquals(removed.size(),data.length-entrySet.size());
		
		for (Entry<String,Integer> e: removed){
			assertFalse(entrySet.contains(e));
			assertFalse(ht.containsKey(e.getKey()));
			assertFalse(ht.containsValue(e.getValue()));
			assertNull(ht.get(e.getKey()));}
		
		entrySet.clear();
		assertEquals(0,entrySet.size());
		assertEquals(0,ht.size());
	}
	
	//Iterator
	public void test03(){
		ht.put("e1",1);
		ht.put("e2",2);
		ht.put("e3",3);
		ht.put("e5",5);
		ht.put("f0", 9);
		//Table: [{e1:1,f0:9},{e2:2},{e3:3},{},{e5:5},{},{},{},{},{}]
		
		it = ht.entrySet().iterator();
		assertTrue(it.hasNext());
		assertEquals(Ts(1482018022),it.next().getKey());//
		assertEquals(Ts(1707638717),it.next().getKey());
		assertEquals(Ti(659096442),(int) it.next().getValue());//
		assertEquals(Ts(26295200),it.next().getKey());//
		assertEquals(Tb(297009907),it.hasNext());//
		assertEquals(Ts(1994506124),it.next().getKey());//
		assertEquals(false,it.hasNext());
	}
	
	public void testIteratorRemove0(){
		ht.put("e1",1);
		ht.put("e2",2);
		ht.put("e3",3);
		ht.put("e5",5);
		ht.put("f0", 9);
		//Table: [{e1:1,f0:9},{e2:2},{e3:3},{},{e5:5},{},{},{},{},{}]
		
		it = ht.entrySet().iterator();
		assertTrue(it.hasNext());
		assertEquals("e1",it.next().getKey());
		it.remove();
		assertTrue(it.hasNext());
		assertEquals(9,(int) it.next().getValue());
		assertEquals("e2",it.next().getKey());
		assertEquals("e3",it.next().getKey());
		it.remove();
		assertTrue(it.hasNext());
		it.next();
		it.remove();
		assertFalse(it.hasNext());
		assertFalse(ht.containsKey("e1"));
		assertFalse(ht.containsKey("e5"));
	}
	
	public void testIteratorRemove1(){
		for (String s: data)
			ht.put(s, s.hashCode());
		
		ArrayList<String> removed = new ArrayList<>();
		
		it = ht.entrySet().iterator();
		while (it.hasNext()){
			if (Math.random()<0.5){
				removed.add(it.next().getKey());
				it.remove();}
		}
		
		for (String s: removed){
			assertFalse(ht.containsKey(s));
			assertFalse(ht.containsValue(s.hashCode()));}
		
		assertEquals(ht.size(),data.length-removed.size());
		assertEquals(ht.entrySet().size(),data.length-removed.size());
	}
	
	public void testIteratorFailFast(){
		ht.put("e1",1);
		ht.put("e2",2);
		ht.put("e3",3);
		ht.put("e4",4);
		it = ht.entrySet().iterator();
		ht.put("e5",5);
		try{it.hasNext();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		try{it.next();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		try{it.remove();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		
		it = ht.entrySet().iterator();
		
		//shouldn't make iterator fail
		ht.containsKey("e1");
		ht.get("e1");
		it.hasNext();
		it.next();
		it.remove();
		
		//should make iterator fail
		ht.remove("e5");
		try{it.hasNext();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		try{it.next();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		try{it.remove();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
	}
	
	public void testIteratorDouble(){
		ht.put("e1",1);
		ht.put("e2",2);
		ht.put("e3",3);
		ht.put("e4",4);
		ht.put("e5",5);
		Iterator<Entry<String,Integer>> it1 = ht.entrySet().iterator();
		Iterator<Entry<String,Integer>> it2 = ht.entrySet().iterator();
		
		assertEquals("e1",it1.next().getKey());
		assertEquals("e2",it1.next().getKey());
		assertEquals("e1",it2.next().getKey());
		it1.remove();
		
		try{it2.hasNext();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		try{it2.next();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		try{it2.remove();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		
		assertTrue(it1.hasNext());
		assertEquals("e3",it1.next().getKey());
		it2 = ht.entrySet().iterator();
		assertEquals("e1",it2.next().getKey());
		assertEquals("e3",it2.next().getKey());
		assertEquals("e4",it2.next().getKey());
		it2.remove();
		
		try{it1.hasNext();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		try{it1.next();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
		try{it1.remove();}
		catch (Exception e){assertTrue(e instanceof ConcurrentModificationException);}
	}
}
