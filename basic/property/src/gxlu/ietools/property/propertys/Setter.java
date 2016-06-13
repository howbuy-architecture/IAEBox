/**************************************************************************
 * $$RCSfile: Setter.java,v $$  $$Revision: 1.6 $$  $$Date: 2010/04/20 02:08:08 $$
 *
 * $$Log: Setter.java,v $
 * $Revision 1.6  2010/04/20 02:08:08  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.property.propertys;

import gxlu.ietools.basic.collection.util.ResultController;
import gxlu.ietools.property.exception.PropertyException;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Sets values to a particular property.
 * 
 * @author kidd
 */
public interface Setter extends Serializable {

	/**
	 * ��ʼ�����Ը�ֵ
	 * @param params
	 * @return
	 * @throws PropertyException
	 */
	public Object[][] initPropertyArray(List params,ResultController resultCol) throws PropertyException;
	
	/**
	 * ���÷�������ж���������
	 * 
	 * @param params ģ�������ֶ���Ϣ
	 * 		param0 --Object*-- Property/Object-to-ObjectԪ��ֵ
	 * 
	 * @param resultCol
	 * 		dynamicObject -- Excel��������
	 * @return
	 * @throws PropertyException
	 */
	public void setAccordDataValue(List params,Object[][] objs) throws PropertyException;
	
	/**
	 * ���ֵ���Ԫ�ؽ��е���ת��
	 * @param params
	 * @param objs
	 * @throws PropertyException
	 */
	public void setDictionaryValue(List params,Object[][] objs) throws PropertyException;
	
	/**
	 * B���Ͷ���ֵ�����˷���Ϊ�ն���
	 * @param params
	 * @param object
	 * @throws PropertyException
	 */
	public void setObjectToObjectValue(List params,Object[][] object) throws PropertyException;
	
	/**
	 * �����޸��ֵ
	 * @param params
	 * @param object
	 * @throws PropertyException
	 */
	public void setUpdateEvaluate(List params,Object[][] object, List dataList) throws PropertyException;
	
	/**
	 * ʹ�÷�����ƶ�ӦB�ಢ��ֵ
	 * @param params ģ�������ֶ���Ϣ	  
	 *	 param0 --Object*-- Property/Object-to-ObjectԪ��ֵ
	 *
	 * @param value ��������
	 * @throws PropertyException
	 */
	public void set(List params, Object[][] value, List dataList) throws PropertyException;
	
	/**
	 * ����ת����֤
	 * @param object ��Excel�ж�ȡ��ֵ
	 * 		-- Object[]
	 *			- object[2]
	 * 				+ boolean* true:��ȷ false:����
	 * 		 		+ 
	 * @param params ģ�������ֶ���Ϣ
	 * 		param0 --String*-- ��Excel�ж�ȡ��ֵ
	 * 		param1 --String* -- ��������
	 * 		param2 --String*-- Title
	 * 
	 * @param rowNumber Excel����
	 * @return
	 * @throws PropertyException
	 */
	public Object[] getTypeConvert(String[] object,int rowNumber);

}
