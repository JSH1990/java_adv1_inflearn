package thread.excutor.test;

public class OldOrderServiceTestMain {
    public static void main(String[] args) {
        String orderNo = "Order#123";
        OldOrderService orderService = new OldOrderService();
        orderService.order(orderNo);
    }
}
