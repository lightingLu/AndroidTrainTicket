package http;

/**
 * Created by Three on 2016/8/29.
 */
public class HttpRequestUrl {
    /**
     * 获取图片验证码的链接
     */
    public  final static String PASSWOR_CODE_URL ="https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&0.012066099559888244";
    /**
     * 图面验证码验证连接
     */
    public final static String PASSWOR_YANZ_URL ="https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn";
    /**
     * 用户登录连接
     */
    public  final  static String PASSWOR_LOGININ_URL ="http://kyfw.12306.cn/otn/login/loginAysnSuggest";
    /**
     * 用户登录状态连接
     */
    public  final static String PASSWOR_LOGIN_STATE_URL ="https://kyfw.12306.cn/otn/login/checkUser?json_att=;";
}
