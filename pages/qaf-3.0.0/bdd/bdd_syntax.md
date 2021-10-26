---
title: BDD syntax
sidebar: qaf_3_0_0-sidebar
permalink: qaf-3.0.0/bdd-syntax.html
summary: "QAF supports multiple BDD syntaxes."
folder: qaf-3.0.0
tags: [bdd]
---

QAF supports multiple BDD syntaxes with [meta-data](scenario-meta-data.html), [comment](bdd-comment.html) and [data-provider](maketest_data_driven.html) support. While using QAF, you can take advantage of each TestNG features, including data-providers, parallel execution [configuration](bdd-configuration.html) in different ways (groups/tests/methods), TestNG listeners.


## QAF BDD2 Syntax
BDD2 syntax is supported by the `BDDTestFactory2` which uses [BDDFileParser2](javadoc/com/qmetry/qaf/automation/step/client/text/BDDFileParser2.html). It is derived from QAF BDD, Jbehave and Gherkin. 
```
@group1
@author:Chirag Jayswal
@channel:["web","mobile"]
Feature: A feature is a collection of scenarios
 
Narrative:
	In order to communicate effectively to the business some functionality
	As a development team
	I want to use Behavior-Driven Development
     
Background: 
 
	Given a step that is executed before each scenario 

@smoke @TestcaseId:12345
Scenario:  A scenario is a collection of executable steps of different type
 
	Given step represents a precondition to an event
	When step represents the occurrence of the event
	Then step represents the outcome of the event

@datafie:resources/${env}/testdata.txt
@regression 
Scenario:  Another scenario exploring different combination using data-provider
 
	Given a "${precondition}"
	When an event occurs
	Then the outcome should "${be-captured}"    


@regression 
Scenario Outline:  Another scenario exploring different combination using examples
 
	Given a "${precondition}"
	When an event occurs
	Then the outcome should "${be-captured}"    
 
Examples: 
	|TestcaseId|precondition|be-captured|
	|123461|abc|be captured    |
	|123462|xyz|not be captured|


```

## QAF BDD Syntax
QAF BDD syntax is supported by the `BDDTestFactory`  which uses [BDDFileParser](javadoc/com/qmetry/qaf/automation/step/client/text/BDDFileParser.html).

```
Feature: A feature is a collection of scenarios
Meta-data: {'groups':['group1'], 'author':'Chirag Jayswal', 'channel':['web','mobile']}

Narrative:
	In order to communicate effectively to the business some functionality
	As a development team
	I want to use Behavior-Driven Development
     
Background: 
 
	Given a step that is executed before each scenario 

Scenario:  A scenario is a collection of executable steps of different type
Meta-data: {'groups':['smoke']}
 
	Given step represents a precondition to an event
	When step represents the occurrence of the event
	Then step represents the outcome of the event

Scenario:  Another scenario exploring different combination using data-provider
Meta-data: {'groups':['regression'],'datafie':'resources/${env}/testdata.txt'}
 
	Given a "${precondition}"
	When an event occurs
	Then the outcome should "${be-captured}" 

```

## Gherkin Syntax
Gherkin syntax is supported by the `GherkinScenarioFactory`  which uses [GherkinFileParser](javadoc/com/qmetry/qaf/automation/step/client/gherkin/GherkinFileParser.html).
QAF also supports parameters as argument and data-providers for examples, which are not currently available in Gherkin.

```
@group1
Feature: A feature is a collection of scenarios

     
Background: 
 
Given a step that is executed before each scenario 
 
@smoke
Scenario:  A scenario is a collection of executable steps of different type
 
Given step represents a precondition to an event
When step represents the occurrence of the event
Then step represents the outcome of the event

@regression
Scenario Outline:  Another scenario exploring different combinations using examples
 
Given a "<precondition>"
When an event occurs
Then the outcome should "<be-captured>"    
 
Examples: 
|precondition|be-captured|
|123461|abc|be captured    |
|123462|xyz|not be captured|

Scenario Outline:  Another scenario exploring different combinations using data provider
 
Given a "<precondition>"
When an event occurs
Then the outcome should "<be-captured>"    
 
Examples: {'datafile':'resources/${env}/testdata.txt'}

```