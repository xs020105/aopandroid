package net.oschina.app.v2.base;

import android.os.Environment;

public class Constants {
    /*
    * 定义常数类
    * */
    public static final String INTENT_ACTION_LOGOUT = "com.tonlin.osc.happy.LOGOUT";
    public static final String INTENT_ACTION_COMMENT_CHANGED = "com.tonlin.osc.happy.COMMENT_CHANGED";
    public static final String INTENT_ACTION_NOTICE = "com.tonlin.osc.happy.action.APPWIDGET_UPDATE";

    public final static String BASE_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/HappyOSC/";

    public final static String CACHE_DIR = BASE_DIR + ".cache/";

    public final static String IMAGE_SAVE_PAHT = BASE_DIR + "download_images";

    public static final String WEICHAT_APPID = "wx7aac2075450f71a2";
    public static final String WEICHAT_SECRET = "0101b0595ffe2042c214420fac358abc";

    public static final String QQ_APPID = "100424468";
    public static final String QQ_APPKEY = "c7394704798a158208a74ab60104f0ba";

    public static final long CACHE_EXPIRE_OND_DAY = 3600000;//一天
}
//interface Animal {
//    public String toString();
//
//}
//
//class Cat implements Animal, Comparable<Animal> {
//    public String name;
//
//    public Cat() {
//        super();
//        name = "猫";
//
//    }
//
//    public String toString() {
//        return name;
//    }
//
//    @Override
//    public int compareTo(Animal o) {
//        if (this.toString().compareTo(o.toString()) == -1) {
//            return -1;
//        } else if (this.toString().compareTo(o.toString()) == 0) {
//            return 0;
//        } else
//            return 1;
//    }
//}
//
//class Dog implements Animal, Comparable<Animal> {
//    public String name;
//
//    public Dog() {
//        super();
//        name = "狗";
//    }
//
//    public String toString() {
//        return name;
//    }
//
//    @Override
//    public int compareTo(Animal o) {
//        if (this.toString().compareTo(o.toString()) == -1) {
//            return -1;
//        } else if (this.toString().compareTo(o.toString()) == 0) {
//            return 0;
//        } else
//            return 1;
//    }
//}
//
//public class am {
//    public static void main(String[] args) {
//
//        // 创建一个动物集合，插入动物园中有的几种动物
//        Collection<Animal> col = new ArrayList<Animal>();
//        col.add(new Cat());
//        col.add(new Dog());
//        col.add(new Cat());
//        // 一次性输出内容
//        System.out.println(Arrays.toString(col.toArray()));
//        // 使用iterator遍历集合中所有内容
//        // 并将集合内容转存储于一个数组内
//        Iterator<Animal> it = col.iterator();
//        int n = 0;
//        Animal[] an = new Animal[3];
//        while (it.hasNext()) {
//            Animal temp = (Animal) it.next();
//            System.out.println("使用iterator遍历集合中所有内容:" + temp);
//            an[n++] = temp;
//        }
//        // 并在数组中进行排序
//        Arrays.sort(an);
//        System.out.println(Arrays.toString(an));
//
//    }
//}