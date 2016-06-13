/**************************************************************************
 * $$RCSfile: Context.java,v $$  $$Revision: 1.7 $$  $$Date: 2010/05/06 07:10:44 $$
 *
 * $$Log: Context.java,v $
 * $Revision 1.7  2010/05/06 07:10:44  zhangj
 * $*** empty log message ***
 * $
 * $Revision 1.6  2010/04/20 02:08:06  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.basic.system.context;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import gxlu.ietools.basic.system.container.ContainerException;

/**
 * @author kidd
 * @version 1.0
 */
public interface Context {

	/**
	 * 
	 * @param name
	 * @return
	 * @throws ContainerException
	 */
	public SessionBean lookupSessionBean(String name) throws ContainerException;

	/**
	 * ����Ԫ�ع��˻����漰��
	 * 
	 * @param name
	 * @return
	 * @throws ContainerException
	 */
	public Object lookupElementDef(String name) throws ContainerException;

	/**
	 * ʹ��ģ��·������B��
	 * 
	 * @param objectInfo
	 * @return
	 * @throws ContainerException
	 */
	public Class lookupBObject(String objectInfo) throws ContainerException;

	/**
	 * ʹ��B�෵��ģ��·��
	 * 
	 * @param objectClass
	 * @return
	 * @throws ContainerException
	 */
	public String lookupBObjectInfo(Object objectClass)
			throws ContainerException;

	/**
	 * �������
	 * 
	 * @param name
	 * @return
	 * @throws ContainerException
	 */
	public Object lookupTagInfo(String name) throws ContainerException;

	/**
	 * ɾ��ָ����ǩֵ
	 * 
	 * @param name
	 * @throws ContainerException
	 */
	public void removeTagInfo(String name) throws ContainerException;

	/**
	 * ��ȡ�����еĴ���ԭ��
	 * 
	 * @return objects[] - object[2] + Integer* ���� + String* ����ԭ��
	 * @throws ContainerException
	 */
	public Object[] lookupTypeConvertError() throws ContainerException;

	/**
	 * ��������еĴ���ԭ��
	 * 
	 * @throws ContainerException
	 */
	public void removeTypeConvertError() throws ContainerException;

	/**
	 * @throws ContainerException
	 */
	public LinkedList lookupObjectList() throws ContainerException;

	/**
	 * @throws ContainerException
	 */
	public void removeObjectList() throws ContainerException;

	public abstract Map lookupAllProperty() throws ContainerException;

	public abstract void removeAllProperty() throws ContainerException;

	public abstract Map lookupObjectListByKey(Object paramObject) throws ContainerException;

	public abstract Map lookupObjectListMap() throws ContainerException;

	public abstract void removeObjectListByObjkey(Object paramObject) throws ContainerException;

	public abstract void removeAllObjectListMap() throws ContainerException;
}