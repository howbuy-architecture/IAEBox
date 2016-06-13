package gxlu.ietools.client.query;

import gxlu.ietools.basic.elements.variables.XmlNode;
import gxlu.ietools.property.mapping.Property;

import java.util.HashMap;
import java.util.List;

public interface QueryConditionParseIFC
{

	/**
	 * ��װ���Զ���
	 * 
	 * @return ��װ��Property���B�༯��
	 */
	public HashMap readDomParseList();

	/**
	 * ��ȡģ���ļ���Ϣ�����Զ���
	 * 
	 * @param params --Ӧ�ò���
	 * 
	 *            param0 --String*-- ģ���ļ�·��
	 * 
	 * @return ���Զ���
	 * 
	 * @throws Exception
	 */
	public abstract Object readDomParse(String file);

	/**
	 * 
	 * @param params param0--String B��·��
	 * @return
	 */
	public List getAllPropertyList();

	/**
	 * ������֧�ֵ��뵼����B���װ��Property
	 * 
	 * @param params param0--String B��·��
	 * @return ��װ��Property���B�༯��
	 */
	public abstract Object getAllProperty(List params);

	/**
	 * �Զ�ȡ�����Խ�������
	 * 
	 * @param ��װ���XML����
	 */
	public void propertyOrderbyCol(Property property);

	/**
	 * ��дģ��������Ϣ
	 * 
	 * @param params --Ӧ�ò���
	 * 
	 *            param0 --String*-- ģ���ļ�·��
	 * 
	 * @param object --���Զ�����Ϣ
	 * 
	 * @return rst0 --Boolean*-- true=��ȷ false=���� rst1 --String*-- ����ԭ��
	 */
	public abstract List writeDomParse(List params, Object object);

	/**
	 * ����ģ���ļ�·������gxlu/ietools/property/template/wlansystem.xml
	 * 
	 * @return String*-- ·��
	 */
	public abstract List getTemplateFileList();

	/**
	 * ��ȡģ��Լ����Ϣ
	 * 
	 * @param params --Ӧ�ò���
	 * 
	 *            param0 --String*-- ģ���ļ�·��
	 * 
	 * @return
	 */
	public abstract XmlNode[] parseElements(String inFile);
}
