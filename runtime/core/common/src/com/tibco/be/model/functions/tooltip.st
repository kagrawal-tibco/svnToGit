<html>
<head>
<title>$signature$</title>
<style type="text/css">
* {border:0;padding:0;margin:0;}
table.descr {margin:10px 10px 0 10px;}
table.params {margin:0 10px 10px 10px;}
td.name,.function td {font-style:italic; font-size:14px;}
td {font-size:11px;font-family:serif;}
td,th {padding:1px;}
th,.paramsheaders td {width:100px;font-size:12px;font-family:sans-serif;letter-spacing:1px;text-align:left;}
.paramsheaders td {text-decoration:underline;}
    </style>
</head>
<body>
<table class="descr" width="700" cellspacing="1" cellpadding="2">
<tr class="function"><th width="100" align="left">Function:</th><td><code>$fullName$</code></td></tr>
<tr><th align="left">Signature:</th><td><code>$signature$</code></td></tr>
<tr><th align="left">Description:</th><td>$description$</td></tr>
</table>
<table class="params" width="700" cellspacing="1" cellpadding="2">
$if(parameters)$
<tr><th width="100" align="left">Parameters:</th></tr>
<tr class="paramsheaders">
<td class="name" align="left"><u>Name</u></td>
<td class="type" align="left"><u>Type</u></td>
<td class="descr" align="left" width="500"><u>Description</u></td></tr>
$else$
<tr><th width="100" align="left">No Parameters</th></tr>
$endif$
$parameters:{ p | <tr><td class="name"><code>$p.name$</code></td><td><code>$p.type$</code></td><td>$p.description$</td></tr> }$
$if(return)$
<tr><th align="left">Returns:</th><td><code>$return.type$</code></td><td width="500">$return.description$</td></tr>
$endif$
$if(cautions)$
<tr><th align="left">Cautions:</th><td colspan="2">$cautions$</td></tr>
$endif$
$if(see)$
<tr><th align="left">See also:</th><td colspan="2">$see$</td></tr>
$endif$
$if(example)$
<tr><th align="left">Example:</th><td colspan="2">$example$</td></tr>
$endif$
</table>
</body>
</html>
