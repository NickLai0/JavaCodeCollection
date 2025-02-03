package code.java.view.container.frame.Jframe;

public class SourceToDestSourceDeletable extends SourceToDestWithCheck {

    @Override
    protected String getTextFieldLabel() {
        return "复制后删来源";
    }

    @Override
    protected String getCheckBoxLabel() {
        return "目标文件前缀";
    }

}
