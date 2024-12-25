package code.java.view.JFileChooser.filter;

public class CommonImageExtensionFileFilter extends BaseExtensionFileFilter {
    public CommonImageExtensionFileFilter() {
        addExtensions("jpg", "jpeg", "gif", "png");
        setDescription("图片文件(*.jpg,*.jpeg,*.gif,*.png)");
    }
}
