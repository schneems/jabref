package org.jabref.gui.util;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Text;

import org.jabref.gui.search.TextFlowEqualityHelper;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jpf
 * @created 11/21/17
 */
public class TooltipTextUtilTest {

    @Test
    public void testCreateText() {
        String testText = "this is a test text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.NORMAL);
        Assert.assertEquals("Regular", text.getFont().getStyle());
        Assert.assertEquals(testText, text.getText());
    }

    @Test
    public void testCreateTextBold() {
        String testText = "this is a test text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.BOLD);
        Assert.assertEquals("tooltip-text-bold", text.getStyleClass().toString());
        Assert.assertEquals(testText, text.getText());
    }

    @Test
    public void testCreateTextItalic() {
        String testText = "this is a test text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.ITALIC);
        Assert.assertEquals("tooltip-text-italic", text.getStyleClass().toString());
        Assert.assertEquals(testText, text.getText());
    }

    @Test
    public void testCreateTextMonospaced() {
        String testText = "this is a test text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.MONOSPACED);
        Assert.assertEquals("tooltip-text-monospaced", text.getStyleClass().toString());
        Assert.assertEquals(testText, text.getText());
    }

    @Test
    public void testTextToHTMLStringBold() {
        String testText = "this is a test text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.BOLD);
        String htmlString = TooltipTextUtil.textToHTMLString(text);
        Assert.assertEquals("<b>" + testText + "</b>", htmlString);
    }

    @Test
    public void testTextToHTMLStringItalic() {
        String testText = "this is a test text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.ITALIC);
        String htmlString = TooltipTextUtil.textToHTMLString(text);
        Assert.assertEquals("<i>" + testText + "</i>", htmlString);
    }

    @Test
    public void testTextToHTMLStringMonospaced() {
        String testText = "this is a test text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.MONOSPACED);
        String htmlString = TooltipTextUtil.textToHTMLString(text);
        Assert.assertEquals("<kbd>" + testText + "</kbd>", htmlString);
    }

    @Test
    public void testTextToHTMLStringMonospacedBold() {
        String testText = "this is a test text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.MONOSPACED);
        text.getStyleClass().add("tooltip-text-bold");
        String htmlString = TooltipTextUtil.textToHTMLString(text);
        Assert.assertEquals("<b><kbd>" + testText + "</kbd></b>", htmlString);
    }

    @Test
    public void testTextToHTMLStringWithLinebreaks() {
        String testText = "this\nis a\ntest text";
        Text text = TooltipTextUtil.createText(testText, TooltipTextUtil.TextType.NORMAL);
        String htmlString = TooltipTextUtil.textToHTMLString(text);
        Assert.assertEquals("this<br>is a<br>test text", htmlString);
    }

    @Test
    public void testFormatToTextsNoReplacements() {
        List<Text> expectedTextList = new ArrayList<>();
        expectedTextList.add(TooltipTextUtil.createText("This search contains entries in which any field contains the regular expression "));
        String test = "This search contains entries in which any field contains the regular expression ";
        List<Text> textList = TooltipTextUtil.formatToTexts(test);
        Assert.assertTrue(TextFlowEqualityHelper.checkIfTextsEqualsExpectedTexts(expectedTextList, textList));
    }


    @Test
    public void testFormatToTextsEnd() {
        List<Text> expectedTextList = new ArrayList<>();
        expectedTextList.add(TooltipTextUtil.createText("This search contains entries in which any field contains the regular expression "));
        expectedTextList.add(TooltipTextUtil.createText("replacing text", TooltipTextUtil.TextType.BOLD));
        String test = "This search contains entries in which any field contains the regular expression <b>%0</b>";
        List<Text> textList = TooltipTextUtil.formatToTexts(test, new TooltipTextUtil.TextReplacement("<b>%0</b>", "replacing text", TooltipTextUtil.TextType.BOLD));
        Assert.assertTrue(TextFlowEqualityHelper.checkIfTextsEqualsExpectedTexts(expectedTextList, textList));
    }

    @Test
    public void testFormatToTextsBegin() {
        List<Text> expectedTextList = new ArrayList<>();
        expectedTextList.add(TooltipTextUtil.createText("replacing text", TooltipTextUtil.TextType.BOLD));
        expectedTextList.add(TooltipTextUtil.createText(" This search contains entries in which any field contains the regular expression"));
        String test = "<b>%0</b> This search contains entries in which any field contains the regular expression";
        List<Text> textList = TooltipTextUtil.formatToTexts(test, new TooltipTextUtil.TextReplacement("<b>%0</b>", "replacing text", TooltipTextUtil.TextType.BOLD));
        Assert.assertTrue(TextFlowEqualityHelper.checkIfTextsEqualsExpectedTexts(expectedTextList, textList));
    }

    @Test
    public void testFormatToTextsMiddle() {
        List<Text> expectedTextList = new ArrayList<>();
        expectedTextList.add(TooltipTextUtil.createText("This search contains entries "));
        expectedTextList.add(TooltipTextUtil.createText("replacing text", TooltipTextUtil.TextType.BOLD));
        expectedTextList.add(TooltipTextUtil.createText(" in which any field contains the regular expression"));
        String test = "This search contains entries <b>%0</b> in which any field contains the regular expression";
        List<Text> textList = TooltipTextUtil.formatToTexts(test, new TooltipTextUtil.TextReplacement("<b>%0</b>", "replacing text", TooltipTextUtil.TextType.BOLD));
        Assert.assertTrue(TextFlowEqualityHelper.checkIfTextsEqualsExpectedTexts(expectedTextList, textList));
    }
}
