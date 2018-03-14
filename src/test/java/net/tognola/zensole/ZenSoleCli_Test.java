package net.tognola.zensole;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class ZenSoleCli_Test {

    @Test
    public void fieldValuePromptShouldTrimResult() {
        InputStream fakeConsole = new ByteArrayInputStream(" hello \n".getBytes()); // entering menu item  5, -1, 0
        ZenSoleCli cli = new ZenSoleCli(fakeConsole);
        String fieldValue = cli.promptForAndReturnFieldValue("");
        assertThat(fieldValue).isEqualTo("hello");
    }



    @Test

    public void menuPromptShouldRepeatOnInvalidEntry() {
        InputStream fakeConsole = new ByteArrayInputStream("5\n-1\n0\n".getBytes()); // entering menu item  5, -1, 0
        ZenSoleCli cli = new ZenSoleCli(fakeConsole);
        String[] menu = new String[]{"A"};
        String selected = cli.promptMenuAndReturnSelectedValue("", menu);
        assertThat(selected).isNotEmpty();
    }



    @Test
    public void menuPromptShouldReturnMenuValue() {
        InputStream fakeConsole = new ByteArrayInputStream("1\n".getBytes());
        ZenSoleCli main = new ZenSoleCli(fakeConsole);
        String[] menu = new String[]{"A", "B"};
        String selected = main.promptMenuAndReturnSelectedValue("", menu);
        assertThat(selected).isEqualTo("B");
    }



    @Test
    public void welcomeShouldContainProductName() {
        ZenSoleCli main = new ZenSoleCli(System.in);
        assertThat(main.welcome()).contains("ZenSole");
    }
}
