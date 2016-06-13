
package gxlu.ietools.basic.collection.util;

import gxlu.afx.publics.swingx.thread.TimeConsumeJob;
import gxlu.afx.publics.swingx.window.JGxluMessageBox;
import gxlu.afx.publics.swingx.window.JGxluWaitDialog;
import gxlu.afx.publics.util.TextLibrary;
import gxlu.afx.system.clientmain.TnmsClient;
import gxlu.afx.system.common.CommonClientEnvironment;
import gxlu.afx.system.common.SDHCursorControl;
import gxlu.afx.system.print.PrintExcelExt;

import java.awt.Frame;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;


public class ExportUtil {
    public static TnmsClient sdhFrame = new TnmsClient();
    private static ResultController resultController;
    private static Frame mf = CommonClientEnvironment.getMainFrame();
    public ExportUtil(){
    }    
    private static List selectedItems;
    public static List getSelectedItems()
    {
        return selectedItems;
    }
    public static void setSelectedItems(List _selectedItems)
    {
        selectedItems=_selectedItems;
    }
    public static void export(ResultController resultController1)
    {
    	resultController=resultController1;
        new JGxluWaitDialog( sdhFrame, TextLibrary.getText( "caption_WaitDialog" ), TextLibrary.getText( "���ڵ�����Ϣ�����Ժ�..." ), new TimeConsumeJob()
        {
            public Object doInConstruct()
            {
                SDHCursorControl.openWaitCursor();
                CommonClientEnvironment.getTimeLog().beginTiming( "toolmenu/export" );
                printDirect();
                SDHCursorControl.closeWaitCursor();
                CommonClientEnvironment.getTimeLog().endTiming( "toolmenu/export" );
                return "success";
            }
            public void doInFinished()
            {
                return;
            }
        } );
    }
    /**
     *  ��������
     */
    public static void printTemplet(ResultController resultController1)
    {
    	resultController=resultController1;
    	ImportUtil.outputTemplet(resultController.getDynamicObject(),resultController.getObjectValueCount());
    }
    /**
     *  ��������
     */
    private static void printDirect()
    {
        setSelectedItems(resultController.getResult() instanceof List?(List)resultController.getResult():null);
        printByList();
    }
    /**
     *  ����
     * @param v 
     */
    private static void printByList(){
    	if ( selectedItems == null || selectedItems.size() < 1 )
        {
            JGxluMessageBox.show( CommonClientEnvironment.getMainFrame(), "�����ڻ�û��ѡ�е�������", "����", JOptionPane.ERROR_MESSAGE );
            return;
        }
        new JGxluWaitDialog( sdhFrame, TextLibrary.getText( "caption_WaitDialog" ), TextLibrary.getText( "���ڵ����豸��Ϣ�����Ժ�..." ), new TimeConsumeJob()
        {
            public Object doInConstruct()
            {
                SDHCursorControl.openWaitCursor();
                CommonClientEnvironment.getTimeLog().beginTiming( "toolmenu/export" );
                PrintExcelExt printer = new PrintExcelExt();
                Vector tempVector= data4Vector(selectedItems);
                String filename=printer.chooseFile(mf, "");
                if(null!=filename&&!filename.equals("")){
	                boolean flag = printer.printExtForSheet(getColNames(), tempVector, new Vector(),5000,filename);  
	                SDHCursorControl.closeWaitCursor();
	                if ( flag )
	                {
	                    JGxluMessageBox.show( CommonClientEnvironment.getMainFrame(), "�����ɹ�", "ȷ��", JGxluMessageBox.INFORMATION_MESSAGE );
	                    return "success";
	                }else{
	                	JGxluMessageBox.show( CommonClientEnvironment.getMainFrame(), "����ʧ��", "ȷ��", JGxluMessageBox.INFORMATION_MESSAGE );
	                }
                }
                CommonClientEnvironment.getTimeLog().endTiming( "toolmenu/export" );
                return "fail";
            }
            public void doInFinished()
            {
                return;
            }
        } );
    }
    //��ñ���
    private static Vector getColNames()
    {
    	Vector vecColName=new Vector();
        for(int i = 0;i < resultController.getObjectValueCount();i++)
        {
        	Object resultObj=ImportUtil.invokeMethod(getResultController(0), "getF"+(i+1));
        	vecColName.add(resultObj!=null?(String)resultObj:"");
        }
        vecColName.add("");
        vecColName.add("�������ԭ��");
        return vecColName;
    }
    //�����Ӧ�Ķ�̬����
    private  static DynamicObject getResultController(int index){
    	Collection col=resultController.getResult();
    	if(col instanceof List){
    		return (DynamicObject)((List)col).get(index);
    	}else{
    		return null;
    	}
    }
    //��װ����
    private static Vector data4Vector(List list)
    {
        if (list == null || list.size() < 1)
            return null;
        Vector dataVector = new Vector();
        for (int i = 1; i < list.size(); i++)
        {
        	DynamicObject dynamicObject=(DynamicObject)list.get(i);
            Vector vec = new Vector();
            for(int j=0;j<resultController.getObjectValueCount();j++){
            	vec.addElement(ImportUtil.invokeMethod(getResultController(i), "getF"+(j+1)));
            }
            dataVector.addElement(vec); 
        }
        return dataVector;
    }
}
