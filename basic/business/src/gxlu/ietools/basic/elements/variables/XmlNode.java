/**************************************************************************
 * $$RCSfile: XmlNode.java,v $$  $$Revision: 1.6 $$  $$Date: 2010/04/20 02:08:06 $$
 *
 * $$Log: XmlNode.java,v $
 * $Revision 1.6  2010/04/20 02:08:06  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.basic.elements.variables;

import org.apache.log4j.Logger;

import gxlu.ietools.property.xml.DomParse;
import gxlu.ietools.property.xml.DomTemplateParse;

/**
 * XmlNode Class.
 * @author kidd
 */
public class XmlNode{

    protected static final Logger log = Logger.getLogger(XmlNode.class);

	//bean class
	private String name;

	/** Ŀ���������� **/
	private String targetObject;
	
	/** Ŀ�귽�� **/
	private String targetMethod;
	
	/** Ŀ�귵������ **/
	private String targetReturn;

	public XmlNode(){}
	
    /**
	 * Static method that creates node for specified input source which
	 * contains XML data
	 * @param inFile
	 * @return XmlNode instance
	 */
	public static XmlNode[] getInstance(String inFile) {
		DomParse domParse = new DomTemplateParse();
        return domParse.parseElements(inFile);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public String getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public String getTargetReturn() {
		return targetReturn;
	}

	public void setTargetReturn(String targetReturn) {
		this.targetReturn = targetReturn;
	}

    
}