package thread.excutor.test;

import java.util.concurrent.*;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class NewOrderService {

    private final ExecutorService es = Executors.newFixedThreadPool(10);


    public void order(String orderNo) throws ExecutionException, InterruptedException {

        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);

        //작업요청
        //작업들을 ExcutorService에 제출
        Future<Boolean> inventoryFuture = es.submit(inventoryWork);
        Future<Boolean> shippingFuture = es.submit(shippingWork);
        Future<Boolean> accountingFuture = es.submit(accountingWork);

        //작업 완료를 기다림
        Boolean inventoryResult = inventoryFuture.get();
        Boolean shippingResult = shippingFuture.get();
        Boolean accountingResult = accountingFuture.get();

        //결과 확인
        if(inventoryResult && shippingResult && accountingResult){
            log("모든 주문 처리가 성공적으로 완료되었습니다.");
        }else {
            log("일부 작업이 실패했습니다.");
        }
    }

    static class InventoryWork implements Callable<Boolean>{
        private final String orderNo;

        public InventoryWork(String orderNo){
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call(){
            log("재고 업데이트:" + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean>{

        private final String orderNo;
        public ShippingWork(String orderNo){
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call(){
            log("선적시스템 업데이트:" + orderNo);
            sleep(1000);
            return true;
        }

    }

    static class AccountingWork implements Callable<Boolean>{
        private final String orderNo;

        public AccountingWork(String orderNo){
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call(){
            log("회계시스템 업데이트:" + orderNo);
            sleep(1000);
            return true;
        }
    }
}
