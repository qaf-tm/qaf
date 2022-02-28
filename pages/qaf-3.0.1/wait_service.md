---
title: Wait Service
sidebar: qaf_3_0_1-sidebar
permalink: qaf-3.0.1/wait_service.html
folder: qaf-3.0.1
tags: [java,webelement,webdriver]
---

There is a listener that automatically injects wait if required before executing selenium command. However, one can utilize wait service which provides different wait methods. In test case method you can create object of WaitService and utilize different wait methods.
When using webdriver API for development different wait methods are available with extended webelement object itself.

 For example assuming that firstName is QAFExtendedWebElement and you want to wait for first name field to be present:
 
```java
firstName.waitForPresent();
```

Other examples of WaitServices methods.

```java
firstName.waitForPresent();
firstName.waitForVisible();
firstName.waitForSelected();
firstName.waitForEnabled();
firstName.waitForCssClass("enabled");
firstName.waitForCssStyle("display","none");
```

