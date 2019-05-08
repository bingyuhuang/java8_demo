package com.eight.demo.chap8;


import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {
    public static void main(String[] args){

    }

    /**
     * 每次调用输出日志都会去查询日志器对象的状态
     */
    private void oldLogLevelJudge() {
        Logger logger = null;
        try {
            logger = Logger.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        logger.setLevel(Level.FINER);
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("Problem: " + generateDiagnostic());
        }
    }

    /**
     * 优点：代码中无条件判断，也不会暴露日志器的状态
     * 缺点：消息是否输出每次都需要构建记录消息？不太明白 包括后面传递Lambda表达式也不太明白
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void newLogLevelJudge() throws IllegalAccessException, InstantiationException {
       Logger logger = Logger.class.newInstance();
       logger.log(Level.FINER, "Problem: " + generateDiagnostic());
    }

    /**
     *  Log a message, which is only to be constructed if the logging level
     *   is such that the message will actually be logged.
     *   意思是消息日志，只有在消息级别与传入消息级别相同时才会被构建
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void betterLogLevelJudge() throws IllegalAccessException, InstantiationException {
        Logger logger = Logger.class.newInstance();
        logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());
    }



    private static String generateDiagnostic(){
        return "generateDiagnostic msg";
    }

}
