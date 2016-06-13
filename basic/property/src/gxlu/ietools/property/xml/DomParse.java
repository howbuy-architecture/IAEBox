package gxlu.ietools.property.xml;

import gxlu.ietools.basic.elements.variables.XmlNode;
import gxlu.ietools.property.mapping.Property;

import java.util.List;

public interface DomParse {
	
	/**
	 * ��ȡģ���ļ���Ϣ�����Զ���
	 * 
	 * @param params --Ӧ�ò���	  
	 * 
	 * param0 --String*--  ģ���ļ�·��
	 * 
	 * @param workFlag
	 * 		param0--int 0:ǰ̨ʹ�� 1:����ʹ�� 2:ȫ��
	 * 
	 * @return ���Զ���
	 * 
	 * @throws Exception
	 */
	public abstract Object readDomParse(List params,int workFlag);
	/**
	 * 
	 * @param params
	 * param0--String B��·��
	 * @return
	 */
	public abstract Object getAllProperty(List params);
	
	/**
	 * �Զ�ȡ�����Խ�������
	 * @param ��װ���XML����
	 */
	public void propertyOrderbyCol(Property property);
	
	/**
	 * ��дģ��������Ϣ
	 * 
	 * @param params --Ӧ�ò���	  
	 * 
	 * param0 --String*--  ģ���ļ�·��
	 * 
	 * @param object --���Զ�����Ϣ
	 * 
	 * @return  
	 *  	rst0 --Boolean*-- true=��ȷ false=����
	 *      rst1 --String*-- ����ԭ��
	 */
	public abstract List writeDomParse(List params,Object object);
	
	/**
	 * ����ģ���ļ�·������gxlu/ietools/property/template/wlansystem.xml
	 * @return
	 *  	String*-- ·��
	 */
	public abstract List getTemplateFileList();
	
	/**
	 * ��ȡģ��Լ����Ϣ
	 * 
	 * @param params --Ӧ�ò���	  
	 * 
	 * param0 --String*--  ģ���ļ�·��
	 *
	 * @return  
	 */
    public abstract XmlNode[] parseElements(String inFile);
}
