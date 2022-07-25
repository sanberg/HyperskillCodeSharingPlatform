<html>
<head>
    <title>Code</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<span id="load_date">${snippet.date}</span>
<pre id="code_snippet">
    <code>${snippet.code}</code>
</pre>
<#if snippet.viewsLimited>
<span id="views_restriction">Views restriction: ${snippet.views}</span>
<br><br>
</#if>
<#if snippet.timeLimited>
<span id="time_restriction">Time restriction: ${snippet.time}</span>
</#if>
</body>
</html>