package lu.pata.ambre.cli;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ClidemoPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("ambre:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE)
        );
    }
}
