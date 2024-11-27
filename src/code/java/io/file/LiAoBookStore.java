package code.java.io.file;

import code.java.utils.ProjectFileUtils;

import java.io.File;
import java.util.*;

public class LiAoBookStore {
    //获取李敖163本书的书本名。
    public static List<String> newLiaoBookNamesList() {
        List<String> liaoBooksList = new ArrayList<>();
        liaoBooksList.add("李敖自传与回忆");
        liaoBooksList.add("李敖自传与回忆续集");
        liaoBooksList.add("我最难忘的事和人");
        liaoBooksList.add("李敖回忆录");
        liaoBooksList.add("李敖快意恩仇录");
        liaoBooksList.add("李敖议坛哀思录");
        liaoBooksList.add("李敖风流自传");
        liaoBooksList.add("李敖相关");
        liaoBooksList.add("传统下的独白");
        liaoBooksList.add("传统下的再白");
        liaoBooksList.add("独白下的传统");
        liaoBooksList.add("李敖文存");
        liaoBooksList.add("李敖文存二集");
        liaoBooksList.add("波波颂");
        liaoBooksList.add("李敖全集");
        liaoBooksList.add("教育与脸谱");
        liaoBooksList.add("文化论战丹火录");
        liaoBooksList.add("为中国思想趋向求答案");
        liaoBooksList.add("上下古今谈");
        liaoBooksList.add("世论新语");
        liaoBooksList.add("求是新语");
        liaoBooksList.add("我是天安门");
        liaoBooksList.add("你是景福门");
        liaoBooksList.add("为自由招魂");
        liaoBooksList.add("你笨蛋，你笨蛋");
        liaoBooksList.add("我梦碎，所以我梦醒");
        liaoBooksList.add("李敖新刊");
        liaoBooksList.add("千秋万岁乌鸦求是合集");
        liaoBooksList.add("李敖杂文集");
        liaoBooksList.add("千秋万岁编外集");
        liaoBooksList.add("北京法源寺");
        liaoBooksList.add("上山·上山·爱");
        liaoBooksList.add("红色11");
        liaoBooksList.add("虚拟的十七岁");
        liaoBooksList.add("阳痿美国");
        liaoBooksList.add("第73烈士");
        liaoBooksList.add("爱情的秘密");
        liaoBooksList.add("李敖的情诗");
        liaoBooksList.add("李语录");
        liaoBooksList.add("李敖语录");
        liaoBooksList.add("虽千万人，李敖往矣");
        liaoBooksList.add("挑战李敖——敖语录");
        liaoBooksList.add("大学札记");
        liaoBooksList.add("早年日记");
        liaoBooksList.add("大学后期日记甲集");
        liaoBooksList.add("大学后期日记乙集");
        liaoBooksList.add("一个预备军官的日记");
        liaoBooksList.add("李敖秘藏日记");
        liaoBooksList.add("李敖札记");
        liaoBooksList.add("李敖五五日记");
        liaoBooksList.add("李敖随写录前集");
        liaoBooksList.add("李敖随写录后集");
        liaoBooksList.add("李敖报刊集");
        liaoBooksList.add("李敖书序集");
        liaoBooksList.add("李敖书序集续集");
        liaoBooksList.add("李敖对话录");
        liaoBooksList.add("李敖访谈录1990-2018");
        liaoBooksList.add("李敖情书集");
        liaoBooksList.add("李敖书信集");
        liaoBooksList.add("李敖书翰集");
        liaoBooksList.add("李敖书简集");
        liaoBooksList.add("李敖书札集");
        liaoBooksList.add("李敖书笺集");
        liaoBooksList.add("李敖书牍集");
        liaoBooksList.add("李敖书函集");
        liaoBooksList.add("李敖书启集");
        liaoBooksList.add("坐牢家爸爸给女儿的八十封信");
        liaoBooksList.add("给马戈的五十封信");
        liaoBooksList.add("历史与人像");
        liaoBooksList.add("读史指南");
        liaoBooksList.add("为历史拨云");
        liaoBooksList.add("要把金针度与人");
        liaoBooksList.add("中国性研究");
        liaoBooksList.add("中国命研究");
        liaoBooksList.add("中国近代史新论");
        liaoBooksList.add("中国现代史正论");
        liaoBooksList.add("中国现代史定论");
        liaoBooksList.add("中国迷信新研");
        liaoBooksList.add("中国艺术新研");
        liaoBooksList.add("李敖笑傲江湖");
        liaoBooksList.add("挑战李敖");
        liaoBooksList.add("李敖秘密书房");
        liaoBooksList.add("李敖颠倒众生");
        liaoBooksList.add("李敖Talk秀");
        liaoBooksList.add("李敖大哥大");
        liaoBooksList.add("李敖有话说");
        liaoBooksList.add("笑敖年代");
        liaoBooksList.add("李敖语妙天下");
        liaoBooksList.add("笑傲六十年·有话说李敖");
        liaoBooksList.add("李敖演讲集");
        liaoBooksList.add("李敖政论综艺集");
        liaoBooksList.add("快意还乡——李敖神州文化之旅");
        liaoBooksList.add("李敖放电集");
        liaoBooksList.add("李敖发电集");
        liaoBooksList.add("李敖送电集");
        liaoBooksList.add("李敖来电集");
        liaoBooksList.add("李敖通电集");
        liaoBooksList.add("胡适研究");
        liaoBooksList.add("胡适评传");
        liaoBooksList.add("胡适与我");
        liaoBooksList.add("孙逸仙和中国西化医学");
        liaoBooksList.add("孙中山研究");
        liaoBooksList.add("李登辉的真面目");
        liaoBooksList.add("李登辉的假面具");
        liaoBooksList.add("郑南榕研究");
        liaoBooksList.add("陈水扁的真面目");
        liaoBooksList.add("李远哲的真面目");
        liaoBooksList.add("你不知道的彭明敏");
        liaoBooksList.add("为文学开窗");
        liaoBooksList.add("丑陋的中国人研究");
        liaoBooksList.add("闽变研究与文星讼案");
        liaoBooksList.add("大江大海骗了你");
        liaoBooksList.add("蒋介石研究");
        liaoBooksList.add("蒋介石研究续集");
        liaoBooksList.add("蒋介石研究三集");
        liaoBooksList.add("蒋介石研究四集");
        liaoBooksList.add("蒋介石研究五集");
        liaoBooksList.add("蒋介石研究六集");
        liaoBooksList.add("蒋介石的真面目");
        liaoBooksList.add("蒋介石评传");
        liaoBooksList.add("蒋经国研究");
        liaoBooksList.add("论定蒋经国");
        liaoBooksList.add("蒋家臭史");
        liaoBooksList.add("李敖论人物");
        liaoBooksList.add("国民党研究");
        liaoBooksList.add("国民党研究续集");
        liaoBooksList.add("国民党臭史");
        liaoBooksList.add("老贼臭史");
        liaoBooksList.add("给国民党难看");
        liaoBooksList.add("给外省人难看");
        liaoBooksList.add("冷眼看台湾");
        liaoBooksList.add("白眼看台湾");
        liaoBooksList.add("法眼看台湾");
        liaoBooksList.add("民进党研究");
        liaoBooksList.add("白色恐怖述奇");
        liaoBooksList.add("给台湾人难看");
        liaoBooksList.add("你不知道的二二八");
        liaoBooksList.add("另一面的二二八");
        liaoBooksList.add("洗你的脑，掐他脖子");
        liaoBooksList.add("李敖闹衙集");
        liaoBooksList.add("李敖刀笔集");
        liaoBooksList.add("李敖弄法集");
        liaoBooksList.add("李敖放刁集");
        liaoBooksList.add("李敖好讼集");
        liaoBooksList.add("你不知道的司法黑暗");
        liaoBooksList.add("笑傲五十年");
        liaoBooksList.add("第一流人的境界");
        liaoBooksList.add("李敖智慧书");
        liaoBooksList.add("恰似我的温柔");
        liaoBooksList.add("启发你的小故事");
        liaoBooksList.add("君子爱人以色");
        liaoBooksList.add("只爱一点点");
        liaoBooksList.add("从万宝囊到臭屎堆");
        liaoBooksList.add("我们没有明天");
        liaoBooksList.add("李敖生死书");
        liaoBooksList.add("我也李敖一下");
        liaoBooksList.add("四十二年，我的“恶邻”李敖大师");
        liaoBooksList.add("李敖登陆记");
        liaoBooksList.add("说李敖长，道李敖短");
        liaoBooksList.add("百家论李敖");
        liaoBooksList.add("敖友论李敖");
        liaoBooksList.add("李敖研究fashion文集");
        liaoBooksList.add("李戡专访与脸书合集");
        return liaoBooksList;
    }


    //获取李敖163本书的书本名Set集合。(为了测试无所谓效率。)
    public static Set<String> newLiaoBookNamesSet() {
        Set<String> set = new HashSet<>();
        set.addAll(newLiaoBookNamesList());
        return set;
    }

    //获取李敖大全集目录下所有文件List
    public static List<File> getLADQJBookFiles() {
        File liaoBooksRootDir = getLiAoBooksRootDir();
        File[] subDirArr = liaoBooksRootDir.listFiles();
        List<File> allBookFiles = new ArrayList<>();
        for (File subDir : subDirArr) {
            if (subDir.isDirectory()) {
                allBookFiles.addAll(Arrays.asList(subDir.listFiles()));
            }
        }
        return allBookFiles;
    }

    public static File getLiAoBooksRootDir() {
        File liaoBooksRootDir = new File(
                ProjectFileUtils.getProjectRootDir(),
                "temp/李敖大全集5.0"
        );
        return liaoBooksRootDir;
    }

    public static File getLiAoBooksSortedRootDir() {
        File liaoBooksRootDir = new File(
                ProjectFileUtils.getProjectRootDir(),
                "temp/李敖大全集5.0(书名按顺序排列)"
        );
        return liaoBooksRootDir;
    }



}