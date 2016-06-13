package gxlu.ietools.service;

import gxlu.afx.system.common.CommonBDConvertor;
import gxlu.afx.system.common.CommonContext;
import gxlu.afx.system.common.interfaces.BObjectInterface;
import gxlu.afx.system.event.common.EventOperation;
import gxlu.afx.system.event.common.SDHEvent;
import gxlu.afx.system.log.common.LogConst;
import gxlu.afx.system.log.service.LogWriter;
import gxlu.ietools.basic.system.util.VariableNames;
import gxlu.ietools.property.exception.PropertyException;
import gxlu.ietools.property.mapping.Property;
import gxlu.ietools.property.mapping.PropertyObject;
import gxlu.ietools.property.mapping.PropertyValue;
import gxlu.ietools.property.util.ReflectHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import TOPLink.Public.Exceptions.TOPLinkException;
import TOPLink.Public.Expressions.ExpressionBuilder;

public class BIEToolsServer {
	protected CommonContext commonContext = null;
	private Map allBObjMap = new HashMap();

	public void setCommonContext(CommonContext commonContext) {
		this.commonContext = commonContext;
	}

	/**
	 * ������������(ֻ������౾�����Ե�ת��)
	 * 
	 * @param bObject
	 *            B�����
	 * @param className
	 *            ��Ӧ��BDConvertor��·��
	 */
	public void import_create(BObjectInterface bObj, Property property) {
		Object dObj = btod(bObj, property, true);
		dObj = create(bObj, dObj);
		this.allBObjMap.put(bObj, dObj);
	}

	/**
	 * ͨ��B�෴�������������ת��������
	 * 
	 * @param bObj
	 */
	public void import_create(BObjectInterface bObj) {
		Object dObj = btod(bObj, true, true);
		dObj = create(bObj, dObj);
	}

	public void import_create(BObjectInterface bObj, Map objMap, Map propertyMap) {
		Object dObj = btod(bObj, propertyMap, true);

		if (objMap != null) {
			processRelation(dObj, (Map) objMap.get(VariableNames.OBJECT_FRONT), propertyMap);
//			dObj = processRelation(dObj, (Map) objMap.get(VariableNames.OBJECT_SELF), propertyMap);
		}
		dObj = create(bObj, dObj);
		this.allBObjMap.put(bObj, dObj);
		if (objMap != null){
			dObj = processRelation(dObj, (Map) objMap.get(VariableNames.OBJECT_SELF), propertyMap);
			processRelation(dObj, (Map) objMap.get(VariableNames.OBJECT_BEHIND), propertyMap);
		}
	}

	/**
	 * ����ԭ����BDConvertor�����ת��������
	 * 
	 * @param bObj
	 */
	public void addObject(BObjectInterface bObj) {
		Object dObj = getDObject(bObj, true);
		CommonBDConvertor.btod(bObj, dObj);
		create(bObj, dObj);
	}

	/**
	 * ����
	 * 
	 * @param bObj
	 * @param dObj
	 */
	private Object create(BObjectInterface bObj, Object dObj) {
		if (dObj == null) {
			return null;
		}
		dObj = commonContext.getUnitOfWork().registerObject(dObj);
		commonContext.getUnitOfWork().assignSequenceNumber(dObj);
		SDHEvent evt = getEventClass(bObj);
		if (evt != null) {
			evt.object = dObj;
			evt.invoker = this.commonContext.getSessionKey();
			evt.operationType = EventOperation.ADD;
			this.commonContext.addEvent(evt);
		}
		this.commonContext.addBObject(dObj);
		LogWriter.writeLog(this.commonContext, LogConst.OPERATION_ADD, bObj);
		return dObj;
	}

	/**
	 * ������·���
	 * 
	 * @param bObject
	 *            B�����
	 * @param className
	 *            ��Ӧ��BDConvertor��·��
	 */
	public void import_update(BObjectInterface bObj, Property property) {
		Object dObj = btod(bObj, property, false);
		dObj = update(bObj, dObj);
		this.allBObjMap.put(bObj, dObj);
	}

	/**
	 * ͨ��B�෴�������������ת��������
	 * 
	 * @param bObj
	 */
	public void import_update(BObjectInterface bObj) {
		Object dObj = btod(bObj, false, true);
		update(bObj, dObj);
	}

	public void import_update(BObjectInterface bObj, Map objMap, Map propertyMap) {
		Object dObj = btod(bObj, propertyMap, false);

		if (objMap != null) {
			processRelation(dObj, (Map) objMap.get(VariableNames.OBJECT_FRONT), propertyMap);
//			dObj = processRelation(dObj, (Map) objMap.get(VariableNames.OBJECT_SELF), propertyMap);
		}
		dObj = update(bObj, dObj);
		this.allBObjMap.put(bObj, dObj);
		if (objMap != null){
			dObj = processRelation(dObj, (Map) objMap.get(VariableNames.OBJECT_SELF), propertyMap);
			processRelation(dObj, (Map) objMap.get(VariableNames.OBJECT_BEHIND), propertyMap);
		}
	}

	/**
	 * �޸�ĳ�����󷽷�(����ԭ�е�DBConvertor)
	 * 
	 * @param bObject
	 *            B�����
	 */
	public void updateObject(BObjectInterface bObj) {
		Object dObj = getDObject(bObj, false);
		if (dObj == null) {
			return;
		}
		CommonBDConvertor.btod(bObj, dObj);
		update(bObj, dObj);
	}

	/**
	 * �޸�
	 * 
	 * @param bObj
	 * @param dObj
	 */
	private Object update(BObjectInterface bObj, Object dObj) {
		if (bObj == null) {
			return null;
		}
		SDHEvent evt = getEventClass(bObj);
		if (evt != null && dObj != null) {
			evt.object = dObj;
			evt.invoker = this.commonContext.getSessionKey();
			evt.operationType = EventOperation.UPDATE;
			this.commonContext.addEvent(evt);
		}
		this.commonContext.addBObject(dObj);
		LogWriter.writeLog(this.commonContext, LogConst.OPERATION_UPDATE, bObj);
		return dObj;
	}

	/**
	 * ͨ��B������Ӧ��Event��
	 * 
	 * @param bObj
	 *            ��B��
	 * @return������ӦEvent���·��
	 */
	private SDHEvent getEventClass(BObjectInterface bObj) {
		if(bObj==null){
			return null;
		}
		SDHEvent evt = null;
		String bClassName = bObj.getClass().getName();
		Object obj = bObj;
		do {
			if(obj==null){
				break;
			}
			String eventPath = bClassName.substring(0, bClassName.indexOf("bizobject"));
			eventPath += "event."+ bClassName.substring(bClassName.lastIndexOf(".") + 2)+ "Event";
			evt = (SDHEvent) ReflectHelper.newInstanceByName(eventPath);
			bClassName = obj.getClass().getSuperclass().getName();
			if (evt == null && !bClassName.equals("java.lang.Object")) {
				obj = (BObjectInterface) ReflectHelper.newInstanceByName(obj.getClass().getSuperclass().getName());
			} else {
				break;
			}
		} while (true);
		return evt;
	}

	/**
	 * ���B��D���ת��
	 * 
	 * @param bObj
	 * @param property
	 * @param flag
	 * @return
	 */
	private Object btod(BObjectInterface bObj, Property property, boolean flag) {
		Object dObj = getDObject(bObj, flag);
		if (dObj != null) {
			// һ�����Ը�ֵ
			if (property.getPropertyValue() != null
					&& property.getPropertyValue().size() > 0) {
				for (int i = 0; i < property.getPropertyValue().size(); i++) {
					valueConvertor(bObj, dObj, (PropertyValue) property.getPropertyValue().get(i));
				}
			}
			// �������Ը�ֵ
			if (property.getPropertyObject() != null
					&& property.getPropertyObject().size() > 0) {
				for (int i = 0; i < property.getPropertyObject().size(); i++) {
					objectConvertor(bObj, dObj, (PropertyObject) property.getPropertyObject().get(i));
				}
			}
		}
		return dObj;
	}

	/**
	 * ��BD����������Զ�ת��(���BD�����ͬ���������Ʋ�һ���᲻��ת��)
	 * 
	 * @param bObj
	 * @param flag
	 * @return
	 */
	private Object btod(BObjectInterface bObj, boolean flag) {
		Object dObj = getDObject(bObj, flag);
		if (dObj != null) {
			Field[] fields = ReflectHelper.getAllFields(bObj.getClass()
					.getName());
			for (int i = 0; i < fields.length; i++) {
				if (!ReflectHelper.isObjectProperty(fields[i].getType()
						.getName())) {
					setMethod(dObj, fields[i].getName(), getMethod(bObj,
							fields[i].getName()));
				} else {
					Object pObj = getMethod(bObj, fields[i].getName());
					// ���B���л�õĶ���Ϊ���򲻸���D�࣬�п���û�в�ѯ���������ֻ��ѯ��ID
					if (pObj != null) {
						Object pDObj = getDObject((BObjectInterface) pObj,
								false);
						pDObj = btod((BObjectInterface) pObj, false);
						setMethod(dObj, fields[i].getName(), pDObj);
					}
				}
			}
		}
		return dObj;
	}

	/**
	 * ֻת�������
	 * 
	 * @param bObj
	 * @param flag
	 * @param convetorObj
	 * @return
	 */
	private Object btod(BObjectInterface bObj, boolean flag, boolean convetorObj) {
		Object dObj = getDObject(bObj, flag);
		if (dObj != null) {
			Field[] fields = ReflectHelper.getAllFields(bObj.getClass()
					.getName());
			for (int i = 0; i < fields.length; i++) {
				if (!ReflectHelper.isObjectProperty(fields[i].getType()
						.getName())) {
					setMethod(dObj, fields[i].getName(), getMethod(bObj,
							fields[i].getName()));
				} else {
					Object pObj = getMethod(bObj, fields[i].getName());
					// ���B���л�õĶ���Ϊ���򲻸���D�࣬�п���û�в�ѯ���������ֻ��ѯ��ID
					if (pObj != null) {
						Object pDObj = getDObject((BObjectInterface) pObj,
								false);
						if (pObj != null && convetorObj) {
							pDObj = btod((BObjectInterface) pObj, false, false);
						} else {
							pDObj = getDObject(bObj, false);
						}
						setMethod(dObj, fields[i].getName(), pDObj);
					}
				}
			}
		}
		return dObj;
	}

	/**
	 * ͨ��Mapת��BD�����Ӧ����
	 * 
	 * @param bObj
	 * @param map
	 *            װ��Ҫת�������Ե���Ϣ
	 * @param flag
	 * @return
	 */
	private Object btod(BObjectInterface bObj, Map map, boolean flag) {
		Object dObj = getDObject(bObj, flag);
		propertyConvertor(bObj, dObj, map);
		return dObj;
	}

	private void propertyConvertor(BObjectInterface bObj, Object dObj, Map map) {
		if (dObj != null && bObj != null) {
			Object obj = map.get(bObj.getClass().getName());
			if (obj != null) {
				Property property = (Property) obj;
				// һ�����Ը�ֵ
				if (property.getPropertyValue() != null
						&& property.getPropertyValue().size() > 0) {
					for (int i = 0; i < property.getPropertyValue().size(); i++) {
						valueConvertor(bObj, dObj, (PropertyValue) property
								.getPropertyValue().get(i));
					}
				}
				// �������Ը�ֵ
				if (property.getPropertyObject() != null
						&& property.getPropertyObject().size() > 0) {
					for (int i = 0; i < property.getPropertyObject().size(); i++) {
						objectConvertor(bObj, dObj, (PropertyObject) property
								.getPropertyObject().get(i), map);
					}
				}
			}
		}
	}

	private Object getDObject(BObjectInterface bObj, boolean flag) {
		if(bObj==null){
			return null;
		}
		if (flag) {
			return ReflectHelper.newInstance(getdObjPath(bObj), null, null);
		} else {
			return findById(bObj, Integer.parseInt(getMethod(bObj, "id")
					.toString()));
		}
	}

	/**
	 * B��D����һ���������Ե�ת��
	 * 
	 * @param bObj
	 * @param dObj
	 * @param propertyValue
	 */
	private void valueConvertor(BObjectInterface bObj, Object dObj,
			PropertyValue propertyValue) {
		this.setMethod(dObj, propertyValue.getName(), getMethod(bObj,
				propertyValue, null));
	}

	/**
	 * BD���ж����������Ե�ת��
	 * 
	 * @param bObj
	 * @param dObj
	 * @param propertyObject
	 */
	public void objectConvertor(BObjectInterface bObj, Object dObj,PropertyObject propertyObject) {
		Object dJoinObj = null;
		// ���B�����������Ե�ID
		Object joinIdValue = getMethod(bObj, propertyObject.getJoinColumn());
		if(propertyObject.getName().indexOf("[")<0){
			if (joinIdValue != null && Long.parseLong(joinIdValue.toString()) > 0) {
				// ͨ��ID��ѯ�����õĶ���
				dJoinObj = findById((BObjectInterface) ReflectHelper.newInstance(propertyObject.getClassName(), null, null), Long.parseLong(joinIdValue.toString()));
				// if(dJionObj!=null){
				// Object pBObj=getMethod(bObj,propertyObject,null);
				// //�������͵�����ʱ������Ҳ���޸ĵ���ԭ����BDConverot
				// CommonBDConvertor.btod(pBObj, dJionObj);
				// }
			} else {
				joinIdValue = -1;
			}
			// ���õĶ��󸳸�D��
			setMethod(dObj, propertyObject.getName(), dJoinObj);
		}
		// ���ö�����Ӧ��ID����D��
		setMethod(dObj, propertyObject.getJoinColumn(), joinIdValue);
	}

	public void objectConvertor(BObjectInterface bObj, Object dObj,PropertyObject propertyObject, Map map) {
		Object dJoinObj = null;
		// ���B�����������Ե�ID
		Object joinIdValue = getMethod(bObj, propertyObject.getJoinColumn());
		Object bJoinObj =null;
		if(propertyObject.getName().indexOf("[")<0){
			bJoinObj = getMethod(bObj, propertyObject.getName());
			if (joinIdValue != null && Long.parseLong(joinIdValue.toString()) > 0) {
				// ͨ��ID��ѯ�����õĶ���
				dJoinObj = findById((BObjectInterface) ReflectHelper.newInstance(propertyObject.getClassName(), null, null), Long.parseLong(joinIdValue.toString()));
				if (dJoinObj != null) {
					Object pBObj = getMethod(bObj, propertyObject, null);
					// �������͵�����ʱ������Ҳ���޸ĵ���ԭ����BDConverot
					propertyConvertor((BObjectInterface) pBObj, dJoinObj, map);
				}
			} else {
				joinIdValue = -1;
			}
			// ���õĶ��󸳸�D��
			setMethod(dObj, propertyObject.getName(), dJoinObj);
		}
		// ���ö�����Ӧ��ID����D��
		setMethod(dObj, propertyObject.getJoinColumn(), joinIdValue);
		this.allBObjMap.put(bJoinObj, dJoinObj);
	}

	/**
	 * ִ����Ӧ��set����
	 * 
	 * @param dObj
	 * @param methodName
	 * @param pvalue
	 */
	private void setMethod(Object dObj, String methodName, Object pvalue) {
		ReflectHelper.setValue(dObj, methodName, pvalue);
	}

	private Object getMethod(BObjectInterface bObj, String methodName) {
		return ReflectHelper.invokeMethod(bObj, "get"+ getMethodName(methodName), null, null);
	}

	/**
	 * ִ����Ӧ��get����
	 * 
	 * @param bObj
	 * @param methodName
	 * @return
	 */
	private Object getMethod(BObjectInterface bObj, Object property,Class theClass) {
		try {
			return ReflectHelper.getBReflectData(bObj, property, theClass);
		} catch (PropertyException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getMethodName(String colName) {
		return colName.substring(0, 1).toUpperCase() + colName.substring(1);
	}

	/**
	 * ����B������ӦD��·��
	 * 
	 * @param bObj
	 * @return
	 */
	private static String getdObjPath(BObjectInterface bObj) {
		String dObjPath = "";
		String bObjPath = bObj.getClass().getName();
		String dClassName = bObjPath.substring(bObjPath.lastIndexOf('.') + 2);
		if (bObjPath.indexOf("bizobject") > 0) {
			dObjPath = bObj.getClass().getName().replaceAll("bizobject","dataobject");
			do {
				dObjPath = dObjPath.substring(0, dObjPath.lastIndexOf('.'));
				try {
					Class dClass = ReflectHelper.classForName(dObjPath + "."+ dClassName);
					break;
				} catch (ClassNotFoundException e) {
					if (dObjPath.substring(dObjPath.lastIndexOf('.') + 1).equals("dataobject")) {
						break;
					}
				}
			} while (true);
		} else if (bObjPath.indexOf("afx") > 0) {
			dObjPath = bObjPath.substring(0, bObjPath.indexOf("common"))+ "service.dataobject";
		}
		dObjPath += ("." + dClassName);
		if (ReflectHelper.newInstance(dObjPath, null, null) == null) {
			return CommonBDConvertor.btod(bObj).getClass().getName();
		} else {
			return dObjPath;
		}
	}

	/**
	 * ����ID������Ӧ��D��
	 * 
	 * @param bObj
	 * @return
	 */
	private Object findById(BObjectInterface bObj, long id) {
		if(bObj==null){
			return null;
		}
		try {
			Class dClass = ReflectHelper.classForName(getdObjPath(bObj));
			return commonContext.getUnitOfWork().readObject(dClass,	new ExpressionBuilder().get("id").equal(id));
		} catch (TOPLinkException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object processRelation(Object dObj, Map objMap, Map propertyMap) {
		if (objMap != null) {
			Object obj;
			Object propertyObj;
			Iterator it;
			
			if ((objMap.get(VariableNames.OBJECT_INSERT) != null)&& (((List) objMap.get(VariableNames.OBJECT_INSERT)).size() > 0)){
				for ( int i = 0; i < ((List) objMap.get(VariableNames.OBJECT_INSERT)).size(); ++i) {
					obj = ((List) objMap.get(VariableNames.OBJECT_INSERT)).get(i);
					if (obj != null) {
						if (obj instanceof Map) {

							Map map = (Map) obj;
							it = map.keySet().iterator();
							while (it.hasNext()) {
								Object key = it.next();
								Object object = map.get(key);
								if (key instanceof BObjectInterface) {
									Object dreleationObj = null;
									if (((BObjectInterface) key).getId() > 0)
										dreleationObj = getDObject((BObjectInterface) key, false);
									else
										dreleationObj = getDObject((BObjectInterface) key, true);

									if (object != null) {
										Map pMap = (Map) object;
										Iterator pIt = pMap.keySet().iterator();
										while (pIt.hasNext()) {
											String pkey = pIt.next().toString();
											Object pvalue = pMap.get(pkey);

											if (pvalue instanceof Map) {
												Map vMap = (Map) pvalue;
												Iterator vIt = vMap.keySet().iterator();
												while (vIt.hasNext()) {
													Object vkey = vIt.next();
													Object o = (this.allBObjMap.get(vkey) != null) ? this.allBObjMap.get(vkey): vkey;
													String method = (vMap.get(vkey) != null) ? vMap.get(vkey).toString(): "";
													pvalue = ReflectHelper.invokeMethodbyName(o, method,null, null);
												}
											}else if(pvalue instanceof BObjectInterface){
												pvalue = (this.allBObjMap.get(pvalue) != null) ? this.allBObjMap.get(pvalue): pvalue;
											}
											ReflectHelper.setValuebysetMethod(dreleationObj,pkey, pvalue);
										}
									}
									create((BObjectInterface) key,dreleationObj);
								}
							}
						} else {
							propertyObj = propertyMap.get(obj.getClass().getName());
							if (propertyObj != null)
								import_create((BObjectInterface) obj,(Property) propertyObj);
						}
					}

				}
			}

			if ((objMap.get(VariableNames.OBJECT_UPDATE) != null)&& (((List) objMap.get(VariableNames.OBJECT_UPDATE)).size() > 0)){
				for (int i = 0; i < ((List) objMap.get(VariableNames.OBJECT_UPDATE)).size(); ++i) {
					obj = ((List) objMap.get(VariableNames.OBJECT_UPDATE)).get(i);
					if (obj != null) {
						propertyObj = propertyMap.get(obj.getClass().getName());
						if (propertyObj != null)
							import_update((BObjectInterface) obj,(Property) propertyObj);
					}

				}
			}
			if ((objMap.get(VariableNames.OBJECT_CONVERTOR) != null)&& (((List) objMap.get(VariableNames.OBJECT_CONVERTOR)).size() > 0)){
				for (int i = 0; i < ((List) objMap.get(VariableNames.OBJECT_CONVERTOR)).size(); ++i) {
					obj = ((List) objMap.get(VariableNames.OBJECT_CONVERTOR)).get(i);

					if (obj != null) {
						Map map = (Map) obj;
						it = map.keySet().iterator();
						while (it.hasNext()) {
							Object key = it.next();
							Object value = map.get(key);
							Object dValue =null;
							if(value instanceof BObjectInterface){
								dValue = this.allBObjMap.get(value);
							}else if(value instanceof Map){
								Map pMap = (Map) value;
								Iterator pIt = pMap.keySet().iterator();
								while (pIt.hasNext()) {
									Object pkey = pIt.next();
									if(pkey instanceof BObjectInterface){
										Object o = (this.allBObjMap.get(pkey) != null) ? this.allBObjMap.get(pkey): pkey;
										String method = (pMap.get(pkey) != null) ? pMap.get(pkey).toString(): "";
										dValue = ReflectHelper.invokeMethodbyName(o, method,null, null);
									}
								}
							}else{
								dValue=value;
							}
							ReflectHelper.setValuebysetMethod(dObj, key.toString(), dValue);
						}
					}
				}
			}
			if ((objMap.get(VariableNames.OBJECT_OLDMETHOD) != null)&& (((List) objMap.get(VariableNames.OBJECT_OLDMETHOD)).size() > 0)){
				for (int i = 0; i < ((List) objMap.get(VariableNames.OBJECT_OLDMETHOD)).size(); ++i) {
					obj = ((List) objMap.get(VariableNames.OBJECT_OLDMETHOD)).get(i);
					if ((obj != null) && (((List) obj).size() > 0)) {
						List list = (List) obj;
						if (((Object[]) list.get(3))[0] == null) {
							((Object[]) list.get(3))[0] = this.commonContext;
						}

						if (((Object[]) list.get(3))[1] instanceof Map) {
							Object releationObj = null;
							Map dMap = (Map) ((Object[]) list.get(3))[1];
							Iterator dIt = dMap.keySet().iterator();
							while (dIt.hasNext()) {
								Object key = dIt.next();
								Object object = dMap.get(key);
								if (object != null) {
									Map pMap = (Map) object;
									Iterator pIt = pMap.keySet().iterator();
									if (key instanceof String)
										releationObj = ReflectHelper.newInstanceByName(key.toString());
									else if (key instanceof BObjectInterface)
										releationObj = findById((BObjectInterface) key,((BObjectInterface) key).getId());

									while (pIt.hasNext()) {
										String pkey = pIt.next().toString();
										Object pvalue = pMap.get(pkey);

										if (pvalue instanceof Map) {
											Map vMap = (Map) pvalue;
											Iterator vIt = vMap.keySet().iterator();
											while (vIt.hasNext()) {
												Object vkey = vIt.next();
												Object o = (this.allBObjMap.get(vkey) != null) ? this.allBObjMap.get(vkey): vkey;
												String method = (vMap.get(vkey) != null) ? vMap.get(vkey).toString(): "";
												pvalue = ReflectHelper.invokeMethodbyName(o,method, null,null);
											}
										}else if(pvalue instanceof BObjectInterface){
											Object o = (this.allBObjMap.get(pvalue) != null) ? this.allBObjMap.get(pvalue): pvalue;
											if(o instanceof BObjectInterface&&((BObjectInterface)o).getId()>0){
												o=findById((BObjectInterface)o, ((BObjectInterface)o).getId());
											}
											if(o!=null)
												pvalue=o;
										}
										ReflectHelper.setValuebysetMethod(releationObj, pkey, pvalue);
									}
								}
							}
							((Object[]) list.get(3))[1] = releationObj;
						}

						Class[] args = (Class[]) null;
						if (list.get(2) != null) {
							Object[] objs = (Object[]) list.get(2);
							args = new Class[objs.length];
							for (int j = 0; j < objs.length;) {
								try {
									if (objs[j] instanceof String) {
										args[j] = ReflectHelper.classForName(objs[j].toString());
									}
									if (objs[j] instanceof Class){
										args[j] = ((Class) objs[j]);
									}
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								++j;
							}

						} else {
							args = (Class[]) null;
						}
						if (list.get(0) instanceof String) {
							ReflectHelper.invokeStaticMethod(list.get(0).toString(), list.get(1).toString(), args,(Object[]) list.get(3));
						} else
							ReflectHelper.invokeMethodbyName(list.get(0), list.get(1).toString(), args, (Object[]) list.get(3));
					}
				}
			}
		}
		return dObj;
	}
}
