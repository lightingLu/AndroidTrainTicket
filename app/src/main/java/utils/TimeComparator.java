package utils;

import java.util.Comparator;

import http.DataBean;
public class TimeComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        DataBean dataBean1 = (DataBean) o1;
        DataBean dataBean2 = (DataBean) o2;
        String start_time1 = dataBean1.getQueryLeftNewDTO().getStart_time() ;
        String start_time2 = dataBean2.getQueryLeftNewDTO().getStart_time();
        int num1 = Integer.parseInt(start_time1.replace(":", ""));
        int num2 = Integer.parseInt(start_time2.replace(":", ""));
        if (num1 > num2) {
            return -1;
        } else if (num1< num2) {
            return 1;
        }
        return 0;
    }
}
