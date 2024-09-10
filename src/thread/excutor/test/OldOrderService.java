package thread.excutor.test;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class OldOrderService {

    public void order(String orderNo){
        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);

        //작업요청
        Boolean inventoryResult = inventoryWork.call();
        Boolean shippingResult = shippingWork.call();
        Boolean accountingResult = accountingWork.call();

        //결과 확인
        if(inventoryResult && shippingResult && accountingResult){
            log("모든 주문 처리가 성공적으로 완료되었습니다.");
        }else {
            log("일부 작업이 실패했습니다.");
        }
    }

    static class InventoryWork{
        private final String orderNo;

        public InventoryWork(String orderNo){
            this.orderNo = orderNo;
        }

        public Boolean call(){
            log("재고 업데이트:" + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork{

        private final String orderNo;
        public ShippingWork(String orderNo){
            this.orderNo = orderNo;
        }

        public Boolean call(){
            log("선적시스템 업데이트:" + orderNo);
            sleep(1000);
            return true;
        }

    }

    static class AccountingWork{
        private final String orderNo;

        public AccountingWork(String orderNo){
            this.orderNo = orderNo;
        }

        public Boolean call(){
            log("회계시스템 업데이트:" + orderNo);
            sleep(1000);
            return true;
        }
    }
}
