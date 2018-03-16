package net.tognola.zensole.gui;

import org.junit.Test;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

public class ZenSoleCli_Test {

    @Test(timeout = 5000)
    public void loopShouldExitWhenUserRequestsExit() throws IOException {
        ZenSoleCli cli = new ZenSoleCli("1", "0", "asdf", "0");
        cli.loop();
    }



    @Test
    public void fieldValuePromptShouldTrimResult() {
        ZenSoleCli cli = new ZenSoleCli(" hello ");
        String fieldValue = cli.promptForAndReturnFieldValue("");
        assertThat(fieldValue).isEqualTo("hello");
    }



    @Test
    public void menuPromptShouldRepeatOnInvalidEntry() {
        ZenSoleCli cli = new ZenSoleCli("5", "-1", "a", "0");
        String[] menu = new String[]{"A"};
        String selected = cli.promptMenuAndReturnSelectedValue("", menu);
        assertThat(selected).isNotEmpty();
    }



    @Test
    public void menuPromptShouldReturnMenuValue() {
        ZenSoleCli main = new ZenSoleCli("1");
        String[] menu = new String[]{"A", "B"};
        String selected = main.promptMenuAndReturnSelectedValue("", menu);
        assertThat(selected).isEqualTo("B");
    }



    @Test
    public void welcomeMessageShouldContainProductName() {
        assertThat(ZenSoleCli.WELCOME).contains("ZenSole");
    }
}
