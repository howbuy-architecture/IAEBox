package gxlu.ietools.property.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import gxlu.ietools.property.mapping.Property;

public class DomHelper{
	private DomParse domParse;
	public DomHelper(DomParse domParse){
		this.domParse = domParse;
	}
	
	public List readDomParseList() {
		List params = domParse.getTemplateFileList();
		List domList = new ArrayList();
		List propertyList=null;
		if(params.size()>0){
			for(int i=0;i<params.size();i++){
				propertyList=new ArrayList();
				propertyList.add(params.get(i));
				Property property = (Property)domParse.readDomParse(propertyList,0);
				propertyList.add(property);
				domList.add(propertyList);
			}
		}
		return domList;
	}
	
	/**
	 * ������֧�ֵ��뵼����B���װ��Property
	 * @return ��װ��Property���B�༯��
	 */
	public List getAllPropertyList(){
		List params = domParse.getTemplateFileList();
		List bClassList=new ArrayList();
		List propertyList=null;
		for(int i=0;i<params.size();i++){
			propertyList=new ArrayList();
			propertyList.add(params.get(i));
			Property property = (Property)domParse.readDomParse(propertyList,0);
			propertyList.clear();
			propertyList.add(property.getBclass());
			propertyList.add(property.getCname());
			property = (Property)domParse.getAllProperty(propertyList);
			bClassList.add(property);
		}
		return bClassList;
	}
	
	public List readDomParseAllList() {
		List params = domParse.getTemplateFileList();
		List domList = new ArrayList();
		List propertyList=null;
		if(params.size()>0){
			for(int i=0;i<params.size();i++){
				propertyList=new ArrayList();
				propertyList.add(params.get(i));
				Property property = (Property)domParse.readDomParse(propertyList,2);
				propertyList.add(property);
				domList.add(propertyList);
			}
		}
		return domList;
	}
	
	/**
	 * ������֧�ֵ��뵼����B���װ��Property
	 * @return ��װ��Property���B�༯��
	 */
	public List getAllPropertyAllList(){
		List params = domParse.getTemplateFileList();
		List bClassList=new ArrayList();
		List propertyList=null;
		for(int i=0;i<params.size();i++){
			propertyList=new ArrayList();
			propertyList.add(params.get(i));
			Property property = (Property)domParse.readDomParse(propertyList,2);
			propertyList.clear();
			propertyList.add(property.getBclass());
			propertyList.add(property.getCname());
			property = (Property)domParse.getAllProperty(propertyList);
			bClassList.add(property);
		}
		return bClassList;
	}
	
    public List getPropertyList(List params){
    	Property property = (Property)domParse.readDomParse(params,0);
    	List returnList = new ArrayList();
    	returnList.addAll(property.getPropertyValue());
    	returnList.addAll(property.getPropertyObject());
    	return returnList;
    }
    
    public Property getProperty(List params){
    	Property property = (Property)domParse.readDomParse(params,0);
    	return property;
    }
	
	
	public List readDomParseEngineList() {
		List params = domParse.getTemplateFileList();
		List domList = new ArrayList();
		List propertyList=null;
		if(params.size()>0){
			for(int i=0;i<params.size();i++){
				propertyList=new ArrayList();
				propertyList.add(params.get(i));
				Property property = (Property)domParse.readDomParse(propertyList,0);
				propertyList.add(property);
				domList.add(propertyList);
			}
		}
		return domList;
	}

	/**
	 * ������֧�ֵ��뵼����B���װ��Property
	 * ����ʹ��
	 * @return ��װ��Property���B�༯��
	 */
	public List getAllPropertyEngineList(){
		List params = domParse.getTemplateFileList();
		List bClassList=new ArrayList();
		List propertyList=null;
		for(int i=0;i<params.size();i++){
			propertyList=new ArrayList();
			propertyList.add(params.get(i));
			Property property = (Property)domParse.readDomParse(propertyList,1);
			propertyList.clear();
			propertyList.add(property.getBclass());
			propertyList.add(property.getCname());
			property = (Property)domParse.getAllProperty(propertyList);
			bClassList.add(property);
		}
		return bClassList;
	}
	
    /**
     * ����ʹ��
     * @param params
     * @return
     */
    public List getPropertyEngineList(List params){
    	Property property = (Property)domParse.readDomParse(params,1);
    	List returnList = new ArrayList();
    	returnList.addAll(property.getPropertyValue());
    	returnList.addAll(property.getPropertyObject());
    	return returnList;
    }
    
    /**
     * ����ʹ��
     * @param params
     * @return
     */
    public Property getPropertyEngine(List params){
    	Property property = (Property)domParse.readDomParse(params,1);
    	return property;
    }

	/**
	 * @param property
	 */
	public void propertyOrderbyCol(Property property){
		domParse.propertyOrderbyCol(property);
	}

	/**
	 * ����ģ��
	 * @param params
	 * @param object
	 * @return
	 */
	public List updateDomParse(List params,Object object){
		return this.domParse.writeDomParse(params, object);
	}

	/**
	 * ���ģ��·������
	 * @return
	 */
	public List getTemplateFileList() {
		return this.domParse.getTemplateFileList();
	}
    
    /**
     * ���ݹ���ģʽ�ж���λ�ȡģ����Ϣ
     * @param node
     * @param workFlag
     * @return
     */
    public static boolean getQueryTagCheck(Node node,int workFlag){
		boolean queryTagCheck = false;
		if(workFlag==0){
			String isQuery = node.getAttributes().getNamedItem("isQuery").getNodeValue();
			if(ElementsNoteNames.ISQUERY_ALL.equalsIgnoreCase(isQuery)||ElementsNoteNames.ISQUERY_SEARCH.equalsIgnoreCase(isQuery)){
				queryTagCheck = true;
			}
		}else if(workFlag==1){
			String isQuery = node.getAttributes().getNamedItem("isQuery").getNodeValue();
			if(ElementsNoteNames.ISQUERY_ALL.equalsIgnoreCase(isQuery)||ElementsNoteNames.ISQUERY_NONE.equalsIgnoreCase(isQuery)){
				queryTagCheck = true;
			}
		}else{
			queryTagCheck = true;
		}
		return queryTagCheck;
    }
}
