---
title: JSON Data Provider
sidebar: qaf_3_0_0-sidebar
permalink: qaf-3.0.0/json.html
tags: [datadriven]
folder: qaf-3.0.0
prev: maketest_data_driven.html
---
Following is the example of JSON data file used to provide test data. The test gets executed 3 times as there are 3 data set in data file.

**logintestdata.json**

```
[
    { "Username" : "admin" , "password":"123abc123" , "isvalid":false },
    { "Username" : "Admin" , "password":"test" , "isvalid":false },
    { "Username" : "admin" , "password":"Chirag2193" , "isvalid":true }
]
```

## Usage
### Java
```java
@QAFDataProvider(dataFile = "resources/data/logintestdata.json")

```
### BDD
```
Meta-data:{"dataFile":"resources/data/logintestdata.json"}
```

### BDD2
```
@dataFile:resources/data/logintestdata.json

```
Or

```
Examples: {"dataFile":"resources/data/logintestdata.json"}

```

### Gherkin
```
Examples: {"dataFile":"resources/data/logintestdata.json"}

```


