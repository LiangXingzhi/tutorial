package com.ericsson.ct.cloud.nfvi.selenium;

import org.openqa.selenium.By;

/*
*/
public class IndexPage {

    static String defaultUrl = "http://localhost:8088/index";
    private Browser browser = new Browser(null, 3, 300);

    public void goTo() {
        String url = System.getProperty("com.ericsson.ct.cloud.nfvi.web.index.url");
        browser.get(url != null ? url : defaultUrl);
    }

    public void clickScaleOut() {
        browser.click(By.xpath("//td[contains(text(), 'Scale-Out vHSS VNF')]"));
    }

    public void clickNewInstance() {
        browser.click(By.xpath("//button[contains(text(),'Start a New Instance')]"));
        browser.refresh();
    }

    public String evalInstanceName() {
        String defaultValue = "Scale-Out VNF_" + System.currentTimeMillis() / 1000;
        return browser.eval(By.className("elWfmgrWidgets-WfStartForm-instanceName-input"), defaultValue);
    }

    public void clickSubmitInstance() {
        browser.click(By.xpath("//button[contains(text(),'Submit')]"));
    }

    public void clickInstance(String instanceName) {
        browser.click(By.xpath("//a[contains(text(), '" + instanceName + "')]"));
    }

    public void clickSelectVNFButton() {
        browser.click(By.cssSelector("button.ebSelect-header"));
    }

    public void clickAndSelectVNF() {
//		browser.click(By.cssSelector("div.ebComponentList-item"));
        browser.click(By.xpath("//div[contains(text(), 'vHSS01-1.19')]"));
    }

    public String evalVMQuantity() {
        return browser.eval(By.cssSelector("input.ebInput_width_xLong"), "2");
    }

    public void clickSubmitScaleOut() {
        browser.click(By.cssSelector("button.ebBtn_color_paleBlue"));
    }

}
