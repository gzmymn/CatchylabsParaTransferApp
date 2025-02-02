package com.catchylabs.step;

import com.catchylabs.base.BaseTest;
import com.catchylabs.model.ElementInfoo;
import com.catchylabs.model.StaleElementHelper;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.json.JSONObject;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseSteps extends BaseTest {
    static HashMap<String, Object> hashMap = new HashMap<String, Object>();
    static HashMap<String, String> headers = new HashMap<>();
    static HashMap<String, String> params = new HashMap<>();
    private static HashMap<String, String> dataList = new HashMap<>();

    @BeforeScenario
    @Override
    public void setUp() throws IOException {
        super.setUp();
    }

    @AfterScenario
    @Override
    public void tearDown() {
        super.tearDown();
    }

    public void storeData(String key, String value) {
        dataList.put(key, value);

    }


    public static final int DEFAULT_MAX_ITERATION_COUNT = 150;
    public static final int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;

    private static String sSavedAttribute;
    private static String sSavedText;

    @SuppressWarnings("unused")
    private static String sStoragedStatus;
    @SuppressWarnings("unused")
    private static String sStoragedTotalValue;
    @SuppressWarnings("unused")
    private static String sStoragedDescription;

    WebElement findElement(String key) {
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        jsExecutor.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    List<WebElement> findElements(String key) {
        return driver.findElements(getElementInfoToBy(findElementInfoByKey(key)));
    }

    public By getElementInfoToBy(ElementInfoo elementInfo) {
        By by = null;
        if (elementInfo.getType().equals("css")) {
            by = By.cssSelector(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("name"))) {
            by = By.name(elementInfo.getValue());
        } else if (elementInfo.getType().equals("id")) {
            by = By.id(elementInfo.getValue());
        } else if (elementInfo.getType().equals("xpath")) {
            by = By.xpath(elementInfo.getValue());
        } else if (elementInfo.getType().equals("linkText")) {
            by = By.linkText(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("partialLinkText"))) {
            by = By.partialLinkText(elementInfo.getValue());
        }
        return by;
    }

    private void clickElement(WebElement element) {
        element.click();
    }

    @SuppressWarnings("unused")
    private void clickElementBy(String key) {
        findElement(key).click();
    }

    private void hoverElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    @SuppressWarnings("unused")
    private void hoverElementBy(String key) {
        WebElement webElement = findElement(key);
        actions.moveToElement(webElement).build().perform();
    }

    @Step({"Send ESCAPE key to element <key>",
            "<key> elementine ESCAPE keyi yolla"})
    public void sendKeyESC(String key) {
        findElement(key).sendKeys(Keys.ESCAPE);
        logger.info(String.format("%s elementine ESCAPE keyi yollandi.", key));
    }


    @SuppressWarnings("unused")
    private boolean isDisplayedBy(By by) {
        return driver.findElement(by).isDisplayed();
    }

    private String getPageSource() {
        return driver.switchTo().alert().getText();
    }


    public String randomString(int stringLength) {
        Random random = new Random();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUWVXYZabcdefghijklmnopqrstuwvxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringLength; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }

    public WebElement findElementWithKey(String key) {
        return findElement(key);
    }

    @Step("Print page source")
    public void printPageSource() {
        logger.info(getPageSource());
    }

    public void javascriptClicker2(WebElement element) {
        jsExecutor.executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                + "arguments[0].dispatchEvent(evt);", element);
    }

    public void javascriptClicker(WebElement element) {
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    /**
     * A static wait method for a specified period of seconds. Logs can be shut off.
     */
    public void waitForSeconds(long duration, boolean logRequested) {
        if (logRequested)
            logger.info(String.format("Waiting for %d seconds...", duration));

        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            logger.error("waitForSeconds method failure!", e);
        }

        if (logRequested)
            logger.info(String.format("Waited for %d seconds.", duration));
    }

    /**
     * waitForSeconds method that logs are enabled by default.
     */
    @Step({"Wait <value> seconds",
            "<int> saniye bekle"})
    public void waitForSeconds(long duration) {
        waitForSeconds(duration, true);
    }

    /**
     * A static wait method for a specified period of milliseconds. Logs can be shut
     * off.
     */
    public void waitForMilliseconds(long duration, boolean logRequested) {
        if (logRequested)
            logger.info(String.format("Waiting for %d milliseconds...", duration));

        try {
            TimeUnit.MILLISECONDS.sleep(duration);
        } catch (InterruptedException e) {
            logger.error("waitForMilliseconds method failure!", e);
        }

        if (logRequested)
            logger.info(String.format("Waited for %d milliseconds.", duration));
    }

    /**
     * waitForMilliseconds method that logs are enabled by default.
     */
    @Step({"Wait <value> milliseconds",
            "<long> milisaniye bekle"})
    public void waitForMilliseconds(long duration) {
        waitForMilliseconds(duration, true);
    }

    @Step({"Wait for element then click <key>",
            "Elementi bekle ve sonra tikla <key>"})
    public void checkElementExistsThenClick(String key) {
        getElementWithKeyIfExists(key);
        clickElement(key);
        logger.info(String.format("%s elementine tiklandi.", key));
    }

    @Step({"Click to element <key>",
            "Elementine tikla <key>"})
    public void clickElement(String key) {
        if (!key.isEmpty()) {
            clickElement(scrollToElementToBeVisible(key));
            logger.info(String.format("%s elementine tiklandi.", key));
        }
    }

    @Step({"Check if element <key> exists",
            "Element var mi kontrol et <key>"})
    public void getElementWithKeyIfExists(String key) {
        ElementInfoo elementInfo = findElementInfoByKey(key);
        By by = getElementInfoToBy(elementInfo);
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() > 0) {
                logger.info(String.format("%s elementi bulundu.", key));
                return;
            }
            loopCount++;
            waitForMilliseconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assertions.fail(String.format("Element: '%s' doesn't exist", key));
    }

    @Step({"Go to <url> address",
            "<url> adresine git"})
    public void goToUrl(String url) {
        logger.info(String.format("%s adresi goruldu.", url));
        driver.get(url);
        logger.info(String.format("%s adresine gidiliyor.", url));
    }

    @Step({"Write value <text> to element <key>",
            "<text> textini <key> elemente yaz"})
    public void ssendKeys(String text, String key) {
        if (!key.equals("")) {
            findElement(key).sendKeys(text);
            logger.info(String.format("%s elementine %s texti yazildi.", key, text));
        }
    }

    @Step({"Check if element <key> has attribute <attribute>",
            "<key> elementinin <attribute> niteligine sahip oldugunu dogrula"})
    public void checkElementAttributeExists(String key, String attribute) {
        WebElement element = findElement(key);
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (element.getAttribute(attribute) != null) {
                logger.info(String.format("%s elementi %s niteligine sahip.", key, attribute));
                return;
            }
            loopCount++;
            waitForMilliseconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assertions.fail(String.format("Element Doesnt't have the attribute: '%s'", attribute));
    }

    @Step({"Clear text of element <key>",
            "<key> elementinin text alanini temizle"})
    public void clearInputArea(String key) {
        findElement(key).clear();
    }

    @Step({"Clear text of element <key> with BACKSPACE",
            "<key> elementinin text alanini BACKSPACE ile temizle"})
    public void clearInputAreaWithBackspace(String key) {
        WebElement textbox = findElement(key);
        int lengthOfTextbox = textbox.getText().length();
        textbox.sendKeys(Keys.CONTROL, Keys.ARROW_RIGHT);
        for (int i = 0; i < lengthOfTextbox; i++) {
            textbox.sendKeys(Keys.BACK_SPACE);
        }
    }

    @Step({"Check if element <key> contains text <expectedText>",
            "<key> elementi <text> degerini iceriyor mu kontrol et"})
    public void checkElementContainsText(String key, String expectedText) {

        Boolean containsText = findElement(key).getText().contains(expectedText);
        assertTrue(containsText, "Expected text is not contained");
        logger.info(String.format("%s elementi %s degerini iceriyor.", key, expectedText));
    }

    @Step({"Check if element <key> does not contains text <expectedText>",
            "<key> elementi <text> degerini icermiyor mu kontrol et"})
    public void checkElementNotContainsText(String key, String expectedText) {

        Boolean containsText = findElement(key).getText().contains(expectedText);
        assertFalse(containsText, "Expected text is contained");
        logger.info(String.format("%s elementi %s degerini icermiyor.", key, expectedText));
    }

    @Step({"Write random value to element <key>",
            "<key> elementine random deger yaz"})
    public void writeRandomValueToElement(String key) {
        findElement(key).sendKeys(randomString(15));
    }

    @Step({"Write random value to element <key> starting with <text>",
            "<key> elementine <text> degeri ile baslayan random deger yaz"})
    public void writeRandomValueToElement(String key, String startingText) {
        String randomText = startingText + randomString(15);
        findElement(key).sendKeys(randomText);
    }

    @Step({"Print <key> element text",
            "<key> elementinin text degerini yazdir"})
    public void printElementText(String key) {
        logger.info(findElement(key).getText());
    }

    @Step({"Write value <string> to element <key> with focus",
            "<string> degerini <key> elementine focus ile yaz"})
    public void sendKeysWithFocus(String text, String key) {
        actions.moveToElement(findElement(key));
        actions.click();
        actions.sendKeys(text);
        actions.build().perform();
        logger.info(String.format("%s elementine %s degeri focus ile yazildi.", key, text));

    }

    @Step({"Open new tab",
            "Yeni sekme ac"})
    public void chromeOpenNewTab() {
        jsExecutor.executeScript("window.open()");
    }

    @Step({"Focus on tab number <number>",
            "<number> numarali sekmeye odaklan"}) // Starting from 1
    public void chromeFocusTabWithNumber(int number) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(number - 1));
    }

    @Step("popupa gec")
    public void switchTo() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Step({"Focus on last tab",
            "Son sekmeye odaklan"})
    public void chromeFocusLastTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    @Step({"Focus on frame with <key>",
            "Frame'e odaklan <key>"})
    public void chromeFocusFrameWithNumber(String key) {
        WebElement webElement = findElement(key);
        driver.switchTo().frame(webElement);
    }

    @Step("<key> comboboxtan <value> degerini sec")
    public void selectDropdown(String key, String text) {
        WebElement e = findElement(key);
        javascriptClicker(e);
        Select dropdown = new Select(e);
        dropdown.selectByVisibleText(text);
        logger.info(String.format("%s dropdownindan %s degeri secildi", key, text));
    }

    public void randomPick(String key) {
        List<WebElement> elements = findElements(key);
        Random random = new Random();
        int index = random.nextInt(elements.size());
        elements.get(index).click();
    }

    // Javascript driverin baslatilmasi
    private JavascriptExecutor getJSExecutor() {
        return jsExecutor;
    }

    // Javascript scriptlerinin calismasi icin gerekli fonksiyon
    private Object executeJS(String script, boolean wait) {
        return wait ? getJSExecutor().executeScript(script, "") : getJSExecutor().executeAsyncScript(script, "");
    }

    // Belirli bir locasyona sayfanin kaydirilmasi
    private void scrollTo(int x, int y) {
        String script = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(script, true);
    }

    public void sayfasonu() {
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        waitForMilliseconds(500);
    }

    public void sayfabasi() {
        jsExecutor.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
        waitForMilliseconds(500);
    }

    // Belirli bir elementin oldugu locasyona websayfasinin kaydirilmasi
    public WebElement scrollToElementToBeVisible(String key) {
        ElementInfoo elementInfo = findElementInfoByKey(key);
        WebElement webElement = driver.findElement(getElementInfoToBy(elementInfo));
        if (webElement != null) {
            scrollTo(webElement.getLocation().getX(), webElement.getLocation().getY() - 100);
        }
        return webElement;
    }

    @Step({"Delete all the text in the element <key>",
            "<key> elementindeki tum texti sil"})
    public void deleteText(String key) {
        WebElement element = findElement(key);

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac"))
            element.sendKeys(Keys.COMMAND + "a");
        else
            element.sendKeys(Keys.CONTROL + "a");

        findElement(key).sendKeys(Keys.BACK_SPACE);
        logger.info(String.format("%s <key> elementindeki tum text silindi", key));
    }

    @Step({"Delete all the text in the element <key> - js",
            "<key> elementindeki tum texti sil - js"})
    public void deleteTextJS(String key) {
        WebElement element = findElement(key);
        jsExecutor.executeScript("arguments[0].setSelectionRange(0, arguments[0].value.length);", element);
        jsExecutor.executeScript("arguments[0].value = '';", element);
        logger.info(String.format("%s <key> elementindeki tum text silindi", key));
    }

    @Step({"Sayfanin Ustunde Bulunan <key> alanina gelindi"})
    public void scrollToPageUp(String key) {
        sayfabasi();
        logger.info(String.format("%s sayfanin en ustundeki elementinin oldugu alana gelindi", key));
    }

    @Step({"<key> alanina js ile kaydir"})
    public void scrollToElementWithJs(String key) {
        ElementInfoo elementInfo = findElementInfoByKey(key);
        WebElement element = driver.findElement(getElementInfoToBy(elementInfo));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Zaman bilgisinin alinmasi
    public Long getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (timestamp.getTime());
    }

    @Step("<key> elementine javascript ile tikla")
    public void clickToElementWithJavaScript(String key) {
        WebElement element = findElement(key);
        javascriptClicker(element);
        logger.info(String.format("%s elementine javascript ile tiklandi", key));
    }

    // Belirli bir key degerinin oldugu locasyona websayfasinin kaydirilmasi
    public void scrollToElementToBeVisiblest(WebElement webElement) {
        if (webElement != null) {
            scrollTo(webElement.getLocation().getX(), webElement.getLocation().getY() - 100);
        }
    }

    @Step("<key> alanini javascript ile temizle")
    public void clearWithJS(String key) {
        WebElement element = findElement(key);
        jsExecutor.executeScript("arguments[0].value ='';", element);
    }

    @Step("<key> elementleri arasindan <text> kayitli degiskene tikla")
    public void clickParticularElement(String key, String text) {
        By aicard = By.xpath(key + text + "')]");
        driver.findElement(aicard).click();
    }

    @Step("<key> menu listesinden rasgele sec")
    public void chooseRandomElementFromList(String key) {
        for (int i = 0; i < 3; i++)
            randomPick(key);
    }

    @Step("<key> elementinin textiyle, <element> elementinin textini karsilastir")
    public void compareTwoText(String key, String element) {
        String firstText = findElement(key).getText();
        logger.info(String.format("%s ilk texti bulundu", firstText));
        String secondText = findElement(element).getText();
        logger.info(String.format("%s ikinci texti bulundu", secondText));
        Assertions.assertEquals(firstText, secondText, "text'ler esit degildir.");
        logger.info("Iki text karsilastirildi.");
    }

    @Step("<key> elementine <text> degerini js ile yaz")
    public void writeToKeyWithJavaScript(String key, String text) {
        WebElement element = findElement(key);
        jsExecutor.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", element, text);
        logger.info(String.format("%s elementine %s degeri js ile yazildi.", key, text));
    }

    // Bugunun Tarihinin secilmesi
    public String chooseDate() {
        Calendar now = Calendar.getInstance();
        int tarih = now.get(Calendar.DATE);
        return String.valueOf(tarih);
    }

    @Step({"sekme kapat"})
    public void sekmeKapat() {
        driver.close();
    }

    @Step({"Check if element <key> exists then click",
            "Element <key> varsa tikla"})
    public void getElementWithKeyClickIfExists(String key) {

        List<WebElement> liste = findElements(key);
        if (liste.size() > 0) {
            logger.info("Element bulundu");
            findElementWithKey(key).click();
            logger.info("Elemente tiklandi");
        } else
            logger.info("Element bulunamadi");
    }

    @Step({"Open <key> element on the new TAB",
            "<key> elementini yeni sekmede ac"})
    public void openElementOnNewTAB(String key) {
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.LEFT_CONTROL)
                .click(findElementWithKey(key))
                .keyUp(Keys.LEFT_CONTROL)
                .build()
                .perform();
        logger.info(String.format("%s elementi yeni sekmede acildi", key));
    }

    @Step({"Check <key> element is disabled",
            "<key> elementi aktif degil mi kontrol et"})
    public void checkElementIsDisabled(String key) {
        if (findElementWithKey(key).isEnabled()) {
            Assertions.fail(String.format("%s elementi aktif", key));
        }
        logger.info(String.format("%s elementi aktif degil", key));
    }

    @Step({"Check <key> element is enabled",
            "<key> elementi aktif mi kontrol et"})
    public void checkElementIsEnabled(String key) {
        if (!findElementWithKey(key).isEnabled()) {
            Assertions.fail(String.format("%s elementi aktif degil", key));
        }
        logger.info(String.format("%s elementi aktif", key));
    }

    @Step("Navigate to refresh")
    public void navigateToRefresh() {
        driver.navigate().refresh();
    }


    @Step("<message> mesajini bastir - dbgInfo: <dbgInfo>")
    public void printInfo(String message, String dbgInfo) {
        boolean printDbgInfo = (dbgInfo.toLowerCase().equals("true")) ? true : false;
        if (!printDbgInfo) {
            return;
        }
        logger.info(String.format("%s%n", message));
    }

    @Step("<key> elementlerinde <attr> ozelliginin <value> degerini <containment> durumu dogrulanir")
    public void verifyValueNotExistInElementList(String key, String attr, String value, String containment) {
        List<WebElement> valueExistingElementList = null;
        if (attr.equalsIgnoreCase("text")) {
            valueExistingElementList = findElements(key).stream().filter(element -> {
                return element.getText().contains(value);
            }).toList();
        } else {
            valueExistingElementList = findElements(key).stream().filter(element -> {
                return element.getAttribute(attr).contains(value);
            }).toList();
        }

        final int FOUND_SIZE = valueExistingElementList.size();
        switch (containment.toLowerCase()) {
            case "icerme":
                if (FOUND_SIZE <= 0) {
                    Assertions.fail(String.format("%s degeri aratilan elementlerin arasinda bulunmadi !", value));
                }
                logger.info(String.format("Aratilan %s degeri %d elementte bulundu.", value, FOUND_SIZE));
                break;
            case "icermeme":
                if (FOUND_SIZE > 0) {
                    Assertions.fail(String.format("Aratilan %s degeri %d elementte bulundu !", value, FOUND_SIZE));
                }
                logger.info(String.format("%s degeri aratilan elementlerin arasinda bulunmadi.", value));
                break;
            default:
                Assertions.fail(String.format("Hatali icerme durumu parametresi: %s", containment));
        }
    }

    @Step({"Scroll to the bottom of the page dynamically - max scroll count: <maxIteration> - scroll waiting interval (ms): <milliseconds>",
            "Dinamik olarak sayfa sonuna in - max scroll sayisi: <maxIteration> - scroll bekleme araligi (ms): <milliseconds>"})
    public void scrollToEndOfPage(int maxIterationCount, long duration) {
        int iteration = 0;
        if (maxIterationCount == 0)
            throw new IllegalArgumentException("Max iterasyon 0 girilemez!");
        Object jsHeightObj = executeJS("return document.body.scrollHeight", true);
        int heightOld = -1, heightNew = Integer.parseInt(jsHeightObj.toString());
        do {
            logger.info(String.format("Scroll islemi gerceklestiriliyor, document.body.scrollHeight: %s", heightNew));
            executeJS("window.scrollTo(0, document.body.scrollHeight);", true);
            waitForMilliseconds(duration);
            jsHeightObj = executeJS("return document.body.scrollHeight", true);
            heightOld = heightNew;
            heightNew = Integer.parseInt(jsHeightObj.toString());
            ++iteration;
            if (iteration == maxIterationCount) {
                logger.info("Max scroll sayisina ulasildi!");
                break;
            }
        } while (heightNew > heightOld);
        logger.info("Scroll tamamlandi.");
    }

    @Step("<outer> textindeki <regex> ile isaretli bolumu <inner> texti ile degistir ve bastir")
    public void printFormattedStrings(String outer, String regex, String inner) {
        if (regex.isEmpty()) {
            outer += inner;
        } else {
            outer = outer.replaceAll(regex, inner);
        }
        logger.info(outer);
    }

    public Object executeAndReturnNonStaleElement(String key, StaleElementHelper helper) {
        WebElement element = null;

        ElementInfoo elementInfo = findElementInfoByKey(key);
        By by = getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        final int elementStaleCheckCount = 5;
        for (int attempt = 1; true; ++attempt) {
            try {
                element = webDriverWait
                        .until(ExpectedConditions.presenceOfElementLocated(by));
                logger.info(String.format("Verifying that element '%s (%s)' is not stale...", key, by.toString()));
                return helper.execute(element);
            } catch (StaleElementReferenceException ex) {
                if (attempt == elementStaleCheckCount) {
                    throw new StaleElementReferenceException(String.format("Element '%s (%s)' was stale after %d retries!",
                            key, by.toString(), elementStaleCheckCount));
                }
                logger.warn(String.format("StaleElementReferenceException has been occurred! Retrying (%d/5)...", attempt));
            }
        }
    }

    @Step({"Verify <key> element or its childs contain <text> text",
            "<key> elementi veya child elementlerinin <text> metnini icerdigini dogrula"})
    public void checkIfElementContainsText(String key, String text) {

        StaleElementHelper helperForElement = (WebElement e) -> {
            return e;
        };
        StaleElementHelper helperForText = (WebElement e) -> {
            jsExecutor.executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})", e);
            return e.getText();
        };

        WebElement element = (WebElement) executeAndReturnNonStaleElement(key, helperForElement);
        String eText = (String) executeAndReturnNonStaleElement(key, helperForText);
        logger.info(String.format("text: %s", eText));
        if (eText.contains(text)) {
            logger.info(String.format("%s elementi '%s' textini iceriyor.", key, text));
            return;
        }

        String xpath = "./*[contains(text(), \"" + text + "\")]";
        try {
            logger.info("Alt elementlerde arama yapiliyor...");
            element.findElement(By.xpath(xpath));
        } catch (StaleElementReferenceException e) {
            logger.warn("Element was stale, retrying...");
            element = (WebElement) executeAndReturnNonStaleElement(key, helperForElement);
            element.findElement(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            fail(String.format("%s elementi veya child elementler '%s' textini icermiyor !", key, text));
        }
        logger.info(String.format("'%s' elementinin child elementlerinden biri veya birkaci '%s' textini iceriyor.", key, text));
    }

    @Step({"Verify <key> element is visible in <x> seconds at most",
            "<key> elementinin gorunur oldugunu en fazla <x> saniye icinde dogrula"})
    public void verifyVisibilityOfElement(String key, long seconds) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(ofSeconds(seconds)).pollingEvery(ofMillis(500))
                    .ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOfElementLocated(getElementInfoToBy(findElementInfoByKey(key))));
        } catch (TimeoutException ignored) {
            Assertions.fail(String.format("%s elementinin gorunur oldugu dogrulanamadi!", key));
        }
        logger.info(String.format("%s elementinin gorunur oldugu dogrulandi.", key));
    }

    @Step({"Verify <key> element is visible and clickable in <x> seconds at most",
            "<key> elementinin gorunur ve tiklanabilir oldugunu en fazla <x> saniye icinde dogrula"})
    public void verifyVisibilityAndClickableOfElement(String key, long seconds) {
        try {
            new FluentWait<>(driver)
                    .withTimeout(ofSeconds(seconds))
                    .pollingEvery(ofMillis(500))
                    .ignoring(NotFoundException.class)
                    .ignoring(NoSuchElementException.class)
                    .until(ExpectedConditions.elementToBeClickable(getElementInfoToBy(findElementInfoByKey(key))));
        } catch (TimeoutException ignored) {
            Assertions.fail(String.format("%s elementinin gorunur oldugu dogrulanamadi!", key));
        }
        logger.info(String.format("%s elementinin gorunur oldugu dogrulandi.", key));
    }

    /**
     * executes a js script to wait for the page to be loaded
     */
    public void waitForThePageToBeLoaded(long timeoutDurationMillis, long pollingDurationMillis) {
        boolean readyState = false;
        for (long waitCounter = 0; waitCounter <= timeoutDurationMillis; waitCounter += pollingDurationMillis) {
            readyState = (boolean) jsExecutor.executeScript("return document.readyState").equals("complete");
            if (readyState) {
                logger.info(String.format("Page was loaded in approximately %d milliseconds.", waitCounter));
                return;
            }
            waitForMilliseconds(pollingDurationMillis, false);
        }
        throw new TimeoutException("Page was not loaded in " + timeoutDurationMillis + " milliseconds.");
    }

    @Step("<key> elementinin yalnizca sayi icermedigini dogrula")
    public void verifyNotOnlyNumeric(String key) {
        WebElement e = findElement(key);
        String elementText = e.getText();
        try {
            Integer.parseInt(elementText);
            Assertions.fail("Hesap adi yalnizca sayi iceriyor!");
        } catch (Exception ex) {
            logger.info("Hesap adi yalnizca sayi icermiyor, dogrulama basarili.");
        }
    }

    @Step({"<text> textini <key> elemente tek tek yaz",
            "Write <text> to element <key> OnebyOne"})
    public void sendKeyOneByOne(String text, String key) throws InterruptedException {
        WebElement field = findElement(key);
        field.clear();
        if (!key.equals("")) {
            for (char ch : text.toCharArray())
                findElement(key).sendKeys(Character.toString(ch));
            Thread.sleep(10);
            logger.info(key + " elementine " + text + " texti karakterler tek tek girlilerek yazıldı.");
        }
    }

    public void javaScriptClicker(WebDriver driver, WebElement element) {
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                + "arguments[0].dispatchEvent(evt);", element);
    }

    @Step({"<key> li elementi bul, temizle ve <text> değerini yaz",
            "Find element by <key> clear and send keys <text>"})
    public void sendKeysByKey(String key, String text) {
        WebElement webElement = findElementWithKey(key);
        webElement.clear();
        webElement.sendKeys(text);
        logger.info(key + " alanina " + text + " degeri yazildi ");
    }
}