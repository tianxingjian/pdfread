package nc.util.algori;

import java.util.LinkedList;
import java.util.List;


public class AgoriList {
	private List<AgoriNode> list;
	
	public AgoriList(){
		list = new LinkedList<AgoriNode>();
	}
	
	/**
	 * ���뷽������AgoriNodeֵ��С��������
	 * @param node
	 * @return
	 */
	public int add(AgoriNode node){
		list.add(node);
		return list.size();
	}
	
	public int insert(AgoriNode node){
		if(node == null){
			return -1;
		}
		boolean isInsert = false;
		int index = -1;
		int len = list.size();
		for(int i = 0; i < len; i++){
			if(node.compareTo(list.get(i)) >= 0){
				list.add(i, node);
				index = i;
				isInsert = true;
				break;
			}
		}
		if(!isInsert){
			list.add(node);
			index = list.size() + 1;
		}
		return index;
	}
	
	public int getLength(){
		return list.size();
	}
	
	/**
	 * 取单个元素
	 * @param i
	 * @return
	 */
	public AgoriNode getNode(int i){
		if(list == null || list.size() <= i){
			return null;
		}else{
			return list.get(i);
		}
	}
}
