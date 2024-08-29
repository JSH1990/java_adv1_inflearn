package thread.start;

import static util.MyLogger.log;

public class InnerRunnableMainV2 {
    public static void main(String[] args) {
        log("main() start");

        //익명클래스 - 특정메서드 안에서만 간단히 정의하고 사용하고 싶다면 익명 클래스를 사용한다.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log("run()");
            }
        };

        new Thread(runnable).start();

        log("main() end");
    }


}
