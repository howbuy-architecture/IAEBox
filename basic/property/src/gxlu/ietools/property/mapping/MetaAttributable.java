/**************************************************************************
 * $$RCSfile: MetaAttributable.java,v $$  $$Revision: 1.6 $$  $$Date: 2010/04/20 02:08:05 $$
 *
 * $$Log: MetaAttributable.java,v $
 * $Revision 1.6  2010/04/20 02:08:05  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.property.mapping;

import gxlu.ietools.basic.collection.util.DynamicObject;
import gxlu.ietools.property.exception.PropertyException;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Common interface for things that can meta attributes.
 * @author kidd
 */
public abstract class MetaAttributable {

	/**
	 * �������
	 * @param metas
	 * 		
	 * @return
	 * @throws PropertyException 
	 */
	public abstract List getExportAttributes(java.util.Map metas) throws PropertyException;

	/**
	 * �������
	 * @param metas
	 * 		dynamicObj -- ��̬���󼯺�
	 *		propertyValue -- PropertyԪ��ֵ����
	 * @return
	 * @throws PropertyException 
	 */
	public abstract List getImportAttribute(java.util.Map metas) throws PropertyException;

	/**
	 * ��ȡexcelͷ��Ϣ
	 * @param metas
	 * @return
	 * @throws PropertyException
	 */
	public abstract DynamicObject getTitleAttribute(java.util.Map metas) throws PropertyException;
}
