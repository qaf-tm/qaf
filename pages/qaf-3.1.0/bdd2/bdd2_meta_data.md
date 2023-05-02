---
title: BDD2 meta-data
sidebar: qaf_3_1_0-sidebar
permalink: qaf-3.1.0/bdd2-meta-data.html
summary: "Scenario metadata provides information to help manage a scenario.The meta-data are collected as part of the BDD parsing."
folder: qaf-3.1.0
tags: [bdd,scenario]
---
## Meta-data 
Scenario [metadata](scenario-meta-data.html) provides information to help categorize and manage scenarios. The meta-data are collected as part of the BDD parsing and made available for different uses, e.g. grouping scenario, setting data-provider. It can be used for scenario selection by providing [meta-data filter](scenario_metadatata_filter_include_exclude_prop.html).

There are predefined meta-key available to use which are listed in [here](scenario-meta-data.html#pre-defined-meta-data-for-bdd). In addition to predefined meta-key, you can have your custom meta-key to categorize scenarios as per AUT. You can choose whatever names are most appropriate for the information they are trying to convey.

```
	@description:Data driven test that uses csv file to provide data
	@group1 @group2
	@author: Chirag Jayswal
```
Meta-value can string, number, boolean or list.

```
	@TestID:12345
	@enabled:true
	@channel:['web','mobile']
``` 

Meta data can be provided with following elements:
 * Feature
 * Scenario
 * Scenario Outline 
 * Examples
 
Meta data provided to feature will be inherited by each scenario or scenario outline.