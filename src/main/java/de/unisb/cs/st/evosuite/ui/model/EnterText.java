package de.unisb.cs.st.evosuite.ui.model;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.uispec4j.TextBox;
import org.uispec4j.UIComponent;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.primitives.PrimitivePool;
import de.unisb.cs.st.evosuite.ui.run.AbstractUIEnvironment;
import de.unisb.cs.st.evosuite.utils.Randomness;

public class EnterText extends UIAction<TextBox> {
    public static enum Mode {
        EnterText, AppendText, InsertText
    }

    private static final long serialVersionUID = 1L;

    protected static PrimitivePool primitive_pool = PrimitivePool.getInstance();

    private String text;
    private Mode mode;
    private double posRand;

    @Override
    public void executeOn(AbstractUIEnvironment env, final TextBox textBox) {
        this.checkTarget(textBox);

        this.run(env, new Runnable() {
            @Override
            public void run() {
                switch (EnterText.this.mode) {
                    case EnterText:
                        textBox.setText(EnterText.this.text);
                        break;

                    case AppendText:
                        textBox.appendText(EnterText.this.text);
                        break;

                    case InsertText:
                        textBox.insertText(EnterText.this.text,
                                (int) (EnterText.this.posRand * textBox
                                        .getText().length()));
                        break;

                    default:
                        throw new IllegalStateException(
                                "Unknown EnterText.Mode " + EnterText.this.mode);
                }
            }
        });
    }

    @Override
    public void randomize() {
        if (Randomness.nextDouble() >= Properties.PRIMITIVE_POOL)
            this.text = Randomness.nextString(Randomness
                    .nextInt(Properties.STRING_LENGTH));
        else
            this.text = primitive_pool.getRandomString();

        this.mode = Randomness.choice(Mode.values());
        this.posRand = (this.mode == Mode.InsertText) ? Randomness.nextDouble()
                : 0;
    }

    @Override
    public String toString() {
        String posStr = (this.mode == Mode.InsertText) ? String.format(
                ", %.4f", this.posRand) : "";
        return String.format("%s[\"%s\"%s]", this.mode.toString(),
                StringEscapeUtils.escapeJava(this.text), posStr);
    }

    @Override
    public String graphVizString() {
        return "EnterText";
    }

    public static void addActions(List<UIAction<? extends UIComponent>> result) {
        result.add(new EnterText());
    }

}