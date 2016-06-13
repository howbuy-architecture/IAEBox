/**************************************************************************
 * $$RCSfile: Variable.java,v $$  $$Revision: 1.6 $$  $$Date: 2010/04/20 02:08:06 $$
 *
 * $$Log: Variable.java,v $
 * $Revision 1.6  2010/04/20 02:08:06  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.basic.elements.variables;

import java.util.*;

/**
 * Variables Interface.
 * @author kidd
 */
public abstract class Variable {

	/**
	 * ���䷽�����ض���
	 * @return
	 */
	abstract public Object getWrappedObject();
    
    /**
     * ���뷵�ؼ���
     * @param object
     */
    abstract public void setObjectList(Object object);

    /**
     * ������϶���ֵ
     * @param object
     */
    abstract public void setWrappedObject(Object object);
    
    /**
     * ���ػ�ȡ����
     * @return
     */
    abstract public List getObjectList();
    
    /**
     * ȷ�ϼ����з���ɾ�������Ķ���
     * @param value
     * @return
     */
    abstract public boolean getCheckVariable(Object value);

    /**
     * Safely converts this variable to array of objects.
     * @return array of objects
     */
    public List toArray() {
        Object wrappedObject = getWrappedObject();
        if (wrappedObject == null) {
        	return new ArrayList();
        }else{
        	return (List)wrappedObject;
        }
        
    }

    /** xmlNode������� **/
	public int xmlNodeLen = 0;

    public int getXmlNodeLen() {
		return xmlNodeLen;
	}

	public void setXmlNodeLen(int xmlNodeLen) {
		this.xmlNodeLen = xmlNodeLen;
	}
}