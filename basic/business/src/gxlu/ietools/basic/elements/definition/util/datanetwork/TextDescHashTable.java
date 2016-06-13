/***************************************************************************************
 * $RCSfile: TextDescHashTable.java,v $
 ***************************************************************************************/
package gxlu.ietools.basic.elements.definition.util.datanetwork;

import java.io.Serializable;
import java.util.Hashtable;

/**
 *�������Ҫ�������ı�������ʱ���õģ��Ͳ�ҪServer���ṩString���صĽӿ�
 *�����ص�hashtableת����String��������һ���ӿ�
 *ֻҪServer���ܹ������㹻����Ϣ���Ϳ����ڿͻ���ת����û�б�Ҫ��Server����װ
 ***/
public class TextDescHashTable implements Serializable{
  public TextDescHashTable()
  {
    ht = new Hashtable();
    //��·���ƽ��ڿͻ��˽������ã�����ȱʡ��Ϊ��
  }
  //Hashtable�в���Key����Valueֻ��String
  private Hashtable ht;

  public void putValue(String key,String value)
  {
    if(value==null|| key==null)return;
    ht.put(key,value);
  }
  public String getValue(String key)
  {//����ǿ������ת����
    return (String)ht.get(key);
  }
  public String toTextDescString()
  {
    if(ht==null)    return null;
    String textDesc = new String("");
    String type = (String)ht.get(TEXTDESC_POINTTYPE);
    String pos = (String)ht.get(TEXTDESC_POSITION);
    if(type.equals(TEXTDESC_POINTTYPE_ATM))
    {
      textDesc += "<"+ht.get(TEXTDESC_PORTNAME)+">";
      textDesc += "<"+ht.get(TEXTDESC_POSITION)+">";
//      textDesc += ht.get(TEXTDESC_STARTSLOT);
//      textDesc += ht.get(TEXTDESC_ENDSLOT);
//      textDesc += ht.get(TEXTDESC_DLCI);
      textDesc += "<"+ht.get(TEXTDESC_VPI)+"/";
      textDesc += ht.get(TEXTDESC_VCI)+">";
      if(!pos.equals(TEXTDESC_POSITION_RELAY))
      {//ֻҪ����ת�Ӷ˿ڣ���Ҫ��ӡ�������Ӻ��ڲ����
	textDesc += "<"+ht.get(TEXTDESC_INNERCONNECTOR)+"��";
	textDesc += ht.get(TEXTDESC_OUTERCONNECTOR)+">";
      }
    }else if(type.equals(TEXTDESC_POINTTYPE_FR))
    {
      textDesc += "<"+ht.get(TEXTDESC_PORTNAME)+">";
      textDesc += "<"+ht.get(TEXTDESC_POSITION)+">";
      textDesc += "<"+ht.get(TEXTDESC_SLOTS)+",";
      textDesc += ht.get(TEXTDESC_DLCI)+">";
//      textDesc += ht.get(TEXTDESC_VPI);
//      textDesc += ht.get(TEXTDESC_VCI);
      if(!pos.equals(TEXTDESC_POSITION_RELAY))
      {//ֻҪ����ת�Ӷ˿ڣ���Ҫ��ӡ�������Ӻ��ڲ����
	textDesc += "<"+ht.get(TEXTDESC_INNERCONNECTOR)+"��";
	textDesc += ht.get(TEXTDESC_OUTERCONNECTOR)+">";
      }
    }else if(type.equals(TEXTDESC_POINTTYPE_DDNE1))
    {
      textDesc += "<"+ht.get(TEXTDESC_PORTNAME)+">";
      textDesc += "<"+ht.get(TEXTDESC_POSITION)+">";
      textDesc += "<"+ht.get(TEXTDESC_SLOTS)+">";
//      textDesc += ht.get(TEXTDESC_DLCI);
//      textDesc += ht.get(TEXTDESC_VPI);
//      textDesc += ht.get(TEXTDESC_VCI);
      if(!pos.equals(TEXTDESC_POSITION_RELAY))
      {//ֻҪ����ת�Ӷ˿ڣ���Ҫ��ӡ�������Ӻ��ڲ����
	textDesc += "<"+ht.get(TEXTDESC_INNERCONNECTOR)+"��";
	textDesc += ht.get(TEXTDESC_OUTERCONNECTOR)+">";
      }
    }else if(type.equals(TEXTDESC_POINTTYPE_DDNUIF))
    {
      textDesc += "<"+ht.get(TEXTDESC_PORTNAME)+">";
      textDesc += "<"+ht.get(TEXTDESC_POSITION)+">";
//      textDesc += ht.get(TEXTDESC_STARTSLOT);
//      textDesc += ht.get(TEXTDESC_ENDSLOT);
//      textDesc += ht.get(TEXTDESC_DLCI);
//      textDesc += ht.get(TEXTDESC_VPI);
//      textDesc += ht.get(TEXTDESC_VCI);
      if(!pos.equals(TEXTDESC_POSITION_RELAY))
      {//ֻҪ����ת�Ӷ˿ڣ���Ҫ��ӡ�������Ӻ��ڲ����
	textDesc += "<"+ht.get(TEXTDESC_INNERCONNECTOR)+"��";
	textDesc += ht.get(TEXTDESC_OUTERCONNECTOR)+">";
      }
    }else if(type.equals(TEXTDESC_POINTTYPE_DDNPORT))
    {
      textDesc += "<"+ht.get(TEXTDESC_PORTNAME)+">";
      textDesc += "<"+ht.get(TEXTDESC_POSITION)+">";
//      textDesc += ht.get(TEXTDESC_STARTSLOT);
//      textDesc += ht.get(TEXTDESC_ENDSLOT);
//      textDesc += ht.get(TEXTDESC_DLCI);
//      textDesc += ht.get(TEXTDESC_VPI);
//      textDesc += ht.get(TEXTDESC_VCI);
      if(!pos.equals(TEXTDESC_POSITION_RELAY))
      {//ֻҪ����ת�Ӷ˿ڣ���Ҫ��ӡ�������Ӻ��ڲ����
	textDesc += "<"+ht.get(TEXTDESC_INNERCONNECTOR)+"��";
	textDesc += ht.get(TEXTDESC_OUTERCONNECTOR)+">";
      }
    }else{
      textDesc += ht.get(TEXTDESC_PORTNAME);
      textDesc += ht.get(TEXTDESC_POSITION);
      textDesc += ht.get(TEXTDESC_SLOTS);
      textDesc += ht.get(TEXTDESC_DLCI);
      textDesc += ht.get(TEXTDESC_RATE);
      textDesc += ht.get(TEXTDESC_VPI);
      textDesc += ht.get(TEXTDESC_VCI);
      textDesc += ht.get(TEXTDESC_INNERCONNECTOR);
      textDesc += ht.get(TEXTDESC_OUTERCONNECTOR);
    }
    textDesc += "\n";
    return textDesc;
  }
//·���ı������ı��Title
  static public final String TEXTDESC_PATHNAME="��·����";
  static public final String TEXTDESC_HOST="����";
  static public final String TEXTDESC_DEVICE="�豸";
  static public final String TEXTDESC_NODE="�ڵ�";
  static public final String TEXTDESC_RACK="����";
  static public final String TEXTDESC_SHELF="�Ӽ�";
  static public final String TEXTDESC_SUBNE= "����";
  static public final String TEXTDESC_PACKAGE="�忨";

  static public final String TEXTDESC_PORTNAME="�˿�";
  static public final String TEXTDESC_POSITION= "λ��";
  static public final String TEXTDESC_SLOTS="ʱ϶";
  static public final String TEXTDESC_DLCI="DLCI";
  static public final String TEXTDESC_RATE="����";
  static public final String TEXTDESC_VPI="VPI";
  static public final String TEXTDESC_VCI="VCI";
  static public final String TEXTDESC_OUTERCONNECTOR= "������";
  static public final String TEXTDESC_INNERCONNECTOR="�ڲ����";
  static public final String TEXTDESC_INOUTCONNECTOR="�շ�����";
  //������Լ��ӵ���չ�����ݵ�����ͽ����ַ�����ʽ��
  //����<>��֪��Ӧ����ʲô�ط�
  static public final String TEXTDESC_POSITION_BEGIN="����";
  static public final String TEXTDESC_POSITION_RELAY="ת��";
  static public final String TEXTDESC_POSITION_END="�Զ�";

  static public final String TEXTDESC_POINTTYPE="������";
  static public final String TEXTDESC_POINTTYPE_ATM= "ATM";
  static public final String TEXTDESC_POINTTYPE_FR= "FR";
  static public final String TEXTDESC_POINTTYPE_DDNE1="DDNE1";
  static public final String TEXTDESC_POINTTYPE_DDNUIF= "DDNUIF";
  static public final String TEXTDESC_POINTTYPE_DDNPORT= "DDNPORT";
}
