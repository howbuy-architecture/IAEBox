package gxlu.ietools.basic.elements.interceptor;

import java.util.List;

public interface UniqueInterface{
	
	/**
	 * Ԫ��Ψһ����֤
	 * @param params --Ӧ�ò���
	 * 		param0 --List*--  B�༯��
	 * 		param1 --String*-- Ŀ�����
	 * 		param2 --String*-- �б�ͷ
	 * @return
	 * 		ArrayList*-- ���˺��B�༯��
	 */
	public abstract Object elementUniqueVerification(List iParam);
}
