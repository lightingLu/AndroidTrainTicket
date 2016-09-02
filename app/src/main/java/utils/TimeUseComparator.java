package utils;

import java.util.Comparator;

import http.DataBean;

public class TimeUseComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {


        DataBean dataBean1 = (DataBean) o1;
        DataBean dataBean2 = (DataBean) o2;
        String lishi1 = dataBean1.getQueryLeftNewDTO().getLishi() ;
        String lish2 = dataBean2.getQueryLeftNewDTO().getLishi();
        int num1 = Integer.parseInt(lishi1.replace(":", ""));
        int num2 = Integer.parseInt(lish2.replace(":", ""));
        if (num1 > num2) {
            return 1;
        } else if (num1< num2) {
            return -1;
        }
        return 0;
    }

}
