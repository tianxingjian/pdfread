package nc.util.algori;

import nc.vo.pub.lang.UFDouble;

import org.apache.poi.ss.formula.functions.T;

public class AgoriNode implements Comparable<AgoriNode>{
	private String key;
	private UFDouble value;
	private Integer index;
	
	public AgoriNode(){};
	
	public AgoriNode(String key, UFDouble value, Integer index){
		this.key = key;
		this.value = value;
		this.index = index;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public UFDouble getValue() {
		return value;
	}
	public void setValue(UFDouble value) {
		this.value = value;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public int compareTo(AgoriNode o) {
		return this.getValue().compareTo(o.getValue());
	}
	
	public String toString(){
		return "Node: " + key + "," + value + "," + index;
	}
}
