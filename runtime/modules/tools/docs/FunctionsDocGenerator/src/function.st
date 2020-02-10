<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>     $categoryFullName$.$function.name$()
</title>
<style type="text/css" media="all">@import "$pathToCatalogRoot$/../functions.css";</style>
<meta http-equiv="Content-type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Language" content="en-us" />
<link rel="Shortcut Icon" href="icon.gif" type="$pathToCatalogRoot$/../image/gif" />
</head>
<body class="function">

<div class="header">
<ul class="ancestors">
<li class="functions"><a href="$pathToCatalogRoot$/../index.html">$productName$</a></li>
<li class="functions"><a href="$pathToCatalogRoot$/$catalogFileName$">$catalogName$</a></li>
$ancestors:{ a |
<li class="functions"><a href="$a.link$">$a.name$</a></li> }$
</ul>
</div>

<div class="pageBody">

<h1><span class="category">$categoryFullName$</span><span class="dot">.</span
   ><span class="name">$function.name$()
</span></h1>

<div class="item Signature">
<h2>             Signature
</h2>

<div class="content"><code>$function.signature$</code></div>
</div>

<div class="item Domain">
<h2>             Domain
</h2>
<div class="content">$function.domain; separator=", "$</div>
</div>

$if(function.description)$
<div class="item Description">
<h2>             Description
</h2>
<div class="content">$function.description$</div>
</div>
$endif$

<div class="item Parameters">
<h2>             Parameters
</h2>
<div class="content">
$if(parameters)$
<table class="Parameters" cellspacing="0">
<tr><th class="Name">Name</th><th class="Type">Type</th><th class="Description">Description</th></tr>
$parameters:{ p |
<tr><td class="Name"><code>$p.name$</code></td><td class="Type"><code>$p.type$</code></td><td class="Description">$p.description$</td></tr>}$
</table>
$else$ None.
$endif$
</div>
</div>

<div class="item Returns">
<h2>             Returns
</h2>
<div class="content">
$if(return)$    
<table class="Parameters" cellspacing="0">
<tr><th class="Type">Type</th><th class="Description">Description</th></tr>
<tr><td class="Type"><code>$return.type$</code></td><td class="Description">$return.description$</td></tr>
</table>
$else$ None.
$endif$
</div>
</div>

$if(function.cautions)$
<div class="item Cautions">
<h2>             Cautions
</h2>
<div class="content">$function.cautions$</div>
</div>
$endif$

$if(function.see)$
<div class="item SeeAlso">
<h2>             See Also
</h2>
<div class="content">$function.see$</div>
</div>
$endif$

$if(function.example)$
<div class="item Example">
<h2>             Example
</h2>
<div class="content">$function.example$</div>
</div>
$endif$

<div class="footer">
<p></p>$productName$<br />
$copyright$</p>
</div>

</body>
</html>
