package com.hzgc.service.people.fields;

import com.hzgc.common.util.json.JacksonUtil;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 信息来源：http://www.tcmap.com.cn/list/jiancheng_list.html
 */
@Component
public class Provinces implements Serializable {

    private static Map<Integer, Map<String, Map<Integer, String>>> model = new LinkedHashMap<>();
    private static Map<Integer, String> province = new LinkedHashMap<>();

    static {
        province.put(1000, "北京");

        province.put(1100, "上海");

        province.put(1200, "天津");

        province.put(1300, "重庆");

        Map<Integer, String> heBei_city = new LinkedHashMap<>();
        heBei_city.put(1401, "石家庄");
        heBei_city.put(1402, "唐山");
        heBei_city.put(1403, "秦皇岛");
        heBei_city.put(1404, "邯郸");
        heBei_city.put(1405, "邢台");
        heBei_city.put(1406, "雄安新区");
        heBei_city.put(1407, "保定");
        heBei_city.put(1408, "张家口");
        heBei_city.put(1409, "承德");
        heBei_city.put(1410, "沧州");
        heBei_city.put(1411, "廊坊");
        heBei_city.put(1412, "衡水");
        Map<String, Map<Integer, String>> heBei_province = new LinkedHashMap<>();
        heBei_province.put("河北", heBei_city);
        model.put(1400, heBei_province);
        province.put(1400, "河北");

        Map<Integer, String> shanXi_city = new LinkedHashMap<>();
        shanXi_city.put(1501, "太原");
        shanXi_city.put(1502, "大同");
        shanXi_city.put(1503, "阳泉");
        shanXi_city.put(1504, "长治");
        shanXi_city.put(1505, "晋城");
        shanXi_city.put(1506, "朔州");
        shanXi_city.put(1507, "晋中");
        shanXi_city.put(1508, "运城");
        shanXi_city.put(1509, "忻州");
        shanXi_city.put(1510, "临汾");
        shanXi_city.put(1511, "吕梁");
        Map<String, Map<Integer, String>> shanXi_province = new LinkedHashMap<>();
        shanXi_province.put("山西", shanXi_city);
        model.put(1500, shanXi_province);
        province.put(1500, "山西");

        Map<Integer, String> neiMengGu_city = new LinkedHashMap<>();
        neiMengGu_city.put(1601, "呼和浩特");
        neiMengGu_city.put(1602, "包头");
        neiMengGu_city.put(1603, "乌海");
        neiMengGu_city.put(1604, "赤峰");
        neiMengGu_city.put(1605, "通辽");
        neiMengGu_city.put(1606, "鄂尔多斯");
        neiMengGu_city.put(1607, "呼伦贝尔");
        neiMengGu_city.put(1608, "巴彦淖尔");
        neiMengGu_city.put(1609, "乌兰察布");
        neiMengGu_city.put(1610, "兴安盟");
        neiMengGu_city.put(1611, "锡林郭勒盟");
        neiMengGu_city.put(1612, "阿拉善盟");
        Map<String, Map<Integer, String>> neiMengGu_province = new LinkedHashMap<>();
        neiMengGu_province.put("内蒙古", neiMengGu_city);
        model.put(1600, neiMengGu_province);
        province.put(1600, "内蒙古");

        Map<Integer, String> liaoLing_city = new LinkedHashMap<>();
        liaoLing_city.put(1701, "沈阳");
        liaoLing_city.put(1702, "大连");
        liaoLing_city.put(1703, "鞍山");
        liaoLing_city.put(1704, "抚顺");
        liaoLing_city.put(1705, "本溪");
        liaoLing_city.put(1706, "丹东");
        liaoLing_city.put(1707, "锦州");
        liaoLing_city.put(1708, "营口");
        liaoLing_city.put(1709, "阜新");
        liaoLing_city.put(1710, "辽阳");
        liaoLing_city.put(1711, "盘锦");
        liaoLing_city.put(1712, "铁岭");
        liaoLing_city.put(1713, "朝阳");
        liaoLing_city.put(1714, "葫芦岛");
        Map<String, Map<Integer, String>> liaoLing_province = new LinkedHashMap<>();
        liaoLing_province.put("辽宁", liaoLing_city);
        model.put(1700, liaoLing_province);
        province.put(1700, "辽宁");

        Map<Integer, String> jiLin_city = new LinkedHashMap<>();
        jiLin_city.put(1801, "长春");
        jiLin_city.put(1802, "吉林");
        jiLin_city.put(1803, "四平");
        jiLin_city.put(1804, "辽源");
        jiLin_city.put(1805, "通化");
        jiLin_city.put(1806, "白山");
        jiLin_city.put(1807, "松原");
        jiLin_city.put(1808, "白城");
        jiLin_city.put(1809, "延边");
        Map<String, Map<Integer, String>> jiLin_province = new LinkedHashMap<>();
        jiLin_province.put("吉林", jiLin_city);
        model.put(1800, jiLin_province);
        province.put(1800, "吉林");

        Map<Integer, String> heiLongJiang_city = new LinkedHashMap<>();
        heiLongJiang_city.put(1901, "哈尔滨");
        heiLongJiang_city.put(1902, "齐齐哈尔");
        heiLongJiang_city.put(1903, "鸡西");
        heiLongJiang_city.put(1904, "鹤岗");
        heiLongJiang_city.put(1905, "双鸭山");
        heiLongJiang_city.put(1906, "大庆");
        heiLongJiang_city.put(1907, "伊春");
        heiLongJiang_city.put(1908, "佳木斯");
        heiLongJiang_city.put(1909, "七台河");
        heiLongJiang_city.put(1910, "牡丹江");
        heiLongJiang_city.put(1911, "黑河");
        heiLongJiang_city.put(1912, "绥化");
        heiLongJiang_city.put(1913, "大兴安岭");
        Map<String, Map<Integer, String>> heiLongJiang_province = new LinkedHashMap<>();
        heiLongJiang_province.put("黑龙江", heiLongJiang_city);
        model.put(1900, heiLongJiang_province);
        province.put(1900, "黑龙江");

        Map<Integer, String> jiangShu_city = new LinkedHashMap<>();
        jiangShu_city.put(2001, "南京");
        jiangShu_city.put(2002, "无锡");
        jiangShu_city.put(2003, "徐州");
        jiangShu_city.put(2004, "常州");
        jiangShu_city.put(2005, "苏州");
        jiangShu_city.put(2006, "南通");
        jiangShu_city.put(2007, "连云港");
        jiangShu_city.put(2008, "淮安");
        jiangShu_city.put(2009, "盐城");
        jiangShu_city.put(2010, "扬州");
        jiangShu_city.put(2011, "镇江");
        jiangShu_city.put(2012, "泰州");
        jiangShu_city.put(2013, "宿迁");
        Map<String, Map<Integer, String>> jiangShu_province = new LinkedHashMap<>();
        jiangShu_province.put("江苏", jiangShu_city);
        model.put(2000, jiangShu_province);
        province.put(2000, "江苏");

        Map<Integer, String> hangZhou_city = new LinkedHashMap<>();
        hangZhou_city.put(2101, "杭州");
        hangZhou_city.put(2102, "宁波");
        hangZhou_city.put(2103, "温州");
        hangZhou_city.put(2104, "嘉兴");
        hangZhou_city.put(2105, "湖州");
        hangZhou_city.put(2106, "绍兴");
        hangZhou_city.put(2107, "金华");
        hangZhou_city.put(2108, "衢州");
        hangZhou_city.put(2109, "舟山");
        hangZhou_city.put(2110, "台州");
        hangZhou_city.put(2111, "丽水");
        Map<String, Map<Integer, String>> hangZhou_province = new LinkedHashMap<>();
        hangZhou_province.put("浙江", hangZhou_city);
        model.put(2100, hangZhou_province);
        province.put(2100, "浙江");

        Map<Integer, String> anHui_city = new LinkedHashMap<>();
        anHui_city.put(2201, "合肥");
        anHui_city.put(2202, "芜湖");
        anHui_city.put(2203, "蚌埠");
        anHui_city.put(2204, "淮南");
        anHui_city.put(2205, "马鞍山");
        anHui_city.put(2206, "淮北");
        anHui_city.put(2207, "铜陵");
        anHui_city.put(2208, "安庆");
        anHui_city.put(2209, "黄山");
        anHui_city.put(2210, "滁州");
        anHui_city.put(2211, "阜阳");
        anHui_city.put(2212, "宿州");
        anHui_city.put(2213, "六安");
        anHui_city.put(2214, "毫州");
        anHui_city.put(2215, "池州");
        anHui_city.put(2216, "宣城");
        Map<String, Map<Integer, String>> anHui_province = new LinkedHashMap<>();
        anHui_province.put("安徽", anHui_city);
        model.put(2200, anHui_province);
        province.put(2200, "安徽");

        Map<Integer, String> fuJian_city = new LinkedHashMap<>();
        fuJian_city.put(2301, "福州");
        fuJian_city.put(2302, "厦门");
        fuJian_city.put(2303, "莆田");
        fuJian_city.put(2304, "三明");
        fuJian_city.put(2305, "泉州");
        fuJian_city.put(2306, "漳州");
        fuJian_city.put(2307, "南平");
        fuJian_city.put(2308, "龙岩");
        fuJian_city.put(2309, "宁德");
        Map<String, Map<Integer, String>> fuJian_province = new LinkedHashMap<>();
        fuJian_province.put("福建", fuJian_city);
        model.put(2300, fuJian_province);
        province.put(2300, "福建");

        Map<Integer, String> jiangXi_city = new LinkedHashMap<>();
        jiangXi_city.put(2401, "南昌");
        jiangXi_city.put(2402, "景德镇");
        jiangXi_city.put(2403, "萍乡");
        jiangXi_city.put(2404, "九江");
        jiangXi_city.put(2405, "新余");
        jiangXi_city.put(2406, "鹰潭");
        jiangXi_city.put(2407, "赣州");
        jiangXi_city.put(2408, "吉安");
        jiangXi_city.put(2409, "宜春");
        jiangXi_city.put(2410, "抚州");
        jiangXi_city.put(2411, "上饶");
        Map<String, Map<Integer, String>> jiangXi_province = new LinkedHashMap<>();
        jiangXi_province.put("江西", jiangXi_city);
        model.put(2400, jiangXi_province);
        province.put(2400, "江西");

        Map<Integer, String> shanDong_city = new LinkedHashMap<>();
        shanDong_city.put(2501, "济南");
        shanDong_city.put(2502, "青岛");
        shanDong_city.put(2503, "淄博");
        shanDong_city.put(2504, "枣庄");
        shanDong_city.put(2505, "东营");
        shanDong_city.put(2506, "烟台");
        shanDong_city.put(2507, "潍坊");
        shanDong_city.put(2508, "济宁");
        shanDong_city.put(2509, "泰安");
        shanDong_city.put(2510, "威海");
        shanDong_city.put(2511, "日照");
        shanDong_city.put(2512, "莱芜");
        shanDong_city.put(2513, "临沂");
        shanDong_city.put(2514, "德州");
        shanDong_city.put(2515, "聊城");
        shanDong_city.put(2516, "滨州");
        shanDong_city.put(2517, "菏泽");
        Map<String, Map<Integer, String>> shanDong_province = new LinkedHashMap<>();
        shanDong_province.put("山东", shanDong_city);
        model.put(2500, shanDong_province);
        province.put(2500, "山东");

        Map<Integer, String> heNan_city = new LinkedHashMap<>();
        heNan_city.put(2601, "郑州");
        heNan_city.put(2602, "开封");
        heNan_city.put(2603, "洛阳");
        heNan_city.put(2604, "平顶山");
        heNan_city.put(2605, "安阳");
        heNan_city.put(2606, "鹤壁");
        heNan_city.put(2607, "新乡");
        heNan_city.put(2608, "焦作");
        heNan_city.put(2609, "濮阳");
        heNan_city.put(2610, "许昌");
        heNan_city.put(2611, "漯河");
        heNan_city.put(2612, "三门峡");
        heNan_city.put(2613, "南阳");
        heNan_city.put(2614, "商丘");
        heNan_city.put(2615, "信阳");
        heNan_city.put(2616, "周口");
        heNan_city.put(2617, "驻马店");
        heNan_city.put(2618, "济源");
        Map<String, Map<Integer, String>> heNan_province = new LinkedHashMap<>();
        heNan_province.put("河南", heNan_city);
        model.put(2600, heNan_province);
        province.put(2600, "河南");

        Map<Integer, String> huBei_city = new LinkedHashMap<>();
        huBei_city.put(2701, "武汉");
        huBei_city.put(2702, "黄石");
        huBei_city.put(2703, "十堰");
        huBei_city.put(2704, "宜昌");
        huBei_city.put(2705, "襄阳");
        huBei_city.put(2706, "鄂州");
        huBei_city.put(2707, "荆门");
        huBei_city.put(2708, "孝感");
        huBei_city.put(2709, "荆州");
        huBei_city.put(2710, "黄冈");
        huBei_city.put(2711, "咸宁");
        huBei_city.put(2712, "随州");
        huBei_city.put(2713, "恩施");
        huBei_city.put(2714, "仙桃");
        huBei_city.put(2715, "潜江");
        huBei_city.put(2716, "天门");
        huBei_city.put(2717, "神农架");
        Map<String, Map<Integer, String>> huBei_province = new LinkedHashMap<>();
        huBei_province.put("湖北", huBei_city);
        model.put(2700, huBei_province);
        province.put(2700, "湖北");

        Map<Integer, String> huNan_city = new LinkedHashMap<>();
        huNan_city.put(2801, "长沙");
        huNan_city.put(2802, "株洲");
        huNan_city.put(2803, "湘潭");
        huNan_city.put(2804, "衡阳");
        huNan_city.put(2805, "邵阳");
        huNan_city.put(2806, "岳阳");
        huNan_city.put(2807, "常德");
        huNan_city.put(2808, "张家界");
        huNan_city.put(2809, "益阳");
        huNan_city.put(2810, "郴州");
        huNan_city.put(2811, "永州");
        huNan_city.put(2812, "怀化");
        huNan_city.put(2813, "湘西州");
        Map<String, Map<Integer, String>> huNan_province = new LinkedHashMap<>();
        huNan_province.put("湖南", huNan_city);
        model.put(2800, huNan_province);
        province.put(2800, "湖南");

        Map<Integer, String> guangDong_city = new LinkedHashMap<>();
        guangDong_city.put(2901, "广州");
        guangDong_city.put(2902, "韶关");
        guangDong_city.put(2903, "深圳");
        guangDong_city.put(2904, "珠海");
        guangDong_city.put(2905, "汕头");
        guangDong_city.put(2906, "佛山");
        guangDong_city.put(2907, "江门");
        guangDong_city.put(2908, "湛江");
        guangDong_city.put(2909, "茂名");
        guangDong_city.put(2910, "肇庆");
        guangDong_city.put(2911, "惠州");
        guangDong_city.put(2912, "梅州");
        guangDong_city.put(2913, "汕尾");
        guangDong_city.put(2914, "河源");
        guangDong_city.put(2915, "阳江");
        guangDong_city.put(2916, "清远");
        guangDong_city.put(2917, "东莞");
        guangDong_city.put(2918, "中山");
        guangDong_city.put(2919, "潮州");
        guangDong_city.put(2920, "揭阳");
        guangDong_city.put(2921, "云浮");
        Map<String, Map<Integer, String>> guangDong_province = new LinkedHashMap<>();
        guangDong_province.put("广东", guangDong_city);
        model.put(2900, guangDong_province);
        province.put(2900, "广东");

        Map<Integer, String> guangXi_city = new LinkedHashMap<>();
        guangXi_city.put(3001, "南宁");
        guangXi_city.put(3002, "柳州");
        guangXi_city.put(3003, "桂林");
        guangXi_city.put(3004, "梧州");
        guangXi_city.put(3005, "北海");
        guangXi_city.put(3006, "防城港");
        guangXi_city.put(3007, "钦州");
        guangXi_city.put(3008, "贵港");
        guangXi_city.put(3009, "玉林");
        guangXi_city.put(3010, "百色");
        guangXi_city.put(3011, "贺州");
        guangXi_city.put(3012, "河池");
        guangXi_city.put(3013, "来宾");
        guangXi_city.put(3014, "崇左");
        Map<String, Map<Integer, String>> guangXi_province = new LinkedHashMap<>();
        guangXi_province.put("广西", guangXi_city);
        model.put(3000, guangXi_province);
        province.put(3000, "广西");

        Map<Integer, String> haiNan_city = new LinkedHashMap<>();
        haiNan_city.put(3101, "海口");
        haiNan_city.put(3102, "三亚");
        haiNan_city.put(3103, "五指山");
        haiNan_city.put(3104, "琼海");
        haiNan_city.put(3105, "儋州");
        haiNan_city.put(3106, "文昌");
        haiNan_city.put(3107, "万宁");
        haiNan_city.put(3108, "东方");
        haiNan_city.put(3109, "三沙");
        Map<String, Map<Integer, String>> haiNan_province = new LinkedHashMap<>();
        haiNan_province.put("海南", haiNan_city);
        model.put(3100, haiNan_province);
        province.put(3100, "海南");

        Map<Integer, String> siChuan_city = new LinkedHashMap<>();
        siChuan_city.put(3201, "成都");
        siChuan_city.put(3202, "自贡");
        siChuan_city.put(3203, "攀枝花");
        siChuan_city.put(3204, "泸州");
        siChuan_city.put(3205, "德阳");
        siChuan_city.put(3206, "绵阳");
        siChuan_city.put(3207, "广元");
        siChuan_city.put(3208, "遂宁");
        siChuan_city.put(3209, "内江");
        siChuan_city.put(3210, "乐山");
        siChuan_city.put(3211, "南充");
        siChuan_city.put(3212, "眉山");
        siChuan_city.put(3213, "宜宾");
        siChuan_city.put(3214, "广安");
        siChuan_city.put(3215, "达州");
        siChuan_city.put(3216, "雅安");
        siChuan_city.put(3217, "巴中");
        siChuan_city.put(3218, "资阳");
        siChuan_city.put(3219, "阿坝");
        siChuan_city.put(3220, "甘孜");
        siChuan_city.put(3221, "凉山州");
        Map<String, Map<Integer, String>> siChuan_province = new LinkedHashMap<>();
        siChuan_province.put("四川", siChuan_city);
        model.put(3200, siChuan_province);
        province.put(3200, "四川");

        Map<Integer, String> guiZhou_city = new LinkedHashMap<>();
        guiZhou_city.put(3301, "贵阳");
        guiZhou_city.put(3302, "六盘水");
        guiZhou_city.put(3303, "遵义");
        guiZhou_city.put(3304, "安顺");
        guiZhou_city.put(3305, "毕节");
        guiZhou_city.put(3306, "铜仁");
        guiZhou_city.put(3307, "黔西南州");
        guiZhou_city.put(3308, "黔东南州");
        guiZhou_city.put(3309, "黔南州");
        Map<String, Map<Integer, String>> guiZhou_province = new LinkedHashMap<>();
        guiZhou_province.put("贵州", guiZhou_city);
        model.put(3300, guiZhou_province);
        province.put(3300, "贵州");

        Map<Integer, String> yunNan_city = new LinkedHashMap<>();
        yunNan_city.put(3401, "昆明");
        yunNan_city.put(3402, "曲靖");
        yunNan_city.put(3403, "玉溪");
        yunNan_city.put(3404, "保山");
        yunNan_city.put(3405, "昭通");
        yunNan_city.put(3406, "丽江");
        yunNan_city.put(3407, "普洱");
        yunNan_city.put(3408, "临沧");
        yunNan_city.put(3409, "楚雄州");
        yunNan_city.put(3410, "红河州");
        yunNan_city.put(3411, "文山州");
        yunNan_city.put(3412, "西双版纳州");
        yunNan_city.put(3413, "大理州");
        yunNan_city.put(3414, "德宏州");
        yunNan_city.put(3415, "怒江州");
        yunNan_city.put(3416, "迪庆州");
        Map<String, Map<Integer, String>> yunNan_province = new LinkedHashMap<>();
        yunNan_province.put("云南", yunNan_city);
        model.put(3400, yunNan_province);
        province.put(3400, "云南");

        Map<Integer, String> xiZang_city = new LinkedHashMap<>();
        xiZang_city.put(3501, "拉萨");
        xiZang_city.put(3502, "日喀则");
        xiZang_city.put(3503, "昌都");
        xiZang_city.put(3504, "林芝");
        xiZang_city.put(3505, "山南");
        xiZang_city.put(3506, "那曲");
        xiZang_city.put(3507, "阿里");
        Map<String, Map<Integer, String>> xiZang_province = new LinkedHashMap<>();
        xiZang_province.put("西藏", xiZang_city);
        model.put(3500, xiZang_province);
        province.put(3500, "西藏");

        Map<Integer, String> shanXXi_city = new LinkedHashMap<>();
        shanXXi_city.put(3601, "西安");
        shanXXi_city.put(3602, "铜川");
        shanXXi_city.put(3603, "宝鸡");
        shanXXi_city.put(3604, "咸阳");
        shanXXi_city.put(3605, "渭南");
        shanXXi_city.put(3606, "延安");
        shanXXi_city.put(3607, "汉中");
        shanXXi_city.put(3608, "榆林");
        shanXXi_city.put(3609, "安康");
        shanXXi_city.put(3610, "商洛");
        Map<String, Map<Integer, String>> shanXXi_province = new LinkedHashMap<>();
        shanXXi_province.put("陕西", shanXXi_city);
        model.put(3600, shanXXi_province);
        province.put(3600, "陕西");

        Map<Integer, String> ganSu_city = new LinkedHashMap<>();
        ganSu_city.put(3701, "兰州");
        ganSu_city.put(3702, "嘉峪关");
        ganSu_city.put(3703, "金昌");
        ganSu_city.put(3704, "白银");
        ganSu_city.put(3705, "天水");
        ganSu_city.put(3706, "武威");
        ganSu_city.put(3707, "张掖");
        ganSu_city.put(3708, "平凉");
        ganSu_city.put(3709, "酒泉");
        ganSu_city.put(37010, "庆阳");
        ganSu_city.put(37011, "定西");
        ganSu_city.put(37012, "陇南");
        ganSu_city.put(37013, "临夏州");
        ganSu_city.put(37014, "甘南州");
        Map<String, Map<Integer, String>> ganSu_province = new LinkedHashMap<>();
        ganSu_province.put("甘肃", ganSu_city);
        model.put(3700, ganSu_province);
        province.put(3700, "甘肃");

        Map<Integer, String> qingHai_city = new LinkedHashMap<>();
        qingHai_city.put(3801, "西宁");
        qingHai_city.put(3802, "海东市");
        qingHai_city.put(3803, "海北州");
        qingHai_city.put(3804, "黄南州");
        qingHai_city.put(3805, "海南州");
        qingHai_city.put(3806, "果洛州");
        qingHai_city.put(3807, "玉树州");
        qingHai_city.put(3808, "海西州");
        Map<String, Map<Integer, String>> qingHai_province = new LinkedHashMap<>();
        qingHai_province.put("青海", qingHai_city);
        model.put(3800, qingHai_province);
        province.put(3800, "青海");

        Map<Integer, String> ningXia_city = new LinkedHashMap<>();
        ningXia_city.put(3901, "银川");
        ningXia_city.put(3902, "石嘴山");
        ningXia_city.put(3903, "吴忠");
        ningXia_city.put(3904, "固原");
        ningXia_city.put(3905, "中卫");
        Map<String, Map<Integer, String>> ningXia_province = new LinkedHashMap<>();
        ningXia_province.put("宁夏", ningXia_city);
        model.put(3900, ningXia_province);
        province.put(3900, "宁夏");

        Map<Integer, String> xinJiang_city = new LinkedHashMap<>();
        xinJiang_city.put(4001, "乌鲁木齐");
        xinJiang_city.put(4002, "克拉玛依");
        xinJiang_city.put(4003, "吐鲁番");
        xinJiang_city.put(4004, "哈密市");
        xinJiang_city.put(4005, "昌吉州");
        xinJiang_city.put(4006, "博尔塔拉州");
        xinJiang_city.put(4007, "巴音郭楞州");
        xinJiang_city.put(4008, "阿克苏地区");
        xinJiang_city.put(4009, "克孜勒苏州");
        xinJiang_city.put(4010, "喀什地区");
        xinJiang_city.put(4011, "和田地区");
        xinJiang_city.put(4012, "伊犁州");
        xinJiang_city.put(4013, "塔城地区");
        xinJiang_city.put(4014, "阿勒泰地区");
        xinJiang_city.put(4015, "石河子");
        xinJiang_city.put(4016, "阿拉尔");
        xinJiang_city.put(4017, "图木舒克");
        xinJiang_city.put(4018, "五家渠");
        xinJiang_city.put(4019, "北屯");
        xinJiang_city.put(4020, "铁门关");
        xinJiang_city.put(4021, "双河");
        xinJiang_city.put(4022, "可克达拉");
        xinJiang_city.put(4023, "昆玉");
        Map<String, Map<Integer, String>> xinJiang_province = new LinkedHashMap<>();
        xinJiang_province.put("新疆", xinJiang_city);
        model.put(4000, xinJiang_province);
        province.put(4000, "新疆");


    }

    public static Map<Integer, String> getProvince() {
        return province;
    }

    public static Map<Integer, String> getCity(int index) {
        if (index < 1400){
            return null;
        }
        Map<String, Map<Integer, String>> map = model.get(index);
        String str = province.get(index);
        return map.get(str);
    }

    public static void main(String[] args) {
        System.out.println(JacksonUtil.toJson(getProvince()));
        System.out.println(JacksonUtil.toJson(getCity(3600)));
    }
}
