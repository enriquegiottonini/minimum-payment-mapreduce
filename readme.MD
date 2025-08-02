Given a set of product prices of the form {basePrice, tag, tag, ...} and a set of appliable discounts of the form {tag type amount}.
Return the minimum payment possible after applying the discounts to each price.

There are three types of discounts:
"1" a fixed amount is available as a final price.
"2" a fixed amount can be subtracted from base price.
"3" a percentage amount can subtracted from the base price.

An implementation is given inspired by map reduce mental model.
For each price EMIT is all possible prices after considering discounts.
For each set of possible prices EMIT the minimum.
Reduce all minimums by aggregating into a single value.

Usage:
```
mvnd test
```

Notes to myself for reproducing a similar project using Java.

You can scaffold a simple test-implementation maven project by using:
```
mvn archetype:generate \
-DgroupId=theia \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DinteractiveMode=false \
-DjavaCompilerVersion=23 \
-DartifactId=nubank-mapreduce
```
Might need to change to manually add the java version:
```
  <properties>
     <maven.compiler.source>23</maven.compiler.source>
     <maven.compiler.target>23</maven.compiler.target>
  </properties>
```

Use sdkman to use a mvn version (or mvnd) and java version. Zulu has good performance.
