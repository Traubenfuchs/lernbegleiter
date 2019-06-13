package at.technikumwien.lernbegleiter.entities.quiz;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class QuizAnswerEntity {
    private String content;

    class MagicIter<X> implements Iterator<X>, AutoCloseable {
        private final ArrayBlockingQueue<X> queue = new ArrayBlockingQueue(10000);
        private final Thread producerThread;

        private volatile boolean done = false;

        private volatile Exception producerException;

        public MagicIter(ResultSet rs) {
            this.producerThread = new Thread(() -> {
                Thread currentThread = Thread.currentThread();
                try {
                    while(!currentThread.isInterrupted()) {
                        if(rs.next()) {
                            queue.put(null);
                        } else {
                            done = true;
                        }
                    }
                } catch(Exception exception) {
                    producerException = exception;
                    done = true;
                    return;
                }

            });
            this.producerThread.setDaemon(true);
            this.producerThread.start();
        }

        @Override
        public boolean hasNext() {
            checkException();
            return !done || !queue.isEmpty();
        }

        @Override
        public X next() {
            checkException();

            try {
                return queue.take();
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                checkException();
            }
        }

        private void checkException() {
            if(producerException != null) {
                throw new RuntimeException(producerException);
            }
        }

        @Override
        public void close() {
            done = true;
            this.producerThread.interrupt();
        }
    }
}
