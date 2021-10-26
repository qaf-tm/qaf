---
title: Scenario
sidebar: qaf_3_0_0-sidebar
permalink: qaf-3.0.0/scenario.html
summary: "Scenario consist of meta-data and sequence of steps to represents a single test case. Each step can be started with standard BDD keywords [Given | When | Then | And | Having]. Refer list of available keywords."
folder: qaf-3.0.0
tags: [bdd,scenario]
---

Scenario consist of meta-data and sequence of steps to represents a single test case.
Each step can be started with standard BDD keywords.

## List of available keywords.
Below are available keywords taht can be used as prefix while calling step in bdd. You can define your own by providing [keyword synonym](BDD_Keywords_Synonyms.html)

|keywords|
|-------|
|Given| 
|When|
|Then|
|And|
|Having|

## Structure 

```
Scenario: <Name of the Scenario>
 
Meta-data: {<meta-key>:<meta-value>[,<meta-key>:<meta-value>]}
 
[Keyword] <first step description with parameters>
 
…
 
 [Keyword] <nth step description>
 
End
```
Want to iterate with set of test data from file? You got it with data-driven Scenario. 

## References
 - [meta-data](scenario-meta-data.html)
 - [make-test-data-driven](maketest_data_driven.html)
 - [comment in BDD](bdd-comment.html)

## Data-driven Scenario
You can iterate your scenario with set of test data. Refer [make test data driven](maketest_data_driven.html), any of the @QAFDataProvider property you can set as meta-data. Below example demonstrates data-driven feature  


```

SCENARIO: Data-driven Example 
META-DATA: {"dataFile":"resources/data/testdata.csv","description":"Data driven test that uses csv file to provide data"}
	Given I am on fruits and colors activity
	When i select '${fruit}'
	Then the color should be '${color}'

END

```

Below is csv data file and first row is column names.

**testdata.csv**

```csv
fruit,color
grapes,green
banana,yellow

```


If you want to use custom TestNG data provider, you need to provide data provider name and class in meta-data.

```
SCENARIO: Custom Data provider Example 
META-DATA: {"dataProvider":"my-custom-dp", "dataProviderClass":"my.project.impl.CustomDataProvider","description":"Data driven test that uses custom data provider"}
	Given I am on fruits and colors activity
	When i select '${fruit}'
	Then the color should be '${color}'

END

```

 
```java

package my.project.impl;

import java.util.Map;
import org.testng.annotations.DataProvider;
import org.testng.collections.Maps;

/**
 * @author chirag.jayswal
 *
 */
public class CustomDataProvider {
	
	@DataProvider(name="my-custom-dp")
	public static Object[][] dataProviderForBDD(){
		
		Map<Object, Object> rec1 = Maps.newHashMap();
		m.put("fruit", "grapes");
		m.put("color", "green");
		
		Map<Object, Object> rec2 = Maps.newHashMap();
		m.put("fruit", "banana");
		m.put("color", "yellow");
		
		return new Object[][] {% raw %}{{rec1},{rec2}}{% endraw %} ;
	}
}

```