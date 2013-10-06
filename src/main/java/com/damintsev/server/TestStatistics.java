package com.damintsev.server;

import com.damintsev.client.devices.BillingInfo;

import java.util.Date;
import java.util.Random;

/**
 * User: Damintsev Andrey
 * Date: 24.08.13
 * Time: 20:41
 */
public class TestStatistics extends  Thread {

    public void run() {
        int i = 0;
        while (true) {
            BillingInfo info = new BillingInfo();
            info.setId((long)i++);
            info.setDate(new Date());
            info.setNumberFrom("666-666");
            info.setTrunkNumber(959459L);
//            info.setNumber("" + StrictMath.abs(new Random().nextLong()));
            Random r = new Random();
            int num = r.nextInt(10);
            switch (num) {
                case 1:
                    info.setNumber("111223");
                    break;
                case 2:
//                case 8:
                    info.setNumber("3777788");
                    break;
//                case 3:
                case 6:
                    info.setNumber("3779888");
                    break;
                case 4:
                    info.setNumber("9677091");
                    break;
                default:
                    info.setNumber("" + StrictMath.abs(new Random().nextLong()));
            }
            info.setNumberShort(info.getNumber().substring(0,6));

            BillingStatistics.getInstance().addBilling(info);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
