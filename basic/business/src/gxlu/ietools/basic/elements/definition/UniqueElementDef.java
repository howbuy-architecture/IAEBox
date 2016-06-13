/**************************************************************************
 * $$RCSfile: UniqueElementDef.java,v $$  $$Revision: 1.14 $$  $$Date: 2010/06/04 07:43:49 $$
 *
 * $$Log: UniqueElementDef.java,v $
 * $Revision 1.14  2010/06/04 07:43:49  zhangj
 * $*** empty log message ***
 * $
 * $Revision 1.13  2010/05/28 05:18:40  zhangj
 * $*** empty log message ***
 * $
 * $Revision 1.12  2010/05/26 09:28:43  zhangj
 * $AC,AP,WlanHotspotҵ����
 * $
 * $Revision 1.11  2010/05/20 05:06:24  zhangj
 * $����WLAN�ȵ�,AC,AP��ҵ����֤
 * $
 * $Revision 1.10  2010/05/14 09:38:13  zhangj
 * $20100514����
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
import gxlu.afx.system.common.sysexception.SDHException;
import gxlu.afx.system.query.client.BQueryClient;
import gxlu.afx.system.query.common.QueryExpr;
import gxlu.afx.system.query.common.QueryExprBuilder;
import gxlu.ietools.basic.elements.definition.util.datanetwork.BDNPathOperation;
import gxlu.ietools.basic.elements.interceptor.UniqueInterface;
import gxlu.ietools.basic.exception.ElementsException;
import gxlu.ietools.basic.system.container.ContainerFactory;
import gxlu.ietools.basic.system.util.VariableNames;
import gxlu.ietools.property.mapping.Property;
import gxlu.ietools.property.util.ReflectHelper;
import gxlu.ossc.datanetwork.common.bizobject.logical.BWlanHotspot;
import gxlu.ossc.datanetwork.common.bizobject.physical.BDNDevice;
import gxlu.ossc.datanetwork.common.bizobject.physical.BDNDeviceType;
import gxlu.ossc.datanetwork.common.bizobject.provision.BRelayPath;
import gxlu.ossc.inventory.basic.common.bizobject.location.BRegion;
import gxlu.ossc.mobilenetwork.common.bizobject.logical.BMNCTP;
import gxlu.ossc.mobilenetwork.common.bizobject.logical.BMNTextRoute;
import gxlu.ossc.mobilenetwork.common.bizobject.logical.BMNTrunk;
import gxlu.ossc.mobilenetwork.common.bizobject.physical.BMNNE;
import gxlu.ossc.mobilenetwork.common.bizobject.physical.BMNPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Definition of UniqueElementDef.
 * 
 * @author kidd
 */
public class UniqueElementDef extends BaseElementDef implements UniqueInterface {

	public UniqueElementDef() {
	}

	/**
	 * ��֤�豸�˿�CTP�Ƿ���ͬһ�豸��
	 * @param iParam
	 * @return
	 */
	public Object mnNEChangeCheck(List iParam)
	{
	    List blist = (List)iParam.get(0);
	    String targetObject = (String)iParam.get(1);
	    String title = (String)iParam.get(2);
	
	    List dataList = new ArrayList();
	    Iterator it = blist.iterator();
	    
	    while (it.hasNext()) {
	      Object[] bObject = (Object[])it.next();
	      BObjectInterface bObj = (BObjectInterface)bObject[0];
	      if (!(bObject[0] instanceof String)) {
	        BObjectInterface oldObj = (BObjectInterface)getBObject(bObj, "id", String.valueOf(bObj.getId()), ReflectHelper.getDClassName(bObj.getClass().getName()) + "[" + ReflectHelper.getUpperCase(targetObject) + "]");
	        //��֤�Ƿ�����޸ı��Զ��豸
	        if (oldObj != null) {
	          Object oldValue = null;
	          if ((bObject[0] instanceof BMNTrunk)&&((BMNTrunk)bObject[0]).getMNRoute_Trunks().size() > 0){
	              try {
	                oldValue = getFieldValue(oldObj, targetObject);
	                setFieldValue(bObject[0], targetObject, oldValue);
	                dataList.add(bObject);
	                continue;
	              } catch (ElementsException e) {
	                e.printStackTrace();
	              }
	          }else if ((bObject[0] instanceof BMNTextRoute) && (((BMNTextRoute)bObject[0]).getMnRoute_Trunks().size() > 0)){
	            try {
	              oldValue = getFieldValue(oldObj, targetObject);
	              setFieldValue(bObject[0], targetObject, oldValue);
	              dataList.add(bObject);
	              continue;
	            } catch (ElementsException e) {
	              e.printStackTrace();
	            }
	          }
	        }
	        //��������޸ı��Զ�����֤���Զ��豸���˿ڣ�CTP
	        Object valueA = null;
	        Object valueZ = null;
	        try {
	          valueA = getFieldValue(bObj, "aMNNeId");
	          valueZ = getFieldValue(bObj, "zMNNeId");
	        } catch (ElementsException e1) {
	          e1.printStackTrace();
	        }
	        if ((valueA != null) && (valueZ != null) && (Integer.parseInt(valueA.toString()) > 0) && (Integer.parseInt(valueZ.toString()) > 0) &&(valueA == valueZ)) {
	          bObject[0] = "���Զ��豸������ͬ";
	          ContainerFactory.removeObjectListMap(bObj);
	        }else{
	          Object bMNNE = null;
	          Object bMNPort = null;
	          Object bMNCtp = null;
	          try {
	            bMNNE = getFieldValue(bObj, targetObject);
	            if (targetObject.substring(0, 1).equalsIgnoreCase("a")) {
		            bMNPort = getFieldValue(bObj, "aMNPort");
		            bMNCtp = getFieldValue(bObj, "aCtp");
                }
		        if (!(targetObject.substring(0, 1).equalsIgnoreCase("z"))){
		            bMNPort = getFieldValue(bObj, "zMNPort");
		            bMNCtp = getFieldValue(bObj, "zCtp");
		        }
	          } catch (ElementsException e1) {
	            e1.printStackTrace();
	          }
	          //����豸��Ϊ��
	          if (bMNNE != null) {
	            if ((bMNPort == null) || (bMNCtp == null)) {
	              if (targetObject.substring(0, 1).equalsIgnoreCase("a")){
		                bObject[0] = "�����ڱ����豸ʱ���˶˿�,����CTPΪ����";
	              }
	              else if (targetObject.substring(0, 1).equalsIgnoreCase("z")){
	                bObject[0] = "�����ڶԶ��豸ʱ�Զ˶˿�,�Զ�CTPΪ����";
	              }
	              ContainerFactory.removeObjectListMap(bObj);
	            }else{
	            	
	            	if (((BMNPort)bMNPort).getMnNEId() != ((BMNNE)bMNNE).getId()) {
			            if (targetObject.substring(0, 1).equalsIgnoreCase("a")){
			              bObject[0] = "���˶˿ڱ����Ǳ����豸�ϵĶ˿�";
			            }
			            else if (targetObject.substring(0, 1).equalsIgnoreCase("z")){
			              bObject[0] = "�Զ˶˿ڱ����ǶԶ��豸�ϵĶ˿�";
			            }
			            ContainerFactory.removeObjectListMap(bObj);
		             }
		            if (((BMNCTP)bMNCtp).getMnportId() != ((BMNPort)bMNPort).getId()) {
			            if (targetObject.substring(0, 1).equalsIgnoreCase("a")){
			              bObject[0] = "����CTP�����Ǳ��˶˿��ϵ�CTP";
			            }else if (targetObject.substring(0, 1).equalsIgnoreCase("z")){
			              bObject[0] = "�Զ�CTP�����ǶԶ˶˿��ϵ�CTP";
			            }
			            ContainerFactory.removeObjectListMap(bObj);
			        }
	            }
	          }else if(bMNPort!=null||bMNCtp!=null){
	        	  bObject[0] = "�����豸,���˶˿�,����CTP����ͬʱ����";
	          }
	        }
	      }
	      dataList.add(bObject);
	    }
	    return dataList;
	  }

	/**
	 * ��֤CTP�Ƿ����
	 * @param iParam
	 * @return
	 */
	public Object mnCTPUsability(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int i = 0;
		Property property = new Property();
		property.setBclass(BMNCTP.class.getName());
		addPropertyValue(property, "serviceStatus");
		ContainerFactory.addProperty(property);

		Property property1 = new Property();
		property1.setBclass(BMNPort.class.getName());
		addPropertyValue(property1, "serviceStatus");
		ContainerFactory.addProperty(property1);

		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			BObjectInterface bObj = (BObjectInterface) bObject[0];
			if (bObject[0] instanceof String) {
				dataList.add(bObject);
			} else {
				BObjectInterface oldObj = (BObjectInterface) getBObject(bObj,"id",String.valueOf(bObj.getId()),
						ReflectHelper.getDClassName(bObj.getClass().getName()) + "["+ ReflectHelper.getUpperCase(targetObject)	+ "]");
				//��������޸����ж��Ƿ�����޸ı��Զ��豸
				if (oldObj != null) {
					Object oldValue = null;
					if (bObject[0] instanceof BMNTrunk&&(((BMNTrunk) bObject[0]).getMNRoute_Trunks().size() > 0)) {
						try {
							oldValue = getFieldValue(oldObj, targetObject);
							setFieldValue(bObject[0], targetObject,oldValue);
						} catch (ElementsException e) {
							e.printStackTrace();
						}
						dataList.add(bObject);
					} else if ((bObject[0] instanceof BMNTextRoute)	&& (((BMNTextRoute) bObject[0]).getMnRoute_Trunks().size() > 0)) {
						try {
							oldValue = getFieldValue(oldObj, targetObject);
							setFieldValue(bObject[0], targetObject, oldValue);
						} catch (ElementsException e) {
							e.printStackTrace();
						}
						dataList.add(bObject);
					}
				}
				
				//���ĿǰCTP
				Object value = null;
				Object oldMNCTPId = null;
				Object newMNCTPId = null;
				try {
					value = getFieldValue(bObj, targetObject);
					newMNCTPId = getFieldValue(bObj, targetObject + "Id");
					if (oldObj == null){
						oldMNCTPId = getFieldValue(oldObj, targetObject + "Id");
					}
				} catch (ElementsException e1) {
					e1.printStackTrace();
				}
				//�ж�CTP�Ƿ�ռ��
				if (value != null) {
					BMNCTP bMNCTP = (BMNCTP) value;
					if ((bObj.getId() <=0)|| (oldMNCTPId == null)|| (bMNCTP.getId() != Long.parseLong(oldMNCTPId.toString())))
						if (bMNCTP.getServiceStatus() != 1) {
							ContainerFactory.removeObjectListMap(bObj);
							bObject[0] = title + ":" + bMNCTP.getId() + "";
							flag = false;
						} else {
							String resultStr = checkExcelRepeat(bObject[0],	blist, targetObject + "id", title, i);
							if (!(resultStr.equals(""))) {
								flag = false;
								bObject[0] = resultStr;
								ContainerFactory.removeObjectListMap(bObj);
							} else {
								bMNCTP.setServiceStatus(3);

								Object objMNPort = getBObject(BMNPort.class, "id", String.valueOf(bMNCTP.getMnportId()),null);
								if (objMNPort != null) {
									BMNPort bMNPort = (BMNPort) objMNPort;
									if (bMNPort.getServiceStatus() != 3) {
										bMNPort.setServiceStatus((byte) 3);
										ContainerFactory.addProperty(property1);
										try {
											if (targetObject.substring(0, 1).equals("a")){												
												setFieldValue(bObject[0],"aMNPort", bMNPort);
											}
											if (!(targetObject.substring(0,1).equals("z"))){
												setFieldValue(bObject[0],"zMNPort", bMNPort);
											}
										} catch (ElementsException e) {
											e.printStackTrace();
										}
										bMNCTP.setMnport(bMNPort);
									}
								} else {
									bObject[0] = title + "";
									flag = false;
									ContainerFactory.removeObjectListMap(bObj);
								}
								try {
									setFieldValue(bObject[0], targetObject,bMNCTP);
								} catch (ElementsException e) {
									e.printStackTrace();
								}
							}
						}
					else{
						flag = false;
					}
					if ((flag)&& (bObj.getId() >0)&& (oldMNCTPId != null)&& (Long.parseLong(newMNCTPId.toString()) != Long.parseLong(oldMNCTPId.toString()))) {
						Object oldBMNPortObj = getBObject(BMNPort.class, "id",oldMNCTPId.toString(), null);
						if (oldBMNPortObj != null) {
							BMNCTP oldBMNCTP = (BMNCTP) oldBMNPortObj;
							oldBMNCTP.setServiceStatus(1);
							ContainerFactory.addobjectListMap(bObj, "BEHIND","UPDATE", oldBMNCTP);

							QueryExprBuilder builder = new QueryExprBuilder();
							QueryExpr expr = builder.get("mnportId").equal(oldBMNCTP.getMnportId());
							expr = expr.and(builder.get("serviceStatus").equal(3));
							expr = expr.and(builder.get("id").notIn(new long[] { oldBMNCTP.getId() }));
							Vector vec = getResultByQueryExpr(oldBMNCTP, expr,null);
							if ((vec == null) && (vec.size() <= 0)) {
								Object objBMNPort = getBObject(BMNPort.class,"id", String.valueOf(oldBMNCTP.getMnportId()), "");
								if (objBMNPort != null){
									ContainerFactory.addobjectListMap(bObj,"BEHIND", "UPDATE", objBMNPort);
								}
							}
						}
					}
				}
				++i;
				dataList.add(bObject);
			}
		}
		return dataList;
	}

	

	/**
	 * ���B����Ӧ���ֶ�ֵ�ڿ����Ƿ��Ѿ�����
	 * 
	 * @param obj
	 * @param targetObject
	 * @return
	 */
	private Object checkExist(BObjectInterface obj, String targetObject) {
		Object value = null;
		try {
			value = getFieldValue(obj, targetObject);
		} catch (ElementsException e) {
			e.printStackTrace();
		}
		Vector result = getResults(obj, targetObject, value.toString(), null);
		if (result != null && result.size() > 0) {
			return value;
		}
		return null;
	}

	/**
	 * �ƶ����жԶ˿��Ƿ���ͬ�Ĺ���
	 * 
	 * @param params
	 *            --Ӧ�ò��� param0 --List*-- B�༯�� param1 --String*-- Ŀ����� param2
	 *            --String*-- �б�ͷ
	 * @return
	 */
	public Object mnPortCheck(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			// ���Ŀ������ֵ
			Object valueA = null;
			Object valueB = null;
			try {
				valueA = getFieldValue(bObject[0], "aMNPortId");
				valueB = getFieldValue(bObject[0], "zMNPortId");
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			if (valueA != null && valueB != null
					&& Integer.parseInt(valueA.toString()) > 0
					&& Integer.parseInt(valueB.toString()) > 0) {
				if (valueA == valueB) {
					bObject[0] = "���Զ˿ڲ�����ͬ";
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	
	public Object dnDevice_CreateSerialNo(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int oldSerialNo = -1;
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();

			if (!(bObject[0] instanceof String)) {
				boolean flag = false;
				int serialNo=-1;
				try {
					BDNDevice bDNDevice = (BDNDevice) bObject[0];
					if (bDNDevice.getId() <= 0) {
						serialNo = getSerialNo(bDNDevice.getDNDeviceType(),	bDNDevice.getHostId());
						int num = -1;
						Iterator iterator = blist.iterator();
						while (iterator.hasNext()) {
							Object[] bObj = (Object[]) iterator.next();
							if (!(bObj[0] instanceof String)) {
								BDNDevice otherBDNDevice = (BDNDevice) bObj[0];
								if ((otherBDNDevice.getHostId() == bDNDevice.getHostId())
										&& (otherBDNDevice.getSerialNo() > num))
									num = otherBDNDevice.getSerialNo();
							}
						}
						if (num >= serialNo){
							serialNo = num + 1;
						}
					}
					Object oldBDNDevice = getBObject(bDNDevice, "id", bDNDevice.getId()+ "", null);
					if (oldBDNDevice != null){
						serialNo=((BDNDevice) oldBDNDevice).getSerialNo();
					}
					setFieldValue(bObject[0], targetObject, Integer.valueOf(serialNo));
				} catch (ElementsException e) {
					e.printStackTrace();
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}

	private int getSerialNo(BDNDeviceType type, long _hostId) {
		int oldSerialNo = 0;
		int newSerialNo = 0;
		Vector result = new Vector();
		int sCategory = type.getCategory();
		if (type.getModel() != null) {
			String host = (_hostId > 0) ? " = " + _hostId
					: "IS NULL";
			String sModel = type.getModel();
			String sql = " SELECT MAX(D.SERIALNO) MAXNO FROM DNDEVICE D, DNDEVICETYPE T  WHERE T.CATEGORY= "
					+ sCategory
					+ " AND T.MODEL= '"
					+ sModel
					+ "'"
					+ " AND D.HOSTID "
					+ host
					+ " AND D.DNDEVICETYPEID = T.ID AND D.ISTEMPLATE = " + 0;
			try {
				result = BQueryClient.queryValueBySQL(sql);
			} catch (SDHException e) {
				e.printStackTrace();
			}
			if ((result != null) && (result.size() > 0)) {
				Hashtable ht = (Hashtable) result.elementAt(0);
				if (ht.get("MAXNO") != null)
					oldSerialNo = Integer.parseInt(ht.get("MAXNO").toString());
			}

			newSerialNo = oldSerialNo + 1;
		}
		return newSerialNo;
	}

	/**
	 * ͨ��Ψһ����֤
	 * 
	 * @param iParam
	 * @return
	 */
	public Object elementUniqueVerification(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			if (!(bObject[0] instanceof String)) {
				BObjectInterface bObj = (BObjectInterface) bObject[0];
				if (bObj.getId() > 0) {
					int num = checkByOld(bObj, targetObject);
					if (num == 1) {
						Object value = checkExist((BObjectInterface) bObject[0], targetObject);
						if (value != null) {
							bObject[0] = title + ":" + value.toString()+ "�Ѿ�����!";
							flag = false;
						}
					} else if (num == 2) {
						bObject[0] = "������IDΪ:" + bObj.getId() + "������!";
						flag = false;
					}
				} else {
					Object value = checkExist((BObjectInterface) bObject[0],
							targetObject);
					if (value != null) {
						bObject[0] = title + ":" + value.toString() + "�Ѿ�����!";
						flag = false;
					}
				}
			}
			dataList.add(bObject);
		}

		return dataList;
	}

	/**
	 * 
	 * @param bobj
	 * @param targetObject
	 * @return 0 ��ԭ����ͬ 1 ��ԭ����ͬ 2 ����ID�Ҳ�������
	 */
	private int checkByOld(BObjectInterface bObj, String targetObject) {
		// ��ѯ���е����
		BObjectInterface oldObj = (BObjectInterface) getBObject(bObj, "id",String.valueOf(bObj.getId()), null);
		if (oldObj == null)
			return 2;
		Object value = null;
		Object oldValue = null;
		try {
			value = getFieldValue(bObj, targetObject);
			oldValue = getFieldValue(oldObj, targetObject);
		} catch (ElementsException e) {
			e.printStackTrace();
		}
		if (value == oldValue
				|| (value != null && oldValue != null && value.toString()
						.equals(oldValue.toString()))) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public Object elementUniqueVerificationForHost(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			if (!(bObject[0] instanceof String)) {
				BObjectInterface bObj = (BObjectInterface) bObject[0];
				Object objValue=null;
				Object value=null;
				try {
					objValue = getFieldValue(bObj, "hostId");
					value = getFieldValue(bObj, targetObject);
				} catch (ElementsException e) {
					e.printStackTrace();
				}
				if(value!=null&&!value.toString().equals("")){
					Map map=new HashMap();
					map.put("hostId", "host");
					Object result = getHostCheckResult(bObj,checkUniqueJionProperty(bObj, targetObject, map),objValue,title);
					if (result != null){
						bObject[0] = result.toString();
					}				
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	/**
	 * ��֤Excel���Ƿ����ظ��ģ�ͬһ��װλ�ã�
	 * @param iParam
	 * @return
	 */
	public Object elementUniqueVerificationForExcel_Host(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int i = 0;
		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			if (!(bObject[0] instanceof String)) {
				String resultStr = checkExcelRepeatForHost(bObject[0], blist,targetObject, title, i,"hostId");
				if (!resultStr.equals("")) {
					bObject[0] = resultStr;
				}				
			}
			i++;
			dataList.add(bObject);
		}
		return dataList;
	}
	/**
	 * ��֤Excel���Ƿ����ظ���
	 * @param iParam
	 * @return
	 */
	public Object elementUniqueVerificationForExcel(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int i = 0;
		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			if (!(bObject[0] instanceof String)) {
				String resultStr = checkExcelRepeat(bObject[0], blist,targetObject, title, i);
				if (!resultStr.equals("")) {
					bObject[0] = resultStr;
				}				
			}
			i++;
			dataList.add(bObject);
		}
		return dataList;
	}
	/**
	 * ���������ֶ�����Ƿ�Ψһ
	 * @param obj
	 * @param targetObject
	 * @param title
	 * @param Objproperty
	 * @param idproperty
	 * @return
	 */
	private Object checkUniqueJionProperty(BObjectInterface obj, String targetObject,Map map) {
		Object value = null;
		try {
			value = getFieldValue(obj, targetObject);
		} catch (ElementsException e) {
			e.printStackTrace();
		}
		QueryExprBuilder builder = new QueryExprBuilder();
		QueryExpr expr = builder.get(targetObject).equal(value.toString());
		
		Iterator it =map.keySet().iterator();
		while(it.hasNext()){
			Object idproperty=it.next();
			Object Objproperty=map.get(idproperty);
			
			Object objValue = null;
			Object objIdValue = null;
			try {
				objIdValue = getFieldValue(obj, idproperty.toString());
				if(Objproperty!=null){
					objValue = getFieldValue(obj, Objproperty.toString());
				}
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			if (objValue != null||(objIdValue!=null&&Long.parseLong(objIdValue.toString())>0))
				expr = expr.and(builder.get(idproperty.toString()).equal(objIdValue.toString()));
			else{
				if(Objproperty!=null){
					expr = expr.and(builder.get(Objproperty.toString()).isNull());
				}else{
					expr=expr.and(builder.get(idproperty.toString()).equal(objIdValue.toString()));
				}
			}
		}
		Vector result = getResultByQueryExpr(obj, expr, null);
		return result;
	}
	/**
	 * ���Excel����ͬ��װλ�����Ƿ����ظ���
	 * 
	 * @param obj
	 * @param blist
	 * @param targetObject
	 * @param title
	 * @param num
	 * @return
	 */
	private String checkExcelRepeatForHost(Object obj, List blist,	String targetObject, String title, int num,String property) {
		Object valueA = null;
		Object valueB = null;
		Object hostIdA=null;
		Object hostIdB=null;
		Object[] objB = null;
		try {
			valueA = getFieldValue(obj, targetObject);
			hostIdA = getFieldValue(obj, property);
			for (int j = 0; j < blist.size(); j++) {
				if (num != j) {
					objB = (Object[]) blist.get(j);
					if (!(objB[0] instanceof String)) {
						Object hostId = getFieldValue(objB[0], property);
						if(hostId.toString().equals(hostIdA.toString())){
							valueB = getFieldValue(objB[0], targetObject);
							//ֵ�����ظ�(Ҫ�ų�1.ͬһ�����ݵ��޸�)
							if (valueA != null&& valueB != null&&!(valueA.toString().equals(""))&& valueA.toString().equals(valueB.toString())
									&& !(((BObjectInterface) obj).getId() > 0&& ((BObjectInterface) objB[0]).getId() > 0
									&& ((BObjectInterface) obj).getId() == ((BObjectInterface) objB[0]).getId())) {
								return title + ":" + valueA.toString() + "���"+ (Integer.parseInt(objB[1].toString())+ 2) + "���ظ�";
							}
						}
					}
				}
			}
		} catch (ElementsException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * ���Excel���Ƿ����ظ���
	 * 
	 * @param obj
	 * @param blist
	 * @param targetObject
	 * @param title
	 * @param num
	 * @return
	 */
	private String checkExcelRepeat(Object obj, List blist,	String targetObject, String title, int num) {
		Object valueA = null;
		Object valueB = null;
		Object[] objB = null;
		try {
			valueA = getFieldValue(obj, targetObject);
			for (int j = 0; j < blist.size(); j++) {
				if (num != j) {
					objB = (Object[]) blist.get(j);
					if (!(objB[0] instanceof String)) {
						valueB = getFieldValue(objB[0], targetObject);
						if (valueA != null&& valueB != null&&!(valueA.toString().equals(""))&& valueA.toString().equals(valueB.toString())
								&& !(((BObjectInterface) obj).getId() > 0&& ((BObjectInterface) objB[0]).getId() > 0
								&& ((BObjectInterface) obj).getId() == ((BObjectInterface) objB[0]).getId())) {
							return title + ":" + valueA.toString() + "���"+ (Integer.parseInt(objB[1].toString()) + 2) + "���ظ�";
						}
					}
				}
			}
		} catch (ElementsException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * �������ȵ���� ������ź��ȵ��ʶ
	 * @param iParam
	 * @return
	 */
	public Object dn_BWlanHotspot_HotspotSerialNo(List iParam) {		
		List blist= (List)setSerialNo(iParam,"dn_BWlanHotspot_getHotspotSerialNo");
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int serialNo=-1;
		while (it.hasNext()) {
			boolean flag=true;
			int currentSerialNo=-1;
			Object[] bObject = (Object[]) it.next();
			if(!(bObject[0] instanceof String)){
				if(((BWlanHotspot)bObject[0]).getId()<=0){
					dn_WlanHotspot_setHotspotID((BWlanHotspot)bObject[0]);
				}else{
					//����޸��˳��С����ػ��ȵ����ͣ����ȵ��ʶ��Ҫ����
					int cityNum=this.checkByOld((BObjectInterface)bObject[0], "cityId");
					int districtNum=this.checkByOld((BObjectInterface)bObject[0], "districtId");
					int hotspotTypeNum=this.checkByOld((BObjectInterface)bObject[0], "hotspotType");
					if(cityNum==1||districtNum==1||hotspotTypeNum==1){
						dn_WlanHotspot_setHotspotID((BWlanHotspot)bObject[0]);
					}
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	/**
	 * ͨ���������
	 * @param iParam
	 * @param methodName Ҫִ�в�ѯ�ķ�����
	 * @return
	 */
	private Object setSerialNo(List iParam,String methodName){
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);
		
		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int serialNo=-1;
		while (it.hasNext()) {
			boolean flag=true;
			int currentSerialNo=-1;
			Object[] bObject = (Object[]) it.next();
			if(bObject[0] !=null){
				BObjectInterface bObj=(BObjectInterface)bObject[0];
				if(bObj.getId()>0){
					Object oldObj=this.getBObject(bObj, "id",bObj.getId()+"",null);
					if(oldObj!=null){
						Object oldValue=null;
						try {
							oldValue=getFieldValue(oldObj, targetObject);
						} catch (ElementsException e) {
							e.printStackTrace();
						}
						if(oldValue!=null){
							currentSerialNo=Integer.parseInt(oldValue.toString());
							flag=false;
						}
					}else{
						bObject[0] = "�Ҳ���IDΪ:" + bObj.getId() + "������";
						flag=false;
					}
				}
				if(flag){
					if(serialNo==-1){
						Object result=ReflectHelper.invokeStaticMethod("gxlu.ietools.basic.collection.util.ElementDefUtil", "dn_BWlanHotspot_getHotspotSerialNo", null, null);
						if(result!=null){
							serialNo=Integer.parseInt(result.toString());
						}
					}else{
						++serialNo;
					}
					currentSerialNo=serialNo;
				}
			}
			try {
				setFieldValue(bObject[0], targetObject, Integer.valueOf(currentSerialNo));
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	private void dn_WlanHotspot_setHotspotID(BWlanHotspot bWlanHotspot){
		//�й����ż�ʡ�ݵı�ʶ
		String s="CT_";
		//������д
        String province=bWlanHotspot.getProvince().getAbbrevPY();
        if(province == null || "".equalsIgnoreCase(province)){
        	province = "**";
        }
		//�ȵ�������
		int j=bWlanHotspot.getHotspotType();
		String hotspottypevalue="";		
		switch(j){
			case 1: hotspottypevalue="01";break;
			case 2: hotspottypevalue="02";break;
			case 3: hotspottypevalue="03";break;
			case 4: hotspottypevalue="04";break;
			case 5: hotspottypevalue="05";break;
			case 6: hotspottypevalue="06";break;
			case 7: hotspottypevalue="07";break;
			case 8: hotspottypevalue="08";break;
			case 9: hotspottypevalue="09";break;
			case 10: hotspottypevalue="10";break;
			case 11: hotspottypevalue="11";break;
			case 12: hotspottypevalue="12";break;
			case 13: hotspottypevalue="13";break;
			case 14: hotspottypevalue="14";break;
			case 15: hotspottypevalue="15";break;
			case 16: hotspottypevalue="16";break;
			case 17: hotspottypevalue="99";break;
			default: hotspottypevalue="";
		}
		
		//��������
		String areaNo=bWlanHotspot.getCity().getCode();
		if(areaNo == null || "".equalsIgnoreCase(areaNo) ){
			areaNo = "000";
		}
		s=s+province+"_"+areaNo+hotspottypevalue;
		//�ȵ����кţ�ʵ������hospotValue
		long hotspotSerialNo=bWlanHotspot.getHotspotSerialNo();
		String hotspotSerialNoS="";
		if(hotspotSerialNo < 10) {
			hotspotSerialNoS="0000"+hotspotSerialNo;
		}
		if(hotspotSerialNo >= 10 && hotspotSerialNo < 100) {
			hotspotSerialNoS="000"+hotspotSerialNo;
		}
		if(hotspotSerialNo >= 100 && hotspotSerialNo < 1000) {
			hotspotSerialNoS="00"+hotspotSerialNo;
		}
		if(hotspotSerialNo >= 1000 && hotspotSerialNo < 10000) {
			hotspotSerialNoS="0"+hotspotSerialNo;
		}
		if(hotspotSerialNo >= 10000 ) {
			hotspotSerialNoS=""+hotspotSerialNo;
		}
		bWlanHotspot.setHotspotID(s + hotspotSerialNoS);
	}
	
	/**
	 * ���������ȵ���������Ψһ
	 * @param iParam
	 * @return
	 */
	public Object dn_WlanHotspot_hotspotNameForCity(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			BObjectInterface bObj=(BObjectInterface)bObject[0];
			Object value=null;
			Object valueId=null;
			try {
				valueId = getFieldValue(bObject[0], "cityId");
				value = getFieldValue(bObject[0], targetObject);
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			if(value!=null&&!value.toString().equals("")){
				Map map=new HashMap();
				map.put("cityId", "city");
				Object resultObj=checkUniqueJionProperty((BObjectInterface)bObject[0], targetObject, map);
				if(resultObj!=null){
					Vector result=(Vector)resultObj;
					for(int i=0;i<result.size();i++){
						if(!(bObj.getId()>0&&((BObjectInterface)result.get(0)).getId()==bObj.getId())){
							if(valueId!=null&&Long.parseLong(valueId.toString())>0){
								bObject[0] = "�ڴ˳�����"+title+"Ϊ:" + value + "��WLAN�ȵ��Ѿ�����";
							}else{
								bObject[0] = "WLAN�ȵ�" + title + "����Ψһ";
							}
							break;
						}
					}
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	/**
	 * �������� WLAN�ȵ� ��֤Excel���Ƿ����ظ��ģ�ͬһ�������ȵ�����Ψһ��
	 * @param iParam
	 * @return
	 */
	public Object dn_WlanHotspot_hotspotNameForExcel_City(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int i = 0;
		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			if (!(bObject[0] instanceof String)) {
				String resultStr = checkExcelRepeatForHost(bObject[0], blist,targetObject, title, i,"cityId");
				if (!resultStr.equals("")) {
					bObject[0] = resultStr;
				}				
			}
			i++;
			dataList.add(bObject);
		}
		return dataList;
	}
	/**
	 * �����������ƺͱ��밲װλ����Ψһ
	 * @param iParam
	 * @return
	 */
	public Object pn_PowerCable_UniqueForSite(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			if (!(bObject[0] instanceof String)) {
				BObjectInterface bObj = (BObjectInterface) bObject[0];
				Object hostValue=null;
				Object value=null;
				try {
					hostValue = getFieldValue(bObj, "siteId");
					value=getFieldValue(bObj, targetObject);
				} catch (ElementsException e) {
					e.printStackTrace();
				}
				if(value!=null&&!value.toString().equals("")){
					Map map=new HashMap();
					map.put("siteId", "site");
					Object result=getHostCheckResult(bObj,checkUniqueJionProperty(bObj, targetObject,map),hostValue,title);
					if(result!=null){
						bObject[0] = result.toString();
					}
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}	
	/**
	 * �����ͬ��װλ�õļ�����
	 * @param obj
	 * @param hostValue
	 * @param title
	 * @return
	 */
	private Object getHostCheckResult(BObjectInterface bObj,Object resultObj,Object hostValue,String title){
		if(resultObj!=null){
			Vector result=(Vector)resultObj;
			if(result!=null&&result.size()>0){
				for(int i=0;i<result.size();i++){
					if(!(bObj.getId()>0&&((BObjectInterface)result.get(0)).getId()==bObj.getId())){
						if (hostValue != null&&Long.parseLong(hostValue.toString())>0){
							return "��ͬһ��װ��ַ(�����)��ͬ�����豸��" + title + "������Ψһ";
						}
						return  "ͬ�����豸��" + title + "������Ψһ";
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * ������ ��������������ƺͱ����豸��Ψһ
	 * @param iParam
	 * @return
	 */
	public Object pn_PowerPort_UniqueForNE(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		while (it.hasNext()) {
			Object[] bObject = (Object[]) it.next();
			Object value=null;
			try {
				value = getFieldValue(bObject[0], "powerNE");
			} catch (ElementsException e) {
				e.printStackTrace();
			}
			BObjectInterface bObj=(BObjectInterface)bObject[0];
			Map map=new HashMap();
			map.put("powerNEId", "powerNE");
			Object resultObj=checkUniqueJionProperty((BObjectInterface)bObject[0], targetObject, map);
			if(resultObj!=null){
				Vector result=(Vector)resultObj;
				for(int i=0;i<result.size();i++){
					if(!(bObj.getId()>0&&((BObjectInterface)result.get(0)).getId()==bObj.getId())){
						if(value!=null&&Long.parseLong(value.toString())>0){
							bObject[0] = "��ͬһ�豸�ж���" + title + "����Ψһ";
						}else{
							bObject[0] = "����" + title + "����Ψһ";
						}
						break;
					}
				}
			}
			dataList.add(bObject);
		}
		return dataList;
	}
	/**
	 * ��֤Excel���Ƿ����ظ��ģ�ͬһ��װλ�ã� ��������
	 * @param iParam
	 * @return
	 */
	public Object pn_PowerCableUniqueForExcel_Site(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int i = 0;
		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			if (!(bObject[0] instanceof String)) {
				String resultStr = checkExcelRepeatForHost(bObject[0], blist,targetObject, title, i,"siteId");
				if (!resultStr.equals("")) {
					bObject[0] = resultStr;
				}				
			}
			i++;
			dataList.add(bObject);
		}
		return dataList;
	}
	
	/**
	 * ��֤Excel���Ƿ����ظ��ģ�ͬһ��װλ�ã������������
	 * @param iParam
	 * @return
	 */
	public Object pn_PowerPortUniqueForExcel_NE(List iParam) {
		List blist = (List) iParam.get(0);
		String targetObject = (String) iParam.get(1);
		String title = (String) iParam.get(2);

		List dataList = new ArrayList();
		Iterator it = blist.iterator();
		int i = 0;
		while (it.hasNext()) {
			boolean flag = true;
			Object[] bObject = (Object[]) it.next();
			if (!(bObject[0] instanceof String)) {
				String resultStr = checkExcelRepeatForHost(bObject[0], blist,targetObject, title, i,"powerNEId");
				if (!resultStr.equals("")) {
					bObject[0] = resultStr;
				}				
			}
			i++;
			dataList.add(bObject);
		}
		return dataList;
	}
//	/**
//	 *  ������    EPON�м�   ����ʱҪ�����
//	 * @param iParam
//	 * @return
//	 */
//	public Object dn_BRelayPath_setValue(List iParam) {
//		List blist = (List) iParam.get(0);
//		String targetObject = (String) iParam.get(1);
//		String title = (String) iParam.get(2);
//		List dataList = new ArrayList();
//		Iterator it = blist.iterator();
//		boolean flag=true;
//		while (it.hasNext()) {
//			Object[] bObject = (Object[]) it.next();
//			BRelayPath bRelayPath=(BRelayPath)bObject[0];
//			if(bRelayPath.getId()<=0){
//				BDNPathOperation.dn_BRelayPath_SetValue(bRelayPath);
//			}else{
//				bRelayPath.setSequenceNo(bRelayPath.getId());
//			}
//			dataList.add(bObject);
//		}
//		return dataList;
//	}	
}
