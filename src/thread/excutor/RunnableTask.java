package thread.excutor;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class RunnableTask implements Runnable {
    private final String name;
    private int sleeMs = 1000;

    public RunnableTask(String name) {
        this.name = name;
    }

    public RunnableTask(String name, int sleeMs) {
        this.name = name;
        this.sleeMs = sleeMs;
    }

    @Override
    public void run() {
        log(name + " 시작");
        sleep(sleeMs); //작업시간 시물레이션
        log(name + " 완료");
    }
}
