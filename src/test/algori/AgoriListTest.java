package test.algori;

import java.util.Random;

import nc.util.algori.AgoriList;
import nc.util.algori.AgoriNode;
import nc.vo.pub.lang.UFDouble;

import org.junit.Test;

public class AgoriListTest {

	@Test
	public void test() {
		AgoriList list = new AgoriList();
		Random rand = new Random();
		for(int i = 0; i < 5; i++){
			AgoriNode node = new AgoriNode();
			String key = "key" + i;
			double d = rand.nextDouble();
			UFDouble value = new UFDouble(d);
			node.setKey(key);
			node.setValue(value);
			node.setIndex(i);
			list.insert(node);
		}
		
		for(int i = 0; i < list.getLength(); i++){
			System.out.println(list.getNode(i));
		}
	}

}
