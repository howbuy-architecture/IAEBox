package gxlu.ietools.basic.system.util;

public class PropertyConstvalue
{
  public static final String AUDIT_OPERATIONTYPE_ADD = "NEW";
  public static final String AUDIT_OPERATIONTYPE_UPDATE = "MODIFY";
  public static final String AUDIT_OPERATIONTYPE_DELETE = "DELETE";
  public static final String AUDIT_OPERATIONTYPE_OTHER = "δ֪";
  //��������
  public final static byte PERSIST_TYPE_NOCHANGE = 1;
  public final static byte PERSIST_TYPE_ADD = 2;
  public final static byte PERSIST_TYPE_UPDATE = 3;
  public final static byte PERSIST_TYPE_DELETE = 4;
  
  //���
  public static final byte DNSUBNEAUDITS_OPERATIONTYPE_NEW = 0; //����
  public static final byte DNSUBNEAUDITS_OPERATIONTYPE_MODIFY = 1; //�޸�
  public static final byte DNSUBNEAUDITS_OPERATIONTYPE_DELETE = 2; //ɾ��
  
  //�ƶ��������
  public final static int MNGLOBAL_AUDITSOPERATIONTYPE_NEW = 1; //�½�
  public final static int MNGLOBAL_AUDITSOPERATIONTYPE_MODIFY = 2; //�޸�
  public final static int MNGLOBAL_AUDITSOPERATIONTYPE_DELETE = 3; //ɾ��
  
  public static final String ASSRT_DEFAULT_COSTCENTER = "������";
  public static final String ASSRT_DEFAULT_STATUS = "����";
  public static final int GLOBAL_ISTEMPLATE_NOMODULE = 0;
  public static final int GLOBAL_ISTEMPLATE_MODULE = 1;
  public static final int GLOBAL_ISTEMPLATE_COMPNENT = 2;
  public static final byte DNDEVICE_ROLES_PE = 1;
  public static final byte DNDEVICE_ROLES_CE = 2;
  public static final byte DNDEVICE_ROLES_ATM = 3;
  public static final byte DNDEVICE_ROLES_DDN = 4;
  public static final byte DNDEVICE_ROLES_FR = 5;
  public static final byte DNDEVICE_ROLES_ROUTER = 6;
  public static final byte DNDEVICE_ROLES_TWOSWITCH = 7;
  public static final byte DNDEVICE_ROLES_THREESWITCH = 8;
  public static final byte DNDEVICE_ROLES_FOURSWITCH = 9;
  public static final byte DNDEVICE_ROLES_XPONOLT = 20;
  public static final byte DNDEVICE_ROLES_XPONONU = 21;
  public static final long NMTOPOMAP_MAP_TRANSPORTSYSTEMMAP_ID = 1L;
  public static final long NMTOPOMAP_MAP_TRANSPORTNETWORKMAP_ID = 2L;
  public static final long NMTOPOMAP_MAP_SWITCHINGNETWORKMAP_ID = 3L;
  public static final long NMTOPOMAP_MAP_ACCESSINGNETWORKMAP_ID = 4L;
  public static final long NMTOPOMAP_MAP_SYNCNETWORKMAP_ID = 5L;
  public static final long NMTOPOMAP_MAP_MW_ID = 7L;
  public static final long NMTOPOMAP_MAP_PAGING_ID = 8L;
  public static final long NMTOPOMAP_MAP_MOBILE_ID = 9L;
  public static final long NMTOPOMAP_MAP_LOCATION_ID = 10L;
  public static final long NMTOPOMAP_MAP_CABLE_ID = 11L;
  public static final long NMTOPOMAP_MAP_64KSLNETWORKMAP_ID = 12L;
  public static final long NMTOPOMAP_MAP_DATA_DDNFR_ID = 13L;
  public static final long NMTOPOMAP_MAP_DATA_ATM_ID = 14L;
  public static final long NMTOPOMAP_MAP_DATA_X25_ID = 15L;
  public static final long NMTOPOMAP_MAP_DATA_BAS_ID = 16L;
  public static final long NMTOPOMAP_MAP_DATA_IPHOTEL_ID = 17L;
  public static final long NMTOPOMAP_MAP_DATA_NAS_ID = 18L;
  public static final long NMTOPOMAP_MAP_DATA_IPLAN_ID = 19L;
  public static final long NMTOPOMAP_MAP_DATA_MEETINGTV_ID = 20L;
  public static final long NMTOPOMAP_MAP_DATA_DCN_ID = 21L;
  public static final long NGTOPOMAP_MAP_TRANSPORTNETWORKMAP_ID = 22L;
  public static final long NGTOPOMAP_MAP_CIRCUITMAP_ID = 23L;
  public static final long NGTOPOMAP_MAP_ROOMMAP_ID = 24L;
  public static final long NGTOPOMAP_MAP_EMSMAP_ID = 25L;
  public static final long NMTOPOMAP_MAP_DNDOMAIN_ID = 26L;
  public static final long NMTOPOMAP_MAP_NODE_ID = 27L;
  public static final long NMTOPOMAP_MAP_IP_ID = 28L;
  public static final long NMTOPOMAP_MAP_VPN_ID = 29L;
  public static final long NMTOPOMAP_MAP_XDSL_ID = 30L;
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_NE = "NE";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_TRANSPORTSYSTEM = "TSYSTEM";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_REGION = "REGION";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_SITE = "SITE";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_DATASET = "DATASET";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_DATANE = "DATANE";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_CABLETERM = "CABLETERM";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_SL64KSYSTEM = "SL64KSYSTEM";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_PAGINGCENTER = "PAGINGCENTER";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_PAGINGSTATION = "STATION";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_TOWER = "TOWER";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_REMOTEBTS = "REMOTEBTS";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_ROOM = "ROOM";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_CABLETERMHOST = "CABLETERM_HOST";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_DNDOMAIN = "DNDOMAIN";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_NODE = "NODE";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_PE = "PE";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_CE = "CE";
  public static final String NMTOPOMAP_MAPNODE_ENTITYTYPE_DSLNODE = "DSLNODE";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_DNDOMAIN = "MAPNODETYPE-DNDOMAIN-";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_NODE_ATM = "MAPNODETYPE-NODE-ATM";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_NODE_DDN = "MAPNODETYPE-NODE-DDN";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_NODE_FR = "MAPNODETYPE-NODE-FR";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_NODE_ATM_DDN_FR = "MAPNODETYPE-NODE-ATM_DDN_FR";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_NODE_IP = "MAPNODETYPE-NODE-IP";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_NODE_OTHER = "MAPNODETYPE-NODE-OTHER";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_IP_NODE = "MAPNODETYPE-IP-NODE";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_CE = "MAPNODETYPE-CE";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_PE = "MAPNODETYPE-PE";
  public static final String NMTOPOMAP_MAPNODE_TYPE_PREFIX_FOR_DSLNODE = "MAPNODETYPE-DSLNODE";
  
  
  public static String Audit_DELIMITER_COL = "\1";
  
  public static final byte ASSETCATEGORY_CATEGORYLEVEL_CLASS = 1; //��
  public static final byte ASSETCATEGORY_CATEGORYLEVEL_ITEM = 2; //��
  public static final byte ASSETCATEGORY_CATEGORYLEVEL_CATEGORY = 3; //Ŀ
  public static final byte ASSETCATEGORY_CATEGORYLEVEL_SECTION = 4; //��
  public static final byte ASSETCATEGORY_CATEGORYLEVEL_DOT = 5; //��
  
  public static final byte DNCONNECTION_LIFESTATUS_OPEN = 0; //��ͨ
  public static final byte DNCONNECTION_LIFESTATUS_WAIT_OPEN = 1; //����ͨ
  public static final byte DNCONNECTION_LIFESTATUS_WAIT_DESTROY = 2; //�����
  public static final byte DNCONNECTION_LIFESTATUS_WAIT_SWITCH = 3; //�����
  public static final byte DNCONNECTION_LIFESTATUS_WAIT_CHANGE = 4; //���Ĳ�
  public static final byte DNCONNECTION_LIFESTATUS_WAIT_STOP = 5; //��ͣ��
  public static final byte DNCONNECTION_LIFESTATUS_WAIT_START = 6; //������
  public static final byte DNCONNECTION_LIFESTATUS_STOP = 7; //ͣ��
  public static final byte DNCONNECTION_LIFESTATUS_OCCUPY_DESTROY = 8; //���Ԥռ
  public static final byte DNCONNECTION_LIFESTATUS_OCCUPY_SWITCH = 9; //�ƻ�Ԥռ
  public static final byte DNCONNECTION_LIFESTATUS_OCCUPY_CHANGE = 10; //�Ĳ�Ԥռ
  public static final byte DNCONNECTION_LIFESTATUS_OCCUPY_STOP = 11; //ͣ��Ԥռ
  public static final byte DNCONNECTION_LIFESTATUS_OCCUPY_START = 12; //����Ԥռ
  public static final byte DNCONNECTION_LIFESTATUS_STOPWAIT_DESTROY = 13; //ͣ�������
//add by tailichun 20060322
  public static final byte DNCONNECTION_LIFESTATUS_OCCUPY_SERVICE = 14; //ҵ��Ԥռ
  public static final byte DNCONNECTION_LIFESTATUS_DESTORY = 15; //�Ѳ��
  
  
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
  
  
  public static String OPR_ADD = "����";
  public static String OPR_UPDATE = "�޸�";
  public static String OPR_DELETE = "ɾ��";
  public static String OPR_INSTALL = "��װ";
  public static String OPR_DESTROY = "���";
  public static String OPR_SWITCH = "�ƻ�";
  public static String OPR_CHANGE = "�Ĳ�";
  public static String OPR_HALT = "ͣ��";
  public static String OPR_RESUME = "����";
  public static String OPR_FINISH = "�����깤";
  public static String OPR_CANCEL = "��������";
  public static String OPR_MODIFYDESIGN = "�޸ĵ������";

  public static String DELIMITER_COL = ",";
  public static String DELIMITER_ROW = "\n";
  
  
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_ATM = 1;
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_DDN = 2;
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_FR = 3;
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_RELAYPATH = 4; //�������м�
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_IPRELAYPATH = 5; //IP���м�
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_XDSLRELAYPATH = 6; //xDSL�м�
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_DDNMULTIPATH = 7; //DDN���õ�·
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_WAN = 8; //������ҵ��
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_VPN = 9; //VPNҵ��
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_LAN = 10; //LANҵ��
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_CIRCUIT = 11; //�û���·

  //add by tailichun 20060713f
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_ATMPORT = 14; //ATMPORT�˿ڵ�·
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_DDNPORT = 15; //DDNPORT�˿ڵ�·
  
  public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_FRPORTSERVICE = 12; //FR�˿ڵ�·
//add by chenlijuan20050608
    public final static byte DNBUSINESSPATHAUDITS_ENTITYTYPE_COMMONSERVICE = 13; //ͨ��ҵ���·
    
    public static final byte RELAYPATH_TYPE_DDN = 0; //DDN
    public static final byte RELAYPATH_TYPE_FR = 1; //FR
    public static final byte RELAYPATH_TYPE_ATM = 2; //ATM
    public static final byte RELAYPATH_TYPE_IP = 3; //IP
    public static final byte RELAYPATH_TYPE_XDSL = 4; //xDSL
    public static final byte RELAYPATH_TYPE_USERCIRCUIT = 5; //�û���·
    public static final byte RELAYPATH_TYPE_INTERLINKAGE = 6; //����
    public static final byte RELAYPATH_TYPE_OTHER = 99; //Other
    //add by chenlijuan 20050609 �м̵�·��������
    public static final byte RELAYPATH_TYPE_IDC = 7; //IDC
    public static final byte RELAYPATH_TYPE_DCN = 8; //DCN
    public static final byte RELAYPATH_TYPE_WLAN = 9; //WLAN
    public static final byte RELAYPATH_TYPE_EXTRASERVICE = 10; //��ֵ����
    public static final byte RELAYPATH_TYPE_EPON = 11; //EPON�м�
    //add by wuzhanghui 20091123 WLAN�м̵�·��������
    public static final byte RELAYPATH_TYPE_WLANACESS = 12; //WLAN�����м�
    
    
    public final static byte DNPATH_INTEGRITYOFPATH_BOTHINTEGRITY = 0; //��������
    public final static byte DNPATH_INTEGRITYOFPATH_SINGLEINTEGRITY = 1; //��������
    public final static byte DNPATH_INTEGRITYOFPATH_NEITHERINTEGRITY = 2; //���˲�����
}