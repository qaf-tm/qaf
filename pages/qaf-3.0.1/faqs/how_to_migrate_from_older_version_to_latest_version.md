---
title: How to migrate from older version to latest version
sidebar: faq_sidebar
permalink: qaf-3.0.1/how_to_migrate_from_older_version.html
folder: qaf-3.0.1
---

Follow below steps to use QAF 2.1.8:

1. Rename below properties.
    * selenium.defaultBrowser-->driver.name
    * selenium.server-->remote.server
    * selenium.port-->remote.port
2. Single quote (‘) or Double quote (“) is compulsory for Test Step string argument
3. Copy dashboard folder and dashboard.html file for latest report. [Github Repo](https://github.com/qmetry/qaf-report)
4. As package structure has been changed from `com.infostretch` to `com.qmetry.qaf`, refactoring is required.One can use normal replace function of editor as well.