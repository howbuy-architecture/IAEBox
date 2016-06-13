/**************************************************************************
 * $$RCSfile: TaskProcessors.java,v $$  $$Revision: 1.3 $$  $$Date: 2010/04/20 02:08:03 $$
 *
 * $$Log: TaskProcessors.java,v $
 * $Revision 1.3  2010/04/20 02:08:03  wudawei
 * $20100420
 * $$
 **************************************************************************/
package gxlu.ietools.basic.threads;

import gxlu.ietools.basic.exception.ThreadException;
import gxlu.ietools.property.mapping.MetaAttribute;

import org.apache.log4j.Logger;

public final class TaskProcessors {
	protected Logger logger = Logger.getLogger(MetaAttribute.class);
	
    private static final int MAX_REQUEST = 100;
    private final CalledRequest[] requestQueue;
    private int tail;  // �´�ҪputRequest��λ��
    private int head;  // �´�ҪtakeRequest��λ��
    private int count; // Request�Ĵ���

    private final TaskThread[] threadPool;

    /**
     * ��ʼ��������к��̳߳�
     * @param threads
     */
    public TaskProcessors(int threads) {
        this.requestQueue = new CalledRequest[MAX_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;

        threadPool = new TaskThread[threads];
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new TaskThread("Worker-" + i, this);
        }
    }
    
    /**
     * ���������߳�
     */
    public void startWorkers() {
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].start();
        }
    }

    /**
     * ���������߳�
     */
    public void stopAllWorkers() {
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].stopThread();
        }
    }
    
    /**
     * ��������������̶߳���
     * @param request
     * @throws ThreadException
     */
    public synchronized void putRequest(CalledRequest request) throws ThreadException {
		try {
	        while (count >= requestQueue.length) {
	            wait();
	        }
	        requestQueue[tail] = request;
	        tail = (tail + 1) % requestQueue.length;
	        count = count + 1;
	        notifyAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace().toString());
			logger.error(e.toString());
			throw new ThreadException(e.getMessage(),e.toString());
		}
    }
    
    /**
     * ���̶߳����л�ȡ
     * @return
     * @throws ThreadException
     */
    public synchronized CalledRequest takeRequest() throws ThreadException {
		try {
	        while (count <= 0) {
	            wait();
	        }
	        CalledRequest request = requestQueue[head];
	        head = (head + 1) % requestQueue.length;
	        count = count - 1;
	        notifyAll();
	        return request;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace().toString());
			logger.error(e.toString());
			throw new ThreadException(e.getMessage(),e.toString());
		}
    }
    
}
