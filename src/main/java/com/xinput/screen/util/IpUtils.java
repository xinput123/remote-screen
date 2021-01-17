package com.xinput.screen.util;

import io.netty.util.internal.StringUtil;

public class IpUtils {

  /**
   * 判断IP地址是否合法
   */
  public static boolean check(String ip) {
    if (StringUtil.isNullOrEmpty(ip)) {
      return false;
    }

    // 定义正则表达式
    String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    // 判断ip地址是否与正则表达式匹配
    if (ip.matches(regex)) {
      // 返回判断信息
      return true;
    } else {
      // 返回判断信息
      return false;
    }
  }

  public static void main(String[] args) {

  }
}
