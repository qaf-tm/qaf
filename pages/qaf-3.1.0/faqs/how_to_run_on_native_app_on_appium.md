---
title: How to run on Native application on appium?
sidebar: faq_sidebar
permalink: qaf-3.1.0/how_to_run_on_native_app_on_appium.html
folder: qaf-3.1.0
---

**Run Native app on appium :**

**1. Appium server**

To install and setting up appium server please follow this link : [http://appium.io/slate/en/master/?java#](http://appium.io/slate/en/master/?java#)
   
**2. Appium Server Configuration**

In Appium set Application Path to ContactManager application
   
**3. Project Configuration**

application.properties file settings for Appium:

```properties
driver.name=appiumDriver
```

Appium server capabilities:

```properties
appium.additional.capabilities= {'browser_name':'','deviceName':'emulator-5554','platformName':'Android','automationName':'Appium','driverClass':'io.appium.java_client.android.AndroidDriver'}
```

User need to set additional capabilities as per application environment. Detailed list of appium capabilities you can find here: [Appium Server Capabilities](http://appium.io/slate/en/master/?java#appium-server-capabilities)
   
**4. Add appium java-client dependency in ivy.xml file**

```xml
<dependency org="io.appium" name="java-client" rev="2.2.0"/>
```
   
**5. Make sure appium server is started**
 

**Execution:**
Run the project
