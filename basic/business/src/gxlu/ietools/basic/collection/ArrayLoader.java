package gxlu.ietools.basic.collection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gxlu.ietools.basic.collection.util.DynamicObject;
import gxlu.ietools.basic.collection.util.ResultController;
import gxlu.ietools.basic.system.container.ContainerException;
import gxlu.ietools.basic.system.container.ContainerFactory;
import gxlu.ietools.basic.system.context.Context;
import gxlu.ietools.basic.system.context.SessionBeanImpl;
import gxlu.ietools.basic.system.util.ClassNoteNames;
import gxlu.ietools.basic.system.util.ExectionUtil;
import gxlu.ietools.basic.system.util.MethodNoteNames;
import gxlu.ietools.basic.threads.CalledThread;
import gxlu.ietools.basic.threads.TaskProcessors;
import gxlu.ietools.property.exception.PropertyException;
import gxlu.ietools.property.mapping.Property;
import gxlu.ietools.property.propertys.Getter;
import gxlu.ietools.property.propertys.PropertyAccessor;
import gxlu.ietools.property.propertys.PropertyAccessorFactory;
import gxlu.ietools.property.xml.DomHelper;
import gxlu.ietools.property.xml.DomTemplateParse;

public abstract class ArrayLoader extends SessionBeanImpl{

	protected List propertyList;
	protected Property property;
	protected String bobjectInfo;
	
	public void setBobjectInfo(String bobjectInfo) {
		this.bobjectInfo = bobjectInfo;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public void setPropertyList(List propertyList) {
		this.propertyList = propertyList;
	}

	/**
	 * @param bobject*-- B����� 
	 * @param objectList*-- ���ݶ��󼯺�
	 * @param propertyList*-- ģ��Ԫ�ؼ���
	 * @param rowsCount*-- ����ͳ������
	 * 			-- [0]* -- ������
	 *      	-- [1]* -- ��ʼ����
	 *      	-- [2]* -- ��������
	 *      	-- [3]* -- ��ǰ����
	 *      	-- [4]* -- �̱߳��
	 * @param workFlag*-- 1-���� 2-����
	 * @return
	 */
	protected abstract ResultController getBObjectListAction(Object bobject,List objectList,List propertyList,String bobjectInfo,int[] rowsCount,int workFlag);
	
	/**
	 * ��֤�����ϵ����֤
	 * @param propertyList
	 * @param objectList
	 * @param workFlag
	 * @return
	 * @throws PropertyException
	 */
	protected abstract Object[] getChkError(List propertyList,List objectList,int workFlag);
	
	/**
	 * ��������:���ݴ���B��������л�ü���
	 * 
	 * @param Object*-- B�����
	 * @param objectList*-- ���󼯺�
	 * @param workFlag*-- 1-���� 2-����
	 *
	 * @return ���󼯺�
	 * 		result
	 * 			-- ��� workFlag = 1��������Ϊbobject
	 *      	-- ��� workFlag = 2��������ΪDynamicObject
	 * 		objectValueCount* -- int
	 * 			-- ����
	 * 		typeConvertError* -- ��� workFlag = 1 Ϊ���룬�����ش�����Ϣ
	 * 			-- Object[]
	 *				- object[2]
	 * 				 + Integer* ����
	 * 				 + String* ����ԭ��
	 * 
	 * 		property* -- ����ģ������
	 * 
	 * 		operationMessage* -- ���������Ϣ
	 * 			-- Object[]
	 *				- object[2]
	 * 				 + Boolean* true:��ȷ false:����
	 * 		 		 + String* ����ԭ��
	 */
	public ResultController getBObjectList(Object bobject,List objectList,int workFlag){
		ResultController resultController = new ResultController();
		Context context = ExectionUtil.getContext();
		try {
			String bobjectInfo = (String)context.lookupBObjectInfo(bobject);
			setBobjectInfo(bobjectInfo);
			
			DomHelper domHelper = new DomHelper(new DomTemplateParse());
			List params = new ArrayList();
			params.add(0,bobjectInfo);
			setPropertyList(domHelper.getPropertyEngineList(params));
			setProperty(domHelper.getPropertyEngine(params));
			
			int thdLineMax = Integer.parseInt(property.getThdLineMax());
			int thdLineNum = Integer.parseInt(property.getThdLineNum());
			int objectNum = objectList.size();

			//�ڽ�������ǰ��Ҫ����֤�����ϵ����֤
			Object[] isCheckTitle = getChkError(propertyList,objectList,workFlag);
			
			if(((Boolean)isCheckTitle[0]).booleanValue()){
				//����ǵ��룬ȥ����һ�еı�ͷ����
				if(workFlag==1){
					objectList.remove(0);
					objectNum--;
				}
				
				context.removeObjectList();
        context.removeAllProperty();
        context.removeAllObjectListMap();
				if(workFlag==2){
					List valueList = new ArrayList();
					PropertyAccessor propertyAccessor = PropertyAccessorFactory.getDynamicMapPropertyAccessor();
					Getter getter = propertyAccessor.getGetter(DynamicObject.class);
					getter.setTitle(propertyList,valueList);
					ContainerFactory.addObjectData(valueList);
				}
				
				//�ж��Ƿ��������̻߳���
				if(thdLineMax==0||objectNum<thdLineMax){
					int[] rowsCount = new int[4];
					rowsCount[0] = objectNum;//������
					rowsCount[1] = 0;
					rowsCount[2] = objectNum;
					resultController = getBObjectListAction(bobject,objectList,propertyList,bobjectInfo,rowsCount,workFlag);
				}else{
					if(objectNum>=thdLineMax){
						BigDecimal threadCount = new BigDecimal((float)objectNum/thdLineNum+1);
						
						TaskProcessors taskProcessors = new TaskProcessors(threadCount.intValue());
						taskProcessors.startWorkers();
						
						for(int i=0;i<threadCount.intValue();i++){
							int firstNum = i*thdLineNum;
							int lastNum = i*thdLineNum+thdLineNum;
							if(lastNum>objectNum){
								lastNum = objectNum;
							}
							int[] rowsCount = new int[4];
							rowsCount[0] = objectNum;
							rowsCount[1] = firstNum;
							rowsCount[2] = lastNum;
							
							List iParams = new ArrayList();
							iParams.add(0,bobject);
							iParams.add(1,objectList.subList(firstNum, lastNum));
							iParams.add(2,new Integer(workFlag));
							iParams.add(3,rowsCount);
							iParams.add(4,ClassNoteNames.ARRAYLOADER_BEAN);
							iParams.add(5,MethodNoteNames.BOBJECTLIST_ACTION);
							iParams.add(6,propertyList);
							iParams.add(7,bobjectInfo);
					        CalledThread alice = new CalledThread(iParams, taskProcessors);
					        alice.start();
						}
						
						listenThread(objectNum);
						
						taskProcessors.stopAllWorkers();
					}
				}
				ContainerFactory.addProperty(this.property);
        resultController.setPropertyMap(ContainerFactory.getAllProperty());
        resultController.setObjectListMap(ContainerFactory.getObjectListMap());
				resultController.setResult(ContainerFactory.getObjectList());
				resultController.setTypeConvertError(getTypeConvertError());
				resultController.setObjectValueCount(propertyList.size());
				resultController.setProperty(property);
			}
			resultController.setOperationMessage(isCheckTitle);

		} catch (ContainerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace().toString());			
			logger.error(e.toString());
			logger.error(e.getMessage());
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace().toString());			
			logger.error(e.toString());
			logger.error(e.getMessage());
		}
		return resultController;
	}

	/**
	 * ���ݴ����B��ִ�еõ����⶯̬����Ĺ���
	 * @param bobject B�����
	 * @return
	 */
	public abstract ResultController getTitleListAction(Object bobject);
	
	/**
	 * ��������:���ݴ���B��õ�����
	 * @param bobject B�����
	 * @return 
	 */
	public ResultController getTitleList(Object bobject){
		try {
			String bobjectInfo = (String)context.lookupBObjectInfo(bobject);
			setBobjectInfo(bobjectInfo);
			
			DomHelper domHelper = new DomHelper(new DomTemplateParse());
			List params = new ArrayList();
			params.add(0,bobjectInfo);
			setPropertyList(domHelper.getPropertyEngineList(params));
			setProperty(domHelper.getPropertyEngine(params));
		} catch (ContainerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace().toString());			
			logger.error(e.toString());
			logger.error(e.getMessage());
		}
		
		ResultController resultController = getTitleListAction(bobject);
		return resultController;
		
	}
	
	/**
	 * �����߳��Ƿ��Ѿ���ɹ���
	 * @param objectNum
	 */
	protected void listenThread(int objectNum){
		while(true){
			LinkedList objectList = ContainerFactory.getObjectList();
			Object[] typeConErr = PropertyAccessorFactory.getTypeConvertError();
			//ͨ����������+�����������=�ܶ�������
			if(objectList!=null&&typeConErr!=null){
				int objectSize = objectList.size() + typeConErr.length;
				if(objectSize>=objectNum){
					break;
				}
			}
		}
	}
	
	/**
	 * ����ת��������Ϣ
	 * 		-- Object[]
	 *			- object[2]
	 * 				+ Integer* ����
	 * 				+ String* ����ԭ��
	 * @return
	 */
	private Object[] getTypeConvertError() {
		Object[] objects = null;
		try {
			objects = PropertyAccessorFactory.getTypeConvertError();
			PropertyAccessorFactory.removeTypeConvertError();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace().toString());
			logger.error(e.toString());
		}
		return objects;
	}
}
