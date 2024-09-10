package thread.excutor.test;

import java.util.concurrent.ExecutionException;

public class NewOrderServiceTestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String orderNo = "Order#123";
        NewOrderService orderService = new NewOrderService();
        orderService.order(orderNo);
    }
}
