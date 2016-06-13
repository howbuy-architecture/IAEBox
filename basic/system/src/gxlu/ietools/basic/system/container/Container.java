/**************************************************************************
 * $$RCSfile: Container.java,v $$  $$Revision: 1.7 $$  $$Date: 2010/05/06 07:10:44 $$
 *
 * $$Log: Container.java,v $
 * $Revision 1.7  2010/05/06 07:10:44  zhangj
 * $*** empty log message ***
 * $
 * $Revision 1.6  2010/04/20 02:08:06  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.basic.system.container;

import java.util.List;

import gxlu.ietools.basic.system.context.Context;
import gxlu.ietools.property.mapping.Property;

/** 
 *
 * @author Kidd
 * @version 1.0
 */
public interface Container {

    /**
     * ����������
     * @return
     */
    public abstract Context newContext();
    
    /**
     * ���ػỰ������
     * @param beanName
     * @param beanInfo
     */
    public abstract void addSessionBean(String beanName, String beanInfo);
    
    /**
     * ����Ԫ�ع��˻����漰��
     * @param beanName
     * @param beanInfo
     */
    public abstract void addElementDef(String beanName, String beanInfo);
    
    /**
     * ����B��������
     * @param objectInfo
     * @param bobject
     */
    public abstract void addBobject(String objectInfo, Object bobject);
    
    /**
     * ����ģ��·��������
     * @param bobject
     * @param objectInfo
     */
    public abstract void addBobjectInfo(Object bobject,String objectInfo);
    
    /**
     * ���ر�ǩֵ
     * @param object
     */
    public abstract void addTagData(Object bobject,String objectInfo);

    /**
     * �������ʹ���
     * @param object
     */
    public abstract void addTypeConvertError(Object[] object);
    
    /**
     * �����������
     * @param object
     */
    public abstract void addObjectData(List objectList);

public abstract void addProperty(Property paramProperty);

  public abstract void addobjectListMap(Object paramObject1, String paramString1, String paramString2, Object paramObject2);

}
