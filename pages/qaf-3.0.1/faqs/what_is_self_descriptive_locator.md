---
title: What is self-descriptive locator?
sidebar: faq_sidebar
permalink: qaf-3.0.1/what_is_self_descriptive_locator.html
folder: qaf-3.0.1
---


Locator is used for locating the element on the page. In addition, using self descriptive locator you can provide description of the locator/element as well. The description will be used in different assertion/verification messages.

```java

String LOGOUT_LINK_LOC = “{‘locator’:'css=a.logout’,'desc’='Logout link’}”;

```
