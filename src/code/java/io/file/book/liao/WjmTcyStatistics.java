package code.java.io.file.book.liao;

import code.java.io.file.book.liao.data.LiAoBookStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static code.java.utils.LU.println;

/*
遍历李敖所有书本每行文字，
统计含“wjm_tcy”的字符串，
输出每个字符串样式的次数和总数。

目的：为了拿出每篇文章的结束字符串，
用来后续做切割出每篇文章用。

得出文章的结尾行标志：.*wjm_tcy.*(制作！)$

* */
public class WjmTcyStatistics {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> wjmTcyStatisticsMap = oganizeWjmTcyStatisticsMap();
        int total = 0;
        while (wjmTcyStatisticsMap.size() > 0) {
            Map.Entry<String, Integer> maxEntry = findMaxWjmTcyEntry(wjmTcyStatisticsMap);
            println(/*"含“wjm_tcy”的字符串: " +*/ maxEntry.getKey() + ", count=" + maxEntry.getValue());
            wjmTcyStatisticsMap.remove(maxEntry.getKey());
            total+= maxEntry.getValue();
        }
        println("含“wjm_tcy”字符串的总数: " + total);
    }

    //找到（含“wjm_tcy”字符串）统计数最大的那个entry
    private static Map.Entry<String, Integer> findMaxWjmTcyEntry(Map<String, Integer> wjmTcyStatisticsMap) {
        if (wjmTcyStatisticsMap == null || wjmTcyStatisticsMap.size() == 0) {
            return null;
        }
        Set<Map.Entry<String, Integer>> entries = wjmTcyStatisticsMap.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
        Map.Entry<String, Integer> maxWjmTcyEntry = iterator.next();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> tempEntry = iterator.next();
            if (tempEntry.getValue() > maxWjmTcyEntry.getValue()) {
                maxWjmTcyEntry = tempEntry;
            }
        }
        return maxWjmTcyEntry;
    }

    //组织含“wjm_tcy”的字符串和其一共出现次数的map
    private static Map<String, Integer> oganizeWjmTcyStatisticsMap() throws IOException {
        //D:\code\java\JavaCodeCollection\temp\李敖大全集5.0(书名按顺序排列)(去除html，仅有文字内容)
        File liaoBooksRootDir = LiAoBookStore.getLiAoBooksWithoutHTMLRootDir();
        Map<String, Integer> wjmTcyStatisticsMap = new HashMap<>();
        for (File liAoBooksSubDir : liaoBooksRootDir.listFiles()) {
            for (File liAoBookFile : liAoBooksSubDir.listFiles()) {
                BufferedReader br = new BufferedReader(new FileReader(liAoBookFile));
                String oneLine;
                while ((oneLine = br.readLine()) != null) {
                    oneLine = oneLine.trim();
                    if (oneLine.contains("wjm_tcy")) {
                        Integer count = wjmTcyStatisticsMap.get(oneLine);
                        if (count == null) {
                            wjmTcyStatisticsMap.put(oneLine, 1);
                        } else {
                            wjmTcyStatisticsMap.put(oneLine, count + 1);
                        }
                    }
                }
            }
        }
        return wjmTcyStatisticsMap;
    }
}


/**
 * 不自由的自由（wjm_tcy）制作！
 * 不自由的自由（wjm_tcy）与“Jeff Ao”联合制作！
 * wjm_tcy（不自由的自由）根据funlin版重新制作！错字由孤笑看一线天校对。
 * <p>
 * 上面一类字符串得出正则规则含有“wjm_tcy”在前或中间且含有"制作"二字在中间或者后面
 * <p>
 * <p>
 * 不自由的自由（wjm_tcy）根据群友raphael、Jeff Ao、太昊兄三种版本互补所编。
 * 上面一类字符串得出正则规则：含有“wjm_tcy”在前或中间且含有"群友"、“所编”二字
 * <p>
 * 此文字由李敖影音E书QQ群578505007群友Mr（Q：984334467）2018.4.6制作；孤笑看一线天提供李敖书籍；wjm_tcy（不自由的自由）最终修改。, count=7
 * 上面一类字符串得出正则规则：含有"群"“制作”“wjm_tcy”“修改”关键字在中间
 * <p>
 * <p>
 * wjm_tcy：《韩诗外传．卷一○》：“制礼约法于四方，臣弗如也。”《后汉书·乌桓传》：“其约法：违大人言者，罪至死。”《南史·袁昂传》：“幸因约法之弘，承解网之宥，犹当降等薪粲，遂乃顿释钳赭。”《汉书·食货志上》：“上於是约法省禁，轻田租，什五而税一。”《晋书·李雄载记》：“雄性宽厚，简刑约法，甚有名称。, count=1
 * wjm_tcy注：提问者杂音太大，完全听不清。
 * wjm_tcy（不自由的自由）注：作者为陈香梅，在李敖的《“洁本”云乎哉？》一文中说：而“陈香梅所写的《我所知道的韩福瑞》”，不幸却也正是我李敖连改带写的（原稿俱在，稿上陈香梅的狗屁文章被我删改后，剩下一千八百四十一个字，我加写了一千六百三十四个字）。
 * 沈园柳老不飞绵；（wjm_tcy注：百度查为——沈园柳老不吹绵）
 * 本来这些错误都在我制作时用（wjm_tcy注）来表示，但想到可能会影响阅读，也有可能是我的错误，所以取消大部分标注，然后集合为这一篇。以下是我在制作过程中所发现的部分：, count=1
 * 上面一类注释字符串得出正则规则：含有"wjm_tcy："或“wjm_tcy注”或“wjm_tcy（不自由的自由）注：”（甚至可以衍生出“wjm_tcy（不自由的自由）：”）
 * <p>
 * 忆及我与李敖的一席话（jiankai） 为什么基督教的理学堂竟会有佛塔呢？（yubar） 学弟与李敖（yubar） 李敖与尼采（只爱一点点） 致谢并论文（congming） 最感动我的一本书（张向阳） 对李敖的三个印象记（弹铗士） 最近几个和李敖时代有关的新闻人物（一剑穿过忧伤） 今晨重读李敖大学日记的发现（一剑穿过忧伤） 李敖与娱乐（郭大少） 解读李敖在北大的演讲（一翁） 研究尚未成功，同志仍须努力（jarvisdd） 别忽视了李敖的早年（高一峰） 杀人手段救人心（高一峰） 文章·责任·扮（高一峰） 关于《历史的李敖》（高一峰） 李敖的“不敢”（钟钱钟书） 桀骜有话说（Jeff Ao） 再见啦李敖（Jeff Ao） 写在5.0发布前（Jeff Ao） JeffAo与《恶邻李敖》（JeffAo） 2018年3月18日（青岛小哥） 李敖先生诞辰87周年（青岛小哥） 念李敖先生（William） 从李敖到自我（William） 从初见李敖说起（微雨中等待） 李敖·李敖·屌（叶正浩） 李敖对我的三大影响（茶花楼主） 李敖是谁（Higher sky） 吾師吾友（宗雲） 何时再会你（是你的嘲笑，觉醒了我的悟） 《忆敖之》（黄达豪） 李敖真的很智慧（悟空兽大王） 李敖：不忘初心，砥砺前行。（散翎/千灯茶社） 李敖对我的影响（李敖精选） 李敖辞世一周年悼念（李敖精选） 李敖与我（樱桃哥哥的大梦想） 李敖杂议（屠伯3411797301） 忆李敖（羽隹） 敖之吾师周年祭（羽隹） 前不见李敖，后不见大师！（OieNg） 与时间赛跑的人——李敖（会思想的芦苇） 赠JEFF游法源寺并壮行色兼怀李敖（孤笑看一线天） 感谢李敖（hua） 李敖周年纪（独白下的传统） 怀李敖二首（金光） 论佩服（哈囉張叡賡） 奠敖文（枫） 写在敖哥逝世一周年之际（草字阔亭） 他改变了台湾（feel me） 忆李敖先生——良知（王建帅） 虽千万人，李敖往矣！（小陈） 回顾《李敖有话说》有感（晖哥） 李敖小错汇总（wjm_tcy） 与李敖观点相左（wjm_tcy） 被李敖观点改变（wjm_tcy） 研究李敖十三年（wjm_tcy）
 * 上面是《敖友论李敖》这本书的目录，目录里居然有“wjm_tcy”字符串。到时候正则肯定不能切掉它！
 */