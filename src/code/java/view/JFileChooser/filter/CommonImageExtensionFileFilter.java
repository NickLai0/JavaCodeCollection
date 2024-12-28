package code.java.view.JFileChooser.filter;

import code.java.utils.ImageUtils;

public class CommonImageExtensionFileFilter extends BaseExtensionFileFilter {

    public CommonImageExtensionFileFilter() {
        addExtensions(ImageUtils.imageSuffixArr);
        StringBuffer sb = new StringBuffer();
        sb.append("图片文件(");
        for (String suffix : ImageUtils.imageSuffixArr) {
            sb.append("*.").append(suffix);
        }
        sb.append(")");
        setDescription(sb.toString());
    }

}
