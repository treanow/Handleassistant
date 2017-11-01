package com.letv.handleassistant.utils;


import android.content.res.Resources;

import com.letv.handleassistant.application.SoftApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 判断字符串是否为null或�?空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        boolean result = false;
        if (null == str || "".equals(str.trim())) {
            result = true;
        }
        return result;
    }

    /**
     * 如果i小于10，添�?后生成string
     *
     * @param i
     * @return
     */
    public static String addZreoIfLessThanTen(int i) {

        String string = "";
        int ballNum = i + 1;
        if (ballNum < 10) {
            string = "0" + ballNum;
        } else {
            string = ballNum + "";
        }
        return string;
    }

    /**
     * @param string
     * @return
     */
    public static boolean isNotNull(String string) {
        if (null != string && !"".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 去掉�?��字符串中的所有的单个空格" "
     *
     * @param string
     */
    public static String replaceSpaceCharacter(String string) {
        if (null == string || "".equals(string)) {
            return "";
        }
        return string.replace(" ", "");
    }

    /**
     * 获取小数位为6位的经纬�?
     *
     * @param string
     * @return
     */
    public static String getStringLongitudeOrLatitude(String string) {
        if (StringUtil.isNullOrEmpty(string)) {
            return "";
        }
        if (string.contains(".")) {
            String[] splitArray = string.split("\\.");
            if (splitArray[1].length() > 6) {
                String substring = splitArray[1].substring(0, 6);
                return splitArray[0] + "." + substring;
            } else {
                return string;
            }
        } else {
            return string;
        }
    }

    /**
     * 设置分页字符串
     *
     * @param pageNum    每页的条数
     * @param totalPages 总共的页数
     * @param sort       排序的方式
     * @param curIndex   当前页面的起始下标
     * @return
     */
    public static String setPageStr(String pageNum, String totalPages,
                                    String sort, String curIndex) {
        String pageStr = "</annex><annex type=\"http://www.webafx.org/datatypes/annex#RandomPaging\">";
        StringBuffer stringBuffer = new StringBuffer(pageStr);
        if (StringUtil.isNotNull(pageNum)) {
            stringBuffer.append("<d>");
            stringBuffer.append(pageNum);
            stringBuffer.append("</d>");
        } else {
            stringBuffer.append("<null/>");
        }
        if (StringUtil.isNotNull(totalPages)) {
            stringBuffer.append("<d>");
            stringBuffer.append(totalPages);
            stringBuffer.append("</d>");
        } else {
            stringBuffer.append("<null/>");
        }
        if (StringUtil.isNotNull(sort)) {
            stringBuffer.append("<array>");
            stringBuffer.append("<d>");
            stringBuffer.append(sort);
            stringBuffer.append("</d>");
            stringBuffer.append("<d>");
            stringBuffer.append(sort);
            stringBuffer.append("</d>");
            stringBuffer.append("</array>");
        } else {
            stringBuffer.append("<null/>");
        }
        if (StringUtil.isNotNull(curIndex)) {
            stringBuffer.append("<d>");
            stringBuffer.append(curIndex);
            stringBuffer.append("</d>");
        } else {
            stringBuffer.append("<null/>");
        }
        stringBuffer.append("</annex></message>");
        return stringBuffer.toString();
    }

    /**
     * 日期中去掉时分秒
     *
     * @param str
     * @return
     */
    public static String removeMHS(String str) {
        if (!isNullOrEmpty(str) && str.length() >= 10) {
            return str.substring(0, 10);
        }
        return "";
    }

    /**
     * 获得字符串的后2位
     *
     * @param str
     * @return
     */
    public static String getStrAfter2Bits(String str) {
        String subStr = "";
        if (VerifyCheck.isPaswOk(str)) {
            subStr = str.substring(str.length() - 2, str.length());
        }
        return subStr;
    }

    /**
     * 判断status标签的属性值后6位是否前3位是字母后3位是数字
     *
     * @param str
     * @return
     */
    public static boolean isNoSysError(String str) {
        if (StringUtil.isNullOrEmpty(str) || str.length() < 6) {
            return false;
        }
        String after6bit = str.substring(str.length() - 6, str.length());
        if (after6bit.length() == 6) {
            Pattern pattern1 = Pattern.compile("^[a-zA-Z]{3}$");
            Matcher matcher1 = pattern1.matcher(after6bit.subSequence(0, 3));
            boolean b1 = matcher1.matches();

            Pattern pattern2 = Pattern.compile("^[0-9]{3}$");
            Matcher matcher2 = pattern2.matcher(after6bit.subSequence(3, 6));
            boolean b2 = matcher2.matches();
            if (b1 && b2) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将电话号码中间4位加密
     *
     * @param str
     * @return
     */
    public static String encPhoneNum(String str) {
        String encStr = "";
        encStr = str.substring(0, 3) + "****" + str.substring(7, str.length());
        return encStr;
    }

    /**
     * 将身份证生日4位加密
     *
     * @param str
     * @return
     */
    public static String encIDCardNum(String str) {
        String encStr = "";
        encStr = str.substring(0, 10) + "****" + str.substring(14, str.length());
        return encStr;
    }

    /**
     * 将银行卡号中间加密
     *
     * @param str
     * @return
     */
    public static String encBankNum(String str) {
        String encStr = "";
        encStr = str.substring(0, 3) + "***" + str.substring(16, str.length());
        return encStr;
    }

    /**
     * 银行卡前面加密***1234
     *
     * @param str
     * @return
     */
    public static String showBankNum(String str) {
        String encStr = "";
        encStr = "***" + str.substring(str.length() - 4, str.length());
        return encStr;
    }

    /**
     * 20151010---> 2015-10-10
     *
     * @param str
     * @return
     */
    public static String castDate(String str) {
        String castDate = "";
        castDate = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, str.length());
        return castDate;
    }

    /**
     * 20151010---> 2015/10/10
     *
     * @param str
     * @return
     */
    public static String castDateTwo(String str) {
        String castDate = "";
        castDate = str.substring(0, 4) + "/" + str.substring(4, 6) + "/" + str.substring(6, str.length());
        return castDate;
    }

    /**
     * 8-6
     * 15000.00 -> 15,000.00
     * <p/>
     * 3000000.00 -> 3,000,000.00
     */
    public static String castMoney(String str) {
        String castMoney = "";
        //
        if (str.length() > 6 && str.length() < 10) {

            castMoney = str.substring(0, str.length() - 6) + "," + str.substring(str.length() - 6, str.length());
        } else if (str.length() > 9) {
            castMoney = str.substring(0, str.length() - 9) + "," + str.substring(str.length() - 9, str.length() - 6) + "," + str.substring(str.length() - 6, str.length());

        } else {
            castMoney = str;
        }
        return castMoney;
    }


    /**
     * 针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
     *
     * @param str
     * @return 返回全部为全角字符的字符串
     */
    public static String toDBC(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }

        }
        return new String(c);
    }

    /**
     * 获取字符串的数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }
    /**
     * 获取resources对象
     *
     * @return
     */
    public static Resources getResources() {
        return SoftApplication.softApplication.getResources();
    }

    // 判断字符串是否 数字
    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    public static boolean isPositiveDecimal(String orginal) {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isNegativeDecimal(String orginal) {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    public static boolean isRealNumber(String orginal) {
        return isWholeNumber(orginal) || isDecimal(orginal);
    }
    public static boolean isTwoFloat(String orginal) {
        return isMatch("^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$", orginal);
    }
}
