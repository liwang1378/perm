package com.huatec.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.huatec.model.Resource;

public class ResourceTreeUtils {
	public static List<Resource> createTree(List<Resource> list) {
		List<Resource> children =  new ArrayList<Resource>();
		for(Resource menu : list){
			if(0 == menu.getPid()){
				initChildren(menu,list);
				children.add(menu);
			}
		}
		
		return children;
	}
	
	private static void initChildren(Resource treeNode, List<Resource> list) {
		List<Resource> children =  new ArrayList<Resource>();
		for(Resource node : list){
			Integer pid = treeNode.getId();
			
			if(node.getPid()!=null && pid.compareTo(node.getPid())==0){
				initChildren(node,list);
				children.add(node);
			}
		}
		Collections.sort(children, new Comparator<Resource>() {
			public int compare(Resource o1, Resource o2) {
				int i = o1.getSort() - o2.getSort();  
                if(i == 0){  
                    return 0;  
                }  
                return i; 
			};
		});
		treeNode.setChildren(children);
	}
}
