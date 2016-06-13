/**********************************************************************
 *$RCSfile: UIXmlCfgMgtTree.java,v $ $Revision: 1.3 $ $Date: 2010/04/20 02:08:06 $
 *********************************************************************/
package gxlu.ietools.client.template;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.tree.*;

import gxlu.afx.publics.swingx.text.JGxluStringTextField;
import gxlu.afx.publics.swingx.window.*;
import gxlu.afx.publics.util.Images;

import gxlu.afx.system.common.*;
import gxlu.ietools.basic.collection.ArrayLoader;
import gxlu.ietools.basic.collection.util.ExportUtil;
import gxlu.ietools.basic.collection.util.ImportUtil;
import gxlu.ietools.basic.collection.util.ResultController;
import gxlu.ietools.basic.system.container.ContainerBootStrap;
import gxlu.ietools.basic.system.container.ContainerException;
import gxlu.ietools.basic.system.container.ContainerFactory;
import gxlu.ietools.basic.system.container.ContainerImpl;
import gxlu.ietools.basic.system.context.Context;
import gxlu.ietools.basic.system.util.ClassNoteNames;
import gxlu.ietools.basic.system.util.ExectionUtil;
import gxlu.ietools.property.mapping.Property;
import gxlu.ietools.property.mapping.PropertyObject;
import gxlu.ietools.property.mapping.PropertyValue;
import gxlu.ietools.property.xml.DomHelper;
import gxlu.ietools.property.xml.DomParse;
import gxlu.ietools.property.xml.DomTemplateParse;

/**
 * <li>Title: test.java</li> <li>Description: ���</li> <li>Project: ResouceWorks</li> <li>
 * Copyright: Copyright (c) 2010</li>
 * 
 * @Company: GXLU. All Rights Reserved.
 * @author zhangwei Of VASG.Dept
 * @version 1.0
 */

public class UIXmlCfgMgtTree extends JGxluChildPanel implements JGxluWindowInterface, JGxluSimpleDialogInterface,
		TreeExpansionListener, MouseListener, ActionListener
{

	// GUI definition
	private DefaultMutableTreeNode rootAll = new DefaultMutableTreeNode("Ŀ�����");

	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ŀ�����");

	// B��������ʾ����B�༰�����ֶ�
	protected JTree treeAll = new JTree(rootAll);

	// XML������ʾѡ��B�༰���ֶ�
	protected JTree treeXml = new JTree(root);

	// ��������btPanel��panel��treePanelAll+btPanel+treePanel
	JPanel navigationPanel = new JPanel();

	// ��ߴ�panel��buttonPanel+dataviewPanel
	JPanel leftPanel = new JPanel();

	// treeAll��panel
	JPanel treePanelAll = new JPanel();

	// ѡ���ֶι��ߵ�panel��btAdd+btDel
	JPanel btPanel = new JPanel();

	// treeXml��panel
	JPanel treePanel = new JPanel();

	// ������ť��panel��λ��leftPanel���ϲ�
	JPanel buttonPanel = new JPanel();

	// �ڵ����Ե�panel��topPanel+midPanel+bottomPanel
	JPanel dataviewPanel = new JPanel();

	// property��Ľڵ�����
	JPanel topPanel = new JPanel();

	// propertyValue��Ľڵ�����
	JPanel midPanel = new JPanel();

	// propertyObject��Ľڵ�����
	JPanel bottomPanel = new JPanel();

	// ������ť
	JButton btAdd = new JButton();

	JButton btDel = new JButton();

	JButton btSave = new JButton();

	JButton btCancle = new JButton();

	JButton btOutputXml = new JButton();

	JButton btOutputXls = new JButton();

	// Property��������ֶ�
	JLabel lbBclass = new JLabel();

	JLabel lbCname = new JLabel();

	JLabel lbIsInsert = new JLabel();

	JLabel lbIsUpdate = new JLabel();

	JLabel lbLineNum = new JLabel();

	JLabel lbLineMax = new JLabel();

	JGxluStringTextField tfBclass = new JGxluStringTextField();

	JGxluStringTextField tfCname = new JGxluStringTextField();

	JGxluStringTextField tfIsInsert = new JGxluStringTextField();

	JGxluStringTextField tfIsUpdate = new JGxluStringTextField();

	JGxluStringTextField tfLineNum = new JGxluStringTextField();

	JGxluStringTextField tfLineMax = new JGxluStringTextField();

	// propertyValue�����ֶ�
	JLabel lbName = new JLabel();

	JLabel lbColumnseq = new JLabel();

	JLabel lbColumnTitle = new JLabel();

	JLabel lbIsDatadict = new JLabel();

	JLabel lbDatadictClass = new JLabel();

	JLabel lbDatadictAttr = new JLabel();

	JLabel lbIsQuery = new JLabel();

	// JLabel lbDataDict = new JLabel();

	JGxluStringTextField tfName = new JGxluStringTextField();

	JGxluStringTextField tfColumnseq = new JGxluStringTextField();

	JGxluStringTextField tfColumnTitle = new JGxluStringTextField();

	JGxluComboBox cbIsDatadict = new JGxluComboBox();

	JGxluStringTextField tfDatadictClass = new JGxluStringTextField();

	JGxluStringTextField tfDatadictAttr = new JGxluStringTextField();

	JGxluComboBox cbIsQuery = new JGxluComboBox();

	// PropertyObject�����ֶ�
	JLabel lbO2OName = new JLabel();

	JLabel lbO2OSeq = new JLabel();

	JLabel lbO2OTitle = new JLabel();

	JLabel lbO2OJoinCol = new JLabel();

	JLabel lbO2OClass = new JLabel();

	JLabel lbO2OClassCol = new JLabel();

	JLabel lbO2OIsQuery = new JLabel();

	JLabel lbO2OQueryClass = new JLabel();

	JGxluStringTextField tfO2OName = new JGxluStringTextField();

	JGxluStringTextField tfO2OSeq = new JGxluStringTextField();

	JGxluStringTextField tfO2OTitle = new JGxluStringTextField();

	JGxluStringTextField tfO2OJoinCol = new JGxluStringTextField();

	JGxluStringTextField tfO2OClass = new JGxluStringTextField();

	JGxluStringTextField tfO2OClassCol = new JGxluStringTextField();

	JGxluComboBox cbO2OIsQuery = new JGxluComboBox();

	JGxluStringTextField tfO2OQueryClass = new JGxluStringTextField();

	PropertyValue tempPropertyValue;

	PropertyObject tempPropertyObject;

	DefaultMutableTreeNode tempNode;

	Hashtable htModule = new Hashtable();

	public List domList = new ArrayList();

	public List BclassList = new ArrayList();

	/**
	 * ���캯��
	 * 
	 * @param appName
	 */
	public UIXmlCfgMgtTree()
	{
		try
		{
			DomParse domTemplateParse = new DomTemplateParse();
			DomHelper domhelper = new DomHelper(domTemplateParse);
			domList = domhelper.readDomParseAllList();
			BclassList = domhelper.getAllPropertyAllList();
			jbInit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception
	{
		this.setLayout(new BorderLayout());
		navigationPanel.setLayout(new BorderLayout());
		leftPanel.setLayout(new BorderLayout());

		treePanelAll.setLayout(new BorderLayout());
		btPanel.setLayout(new GridBagLayout());
		treePanel.setLayout(new BorderLayout());

		buttonPanel.setLayout(new GridBagLayout());
		dataviewPanel.setLayout(new GridBagLayout());
		topPanel.setLayout(new GridBagLayout());
		midPanel.setLayout(new GridBagLayout());
		bottomPanel.setLayout(new GridBagLayout());
		btPanel.setLayout(new GridBagLayout());

		// Renderer
		treeXml.setCellRenderer(new SysDataTreeCellRenderer());

		this.add(navigationPanel, BorderLayout.WEST);
		this.add(leftPanel, BorderLayout.CENTER);

		// navigationPanel
		navigationPanel.add(treePanelAll, BorderLayout.WEST);
		navigationPanel.add(btPanel, BorderLayout.CENTER);
		navigationPanel.add(treePanel, BorderLayout.EAST);

		// leftPanel
		leftPanel.add(buttonPanel, BorderLayout.NORTH);
		leftPanel.add(dataviewPanel, BorderLayout.CENTER);

		// treePanelAll
		JScrollPane treeViewAll = new JScrollPane(treeAll);
		treeViewAll.setPreferredSize(new Dimension(220, 324));
		treePanelAll.add(treeViewAll, BorderLayout.CENTER);
		treePanelAll.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)), "��ѡ�ֶ��б�",
			TitledBorder.LEFT, TitledBorder.TOP, new Font("����", 0, 13)), BorderFactory.createEmptyBorder(5, 0, 5, 0)));

		btPanel.add(btAdd, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(55, 0, 5, 0), 0, 0));
		btPanel.add(btDel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(55, 0, 5, 0), 0, 0));

		// treePanel
		JScrollPane treeView = new JScrollPane(treeXml);
		treeView.setPreferredSize(new Dimension(220, 324));
		treePanel.add(treeView, BorderLayout.CENTER);
		treePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)), "��ѡ�ֶ��б�",
			TitledBorder.LEFT, TitledBorder.TOP, new Font("����", 0, 13)), BorderFactory.createEmptyBorder(5, 0, 5, 0)));

		// buttonPanel
		buttonPanel.add(btSave, new GridBagConstraints(0, 0, 1, 1, 0.25, 0.25, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(btCancle, new GridBagConstraints(1, 0, 1, 1, 0.25, 0.25, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(btOutputXml, new GridBagConstraints(2, 0, 1, 1, 0.25, 0.25, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(btOutputXls, new GridBagConstraints(3, 0, 1, 1, 0.25, 0.25, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		// dataviewPanel
		dataviewPanel.add(topPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 1, 1));
		dataviewPanel.add(midPanel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 1, 1));
		dataviewPanel.add(bottomPanel, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 1, 1));

		// topPanel
		topPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)), "B��",
			TitledBorder.LEFT, TitledBorder.TOP, new Font("����", 0, 13)), BorderFactory.createEmptyBorder(5, 0, 5, 0)));
		midPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)), "��������",
			TitledBorder.LEFT, TitledBorder.TOP, new Font("����", 0, 13)), BorderFactory.createEmptyBorder(5, 0, 5, 0)));
		bottomPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)), "��չ����",
			TitledBorder.LEFT, TitledBorder.TOP, new Font("����", 0, 13)), BorderFactory.createEmptyBorder(5, 0, 5, 0)));

		btSave.setPreferredSize(new Dimension(18, 18));
		btSave.setText("����");

		btCancle.setPreferredSize(new Dimension(18, 18));
		btCancle.setText("ȡ��");

		btOutputXml.setPreferredSize(new Dimension(18, 18));
		btOutputXml.setText("��������ģ��");

		btOutputXls.setPreferredSize(new Dimension(18, 18));
		btOutputXls.setText("����EXCELģ��");

		// btAdd.setText(">>");
		btAdd.setPreferredSize(new Dimension(24, 18));
		btAdd.setText("��");
		// btAdd.setPressedIcon(IeIconFactory.forwardIcon);
		// btDel.setText("<<");
		btDel.setPreferredSize(new Dimension(24, 18));
		// btDel.setPressedIcon(IeIconFactory.backIcon);
		btDel.setText("��");

		topPanel.add(lbBclass, new GridBagConstraints(0, 0, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(tfBclass, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(lbCname, new GridBagConstraints(2, 0, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(tfCname, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(lbIsInsert, new GridBagConstraints(0, 1, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(tfIsInsert, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(lbIsUpdate, new GridBagConstraints(2, 1, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(tfIsUpdate, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(lbLineNum, new GridBagConstraints(0, 2, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(tfLineNum, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(lbLineMax, new GridBagConstraints(2, 2, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		topPanel.add(tfLineMax, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		lbBclass.setHorizontalAlignment(SwingConstants.RIGHT);
		lbBclass.setText("B������:");
		lbCname.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCname.setText("��ʾ����:");
		lbIsInsert.setHorizontalAlignment(SwingConstants.RIGHT);
		lbIsInsert.setText("�Ƿ����:");
		lbIsUpdate.setHorizontalAlignment(SwingConstants.RIGHT);
		lbIsUpdate.setText("�Ƿ����:");
		lbLineNum.setHorizontalAlignment(SwingConstants.RIGHT);
		lbLineNum.setText("�̷߳�ֵ:");
		lbLineMax.setHorizontalAlignment(SwingConstants.RIGHT);
		lbLineMax.setText("�����ֵ:");

		midPanel.add(lbName, new GridBagConstraints(0, 0, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(tfName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(lbColumnTitle, new GridBagConstraints(2, 0, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(tfColumnTitle, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(lbIsDatadict, new GridBagConstraints(0, 1, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(cbIsDatadict, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(lbDatadictClass, new GridBagConstraints(2, 1, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(tfDatadictClass, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(lbDatadictAttr, new GridBagConstraints(0, 2, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(tfDatadictAttr, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(lbIsQuery, new GridBagConstraints(2, 2, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		midPanel.add(cbIsQuery, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

		lbName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbName.setText("�ֶ�����:");
		lbColumnseq.setHorizontalAlignment(SwingConstants.RIGHT);
		lbColumnseq.setText("�ֶ����:");
		lbColumnTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lbColumnTitle.setText("��ʾ����:");
		lbIsDatadict.setHorizontalAlignment(SwingConstants.RIGHT);
		lbIsDatadict.setText("�Ƿ��ֵ�:");
		lbDatadictClass.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDatadictClass.setText("�ֵ�����:");
		lbDatadictAttr.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDatadictAttr.setText("�ֵ�����:");
		lbIsQuery.setHorizontalAlignment(SwingConstants.RIGHT);
		lbIsQuery.setText("�Ƿ��ѯ:");
		lbIsQuery.setHorizontalAlignment(SwingConstants.RIGHT);

		cbIsDatadict.addItem("false");
		cbIsDatadict.addItem("true");

		cbIsQuery.addItem("all");
		cbIsQuery.addItem("search");
		cbIsQuery.addItem("none");

		cbO2OIsQuery.addItem("all");
		cbO2OIsQuery.addItem("search");
		cbO2OIsQuery.addItem("none");

		bottomPanel.add(lbO2OName, new GridBagConstraints(0, 0, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(tfO2OName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(lbO2OTitle, new GridBagConstraints(2, 0, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(tfO2OTitle, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(lbO2OJoinCol, new GridBagConstraints(0, 1, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(tfO2OJoinCol, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(lbO2OClass, new GridBagConstraints(2, 1, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(tfO2OClass, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(lbO2OClassCol, new GridBagConstraints(0, 2, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(tfO2OClassCol, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(lbO2OIsQuery, new GridBagConstraints(2, 2, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(cbO2OIsQuery, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(lbO2OQueryClass, new GridBagConstraints(0, 3, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(tfO2OQueryClass, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		lbO2OName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbO2OName.setText("�ֶ�����:");
		lbO2OSeq.setHorizontalAlignment(SwingConstants.RIGHT);
		lbO2OSeq.setText("�ֶ����:");
		lbO2OTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lbO2OTitle.setText("��ʾ����:");
		lbO2OJoinCol.setHorizontalAlignment(SwingConstants.RIGHT);
		lbO2OJoinCol.setText(" ��������:");
		lbO2OClass.setHorizontalAlignment(SwingConstants.RIGHT);
		lbO2OClass.setText("����B��:");
		lbO2OClassCol.setHorizontalAlignment(SwingConstants.RIGHT);
		lbO2OClassCol.setText("B���ֶ�:");
		lbO2OIsQuery.setHorizontalAlignment(SwingConstants.RIGHT);
		lbO2OIsQuery.setText("�Ƿ��ѯ:");
		lbO2OQueryClass.setHorizontalAlignment(SwingConstants.RIGHT);
		lbO2OQueryClass.setText("��ѯ��:");

		tfBclass.setEditable(false);
		tfCname.setEditable(false);
		tfIsInsert.setEditable(false);
		tfIsUpdate.setEditable(false);
		tfLineNum.setEditable(false);
		tfLineMax.setEditable(false);
		tfName.setEditable(false);
		tfO2OName.setEditable(false);

		loadXmlNodes(root);
		loadAllNodes(rootAll);

		treeXml.addMouseListener(this);
		treeXml.addTreeExpansionListener(this);

		btAdd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onAddMenu();
			}
		});
		btDel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onDelMenu();
			}
		});
		btSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onSave();
			}
		});
		btCancle.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onCancle();
			}
		});
		btOutputXml.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onOutputXml();
			}
		});
		btOutputXls.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onOutputXls();
			}
		});
		cbIsDatadict.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onIsDataDict();
			}
		});
		cbO2OIsQuery.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onIsQuery();
			}
		});
	}

	// ���ڵ��click�¼�
	public void mouseClicked(MouseEvent e)
	{
		if (1 == e.getClickCount() && e.getModifiers() == MouseEvent.BUTTON1_MASK)
			showCategoryInfo();
	}

	/**
	 * ��ȡ���е�node���ݲ�add��treeXml
	 * 
	 * @param node
	 */
	private void loadXmlNodes(DefaultMutableTreeNode node)
	{
		if (domList.size() == 0)
			return;
		DefaultMutableTreeNode nodeModule = null;
		DefaultMutableTreeNode nodeSubPv = null;
		DefaultMutableTreeNode nodeSubPo = null;

		for (int i = 0; i < domList.size(); i++)
		{
			Property proModule = (Property) (((List) domList.get(i)).get(1));
			PropertyValue pvSub = null;
			PropertyObject poSub = null;
			nodeModule = new DefaultMutableTreeNode(proModule);
			node.add(nodeModule);
			for (int j = 0; j < proModule.getPropertyValue().size(); j++)
			{
				pvSub = ((PropertyValue) proModule.getPropertyValue().get(j));
				nodeSubPv = new DefaultMutableTreeNode(pvSub);
				nodeModule.add(nodeSubPv);
			}
			for (int k = 0; k < proModule.getPropertyObject().size(); k++)
			{
				poSub = ((PropertyObject) proModule.getPropertyObject().get(k));
				nodeSubPo = new DefaultMutableTreeNode(poSub);
				nodeModule.add(nodeSubPo);
			}
		}
	}

	/**
	 * ��ȡ���е�node���ݲ�add��treeAll
	 * 
	 * @param node
	 */
	private void loadAllNodes(DefaultMutableTreeNode node)
	{
		if (BclassList.size() == 0)
			return;
		DefaultMutableTreeNode nodeModule = null;
		DefaultMutableTreeNode nodeSubPv = null;
		DefaultMutableTreeNode nodeSubPo = null;

		for (int i = 0; i < BclassList.size(); i++)
		{
			Property proModule = (Property) (BclassList.get(i));
			PropertyValue pvSub = null;
			PropertyObject poSub = null;
			nodeModule = new DefaultMutableTreeNode(proModule);
			node.add(nodeModule);
			for (int j = 0; j < proModule.getPropertyValue().size(); j++)
			{
				pvSub = ((PropertyValue) proModule.getPropertyValue().get(j));
				nodeSubPv = new DefaultMutableTreeNode(pvSub);
				nodeModule.add(nodeSubPv);
			}
			for (int k = 0; k < proModule.getPropertyObject().size(); k++)
			{
				poSub = ((PropertyObject) proModule.getPropertyObject().get(k));
				nodeSubPo = new DefaultMutableTreeNode(poSub);
				nodeModule.add(nodeSubPo);
			}
		}
	}

	/**
	 * ��dataviewPanel��ʾxml���Ľڵ�����
	 * 
	 * @param needCheck
	 * 
	 */
	private void showCategoryInfo()
	{

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeXml.getLastSelectedPathComponent();
		tempNode = node;
		if (node == null)
			return;
		DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();
		Object nodeInfo = node.getUserObject();
		if (nodeParent == null)
			return;
		Object nodeParentInfo = nodeParent.getUserObject();

		if (node.isLeaf())
		{
			Property proNodeInfo = (Property) nodeParentInfo;
			showCategoryInfo(proNodeInfo);
			if (nodeInfo instanceof PropertyValue)
			{
				PropertyValue pvNodeInfo = (PropertyValue) nodeInfo;
				tempPropertyValue = pvNodeInfo;
				showCategoryInfo(pvNodeInfo);
			}
			else if (nodeInfo instanceof PropertyObject)
			{
				PropertyObject poNodeInfo = (PropertyObject) nodeInfo;
				tempPropertyObject = poNodeInfo;
				showCategoryInfo(poNodeInfo);
			}
		}
	}

	/**
	 * ����Bclass��ص��ı����е�ֵ
	 * 
	 * @param category
	 */
	private void showCategoryInfo(Property pro)
	{
		tfBclass.setText(pro.getBclass().trim());
		tfCname.setText(pro.getCname().trim());
		tfIsInsert.setText(pro.getByInsert().trim());
		tfIsUpdate.setText(pro.getByUpdate().trim());
		tfLineNum.setText(pro.getThdLineNum().trim());
		tfLineMax.setText(pro.getThdLineMax().trim());
	}

	/**
	 * ����PV��ص��ı����е�ֵ
	 * 
	 * @param category
	 */
	private void showCategoryInfo(PropertyValue pv)
	{
		resetPVTextField();
		resetPOTextField();
		if (pv.getName() == null)
			tfName.setText("");
		else
			tfName.setText(pv.getName().trim());
		if (pv.getColumnTitle() == null)
			tfColumnTitle.setText("");
		else
			tfColumnTitle.setText(pv.getColumnTitle().trim());
		if (pv.getDatadictClass() == null)
			tfDatadictClass.setText("");
		else
			tfDatadictClass.setText(pv.getDatadictClass().trim());
		if (pv.getDatadictAttr() == null)
			tfDatadictAttr.setText("");
		else
			tfDatadictAttr.setText(pv.getDatadictAttr().trim());
		cbIsDatadict.setSelectedItem(String.valueOf(pv.isDatadict()));
		cbIsQuery.setSelectedItem(String.valueOf(pv.getIsQuery()));
	}

	/**
	 * ����PO��ص��ı����е�ֵ
	 * 
	 * @param category
	 */
	private void showCategoryInfo(PropertyObject po)
	{

		resetPVTextField();
		resetPOTextField();
		if (po.getName() == null)
			tfO2OName.setText("");
		else
			tfO2OName.setText(po.getName().trim());
		if (po.getColumnTitle() == null)
			tfO2OTitle.setText("");
		else
			tfO2OTitle.setText(po.getColumnTitle().trim());
		if (po.getJoinColumn() == null)
			tfO2OJoinCol.setText("");
		else
			tfO2OJoinCol.setText(po.getJoinColumn().trim());
		if (po.getClassName() == null)
			tfO2OClass.setText("");
		else
			tfO2OClass.setText(po.getClassName().trim());
		if (po.getClassColumn() == null)
			tfO2OClassCol.setText("");
		else
			tfO2OClassCol.setText(po.getClassColumn().trim());
		if (po.getQueryClass() == null)
			tfO2OQueryClass.setText("");
		else
			tfO2OQueryClass.setText(po.getQueryClass().trim());
		cbO2OIsQuery.setSelectedItem(String.valueOf(po.getIsQuery()));
	}

	/**
	 * ��PV��ص��ı����е�ֵ���
	 */
	private void resetPVTextField()
	{
		tfName.setText("");
		tfColumnseq.setText("");
		tfColumnTitle.setText("");
		tfDatadictClass.setText("");
		tfDatadictAttr.setText("");
	}

	/**
	 * ��PO��ص��ı����е�ֵ���
	 */
	private void resetPOTextField()
	{
		tfO2OName.setText("");
		tfO2OSeq.setText("");
		tfO2OTitle.setText("");
		tfO2OJoinCol.setText("");
		tfO2OClass.setText("");
		tfO2OClassCol.setText("");
		tfO2OQueryClass.setText("");
	}

	/**
	 * ����֦����չ���ͻ���ʱ�ĳ�ʼ������֤
	 */
	private void treeOperationInit()
	{
		// resetTextField();
		// haveSaved = true;
	}

	/**
	 * ��ӦӦ�ð�ť�������¼�
	 * 
	 * @return
	 */
	private void save()
	{
		int question = JGxluMessageBox.show(CommonClientEnvironment.getMainFrame(), "��ȷ���������б���ʾ�������ֵ����ʾ˳����", "ȷ��",
			JGxluMessageBox.QUESTION_MESSAGE, JGxluMessageBox.YES_NO_OPTION);
		if (question != JGxluMessageBox.YES_OPTION)
		{
			return;
		}
		else
		{

		}
		// return saveDictionary();
	}

	public boolean perform(int nResourceID)
	{
		switch (nResourceID) {
		case JGxluSimpleDialog.SB_APPLY:
			save();
			return true;
			// case JGxluSimpleDialog.SB_HELP:
			// return false;
		case JGxluSimpleDialog.SB_EXIT:
			return true;
		case JGxluSimpleDialog.SB_CANCEL:
			return true;
		}
		return true;
	}

	// �¼�
	public void treeExpanded(TreeExpansionEvent event)
	{
		treeOperationInit();
	}

	public void treeCollapsed(TreeExpansionEvent event)
	{
		treeOperationInit();
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void actionPerformed(ActionEvent e)
	{

	}

	public void initDialog()
	{
		// TODO Auto-generated method stub

	}

	public void showSimpleDialog()
	{
		JGxluSimpleDialog simpledialog = new JGxluSimpleDialog(CommonClientEnvironment.getMainFrame(), this, this,
				JGxluSimpleDialog.SB_APPLY | JGxluSimpleDialog.SB_EXIT, // Modify-Key:
				"�����ֶ�����");
		simpledialog.centerDialog();
		simpledialog.setVisible(true);
	}

	/**
	 * ��ʾ����
	 */
	public void showWindow()
	{
		String functionName = "UIXmlCfgMgtTree";

		JGxluChildWindow cw = JGxluChildWindow.getOriginalChildWindowByFunction(functionName);

		if (cw == null)
		{
			JGxluWindow win = new JGxluWindow(this, this, JGxluWindow.SB_EXIT);
			String caption = "ģ�����ù���";
			cw = JGxluChildWindow.createChildWindow(caption, win, functionName, new Dimension(780, 620));

			cw.setFrameIcon(Images.getImage("images/user/user.gif"));

			// ���ð���
			// JButton btnHelp = win.getButtonInstance(JGxluWindow.SB_HELP);
			// Tools.setHelp(this, btnHelp, this.helpId);
		}
		else
		{
			JGxluChildWindow.setActiveChildWindow(functionName);
		}

		cw.show();
	}

	/**
	 * ��������������Ҳ����ڵ�
	 */
	public void onAddMenu()
	{
		TreePath[] pathSelects = treeAll.getSelectionPaths();
		if (pathSelects == null)
		{
			return;
		}
		for (int k = 0; k < pathSelects.length; k++)
		{
			// ����ѡ���·��
			Object[] nodes = pathSelects[k].getPath();
			for (int i = 1; i < nodes.length; i++)
			{
				addNode((DefaultMutableTreeNode) nodes[i - 1], (DefaultMutableTreeNode) nodes[i], false);
			}
			// ���·�������һ���ڵ㻹�ж��ӣ�������ȫ������
			DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) pathSelects[k].getLastPathComponent();
			if (lastNode.getChildCount() != 0)
			{
				for (int i = 0; i < lastNode.getChildCount(); i++)
				{
					addNode(lastNode, (DefaultMutableTreeNode) lastNode.getChildAt(i), true);
				}
			}
		}
		treeXml.updateUI();
	}

	/**
	 * �ڸ��׽ڵ������ӽڵ㣬ע�⣺��ζ�Ϊ�������ϵĽڵ�
	 * 
	 * @param _parentNode DefaultMutableTreeNode
	 * @param _node DefaultMutableTreeNode
	 * @param isAddChild boolean
	 */
	private void addNode(DefaultMutableTreeNode _parentNode, DefaultMutableTreeNode _node, boolean isAddChild)
	{
		if (searchNodeInUserTree(_node) != null)
		{
			return;
		}
		else
		{
			DefaultMutableTreeNode parentNode = searchNodeInUserTree(_parentNode);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(_node.getUserObject());
			parentNode.add(node);
			if (isAddChild)
			{
				if (_node.getChildCount() != 0)
				{
					for (int i = 0; i < _node.getChildCount(); i++)
					{
						addNode(_node, (DefaultMutableTreeNode) _node.getChildAt(i), true);
					}
				}
			}
		}
	}

	/**
	 * ��"��ѡ�ֶ�"����ɾ���ڵ�
	 */
	public void onDelMenu()
	{
		TreePath[] pathSelects = treeXml.getSelectionPaths();
		if (pathSelects == null)
		{
			return;
		}
		for (int k = 0; k < pathSelects.length; k++)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) pathSelects[k].getLastPathComponent();
			if (node.getParent() != null)
			{
				removeNode((DefaultMutableTreeNode) node.getParent(), node);
			}
		}
		// ע�͵�ɾ���ڵ�չ�������Ĺ���
		// expandAll(treeXml, true);
		treeXml.updateUI();
	}

	/**
	 * ɾ���ڵ㣬���û���ֵܣ�˵��Ŀ¼�ѿգ��ݹ�ɾ������
	 * 
	 * @param parent DefaultMutableTreeNode
	 * @param node DefaultMutableTreeNode
	 */
	private void removeNode(DefaultMutableTreeNode parent, DefaultMutableTreeNode node)
	{
		parent.remove(node);
		if (parent.getChildCount() == 0 && parent.getParent() != null)
		{
			removeNode((DefaultMutableTreeNode) parent.getParent(), parent);
		}
	}

	/**
	 * ���û�Ȩ������Ѱ�ҽڵ�
	 * 
	 * @param node DefaultMutableTreeNode
	 * @return DefaultMutableTreeNode
	 */
	private DefaultMutableTreeNode searchNodeInUserTree(DefaultMutableTreeNode node)
	{
		// ���Ǹ��ڵ�
		if (node.getUserObject() instanceof String && node.toString().equals("�����ֶ�"))
		{
			return (DefaultMutableTreeNode) treeXml.getModel().getRoot();
		}
		// ���Ǹ��ڵ㣬�������ڵ㿪ʼ�ݹ�Ƚ�
		return searchNodeInUserTree((DefaultMutableTreeNode) treeXml.getModel().getRoot(), node);
	}

	/**
	 * ���û�Ȩ�����ϵݹ�Ѱ�ҽڵ�
	 * 
	 * @param parentNode DefaultMutableTreeNode
	 * @param node DefaultMutableTreeNode
	 * @return DefaultMutableTreeNode
	 */
	private DefaultMutableTreeNode searchNodeInUserTree(DefaultMutableTreeNode currentNode, DefaultMutableTreeNode node)
	{
		if (currentNode.getChildCount() == 0)
		{
			return null;
		}

		// �ӽڵ����ҵ��������ӽڵ�
		for (int i = 0; i < currentNode.getChildCount(); i++)
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) currentNode.getChildAt(i);
			// if ((child.toString()).equals(node.getUserObject().toString()))
			// return child;
			// Modified by ZhangWei 2010-03-29 for add new node for MR RSW10-772
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
			if ((child.toString()).equals(node.getUserObject().toString())
				&& (currentNode.toString()).equals(parent.toString()))
				return child;
		}

		// �ӽڵ���δ�ҵ�����ݹ飬���ӽڵ���ӽڵ��еݹ�Ѱ��
		for (int i = 0; i < currentNode.getChildCount(); i++)
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) currentNode.getChildAt(i);
			DefaultMutableTreeNode result = searchNodeInUserTree(child, node);
			if (result != null)
			{
				return result;
			}
		}
		return null;
	}

	public void expandAll(JTree tree, boolean expand)
	{
		if (tree == null)
		{
			return;
		}
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root), expand);
	}

	private void expandAll(JTree tree, TreePath parent, boolean expand)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0)
		{
			for (Enumeration e = node.children(); e.hasMoreElements();)
			{
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand)
		{
			tree.expandPath(parent);
		}
		else
		{
			if (!node.isRoot())
			{
				tree.collapsePath(parent);
			}
		}
	}

	/**
	 * ����ڵ�༭
	 */
	public void onSave()
	{
		TreePath[] pathSelects = treeXml.getSelectionPaths();
		if (pathSelects == null)
		{
			return;
		}
		if (pathSelects.length > 1)
		{
			JGxluMessageBox.show(this, "��ȷ��ֻѡ����һ���ڵ㣡", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
			return;
		}
		Object[] nodes = pathSelects[0].getPath();
		if (nodes.length < 3)
		{
			JGxluMessageBox.show(this, "��ȷ��ѡ�����Ҷ�ڵ㣡", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
			return;
		}
		DefaultMutableTreeNode BclassNode = (DefaultMutableTreeNode) nodes[nodes.length - 2];
		DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) nodes[nodes.length - 1];
		if (leaf.toString().equals(tfName.getText().trim()) == false
			&& leaf.toString().equals(tfO2OName.getText().trim()) == false)
		{
			JGxluMessageBox.show(this, "��ȷ�����ѡ��Ľڵ���ұߵĽڵ���Ϣ����һ�£�", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
			return;
		}
		Object leafInfo = leaf.getUserObject();
		if (leafInfo instanceof PropertyValue)
		{
			if (tfColumnTitle.getText() == null || tfColumnTitle.getText().equals(""))
			{
				JGxluMessageBox.show(this, "�������д����ʾ���ơ���", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
				return;
			}
			PropertyValue pv = (PropertyValue) leafInfo;
			pv.setName(tfName.getText());
			pv.setColumnTitle(tfColumnTitle.getText());
			pv.setDatadictClass(tfDatadictClass.getText());
			pv.setDatadictAttr(tfDatadictAttr.getText());
			pv.setDatadict(Boolean.valueOf(cbIsDatadict.getValueString()));
			pv.setIsQuery(cbIsQuery.getValueString());
		}
		else if (leafInfo instanceof PropertyObject)
		{
			if (tfO2OTitle.getText() == null || tfO2OTitle.getText().equals(""))
			{
				JGxluMessageBox.show(this, "�������д����ʾ���ơ���", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
				return;
			}
			PropertyObject po = (PropertyObject) leafInfo;
			po.setName(tfO2OName.getText());
			po.setColumnTitle(tfO2OTitle.getText());
			// po.setColumnSeq(tfO2OSeq.getText());
			po.setJoinColumn(tfO2OJoinCol.getText());
			po.setClassName(tfO2OClass.getText());
			po.setClassColumn(tfO2OClassCol.getText());
			po.setIsQuery(cbO2OIsQuery.getValueString());
			po.setQueryClass(tfO2OQueryClass.getValueString());
		}

		JGxluMessageBox.show(this, "�ڵ������޸ĳɹ���", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
		treeXml.updateUI();
	}

	public void setSeqs(DefaultMutableTreeNode parent)
	{
		for (int i = 0; i < parent.getChildCount(); i++)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getChildAt(i);
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof PropertyValue)
			{
				PropertyValue pv = (PropertyValue) nodeInfo;
				pv.setColumnSeq(String.valueOf(i + 1));
			}
			else if (nodeInfo instanceof PropertyObject)
			{
				PropertyObject po = (PropertyObject) nodeInfo;
				po.setColumnSeq(String.valueOf(i + 1));
			}
		}
	}

	/**
	 * ȡ���ڵ�༭
	 */
	public void onCancle()
	{
		showCategoryInfo();
	}

	/**
	 * ����ģ�������ļ�
	 */
	public void onOutputXml()
	{
		DefaultMutableTreeNode BclassNode = (DefaultMutableTreeNode) treeXml.getLastSelectedPathComponent();
		if (BclassNode == null)
		{
			JGxluMessageBox.show(this, "����ѡ��Ŀ�꣡", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
			return;
		}
		DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) BclassNode.getParent();
		if (nodeParent == null)
		{
			JGxluMessageBox.show(this, "����ѡ��Ŀ�꣡", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
			return;
		}
		if (nodeParent.equals(root) == false)
		{
			DefaultMutableTreeNode nodeParentParent = (DefaultMutableTreeNode) nodeParent.getParent();
			if (nodeParentParent == null)
			{
				JGxluMessageBox.show(this, "����ѡ��Ŀ�꣡", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
				return;
			}
			if (nodeParentParent.equals(root) == true)
				BclassNode = nodeParent;
			else
			{
				JGxluMessageBox.show(this, "����ȷѡ�񡰵����ֶΡ������һ���Ӳ˵���", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
				return;
			}
		}

		Property p = null;
		PropertyValue pv = null;
		PropertyObject po = null;
		List pList = new ArrayList(), pvList = new ArrayList(), poList = new ArrayList();

		p = (Property) BclassNode.getUserObject();
		String strBclass = ((Property) BclassNode.getUserObject()).getBclass();
		strBclass = strBclass.substring(strBclass.lastIndexOf(".") + 2, strBclass.length());
		for (int j = 0; j < BclassNode.getChildCount(); j++)
		{
			DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) BclassNode.getChildAt(j);
			Object leafInfo = leaf.getUserObject();
			if (leafInfo instanceof PropertyValue)
			{
				pv = (PropertyValue) leafInfo;
				if (pv.getColumnTitle() == null || pv.getColumnTitle().equals(""))
				{
					JGxluMessageBox.show(this, "�������д��" + pv.getName() + "����ʾ���ƣ�", "��ʾ",
						JGxluMessageBox.INFORMATION_MESSAGE);
					return;
				}
				pvList.add(pv);
			}
			else if (leafInfo instanceof PropertyObject)
			{
				po = (PropertyObject) leafInfo;
				if (po.getColumnTitle() == null || po.getColumnTitle().equals(""))
				{
					JGxluMessageBox.show(this, "�������д��" + po.getName() + "����ʾ���ƣ�", "��ʾ",
						JGxluMessageBox.INFORMATION_MESSAGE);
					return;
				}
				poList.add(po);
			}
		}
		p.setPropertyValue(pvList);
		p.setPropertyObject(poList);
		List params = new ArrayList();
		String strPath = new String();
		ContainerBootStrap bootStrap = new ContainerBootStrap();
		bootStrap.startup();
		DomParse domTemplateParse = new DomTemplateParse();
		List templateList = domTemplateParse.getTemplateFileList();
		if (templateList.size() < 0)
			return;
		for (int i = 0; i < templateList.size(); i++)
		{
			String strTemplatePath = templateList.get(i).toString();
			String templateName = strTemplatePath.substring(strTemplatePath.lastIndexOf("/") + 1, strTemplatePath
					.length() - 4);
			if (templateName.equalsIgnoreCase(strBclass))
			{
				strPath = strTemplatePath;
				break;
			}
		}
		params.add(strPath);
		DomHelper domhelper = new DomHelper(domTemplateParse);
		List result = domhelper.updateDomParse(params, p);
		if ((Boolean) result.get(0) == false)
		{
			JGxluMessageBox.show(this, "ģ���ļ�����ʧ�ܣ�ʧ��ԭ����" + result.get(0) + "��", "��ʾ", JGxluMessageBox.ERROR_MESSAGE);
			return;
		}
		JGxluMessageBox.show(this, "ģ�������ļ����ɳɹ���", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
	}

	// /**
	// * ����ģ�������ļ�
	// */
	// public void onOutputXml()
	// {
	// Property p = null;
	// PropertyValue pv = null;
	// PropertyObject po = null;
	// List pList = new ArrayList();
	// ;
	// List pvList = null;
	// List poList = null;
	// DomParse domTemplateParse = new DomTemplateParse();
	// DomHelper domhelper = new DomHelper(domTemplateParse);
	// for (int i = 0; i < root.getChildCount(); i++)
	// {
	// p = new Property();
	// pvList = new ArrayList();
	// poList = new ArrayList();
	// DefaultMutableTreeNode BclassNode = (DefaultMutableTreeNode) root.getChildAt(i);
	// Object BclassInfo = BclassNode.getUserObject();
	// p = (Property) BclassInfo;
	// for (int j = 0; j < BclassNode.getChildCount(); j++)
	// {
	// DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) BclassNode.getChildAt(j);
	// Object leafInfo = leaf.getUserObject();
	// if (leafInfo instanceof PropertyValue)
	// {
	// pv = (PropertyValue) leafInfo;
	// pvList.add(pv);
	// }
	// else if (leafInfo instanceof PropertyObject)
	// {
	// po = (PropertyObject) leafInfo;
	// poList.add(po);
	// }
	// }
	// p.setPropertyValue(pvList);
	// p.setPropertyObject(poList);
	// List params = new ArrayList();
	// params.add(((List) domList.get(i)).get(0));
	// List result = domhelper.updateDomParse(params, p);
	// if ((Boolean) result.get(0) == false)
	// {
	// JGxluMessageBox.show(this, "ģ���ļ�����ʧ�ܣ�ʧ��ԭ����" + result.get(0) + "��", "��ʾ",
	// JGxluMessageBox.ERROR_MESSAGE);
	// return;
	// }
	// }
	// // domhelper.updateDomParse(params, object)
	//
	// JGxluMessageBox.show(this, "ģ�������ļ����ɳɹ���", "��ʾ",
	// JGxluMessageBox.INFORMATION_MESSAGE);
	// }

	/**
	 * ����ģ���ļ�
	 */
	public void onOutputXls()
	{
		try
		{
			DefaultMutableTreeNode BclassNode = (DefaultMutableTreeNode) treeXml.getLastSelectedPathComponent();
			if (BclassNode == null)
			{
				JGxluMessageBox.show(this, "����ѡ��Ŀ�꣡", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
				return;
			}
			DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) BclassNode.getParent();
			if (nodeParent == null)
			{
				JGxluMessageBox.show(this, "����ѡ��Ŀ�꣡", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
				return;
			}
			if (nodeParent.equals(root) == false)
			{
				DefaultMutableTreeNode nodeParentParent = (DefaultMutableTreeNode) nodeParent.getParent();
				if (nodeParentParent == null)
				{
					JGxluMessageBox.show(this, "����ѡ��Ŀ�꣡", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
					return;
				}
				if (nodeParentParent.equals(root) == true)
					BclassNode = nodeParent;
				else
				{
					JGxluMessageBox.show(this, "����ȷѡ�񡰵����ֶΡ������һ���Ӳ˵���", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
					return;
				}
			}
			String strPath = new String();
			ContainerBootStrap bootStrap = new ContainerBootStrap();
			bootStrap.startup();
			DomParse domTemplateParse = new DomTemplateParse();
			List templateList = domTemplateParse.getTemplateFileList();
			Property p = (Property) BclassNode.getUserObject();
			String strBclass = ((Property) BclassNode.getUserObject()).getBclass();
			strBclass = strBclass.substring(strBclass.lastIndexOf(".") + 2, strBclass.length());
			if (templateList.size() < 0)
				return;
			for (int i = 0; i < templateList.size(); i++)
			{
				String strTemplatePath = templateList.get(i).toString();
				String templateName = strTemplatePath.substring(strTemplatePath.lastIndexOf("/") + 1, strTemplatePath
						.length() - 4);
				if (templateName.equalsIgnoreCase(strBclass))
				{
					strPath = strTemplatePath;
					break;
				}
			}
			Class BClass = (Class) ((ContainerImpl) ContainerFactory.getContainer()).lookupBObject(strPath);
			Context context = ExectionUtil.getContext();
			ArrayLoader arrayLoader = (ArrayLoader) context.lookupSessionBean(ClassNoteNames.ARRAYLOADER_BEAN);
			ResultController resultController = arrayLoader.getTitleList(BClass);
			ExportUtil.printTemplet(resultController);

		}
		catch (ContainerException e)
		{
			e.printStackTrace();
		}
	}

	// /**
	// * ����ģ���ļ�
	// */
	// public void onOutputXls()
	// {
	// try
	// {
	// ContainerBootStrap bootStrap = new ContainerBootStrap();
	// bootStrap.startup();
	// DomParse domTemplateParse = new DomTemplateParse();
	// List templateList = domTemplateParse.getTemplateFileList();
	// if (templateList.size() < 0)
	// return;
	// for (int i = 0; i < templateList.size(); i++)
	// {
	// String strTemplatePath = templateList.get(i).toString();
	// Class BClass = (Class) ((ContainerImpl)
	// ContainerFactory.getContainer()).lookupBObject(strTemplatePath);
	// Context context = ExectionUtil.getContext();
	// ArrayLoader arrayLoader = (ArrayLoader)
	// context.lookupSessionBean(ClassNoteNames.ARRAYLOADER_BEAN);
	// ResultController resultController = arrayLoader.getTitleList(BClass);
	// ExportUtil.printTemplet(resultController);
	// // if ((Boolean) result.get(0) == false)
	// // {
	// // JGxluMessageBox.show(this, "ģ���ļ�����ʧ�ܣ�ʧ��ԭ����" + result.get(0) + "��");
	// // return;
	// // }
	// }
	//
	// }
	// catch (ContainerException e)
	// {
	// e.printStackTrace();
	// }
	// // domhelper.updateDomParse(params, object)
	//
	// JGxluMessageBox.show(this, "ģ���ļ����ɳɹ���", "��ʾ", JGxluMessageBox.INFORMATION_MESSAGE);
	// }

	public void onIsDataDict()
	{
		if (cbIsDatadict.getSelectedItem().toString().equalsIgnoreCase("true"))
		{
			tfDatadictClass.setEditable(true);
			tfDatadictAttr.setEditable(true);
		}
		else
		{
			tfDatadictClass.setEditable(false);
			tfDatadictAttr.setEditable(false);
		}
	}

	public void onIsQuery()
	{
		if (cbO2OIsQuery.getSelectedItem().toString().equalsIgnoreCase("all"))
			tfO2OQueryClass.setEditable(true);
		else if (cbO2OIsQuery.getSelectedItem().toString().equalsIgnoreCase("search"))
			tfO2OQueryClass.setEditable(true);
		else if (cbO2OIsQuery.getSelectedItem().toString().equalsIgnoreCase("none"))
			tfO2OQueryClass.setEditable(false);
	}

//	public void popUpDataDict()
//	{
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeXml.getLastSelectedPathComponent();
//		if (node == null)
//			return;
//		Object nodeInfo = node.getUserObject();
//		String classID = "", attributeID = "";
//		if (node.isLeaf())
//		{
//			if (nodeInfo instanceof PropertyValue)
//			{
//				// PropertyValue pvNodeInfo = (PropertyValue) nodeInfo;
//				classID = ((PropertyValue) nodeInfo).getDatadictClass();
//				attributeID = ((PropertyValue) nodeInfo).getDatadictAttr();
//			}
//			else
//				return;
//		}
//		// String appModule = CommonClientEnvironment.getSubModule();
//		// IESysDictionaryMgt panel = new IESysDictionaryMgt(appModule);
//		// // panel.setDefaultSysDictionary(classID,attributeID);
//		// panel.showSimpleDialog();
//		String appModule = CommonClientEnvironment.getSubModule();
//		UISysDictionaryMgt panel = new UISysDictionaryMgt(appModule);
//		panel.setDefaultSysDictionary(classID, attributeID);
//		panel.showSimpleDialog();
//	}

	/**
	 * ����Ⱦ��
	 */
	private class SysDataTreeCellRenderer extends DefaultTreeCellRenderer
	{
		// �ڵ���ɫ
		private Color strNodeColor = Color.black;

		private Color savedColor = Color.blue;

		private Color unsavedColor = Color.green;

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus)
		{
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

			if (node != null && node.isLeaf())
			{
				Object nodeInfo = node.getUserObject();
				if (nodeInfo instanceof PropertyValue)
				{
					PropertyValue pv = (PropertyValue) nodeInfo;
					if (pv.getColumnTitle() == null)
						setForeground(savedColor);
					else
						setForeground(unsavedColor);
				}
				else if (nodeInfo instanceof PropertyObject)
				{
					PropertyObject po = (PropertyObject) nodeInfo;
					if (po.getColumnTitle() == null)
						setForeground(savedColor);
					else
						setForeground(unsavedColor);
				}
			}
			return this;
		}
	}

	class myJGxluFilterTextDocument extends PlainDocument
	{
		private boolean haveDot = false;

		private int length = 0;

		private double max = Double.MIN_VALUE, min = Double.MAX_VALUE;

		private boolean isLimit = false;

		/**
		 * ����һ��ȱʡ��JGxluFilterTextDocument��
		 * 
		 * @since 0.1
		 */
		public myJGxluFilterTextDocument()
		{

		}

		/**
		 * ����һ���������ִ�С��JGxluFilterTextDocument�� ֻ����max��С��min�������max��min�����ò���Ч��
		 * 
		 * @param max ���ֵ
		 * @param min ��Сֵ
		 * @since 0.3
		 */
		public myJGxluFilterTextDocument(double max, double min)
		{
			if (max >= min)
			{
				this.max = max;
				this.min = min;
			}
			this.isLimit = true;
		}

		/**
		 * ����һ���������ִ�С��NumberOnlyDocument�� ֻ����max��С��min�������max��min�����ò���Ч��
		 * 
		 * @param max ���ֵ
		 * @param min ��Сֵ
		 * @param isLimit �Ƿ��������ִ�С
		 * @since 0.3
		 */
		public myJGxluFilterTextDocument(double max, double min, boolean isLimit)
		{
			if (max >= min)
			{
				this.max = max;
				this.min = min;
			}
			this.isLimit = isLimit;
		}

		/**
		 * �����Ƿ��������ֵĴ�С�� ���������trueʱֻ����ԭ�������ֵ������Сֵ��������ò���Ч������˷�������û���κ�Ч����
		 * �������ֵ��false��ȡ���������ִ�С������
		 * 
		 * @param isLimit �Ƿ������������ֵĴ�С
		 * @since 0.3
		 */
		public void setLimit(boolean isLimit)
		{
			if (isLimit == true && max >= min)
			{
				this.isLimit = isLimit;
			}
			else if (isLimit == false)
			{
				this.isLimit = isLimit;
			}
		}

		/**
		 * ���ÿ��������ֵ�ķ�Χ�� ���maxС��min�������������ֵ����Сֵ��������Ч����Ϊû�д�С���Ƶ��������
		 * 
		 * @param max ������������ֵ
		 * @param min �����������Сֵ
		 * @since 0.3
		 */
		public void setRange(double max, double min)
		{
			if (max >= min)
			{
				this.max = max;
				this.min = min;
				isLimit = true;
			}
		}

		/**
		 * �Ƿ��������ֵĴ�С��
		 * 
		 * @return ���������Сʱ����true�����򷵻�false��
		 * @since 0.3
		 */
		public boolean isLimit()
		{
			return isLimit;
		}

		/**
		 * �������Ƶ����ֵ��
		 * 
		 * @return ���Ƶ����ֵ������ǲ������򷵻�Double.MIN_VALUE��
		 * @since 0.3
		 */
		public double getLimitedMax()
		{
			if (!isLimit)
			{
				return Double.MIN_VALUE;
			}
			else
			{
				return max;
			}
		}

		/**
		 * �������Ƶ���Сֵ��
		 * 
		 * @return ���Ƶ���Сֵ������ǲ������򷵻�Double.MAX_VALUE��
		 * @since 0.3
		 */
		public double getLimitedMin()
		{
			if (!isLimit)
			{
				return Double.MAX_VALUE;
			}
			else
			{
				return min;
			}
		}

		/**
		 * ����ĳЩ���ݵ��ĵ��С� ֻ�е�����������ֻ��ߺ�������صġ�.������-���ȷ��Ų��ҷ��Ϲ��ɺϷ����ֵĹ���ʱ�ű����롣 �˷������̰߳�ȫ�ġ�
		 * 
		 * @param offs ����λ�õ�ƫ����
		 * @param str ��������
		 * @param a ���Լ���
		 * @throws BadLocationException �����Ĳ���λ�ò����ĵ��е���Чλ��
		 * @since 0.1
		 */
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
		{

			if (str == null)
			{
				return;
			}
			char[] number = str.toUpperCase().toCharArray();
			for (int i = 0; i < number.length; i++)
			{
				if (offs == 0)
				{
					if (!((number[i] >= '0' && number[i] <= '9') || number[i] == '-' || (number[i] >= 'A' && number[i] <= 'F')))
					{
						if (offs == length - 1)
						{
							remove(offs + i, 1);
						}
						else
						{
							return;
						}

					}
					else
					{
						length++;
					}
				}
				else
				{
					if (!haveDot)
					{
						if (!(number[i] >= '0' && number[i] <= '9' || number[i] == '-' || (number[i] >= 'A' && number[i] <= 'F')))
						{
							if (offs == length - 1)
							{
								remove(offs + i, 1);
							}
							else
							{
								return;
							}
						}
						else
						{
							if (number[i] == '.')
							{
								haveDot = true;
							}
							length++;
						}
					}
					else
					{
						if (!(number[i] >= '0' && number[i] <= '9' || number[i] == '-' || (number[i] >= 'A' && number[i] <= 'F')))
						{
							if (offs == length - 1)
							{
								remove(offs + i, 1);
							}
							else
							{
								Toolkit.getDefaultToolkit().beep();
								return;
							}
						}
						else
						{
							length++;
						}
					}
				}
			}
			if (isLimit == true)
			{
				String text = getText(0, offs) + str + getText(offs, getLength());
				double result = Double.parseDouble(text);
				if (result > max || result < min)
				{
					return;
				}
			}
			super.insertString(offs, new String(number), a);
		}
	}
}

/**********************************************************************
 *$RCSfile: UIXmlCfgMgtTree.java,v $ $Revision: 1.3 $ $Date: 2010/04/20 02:08:06 $
 * 
 *$Log: UIXmlCfgMgtTree.java,v $
 *Revision 1.3  2010/04/20 02:08:06  wudawei
 *20100420
 * Revision 1.1 2010/01/19 01:16:46 zhangw empty log
 * message ***
 * 
 * 
 *********************************************************************/
