/**************************************************************************
 * $$RCSfile: NullElementDef.java,v $$  $$Revision: 1.12 $$  $$Date: 2010/06/04 07:43:49 $$
 *
 * $$Log: NullElementDef.java,v $
 * $Revision 1.12  2010/06/04 07:43:49  zhangj
 * $*** empty log message ***
 * $
 * $Revision 1.11  2010/05/26 09:28:43  zhangj
 * $AC,AP,WlanHotspotҵ����
 * $
 * $Revision 1.10  2010/05/20 05:06:24  zhangj
 * $����WLAN�ȵ�,AC,AP��ҵ����֤
 * $
 * $Revision 1.9  2010/05/14 01:11:19  zhangj
 * $20100514����
 * $
 * $Revision 1.8  2010/05/07 12:52:54  zhangj
 * $*** empty log message ***
 * $
 * $Revision 1.7  2010/05/06 07:10:28  zhangj
 * $*** empty log message ***
 * $
 * $Revision 1.6  2010/04/20 02:08:02  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.basic.elements.definition;

import gxlu.afx.system.common.interfaces.BObjectInterface;
import gxlu.ietools.basic.elements.interceptor.NullInterface;
import gxlu.ietools.basic.exception.ElementsException;
import gxlu.ietools.property.util.ReflectHelper;
import gxlu.ossc.datanetwork.common.bizobject.logical.BWlanHotspot;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Definition of NullElementDef.
 * 
 * @author kidd
 */
public class NullElementDef extends BaseElementDef implements NullInterface {

	public NullElementDef() {
	}

	/**
	 * ͨ�÷ǿ���֤
	 */
	public Object elementNullVerification(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			// ���Ŀ������ֵ
			Object value = null;
			try {
				if(targetObject.indexOf("[")<0){
					value = getFieldValue(bObject[0], targetObject);
				}else{
					value = getFieldValue(bObject[0], targetObject.substring(0,targetObject.indexOf("[")));
				}
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			

			
			if (value == null || value.toString().equals("")||value.toString().equals("-1")||value.toString().equals("-1.0")||specialCheckOne(targetObject,value)) {
				if (title.equals("")) {
					bObject[0] = "�ֶ�:" + targetObject + "����Ϊ��!";
				} else {
					bObject[0] = title + "����Ϊ��!";
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}

	/**
	 * ���⴦��
	 * @param specValue
	 * @return
	 */
	private boolean specialCheckOne(String specValue,Object checkValue){
		if("rateId[id]".equals(specValue)){
			return checkValue.toString().equals("0");
		}
		return false;
	}
	
	/**
	 * ����ʱ����Ϊ�գ��޸�ʱ����Ϊ��(��Ҫ������Щ����ʱ������޸�ʱ�����޸ĵ�)
	 * 
	 * @param iParam
	 * @return
	 */
	public Object elementNullVerificationForAdd(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			BObjectInterface bObj = (BObjectInterface) bObject[0];
			if (bObj.getId() > 0) {
				String className = bObj.getClass().getName();
				// �����������
				Field filed = ReflectHelper.getFieldbyName(className,
						targetObject);
				if (filed != null) {
					String fieldType = filed.getType().getName();
					String assemble = null;
					// �ж��ǲ����������ͣ������ƴװ��ѯ���
					if (ReflectHelper.isObjectProperty(fieldType)) {
						assemble = ReflectHelper.getDClassName(className) + "["	+ ReflectHelper.getUpperCase(targetObject)+ "]";
					}
					Object oldObj = getBObject(bObj, "id", String.valueOf(bObj.getId()), assemble);
					try {
						Object value = getFieldValue(oldObj, targetObject);
						setFieldValue(bObject[0], targetObject, value);
					} catch (ElementsException e) {
						e.printStackTrace();
					}
				}
			} else {
				// ���Ŀ������ֵ
				Object value = null;
				try {
					value = getFieldValue(bObject[0], targetObject);
				} catch (ElementsException e) {
					e.printStackTrace();
				}
				if (value == null || value.toString().equals("")) {
					if (title.equals("")) {
						bObject[0] = "�����½�ʱ�ֶ�:" + targetObject + "����Ϊ��!";
					} else {
						bObject[0] = "�����½�ʱ" + title + "����Ϊ��!";
					}
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	
	/**
	 * �ı�·���������ܳ���2000��
	 * @param iParam
	 * @return
	 */
	public Object mnTextRoute_textRoutelength(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			BObjectInterface bObj = (BObjectInterface) bObject[0];
			if (!(bObject[0] instanceof String)){
				try {
					Object value = getFieldValue(bObj, targetObject);
					if ((value != null)&& (value.toString().length()>2000)) {
						bObject[0] = title + "���ܳ���2000��";
					}
				} catch (ElementsException e) {
					e.printStackTrace();
				}
				dataList.add(bObject);
			}
		}
		return dataList;
	}
	/**
	 * �������� WLAN�ȵ� ����Ĭ��ֵ
	 * @param iParam
	 * @return
	 */
	public Object dn_BWlanHotspot_SetDefault(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		boolean flag=true;
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			BWlanHotspot bWlanHotspot=(BWlanHotspot)bObject[0];
			if(bWlanHotspot.getId()<0){
				if(bWlanHotspot.getSSID()==null||bWlanHotspot.getSSID().equals("")){
					bWlanHotspot.setSSID("ChinaNet");	
				}
				if(bWlanHotspot.getISPID()==null||bWlanHotspot.getISPID().equals("")){
					bWlanHotspot.setISPID("�й�����");
				}
				if(bWlanHotspot.getServeStatus()<=0){
					bWlanHotspot.setServeStatus(2);
				}
				if(bWlanHotspot.getAPCoverageTechnology()<=0){
					bWlanHotspot.setAPCoverageTechnology(1);
				}
				if(bWlanHotspot.getAPType()<=0){
					bWlanHotspot.setAPType(2);
				}
				if(bWlanHotspot.getCanSupportClientNetworking()<=0){
					bWlanHotspot.setCanSupportClientNetworking(1);
				}
			}else{
				if(bWlanHotspot.getSSID()==null||bWlanHotspot.getSSID().equals("")){
					bObject[0] = "�޸�ʱSSID����Ϊ��!";
				}
				if(bWlanHotspot.getISPID()==null||bWlanHotspot.getISPID().equals("")){
					bObject[0] = "�޸�ʱ�����ṩ�̲���Ϊ��!";
				}
				if(bWlanHotspot.getHotspotID()==null||bWlanHotspot.getHotspotID().equals("")){
					bObject[0] = "�޸�ʱ�ȵ��ʶ����Ϊ��!";
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	
	/**
	 * �������� AC��AP ����WLAN����ϵͳ������ֵ
	 * @param iParam
	 * @return
	 */
	public Object dn_BAC_parentWLANSystem(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		boolean flag=true;
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			Object value=null;
			try {
				value = getFieldValue(bObject[0], targetObject);
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			if(!(value==null||value.toString().equals(""))){
				try {
					this.setFieldValue(bObject[0], targetObject,"");
				} catch (ElementsException e) {
					e.printStackTrace();
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	
	/**
	 * ��֤����С��180
	 * @param iParam
	 * @return
	 */
	public Object longitudeCheck(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		boolean flag=true;
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			Object value=null;
			try {
				value = getFieldValue(bObject[0], targetObject);
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			if(value!=null&&!value.toString().equals("-1")){
				BigDecimal longitude=new BigDecimal(value.toString());
				if(longitude.compareTo(new BigDecimal(180))>0||longitude.compareTo(new BigDecimal(0))<0){
					bObject[0] = "����Ӧ��0-180֮��!";
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	/**
	 * ��֤γ��С��90
	 * @param iParam
	 * @return
	 */
	public Object latitudeCheck(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		boolean flag=true;
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			Object value=null;
			try {
				value = getFieldValue(bObject[0], targetObject);
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			if(value!=null&&!value.toString().equals("-1")){
				BigDecimal latitude=new BigDecimal(value.toString());
				if(latitude.compareTo(new BigDecimal(90))>0||latitude.compareTo(new BigDecimal(0))<0){
					bObject[0] = "γ��Ӧ��0-90֮��!";
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
}
