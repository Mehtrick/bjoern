= ${feature}
:toc:

<#if background??>
== Background
[cols="10%,90%"]
|===
<#list background.given as given>
<#if given?counter==1>|*Given* |${given.asciidocEscapedPrimitveStatement}
<#else>>|*And* |${given.asciidocEscapedPrimitveStatement}
</#if>
</#list>
|===
</#if>


<#list scenarios as scenario>
== Scenario: ${scenario.name}
[cols="10%,90%"]
|===
<#if scenario.given??>
<#list scenario.given as given>
<#if given?counter==1>|*Given* |${given.asciidocEscapedPrimitveStatement}
<#else>>|*And* |${given.asciidocEscapedPrimitveStatement}
</#if>
</#list>
</#if>
<#if scenario.when??>
<#list scenario.when as when>
<#if when?counter==1>|*When* |${when.asciidocEscapedPrimitveStatement}
<#else>>|*And* |${when.asciidocEscapedPrimitveStatement}
</#if>
</#list>
</#if>
<#if scenario.then??>
<#list scenario.then as then>
<#if then?counter==1>|*Then* |${then.asciidocEscapedPrimitveStatement}
<#else>>|*And* |${then.asciidocEscapedPrimitveStatement}
</#if>
</#list>
</#if>
|===

</#list>