/**************************************************************************
 * $$RCSfile: PropertyAccessor.java,v $$  $$Revision: 1.6 $$  $$Date: 2010/04/20 02:08:08 $$
 *
 * $$Log: PropertyAccessor.java,v $
 * $Revision 1.6  2010/04/20 02:08:08  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.property.propertys;

import gxlu.ietools.property.exception.PropertyNotFoundException;

/**
 * Abstracts the notion of a "property". Defines a strategy for accessing the
 * value of an attribute.
 * @author kidd
 */
public interface PropertyAccessor {

	/**
	 * ��������
	 * @param theClass B��
	 * @param propertyName ģ���ļ�����
	 * @return
	 * @throws PropertyNotFoundException
	 */
	public Getter getGetter(Class theClass) throws PropertyNotFoundException;

	/**
	 * ��������
	 * @param theClass B�����
	 * @return
	 * @throws PropertyNotFoundException
	 */
	public Setter getSetter(Class theClass) throws PropertyNotFoundException;
}
