/**************************************************************************
 * $$RCSfile: Getter.java,v $$  $$Revision: 1.6 $$  $$Date: 2010/04/20 02:08:08 $$
 *
 * $$Log: Getter.java,v $
 * $Revision 1.6  2010/04/20 02:08:08  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.property.propertys;

import gxlu.ietools.property.exception.PropertyException;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Gets values of a particular property
 *
 * @author kidd
 */
public interface Getter extends Serializable {

	/**
	 * ��ʼ�����Ը�ֵ
	 * @param params
	 * @return
	 * @throws PropertyException
	 */
	public Object[][] initPropertyArray(List params,Object bobject) throws PropertyException;
	
	/**
	 * ʹ�÷�����ƶ�Ӧ��̬�ಢ��ֵ
	 * @param params ģ�������ֶ���Ϣ
	 *	 param0 --Property*-- Property/Object-to-ObjectԪ��ֵ
	 *
	 * @param bobject B�����
	 * @throws PropertyException
	 */
	public void set(List params,Object[][] objs,List dataList) throws PropertyException;
	
	/**
	 * ���ֵ���Ԫ�ؽ��е���ת��
	 * @param params
	 * @param objs
	 * @throws PropertyException
	 */
	public void setDictionaryValue(List params,Object[][] objs) throws PropertyException;

	/**
	 * ʹ�÷�����ƻ�ñ�ͷ���󲢴�������
	 * @param params --Property*-- Property/Object-to-ObjectԪ��ֵ
	 * @param dataList 
	 * @param rowNumber
	 * @throws PropertyException
	 */
	public void setTitle(List params, List dataList) throws PropertyException;
	
	/**
	 * ʹ�÷�����Ʒ��ر�ͷ����
	 * @param params --Property*-- Property/Object-to-ObjectԪ��ֵ
	 * @return
	 * @throws PropertyException
	 */
	public Object getTitle(List params) throws PropertyException;

}
