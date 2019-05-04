package com.qiao;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Created by ql on 2019/5/4/004 18:08
 * @Version: v1.0
 */
public class Ipv6Util {

    public static void main(String[] args) {
        System.out.println(parseAbbreviationToFullIPv6("fe80::c0a8:34c9"));
        System.out.println(parseFullIPv6ToAbbreviation("fe80:0:0:0:0:0:c0a8:34c8"));
    }
    /**
     * 将 简写的IPv6 转换成 非简写的IPv6
     * 
     * @param abbreviation
     * @return
     */
    public static String parseAbbreviationToFullIPv6(String abbreviation)
    {
        String fullIPv6 = "";
        
        if ("::".equals(abbreviation))
        {
            return "0000:0000:0000:0000:0000:0000:0000:0000";
        }
        
        String[] arr = new String[]
        { "0000", "0000", "0000", "0000", "0000", "0000", "0000", "0000" };
        
        if (abbreviation.startsWith("::"))
        {
            String[] temp = abbreviation.substring(2, abbreviation.length()).split(":");
            for (int i = 0; i < temp.length; i++)
            {
                String tempStr = "0000" + temp[i];
                arr[i + 8 - temp.length] = tempStr.substring(tempStr.length() - 4);
            }
            
        } else if (abbreviation.endsWith("::")) {
            String[] temp = abbreviation.substring(0, abbreviation.length() - 2).split(":");
            for (int i = 0; i < temp.length; i++)
            {
                String tempStr = "0000" + temp[i];
                arr[i] = tempStr.substring(tempStr.length() - 4);
            }
            
        } else if (abbreviation.contains("::")) {
            String[] tempArr = abbreviation.split("::");
            
            String[] temp0 = tempArr[0].split(":");
            for (int i = 0; i < temp0.length; i++)
            {
                String tempStr = "0000" + temp0[i];
                arr[i] = tempStr.substring(tempStr.length() - 4);
            }
            
            String[] temp1 = tempArr[1].split(":");
            for (int i = 0; i < temp1.length; i++)
            {
                String tempStr = "0000" + temp1[i];
                arr[i + 8 - temp1.length] = tempStr.substring(tempStr.length() - 4);
            }
            
        } else {
            String[] tempArr = abbreviation.split(":");
            
            for (int i = 0; i < tempArr.length; i++)
            {
                String tempStr = "0000" + tempArr[i];
                arr[i] = tempStr.substring(tempStr.length() - 4);
            }
            
        }
        
        fullIPv6 = StringUtils.join(arr, ":");

        return fullIPv6;
    }

    /**
          * 将 非简写的IPv6 转换成 简写的IPv6
          * 
          * @param fullIPv6 非简写的IPv6
          * @return 简写的IPv6
          */
    public static String parseFullIPv6ToAbbreviation(String fullIPv6) {
        String abbreviation = "";
        
        // 1,校验 ":" 的个数 不等于7  或者长度不等于39  直接返回空串
        int count = fullIPv6.length() - fullIPv6.replaceAll(":", "").length();
        if (fullIPv6.length() != 39 && count != 7)
        {
            return abbreviation;
        }
        
        // 2,去掉每一位前面的0
        String[] arr = fullIPv6.split(":");
        
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = arr[i].replaceAll("^0{1,3}", "");
        }
        
        // 3,找到最长的连续的0
        String[] arr2 = arr.clone();
        for (int i = 0; i < arr2.length; i++)
        {
            if (!"0".equals(arr2[i]))
            {
                arr2[i] = "-";
            }
        }
        
        Pattern pattern = Pattern.compile("0{2,}");
        Matcher matcher = pattern.matcher(StringUtils.join(arr2, ""));
        String maxStr= "";
        int start = -1;
        int end = -1;
        while (matcher.find()) {
            if(maxStr.length()<matcher.group().length()) {
                maxStr=matcher.group();
                start = matcher.start();
                end = matcher.end();
            }
        }
        
        // 3,合并        
        if(maxStr.length()>0) {
            for (int i = start; i < end; i++)
            {
                    arr[i] = ":";
            }
        }
        abbreviation = StringUtils.join(arr, ":");
        abbreviation= abbreviation.replaceAll(":{2,}", "::");
        
        return abbreviation;
    }

    void function IPv4ToIPv6(IP){


    var result = IP.split('\.');    
    result[0]= (Array(8).join(0) + parseInt(result[0]).toString(2)).slice(-8);
    result[1]= (Array(8).join(0) + parseInt(result[1]).toString(2)).slice(-8);
    result[2]= (Array(8).join(0) + parseInt(result[2]).toString(2)).slice(-8);
    result[3]= (Array(8).join(0) + parseInt(result[3]).toString(2)).slice(-8);
    
    var result2=[];
    result2[0]=(Array(4).join(0) + parseInt(''+result[0]+result[1],2).toString(16)).slice(-4);
    result2[1]=(Array(4).join(0) + parseInt(''+result[2]+result[3],2).toString(16)).slice(-4);
    
    return '0000:0000:0000:0000:0000:0000:'+result2[0]+':'+result2[1];
    }
}
