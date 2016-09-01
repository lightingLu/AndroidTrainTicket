package http;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Three on 2016/9/1.
 */
public class DataBean implements Parcelable {
    /**
     * train_no : 240000G1370F
     * station_train_code : G137
     * start_station_telecode : VNP
     * start_station_name : 北京南
     * end_station_telecode : AOH
     * end_station_name : 上海虹桥
     * from_station_telecode : VNP
     * from_station_name : 北京南
     * to_station_telecode : AOH
     * to_station_name : 上海虹桥
     * start_time : 13:07
     * arrive_time : 19:01
     * day_difference : 0
     * train_class_name :
     * lishi : 05:54
     * canWebBuy : Y
     * lishiValue : 354
     * yp_info : O055300000M0933000519174800018
     * control_train_day : 20301231
     * start_train_date : 20160901
     * seat_feature : O3M393
     * yp_ex : O0M090
     * train_seat_feature : 3
     * seat_types : OM9
     * location_code : P3
     * from_station_no : 01
     * to_station_no : 11
     * control_day : 59
     * sale_time : 1230
     * is_support_card : 1
     * controlled_train_flag : 0
     * controlled_train_message : 正常车次，不受控
     * gg_num : --
     * gr_num : --
     * qt_num : --
     * rw_num : --
     * rz_num : --
     * tz_num : --
     * wz_num : --
     * yb_num : --
     * yw_num : --
     * yz_num : --
     * ze_num : 无
     * zy_num : 有
     * swz_num : 18
     */

    private QueryLeftNewDTOBean queryLeftNewDTO;
    private String secretStr;
    private String buttonTextInfo;

    public QueryLeftNewDTOBean getQueryLeftNewDTO() {
        return queryLeftNewDTO;
    }

    public void setQueryLeftNewDTO(QueryLeftNewDTOBean queryLeftNewDTO) {
        this.queryLeftNewDTO = queryLeftNewDTO;
    }

    public String getSecretStr() {
        return secretStr;
    }

    public void setSecretStr(String secretStr) {
        this.secretStr = secretStr;
    }

    public String getButtonTextInfo() {
        return buttonTextInfo;
    }

    public void setButtonTextInfo(String buttonTextInfo) {
        this.buttonTextInfo = buttonTextInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.queryLeftNewDTO, flags);
        dest.writeString(this.secretStr);
        dest.writeString(this.buttonTextInfo);
    }

    public DataBean() {
    }

    protected DataBean(Parcel in) {
        this.queryLeftNewDTO = in.readParcelable(QueryLeftNewDTOBean.class.getClassLoader());
        this.secretStr = in.readString();
        this.buttonTextInfo = in.readString();
    }

    public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel source) {
            return new DataBean(source);
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };
}
