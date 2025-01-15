package code.java.data;

public class ImageHolder {
    // 封装图片的ID
    private int id;
    // 封装图片的图片名字
    private String name;

    public ImageHolder() {
    }

    public ImageHolder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // id的setter和getter方法
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    // name的setter和getter方法
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // 重写toString方法，返回图片名
    public String toString() {
        return name;
    }
}