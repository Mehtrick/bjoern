= ${feature}
:toc:

<#if background??>
== Background
[cols="10%,90%"]
|===
<#list background.given as given>
<#if given?counter==1>|*Given* |${given.primitiveStatement}
<#else>>|*And* |${given.primitiveStatement}
</#if>
</#list>
|===
</#if>


<#list scenarios as scenario>
== Scenario: ${scenario.name}
[cols="10%,90%"]
|===
<#list scenario.given as given>
<#if given?counter==1>|*Given* |${given.primitiveStatement}
<#else>>|*And* |${given.primitiveStatement}
</#if>
</#list>
<#list scenario.when as when>
<#if when?counter==1>|*When* |${when.primitiveStatement}
<#else>>|*And* |${when.primitiveStatement}
</#if>
</#list>
<#list scenario.then as then>
<#if then?counter==1>|*Then* |${then.primitiveStatement}
<#else>>|*And* |${then.primitiveStatement}
</#if>
</#list>
|===

</#list>