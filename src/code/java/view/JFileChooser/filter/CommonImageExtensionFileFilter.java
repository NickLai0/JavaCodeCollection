package code.java.view.JFileChooser.filter;

public class CommonImageExtensionFileFilter extends BaseExtensionFileFilter {
    public CommonImageExtensionFileFilter() {
        //webp在JLabel上显示不了
        addExtensions("jpg", "jpeg", "gif", "png"/*,"webp"*/);
        setDescription("图片文件(*.jpg,*.jpeg,*.gif,*.png)");
    }
}
